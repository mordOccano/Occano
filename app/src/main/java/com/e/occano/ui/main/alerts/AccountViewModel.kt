package com.e.occano.ui.main.alerts

import android.util.Log
import com.e.occano.di.main.MainScope
import com.e.occano.models.AccountProperties
import com.e.occano.repository.main.AccountRepositoryImpl
import com.e.occano.session.SessionManager
import com.e.occano.ui.BaseViewModel
import com.e.occano.ui.main.alerts.state.AccountStateEvent
import com.e.occano.ui.main.alerts.state.AccountViewState
import com.e.occano.utils.*
import com.e.occano.utils.ErrorHandling.Companion.INVALID_STATE_EVENT
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@ExperimentalCoroutinesApi
@FlowPreview
@MainScope
class AccountViewModel
@Inject
constructor(
    val sessionManager: SessionManager,
    val accountRepository: AccountRepositoryImpl
)
    : BaseViewModel<AccountViewState>()
{

    override fun handleNewData(data: AccountViewState) {
        data.accountProperties?.let { accountProperties ->
            setAccountPropertiesData(accountProperties)
        }
    }

    override fun setStateEvent(stateEvent: StateEvent) {
        sessionManager.cachedToken.value?.let { authToken ->
            val job: Flow<DataState<AccountViewState>> = when(stateEvent){

                is AccountStateEvent.GetAccountPropertiesEvent -> {
                    accountRepository.getAccountProperties(
                        stateEvent = stateEvent,
                        authToken = authToken
                    )
                }

                is AccountStateEvent.UpdateAccountPropertiesEvent -> {
                    accountRepository.saveAccountProperties(
                        stateEvent = stateEvent,
                        authToken = authToken,
                        email = stateEvent.email,
                        username = stateEvent.username
                    )
                }

                is AccountStateEvent.ChangePasswordEvent -> {
                    accountRepository.updatePassword(
                        stateEvent = stateEvent,
                        authToken = authToken,
                        currentPassword = stateEvent.currentPassword,
                        newPassword = stateEvent.newPassword,
                        confirmNewPassword = stateEvent.confirmNewPassword
                    )
                }

                else -> {
                    flow{
//                        emit(
//                            DataState.error<>(
//                                response = Response(
//                                    message = INVALID_STATE_EVENT,
//                                    uiComponentType = UIComponentType.None(),
//                                    messageType = MessageType.Error()
//                                ),
//                                stateEvent = stateEvent
//                            )
//                        )
                    }
                }
            }
            launchJob(stateEvent, job)
        }
    }

    fun setAccountPropertiesData(accountProperties: AccountProperties){
        val update = getCurrentViewStateOrNew()
        if(update.accountProperties == accountProperties){
            return
        }
        update.accountProperties = accountProperties
        setViewState(update)
    }

    override fun initNewViewState(): AccountViewState {
        return AccountViewState()
    }

    fun logout(){
        sessionManager.logout()
    }

    override fun onCleared() {
        super.onCleared()
        cancelActiveJobs()
    }

}

