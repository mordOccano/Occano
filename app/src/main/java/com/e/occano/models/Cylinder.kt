package com.e.occano.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
@Entity(tableName = "cylinder")
data class Cylinder(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "cylinder_num")
    var numOfCylInEngine: Int = 0,

    @ColumnInfo(name ="rpm")
    var rpm: Float?=0.0f,

    @ColumnInfo(name ="exhaust_temperature")
    var exhaust_temperature: Float?=0.0f,

    @ColumnInfo(name ="load")
    var load: Float?=0.0f,

    @ColumnInfo(name ="firing_pressure")
    var firing_pressure: Float?=0.0f,

    @ColumnInfo(name ="scavenging_pressure")
    var scavenging_pressure: Float?=0.0f,

    @ColumnInfo(name ="compression_pressure")
    var compression_pressure: Float?=0.0f,

    @ColumnInfo(name ="break_power")
    var break_power: Float?=0.0f,

    @ColumnInfo(name ="imep")
    var imep: Float?=0.0f,

    @ColumnInfo(name ="torque_engine")
    var torque_engine: Float?=0.0f,

    @ColumnInfo(name ="bmep")
    var bmep: Float?=0.0f,

    @ColumnInfo(name ="injection_timing")
    var injection_timing: Float?=0.0f,

    @ColumnInfo(name ="fuel_flow_rate")
    var fuel_flow_rate: Float?=0.0f
) : Parcelable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Cylinder) return false

        if (numOfCylInEngine != other.numOfCylInEngine) return false
        if (rpm != other.rpm) return false
        if (exhaust_temperature != other.exhaust_temperature) return false
        if (load != other.load) return false
        if (firing_pressure != other.firing_pressure) return false
        if (scavenging_pressure != other.scavenging_pressure) return false
        if (compression_pressure != other.compression_pressure) return false
        if (break_power != other.break_power) return false
        if (imep != other.imep) return false
        if (torque_engine != other.torque_engine) return false
        if (bmep != other.bmep) return false
        if (injection_timing != other.injection_timing) return false
        if (fuel_flow_rate != other.fuel_flow_rate) return false

        return true
    }

    override fun hashCode(): Int {
        var result = numOfCylInEngine
        result = 31 * result + (rpm?.hashCode() ?: 0)
        result = 31 * result + (exhaust_temperature?.hashCode() ?: 0)
        result = 31 * result + (load?.hashCode() ?: 0)
        result = 31 * result + (firing_pressure?.hashCode() ?: 0)
        result = 31 * result + (scavenging_pressure?.hashCode() ?: 0)
        result = 31 * result + (compression_pressure?.hashCode() ?: 0)
        result = 31 * result + (break_power?.hashCode() ?: 0)
        result = 31 * result + (imep?.hashCode() ?: 0)
        result = 31 * result + (torque_engine?.hashCode() ?: 0)
        result = 31 * result + (bmep?.hashCode() ?: 0)
        result = 31 * result + (injection_timing?.hashCode() ?: 0)
        result = 31 * result + (fuel_flow_rate?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "Cylinder(numOfCylInEngine=$numOfCylInEngine, rpm=$rpm, exhaust_temperature=$exhaust_temperature, load=$load, firing_pressure=$firing_pressure, scavenging_pressure=$scavenging_pressure, compression_pressure=$compression_pressure, break_power=$break_power, imep=$imep, torque_engine=$torque_engine, bmep=$bmep, injection_timing=$injection_timing, fuel_flow_rate=$fuel_flow_rate)"
    }

}