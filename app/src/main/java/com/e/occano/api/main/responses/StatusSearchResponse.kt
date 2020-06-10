package com.e.occano.api.main.responses

import com.e.occano.models.BlogPost
import com.e.occano.models.Status
import com.e.occano.utils.DateUtils
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class StatusSearchResponse(
    @Expose
    @SerializedName( "cylinder_num")
    private var cylinder_num: Int = 0,

    private var statusId: Int = 0,

    @Expose
    @SerializedName( "mainTitle")
    private var statusMainTitle: String? = null,

    @Expose
    @SerializedName( "subTitle")
    private var statusSubTitle: String? = null,

    @Expose
    @SerializedName( "lessContent")
    private var statusLessContent: String? = null,

    @Expose
    @SerializedName( "moreContent")
    private var statusMoreContent: String? = null,

    @Expose
    @SerializedName( "KindOfDanger")
    private var statusKindOfDanger:Int = 0,

    @Expose
    @SerializedName( "kindOfAcknowledge")
    private var kindOfAcknowledge:Int = 0,

    @Expose
    @SerializedName( "numberOfStatus")
    private var numberOfstatus:Int = 0,

    @Expose
    @SerializedName( "timeStampOfStatus")
    private var timeStampOfstatus: String? = null


) {
    fun toStatus(): Status{
        return Status(
            statusId,
            cylinder_num,
            statusMainTitle,
            statusSubTitle,
            statusLessContent,
            statusMoreContent,
            statusKindOfDanger,
            kindOfAcknowledge,
            numberOfstatus,
            timeStampOfstatus
        )
    }

    override fun toString(): String {
        return "StatusSearchResponse(statusId=$statusId, statusMainTitle=$statusMainTitle, statusSubTitle=$statusSubTitle, statusLessContent=$statusLessContent, statusMoreContent=$statusMoreContent, statusKindOfDanger=$statusKindOfDanger, kindOfAcknowledge=$kindOfAcknowledge, numberOfstatus=$numberOfstatus, timeStampOfstatus=$timeStampOfstatus)"
    }


}





