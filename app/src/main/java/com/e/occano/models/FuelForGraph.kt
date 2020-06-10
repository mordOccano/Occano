package com.e.occano.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@Entity(tableName = "fuel_details_for_graph")
data class FuelForGraph(
    @PrimaryKey(autoGenerate = true)
    var id:Int = 0,

    @ColumnInfo(name = "fuel_timestamp_graph")
    var timeStamp: String = ""
//        Date()
    ,

    @ColumnInfo(name = "fuel_value_graph")
    var value: Double = 0.0
) : Parcelable{
    override fun equals(other: Any?): Boolean {
        return super.equals(other)
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }
}