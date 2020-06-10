package com.e.occano.api.auth

import com.e.occano.api.auth.network_responses.LoginAsVesselResponse
import com.e.occano.api.auth.network_responses.LoginResponse
import com.e.occano.api.auth.network_responses.RegistrationResponse
import com.e.occano.di.auth.AuthScope
import retrofit2.http.*

@AuthScope
interface ApiAuthService {

    @POST("account/login")
    @FormUrlEncoded
    suspend fun login(
        @Field("username") email: String,
        @Field("password") password: String
    ): LoginResponse

    @POST("account/register")
    @FormUrlEncoded
    suspend fun register(
        @Field("email") email: String,
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("password2") password2: String
    ): RegistrationResponse

    @POST("account/loginAsVessel")
    @FormUrlEncoded
    suspend fun loginAsVessel(
        @Field ("vessel")vessel: String,
        @Field ("m_e")m_e: String ,
        @Field ("displacement_engine")displacement_engine: String ,
        @Field ("Number_of_cylinders")Number_of_cylinders: Int,
        @Field ("Stroke_bore_ratio")Stroke_bore_ratio: String,
        @Field ("Diameter_of_piston_in_cm")Diameter_of_piston_in_cm: String ,
        @Field ("Concept")Concept: String,
        @Field ("Design")Design: String,
        @Field ("IMO")IMO: String,
        @Field ("AIS_Vessel_Type")AIS_Vessel_Type	: String,
        @Field ("Year_Built")Year_Built: String ,
        @Field ("Length_Overall")Length_Overall: String ,
        @Field ("Breadth_Extreme")Breadth_Extreme: String,
        @Field ("Deadweight")Deadweight: String ,
        @Field ("Gross_Tonnage")Gross_Tonnage: String
    ): LoginAsVesselResponse

}