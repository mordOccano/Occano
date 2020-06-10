package com.e.occano.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize


@Parcelize
@Entity(tableName = "statuses")

data class Status(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "statusId")
     var statusId: Int = 0,

    @ColumnInfo(name = "cylinder_num")
     var cylinder_num: Int = 0,

    @ColumnInfo(name = "mainTitle")
     var statusMainTitle: String? = null,

    @ColumnInfo(name = "subTitle")
     var statusSubTitle: String? = null,

    @ColumnInfo(name = "lessContent")
     var statusLessContent: String? = null,

    @ColumnInfo(name = "moreContent")
     var statusMoreContent: String? = null,

    @ColumnInfo(name = "KindOfDanger")
     var statusKindOfDanger:Int = 0,

    @ColumnInfo(name = "kindOfAcknowledge")
     var kindOfAcknowledge:Int = 0,

    @ColumnInfo(name = "numberOfStatus")
     var numberOfstatus:Int = 0,

    @ColumnInfo(name = "timeStampOfStatus")
     var timeStampOfstatus: String? = null
) : Parcelable
{
    override fun toString(): String {
        return "Status(statusId=$statusId, cylinder_num=$cylinder_num, statusMainTitle=$statusMainTitle, statusSubTitle=$statusSubTitle, statusLessContent=$statusLessContent, statusMoreContent=$statusMoreContent, statusKindOfDanger=$statusKindOfDanger, kindOfAcknowledge=$kindOfAcknowledge, numberOfstatus=$numberOfstatus, timeStampOfstatus=$timeStampOfstatus)"
    }
}



