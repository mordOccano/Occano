package com.e.occano.repository.main

import android.util.Log
import com.e.occano.api.GenericResponse
import com.e.occano.api.main.ApiMainService
import com.e.occano.di.main.MainScope
import com.e.occano.models.AccountProperties
import com.e.occano.models.AuthToken
import com.e.occano.persistence.AccountPropertiesDao
import com.e.occano.repository.NetworkBoundResource
import com.e.occano.repository.safeApiCall
import com.e.occano.session.SessionManager
import com.e.occano.ui.main.alerts.state.AccountViewState
import com.e.occano.utils.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@FlowPreview
@MainScope
class AccountRepositoryImpl
@Inject
constructor(
    val apiMainService: ApiMainService,
    val accountPropertiesDao: AccountPropertiesDao,
    val sessionManager: SessionManager
): AccountRepository
{

    private val TAG: String = "AppDebug"

    override fun getAccountProperties(
        authToken: AuthToken,
        stateEvent: StateEvent
    ): Flow<DataState<AccountViewState>> {
        return object: NetworkBoundResource<AccountProperties, AccountProperties, AccountViewState>(
            dispatcher = IO,
            stateEvent = stateEvent,
            apiCall = {
                apiMainService
                    .getAccountProperties("Token ${authToken.token!!}")
            },
            cacheCall = {
                accountPropertiesDao.searchByPk(authToken.account_pk!!)
            }

        ){
            override suspend fun updateCache(networkObject: AccountProperties) {
                Log.d(TAG, "updateCache: ${networkObject} ")
                accountPropertiesDao.updateAccountProperties(
                    networkObject.pk,
                    networkObject.email,
                    networkObject.username
                )
            }

            override fun handleCacheSuccess(
                resultObj: AccountProperties
            ): DataState<AccountViewState> {
                return DataState.data(
                    response = null,
                    data = AccountViewState(
                        accountProperties = resultObj
                    ),
                    stateEvent = stateEvent
                )
            }

        }.result
    }

    override fun saveAccountProperties(
        authToken: AuthToken,
        email: String,
        username: String,
        stateEvent: StateEvent
    ) = flow{
        val apiResult = safeApiCall(IO){
            apiMainService.saveAccountProperties(
                "Token ${authToken.token!!}",
                email,
                username
            )
        }
        emit(
            object: ApiResponseHandler<AccountViewState, GenericResponse>(
                response = apiResult,
                stateEvent = stateEvent
            ){
                override suspend fun handleSuccess(
                    resultObj: GenericResponse
                ): DataState<AccountViewState> {

                    val updatedAccountProperties = apiMainService
                        .getAccountProperties("Token ${authToken.token!!}")

                    accountPropertiesDao.updateAccountProperties(
                        pk = updatedAccountProperties.pk,
                        email = updatedAccountProperties.email,
                        username = updatedAccountProperties.username
                    )

                    return DataState.data(
                        data = null,
                        response = Response(
                            message = resultObj.response,
                            uiComponentType = UIComponentType.Toast(),
                            messageType = MessageType.Success()
                        ),
                        stateEvent = stateEvent
                    )
                }

            }.getResult()
        )
    }

    override fun updatePassword(
        authToken: AuthToken,
        currentPassword: String,
        newPassword: String,
        confirmNewPassword: String,
        stateEvent: StateEvent
    ) = flow {
        val apiResult = safeApiCall(IO){
            apiMainService.updatePassword(
                "Token ${authToken.token!!}",
                currentPassword,
                newPassword,
                confirmNewPassword
            )
        }
        emit(
            object: ApiResponseHandler<AccountViewState, GenericResponse>(
                response = apiResult,
                stateEvent = stateEvent
            ){
                override suspend fun handleSuccess(
                    resultObj: GenericResponse
                ): DataState<AccountViewState> {

                    return DataState.data(
                        data = null,
                        response = Response(
                            message = resultObj.response,
                            uiComponentType = UIComponentType.Toast(),
                            messageType = MessageType.Success()
                        ),
                        stateEvent = stateEvent
                    )
                }
            }.getResult()
        )
    }

}