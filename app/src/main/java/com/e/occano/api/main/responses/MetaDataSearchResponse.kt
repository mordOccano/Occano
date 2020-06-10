package com.e.occano.api.main.responses

import com.e.occano.models.BlogPost
import com.e.occano.models.DashMetaData
import com.e.occano.utils.DateUtils
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class MetaDataSearchResponse(

    @Expose
    @SerializedName( "mics_recv")
    var microphonesDate: String = "",

    @SerializedName( "ir_recv")
    var infraredDate: String = ""


) {
    var counter: Int = 0
    fun toMetaData(): DashMetaData{
        return DashMetaData(
            counter++,
            microphonesDate,
            infraredDate
        )
    }


}





