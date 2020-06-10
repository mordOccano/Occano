package com.e.occano.api.main.responses

import com.e.occano.models.BlogPost
import com.e.occano.models.Cylinder
import com.e.occano.utils.DateUtils
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CylinderSearchResponse(

    @Expose
    @SerializedName("cylinder_num")
    var numOfCylInEngine: Int = 0,
    @Expose
    @SerializedName("rpm")
    var rpm: Float?=0.0f,

    @Expose
    @SerializedName("exhaust_temperature")
    var exhaust_temperature: Float?=0.0f,

    @Expose
    @SerializedName("load")
    var load: Float?=0.0f,

    @Expose
    @SerializedName("firing_pressure")
    var firing_pressure: Float?=0.0f,

    @Expose
    @SerializedName("scavenging_pressure")
    var scavenging_pressure: Float?=0.0f,

    @Expose
    @SerializedName("compression_pressure")
    var compression_pressure: Float?=0.0f,

    @Expose
    @SerializedName("break_power")
    var break_power: Float?=0.0f,

    @Expose
    @SerializedName("imep")
    var imep: Float?=0.0f,

    @Expose
    @SerializedName("torque_engine")
    var torque_engine: Float?=0.0f,

    @Expose
    @SerializedName("bmep")
    var bmep: Float?=0.0f,

    @Expose
    @SerializedName("injection_timing")
    var injection_timing: Float?=0.0f,

    @Expose
    @SerializedName("fuel_flow_rate")
    var fuel_flow_rate: Float?=0.0f
) {
    fun toCylinder(): Cylinder{
        return Cylinder(
            numOfCylInEngine,rpm,exhaust_temperature,
            load,firing_pressure,scavenging_pressure,
            compression_pressure,break_power,imep,
            torque_engine, bmep, injection_timing,
            fuel_flow_rate)

    }

    override fun toString(): String {
        return "CylinderSearchResponse(numOfCylInEngine=$numOfCylInEngine, rpm=$rpm, exhaust_temperature=$exhaust_temperature, load=$load, firing_pressure=$firing_pressure, scavenging_pressure=$scavenging_pressure, compression_pressure=$compression_pressure, break_power=$break_power, imep=$imep, torque_engine=$torque_engine, bmep=$bmep, injection_timing=$injection_timing, fuel_flow_rate=$fuel_flow_rate)"
    }


}

//date_updated = DateUtils.convertServerStringDateToLong(
//                date_updated
//            ),




