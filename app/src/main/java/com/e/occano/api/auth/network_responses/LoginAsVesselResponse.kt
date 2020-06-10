package com.e.occano.api.auth.network_responses

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class LoginAsVesselResponse (

    @Expose
    @SerializedName("vessel")
    var response: String = "",

    @Expose
    @SerializedName("vessel")
    var vessel: String = "",
    @Expose
    @SerializedName("m_e")
    var m_e: String = "0",
    @Expose
    @SerializedName("displacement_engine")
    var displacement_engine: String = "0",
    @Expose
    @SerializedName("Number_of_cylinders")
    var Number_of_cylinders: Int = 0,
    @Expose
    @SerializedName("Stroke_bore_ratio")
    var Stroke_bore_ratio: String = "0",
    @Expose
    @SerializedName("Diameter_of_piston_in_cm")
    var Diameter_of_piston_in_cm: String = "0",
    @Expose
    @SerializedName("Concept")
    var Concept: String = "",
    @Expose
    @SerializedName("Design")
    var Design: String = "",
    @Expose
    @SerializedName("IMO")
    var IMO: String = "",
    @Expose
    @SerializedName("AIS_Vessel_Type")
    var AIS_Vessel_Type	: String = "",
    @Expose
    @SerializedName("Year_Built")
    var Year_Built: String = "0",
    @Expose
    @SerializedName("Length_Overall")
    var Length_Overall: String = "0",
    @Expose
    @SerializedName("Breadth_Extreme")
    var Breadth_Extreme: String = "",
    @Expose
    @SerializedName("Deadweight")
    var Deadweight: String = "0",
    @Expose
    @SerializedName("Gross_Tonnage")
    var Gross_Tonnage: String = "0",
    @Expose
    @SerializedName("Gross_Tonnage")
    var errorMessage: String = "",

    @SerializedName("pk")
    @Expose
    var pk: Int,

    @SerializedName("token")
    @Expose
    var token: String

){
    override fun toString(): String {
        return "LoginAsVesselResponse(response='$response', vessel='$vessel', m_e='$m_e', displacement_engine='$displacement_engine', Number_of_cylinders=$Number_of_cylinders, Stroke_bore_ratio='$Stroke_bore_ratio', Diameter_of_piston_in_cm='$Diameter_of_piston_in_cm', Concept='$Concept', Design='$Design', IMO='$IMO', AIS_Vessel_Type='$AIS_Vessel_Type', Year_Built='$Year_Built', Length_Overall='$Length_Overall', Breadth_Extreme='$Breadth_Extreme', Deadweight='$Deadweight', Gross_Tonnage='$Gross_Tonnage', errorMessage='$errorMessage', pk=$pk, token='$token')"
    }
}