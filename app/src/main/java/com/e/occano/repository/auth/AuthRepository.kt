package com.e.occano.repository.auth

import com.e.occano.di.auth.AuthScope
import com.e.occano.models.Ship
import com.e.occano.ui.auth.state.AuthViewState
import com.e.occano.utils.DataState
import com.e.occano.utils.StateEvent
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow

@FlowPreview
@AuthScope
interface AuthRepository {

    fun attemptLogin(
        stateEvent: StateEvent,
        email: String,
        password: String
    ): Flow<DataState<AuthViewState>>

    fun attemptRegistration(
        stateEvent: StateEvent,
        email: String,
        username: String,
        password: String,
        confirmPassword: String
    ): Flow<DataState<AuthViewState>>

    fun checkPreviousAuthUser(
        stateEvent: StateEvent
    ): Flow<DataState<AuthViewState>>

    fun saveAuthenticatedUserToPrefs(email: String)

    fun returnNoTokenFound(
        stateEvent: StateEvent
    ): DataState<AuthViewState>

    fun attemptLoginAsVessel(
        stateEvent: StateEvent,
        vessel: String?,
        m_e: String?,
        displacement_engine: String? ,
        Number_of_cylinders: Int = 0,
        Stroke_bore_ratio: String? ,
        Diameter_of_piston_in_cm: String? ,
        Concept: String?,
        Design: String?,
        IMO: String?,
        AIS_Vessel_Type	: String?,
        Year_Built: String?,
        Length_Overall: String? ,
        Breadth_Extreme: String?,
        Deadweight: String?,
        Gross_Tonnage: String?
    ): Flow<DataState<AuthViewState>>


}

