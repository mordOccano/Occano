package com.e.occano.repository.auth

import android.content.SharedPreferences
import android.util.Log
import com.e.occano.api.auth.ApiAuthService
import com.e.occano.api.auth.network_responses.LoginAsVesselResponse
import com.e.occano.api.auth.network_responses.LoginResponse
import com.e.occano.api.auth.network_responses.RegistrationResponse
import com.e.occano.di.auth.AuthScope
import com.e.occano.models.AccountProperties
import com.e.occano.models.AuthToken
import com.e.occano.persistence.AccountPropertiesDao
import com.e.occano.persistence.AuthTokenDao
import com.e.occano.repository.buildError
import com.e.occano.repository.safeApiCall
import com.e.occano.repository.safeCacheCall
import com.e.occano.session.SessionManager
import com.e.occano.ui.auth.state.AuthViewState
import com.e.occano.ui.auth.state.LoginAsVesselFields
import com.e.occano.ui.auth.state.LoginFields
import com.e.occano.ui.auth.state.RegistrationFields
import com.e.occano.utils.*
import com.e.occano.utils.ErrorHandling.Companion.ERROR_SAVE_ACCOUNT_PROPERTIES
import com.e.occano.utils.ErrorHandling.Companion.ERROR_SAVE_AUTH_TOKEN
import com.e.occano.utils.ErrorHandling.Companion.GENERIC_AUTH_ERROR
import com.e.occano.utils.ErrorHandling.Companion.INVALID_CREDENTIALS
import com.e.occano.utils.SuccessHandling.Companion.RESPONSE_CHECK_PREVIOUS_AUTH_USER_DONE
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@FlowPreview
@AuthScope
class AuthRepositoryImpl
@Inject
constructor(
    val authTokenDao: AuthTokenDao,
    val accountPropertiesDao: AccountPropertiesDao,
    val apiAuthService: ApiAuthService,
    val sessionManager: SessionManager,
    val sharedPreferences: SharedPreferences,
    val sharedPrefsEditor: SharedPreferences.Editor
): AuthRepository
{

    private val TAG: String = "AppDebug"

    override fun attemptLogin(
        stateEvent: StateEvent,
        email: String,
        password: String
    ): Flow<DataState<AuthViewState>> = flow {

        val loginFieldErrors = LoginFields(email, password).isValidForLogin()
        if(loginFieldErrors.equals(LoginFields.LoginError.none())){
            val apiResult = safeApiCall(IO){
                apiAuthService.login(email, password)
            }
            emit(
                object: ApiResponseHandler<AuthViewState, LoginResponse>(
                    response = apiResult,
                    stateEvent = stateEvent
                ) {
                    override suspend fun handleSuccess(resultObj: LoginResponse): DataState<AuthViewState> {

                        Log.d(TAG, "handleSuccess ")

                        // Incorrect login credentials counts as a 200 response from server, so need to handle that
                        if(resultObj.response.equals(GENERIC_AUTH_ERROR)){
                            return DataState.error(
                                response = Response(
                                    INVALID_CREDENTIALS,
                                    UIComponentType.Dialog(),
                                    MessageType.Error()
                                ),
                                stateEvent = stateEvent
                            )
                        }
                        accountPropertiesDao.insertOrIgnore(
                            AccountProperties(
                                resultObj.pk,
                                resultObj.email,
                                ""
                            )
                        )

                        // will return -1 if failure
                        val authToken = AuthToken(
                            resultObj.pk,
                            resultObj.token
                        )
                        val result = authTokenDao.insert(authToken)
                        if(result < 0){
                            return DataState.error(
                                response = Response(
                                    ERROR_SAVE_AUTH_TOKEN,
                                    UIComponentType.Dialog(),
                                    MessageType.Error()
                                ),
                                stateEvent = stateEvent
                            )
                        }
                        saveAuthenticatedUserToPrefs(email)

                        return DataState.data(
                            data = AuthViewState(
                                authToken = authToken
                            ),
                            stateEvent = stateEvent,
                            response = null
                        )
                    }

                }.getResult()
            )
        }
        else{
            Log.d(TAG, "emitting error: ${loginFieldErrors}")
//            emit(
//                buildError(
//                    loginFieldErrors,
//                    UIComponentType.Dialog(),
//                    stateEvent
//                )
//            )
        }
    }

    override fun attemptRegistration(
        stateEvent: StateEvent,
        email: String,
        username: String,
        password: String,
        confirmPassword: String
    ): Flow<DataState<AuthViewState>> = flow {
        val registrationFieldErrors = RegistrationFields(email, username, password, confirmPassword).isValidForRegistration()
        if(registrationFieldErrors.equals(RegistrationFields.RegistrationError.none())){

            val apiResult = safeApiCall(IO){
                apiAuthService.register(
                    email,
                    username,
                    password,
                    confirmPassword
                )
            }
            emit(
                object: ApiResponseHandler<AuthViewState, RegistrationResponse>(
                    response = apiResult,
                    stateEvent = stateEvent
                ){
                    override suspend fun handleSuccess(resultObj: RegistrationResponse): DataState<AuthViewState> {
                        if(resultObj.response.equals(GENERIC_AUTH_ERROR)){
                            return DataState.error(
                                response = Response(
                                    resultObj.errorMessage,
                                    UIComponentType.Dialog(),
                                    MessageType.Error()
                                ),
                                stateEvent = stateEvent
                            )
                        }
                        val result1 = accountPropertiesDao.insertAndReplace(
                            AccountProperties(
                                resultObj.pk,
                                resultObj.email,
                                resultObj.username
                            )
                        )
                        // will return -1 if failure
                        if(result1 < 0){
                            return DataState.error(
                                response = Response(
                                    ERROR_SAVE_ACCOUNT_PROPERTIES,
                                    UIComponentType.Dialog(),
                                    MessageType.Error()
                                ),
                                stateEvent = stateEvent
                            )
                        }

                        // will return -1 if failure
                        val authToken = AuthToken(
                            resultObj.pk,
                            resultObj.token
                        )
                        val result2 = authTokenDao.insert(authToken)
                        if(result2 < 0){
                            return DataState.error(
                                response = Response(
                                    ERROR_SAVE_AUTH_TOKEN,
                                    UIComponentType.Dialog(),
                                    MessageType.Error()
                                ),
                                stateEvent = stateEvent
                            )
                        }
                        saveAuthenticatedUserToPrefs(email)
                        return DataState.data(
                            data = AuthViewState(
                                authToken = authToken
                            ),
                            stateEvent = stateEvent,
                            response = null
                        )
                    }
                }.getResult()
            )

        }
        else{
//            emit(
//                buildError(
//                    registrationFieldErrors,
//                    UIComponentType.Dialog(),
//                    stateEvent
//                )
//            )
        }

    }

    override fun attemptLoginAsVessel(
        stateEvent: StateEvent,
        vessel: String?,
        m_e: String?,
        displacement_engine: String?,
        Number_of_cylinders: Int,
        Stroke_bore_ratio: String?,
        Diameter_of_piston_in_cm: String?,
        Concept: String?,
        Design: String?,
        IMO: String?,
        AIS_Vessel_Type: String?,
        Year_Built: String?,
        Length_Overall: String?,
        Breadth_Extreme: String?,
        Deadweight: String?,
        Gross_Tonnage: String?
    ): Flow<DataState<AuthViewState>> = flow{

        val loginAsVesselFieldErrors = LoginAsVesselFields(
            vessel,
            m_e,
            displacement_engine,
            Number_of_cylinders,
            Stroke_bore_ratio,
            Diameter_of_piston_in_cm,
            Concept,
            Design,
            IMO,
            AIS_Vessel_Type,
            Year_Built,
            Length_Overall,
            Breadth_Extreme,
            Deadweight,
            Gross_Tonnage
                ).isValidForVesselLogin()
        if(loginAsVesselFieldErrors.equals(LoginAsVesselFields.LoginAsVesselError.none())){

            val apiResult = safeApiCall(IO){
                apiAuthService.loginAsVessel(
                    vessel!!,
                    m_e!!,
                    displacement_engine!!,
                    Number_of_cylinders!!,
                    Stroke_bore_ratio!!,
                    Diameter_of_piston_in_cm!!,
                    Concept!!,
                    Design!!,
                    IMO!!,
                    AIS_Vessel_Type!!,
                    Year_Built!!,
                    Length_Overall!!,
                    Breadth_Extreme!!,
                    Deadweight!!,
                    Gross_Tonnage!!
                )
            }
            emit(
                object: ApiResponseHandler<AuthViewState, LoginAsVesselResponse>(
                    response = apiResult,
                    stateEvent = stateEvent
                ){
                    override suspend fun handleSuccess(resultObj: LoginAsVesselResponse): DataState<AuthViewState> {
                        if(resultObj.response.equals(GENERIC_AUTH_ERROR)){
                            return DataState.error(
                                response = Response(
                                    resultObj.errorMessage,
                                    UIComponentType.Dialog(),
                                    MessageType.Error()
                                ),
                                stateEvent = stateEvent
                            )
                        }
                        val result1 = accountPropertiesDao.insertAndReplace(
                            AccountProperties(
                                resultObj.pk,
                                resultObj.IMO,
                                resultObj.vessel
                            )
                        )
                        // will return -1 if failure
                        if(result1 < 0){
                            return DataState.error(
                                response = Response(
                                    ERROR_SAVE_ACCOUNT_PROPERTIES,
                                    UIComponentType.Dialog(),
                                    MessageType.Error()
                                ),
                                stateEvent = stateEvent
                            )
                        }

                        // will return -1 if failure
                        val authToken = AuthToken(
                            resultObj.pk,
                            resultObj.token
                        )
                        val result2 = authTokenDao.insert(authToken)
                        if(result2 < 0){
                            return DataState.error(
                                response = Response(
                                    ERROR_SAVE_AUTH_TOKEN,
                                    UIComponentType.Dialog(),
                                    MessageType.Error()
                                ),
                                stateEvent = stateEvent
                            )
                        }
                        return DataState.data(
                            data = AuthViewState(
                                authToken = authToken
                            ),
                            stateEvent = stateEvent,
                            response = null
                        )
                    }
                }.getResult()
            )

        }
        else{
//            emit(
//                buildError(
//                    registrationFieldErrors,
//                    UIComponentType.Dialog(),
//                    stateEvent
//                )
//            )
        }

    }



    override fun checkPreviousAuthUser(
        stateEvent: StateEvent
    ): Flow<DataState<AuthViewState>> = flow {
        Log.d(TAG, "checkPreviousAuthUser: ")
        val previousAuthUserEmail: String? = sharedPreferences.getString(PreferenceKeys.PREVIOUS_AUTH_USER, null)

        if(previousAuthUserEmail.isNullOrBlank()){
            Log.d(TAG, "checkPreviousAuthUser: No previously authenticated user found.")
            emit(returnNoTokenFound(stateEvent))
        }
        else{
            val apiResult = safeCacheCall(IO){
                accountPropertiesDao.searchByEmail(previousAuthUserEmail)
            }
            emit(
                object: CacheResponseHandler<AuthViewState, AccountProperties>(
                    response = apiResult,
                    stateEvent = stateEvent
                ){
                    override suspend fun handleSuccess(resultObj: AccountProperties): DataState<AuthViewState> {

                        if(resultObj.pk > -1){
                            authTokenDao.searchByPk(resultObj.pk).let { authToken ->
                                if(authToken != null){
                                    if(authToken.token != null){
                                        return DataState.data(
                                            data = AuthViewState(
                                                authToken = authToken
                                            ),
                                            response = null,
                                            stateEvent = stateEvent
                                        )
                                    }
                                }
                            }
                        }
                        Log.d(TAG, "createCacheRequestAndReturn: AuthToken not found...")
                        return DataState.error(
                            response = Response(
                                RESPONSE_CHECK_PREVIOUS_AUTH_USER_DONE,
                                UIComponentType.None(),
                                MessageType.Error()
                            ),
                            stateEvent = stateEvent
                        )
                    }
                }.getResult()
            )
        }
    }

    override fun saveAuthenticatedUserToPrefs(email: String) {
        sharedPrefsEditor.putString(PreferenceKeys.PREVIOUS_AUTH_USER, email)
        sharedPrefsEditor.apply()
    }

    override fun returnNoTokenFound(
        stateEvent: StateEvent
    ): DataState<AuthViewState> {

        return DataState.error(
            response = Response(
                RESPONSE_CHECK_PREVIOUS_AUTH_USER_DONE,
                UIComponentType.None(),
                MessageType.Error()
            ),
            stateEvent = stateEvent
        )
    }



}