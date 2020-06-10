package com.e.occano.ui.auth.state

import android.os.Parcelable
import com.e.occano.models.AuthToken
import kotlinx.android.parcel.Parcelize

// hold all the fields of the view
const val AUTH_VIEW_STATE_BUNDLE_KEY = "com.codingwithmitch.openapi.ui.auth.state.AuthViewState"

@Parcelize
data class AuthViewState(
    var registrationFields: RegistrationFields? = null,

    var loginFields: LoginFields? = null,

    var authToken: AuthToken? = null,

    var loginAsVesselFields: LoginAsVesselFields? = null

) : Parcelable


@Parcelize
data class RegistrationFields(
    var registration_email: String? = null,
    var registration_username: String? = null,
    var registration_password: String? = null,
    var registration_confirm_password: String? = null
) : Parcelable {

    class RegistrationError {
        companion object{

            fun mustFillAllFields(): String{
                return "All fields are required."
            }

            fun passwordsDoNotMatch(): String{
                return "Passwords must match."
            }

            fun none():String{
                return "None"
            }

        }
    }

    fun isValidForRegistration(): String{
        if(registration_email.isNullOrEmpty()
            || registration_username.isNullOrEmpty()
            || registration_password.isNullOrEmpty()
            || registration_confirm_password.isNullOrEmpty()){
            return RegistrationError.mustFillAllFields()
        }

        if(!registration_password.equals(registration_confirm_password)){
            return RegistrationError.passwordsDoNotMatch()
        }
        return RegistrationError.none()
    }
}

@Parcelize
data class LoginFields(
    var login_email: String? = null,
    var login_password: String? = null
) : Parcelable {
    class LoginError {

        companion object{

            fun mustFillAllFields(): String{
                return "You can't login without an email and password."
            }

            fun none():String{
                return "None"
            }

        }
    }
    fun isValidForLogin(): String{

        if(login_email.isNullOrEmpty()
            || login_password.isNullOrEmpty()){

            return LoginError.mustFillAllFields()
        }
        return LoginError.none()
    }

    override fun toString(): String {
        return "LoginState(email=$login_email, password=$login_password)"
    }
}


@Parcelize
data class LoginAsVesselFields(
    var vessel: String? = null,
    var m_e: String? = null,
    var displacement_engine: String? = null,
    var Number_of_cylinders: Int? = null,
    var Stroke_bore_ratio: String? = null,
    var Diameter_of_piston_in_cm: String? = null,
    var Concept: String? = null,
    var Design: String? = null,
    var IMO: String? = null,
    var AIS_Vessel_Type	: String? = null,
    var Year_Built: String? = null,
    var Length_Overall: String? = null,
    var Breadth_Extreme: String? = null,
    var Deadweight: String? = null,
    var Gross_Tonnage: String? = null
):Parcelable{
    class LoginAsVesselError{
        companion object{

            fun mustFillAllFields(): String{
                return "some details are missing."
            }

            fun none():String{
                return "None"
            }

        }
    }
    fun isValidForVesselLogin(): String{

        if(vessel.isNullOrEmpty()||
        m_e.isNullOrEmpty()||
        displacement_engine.isNullOrEmpty()||
        Number_of_cylinders != 0||
        Stroke_bore_ratio.isNullOrEmpty()||
        Diameter_of_piston_in_cm.isNullOrEmpty()||
        Concept.isNullOrEmpty()||
        Design.isNullOrEmpty()||
        IMO.isNullOrEmpty()||
        AIS_Vessel_Type	.isNullOrEmpty()||
        Year_Built.isNullOrEmpty()||
        Length_Overall.isNullOrEmpty()||
        Breadth_Extreme.isNullOrEmpty()||
        Deadweight.isNullOrEmpty()||
        Gross_Tonnage.isNullOrEmpty()
        ){
            return LoginAsVesselError.mustFillAllFields()
        }
        return LoginAsVesselError.none()
    }
}