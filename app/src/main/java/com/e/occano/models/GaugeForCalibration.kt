package com.e.occano.models

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity (tableName = "gauge_for_calibration")
data class GaugeForCalibration(
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id:Int = 0,
   @ColumnInfo(name= "viewed_field")
    var viewed_field: String = "",
    @ColumnInfo(name = "viewed_value")
    var viewed_value: Float = 0F,
    @ColumnInfo(name = "manual_corrected_value")
    var manual_corrected_value: Float = 0F
):Parcelable

