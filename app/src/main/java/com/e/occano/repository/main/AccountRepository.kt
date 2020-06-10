package com.e.occano.repository.main

import com.e.occano.di.main.MainScope
import com.e.occano.models.AccountProperties
import com.e.occano.models.AuthToken
import com.e.occano.ui.main.alerts.state.AccountViewState
import com.e.occano.utils.DataState
import com.e.occano.utils.StateEvent
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow

@FlowPreview
@MainScope
interface AccountRepository {

    fun getAccountProperties(
        authToken: AuthToken,
        stateEvent: StateEvent
    ): Flow<DataState<AccountViewState>>

    fun saveAccountProperties(
        authToken: AuthToken,
        email: String,
        username: String,
        stateEvent: StateEvent
    ): Flow<DataState<AccountViewState>>

    fun updatePassword(
        authToken: AuthToken,
        currentPassword: String,
        newPassword: String,
        confirmNewPassword: String,
        stateEvent: StateEvent
    ): Flow<DataState<AccountViewState>>
}