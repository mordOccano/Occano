package com.e.occano.models

import android.os.Parcel
import android.os.Parcelable

data class Ship(
    var id: Int = 0,
    var vessel: String? = "",
    var m_e: String? = "0",
    var displacement_engine: String? = "0",
    var Number_of_cylinders: Int? = 0,
    var Stroke_bore_ratio: String? = "0",
    var Diameter_of_piston_in_cm: String? = "0",
    var Concept: String? = "",
    var Design: String? = "",
    var IMO: String? = "",
    var AIS_Vessel_Type	: String? = "",
    var Year_Built: String? = "0",
    var Length_Overall: String? = "0",
    var Breadth_Extreme: String? = "",
    var Deadweight: String? = "0",
    var Gross_Tonnage: String? = "0",

    var isSelected: Boolean = false
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readByte() != 0.toByte()
    ) {
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Ship) return false

        if (id != other.id) return false
        if (vessel != other.vessel) return false
        if (m_e != other.m_e) return false
        if (displacement_engine != other.displacement_engine) return false
        if (Number_of_cylinders != other.Number_of_cylinders) return false
        if (Stroke_bore_ratio != other.Stroke_bore_ratio) return false
        if (Diameter_of_piston_in_cm != other.Diameter_of_piston_in_cm) return false
        if (Concept != other.Concept) return false
        if (Design != other.Design) return false
        if (IMO != other.IMO) return false
        if (AIS_Vessel_Type != other.AIS_Vessel_Type) return false
        if (Year_Built != other.Year_Built) return false
        if (Length_Overall != other.Length_Overall) return false
        if (Breadth_Extreme != other.Breadth_Extreme) return false
        if (Deadweight != other.Deadweight) return false
        if (Gross_Tonnage != other.Gross_Tonnage) return false
        if (isSelected != other.isSelected) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + (vessel?.hashCode() ?: 0)
        result = 31 * result + (m_e?.hashCode() ?: 0)
        result = 31 * result + (displacement_engine?.hashCode() ?: 0)
        result = 31 * result + (Number_of_cylinders?.hashCode() ?: 0)
        result = 31 * result + (Stroke_bore_ratio?.hashCode() ?: 0)
        result = 31 * result + (Diameter_of_piston_in_cm?.hashCode() ?: 0)
        result = 31 * result + (Concept?.hashCode() ?: 0)
        result = 31 * result + (Design?.hashCode() ?: 0)
        result = 31 * result + (IMO?.hashCode() ?: 0)
        result = 31 * result + (AIS_Vessel_Type?.hashCode() ?: 0)
        result = 31 * result + (Year_Built?.hashCode() ?: 0)
        result = 31 * result + (Length_Overall?.hashCode() ?: 0)
        result = 31 * result + (Breadth_Extreme?.hashCode() ?: 0)
        result = 31 * result + (Deadweight?.hashCode() ?: 0)
        result = 31 * result + (Gross_Tonnage?.hashCode() ?: 0)
        result = 31 * result + isSelected.hashCode()
        return result
    }

    override fun toString(): String {
        return "Ship(id=$id, vessel=$vessel, m_e=$m_e, displacement_engine=$displacement_engine, Number_of_cylinders=$Number_of_cylinders, Stroke_bore_ratio=$Stroke_bore_ratio, Diameter_of_piston_in_cm=$Diameter_of_piston_in_cm, Concept=$Concept, Design=$Design, IMO=$IMO, AIS_Vessel_Type=$AIS_Vessel_Type, Year_Built=$Year_Built, Length_Overall=$Length_Overall, Breadth_Extreme=$Breadth_Extreme, Deadweight=$Deadweight, Gross_Tonnage=$Gross_Tonnage, isSelected=$isSelected)"
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(vessel)
        parcel.writeString(m_e)
        parcel.writeString(displacement_engine)
        parcel.writeValue(Number_of_cylinders)
        parcel.writeString(Stroke_bore_ratio)
        parcel.writeString(Diameter_of_piston_in_cm)
        parcel.writeString(Concept)
        parcel.writeString(Design)
        parcel.writeString(IMO)
        parcel.writeString(AIS_Vessel_Type)
        parcel.writeString(Year_Built)
        parcel.writeString(Length_Overall)
        parcel.writeString(Breadth_Extreme)
        parcel.writeString(Deadweight)
        parcel.writeString(Gross_Tonnage)
        parcel.writeByte(if (isSelected) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Ship> {
        override fun createFromParcel(parcel: Parcel): Ship {
            return Ship(parcel)
        }

        override fun newArray(size: Int): Array<Ship?> {
            return arrayOfNulls(size)
        }
    }


}