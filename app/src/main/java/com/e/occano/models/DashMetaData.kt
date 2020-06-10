package com.e.occano.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "dashboard_meta_data")
data class DashMetaData (
    @PrimaryKey(autoGenerate = false)
    var id:Int = 0,

    @ColumnInfo(name = "mics_recv")
    var microphonesDate: String = "",

    @ColumnInfo(name = "ir_recv")
    var infraredDate: String = ""



) : Parcelable {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is DashMetaData) return false

        if (id != other.id) return false
        if (microphonesDate != other.microphonesDate) return false
        if (infraredDate != other.infraredDate) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + microphonesDate.hashCode()
        result = 31 * result + infraredDate.hashCode()
        return result
    }

    override fun toString(): String {
        return "DashMetaData(id=$id, microphonesDate='$microphonesDate', infraredDate='$infraredDate')"
    }


}


