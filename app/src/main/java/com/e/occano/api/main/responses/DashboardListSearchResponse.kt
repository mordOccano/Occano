package com.e.occano.api.main.responses

import com.e.occano.models.Cylinder
import com.e.occano.models.DashMetaData
import com.e.occano.models.Status
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class DashboardListSearchResponse(

    @SerializedName("cylinders")
    @Expose
    var cylinders: List<CylinderSearchResponse>,

    @SerializedName("status")
    @Expose
    var status: List<StatusSearchResponse>,

    @SerializedName("metadata")
    @Expose
    var metaData: MetaDataSearchResponse,

    @SerializedName("count_of_cylinders")
    @Expose
    var count_of_cylinders: Int,

    @SerializedName("time_passed")
    @Expose
    var time_passed: Long


) {

    fun cylToList(): List<Cylinder>{
        val cylsList: ArrayList<Cylinder> = ArrayList()
        for(cylinderResponse in cylinders){
            cylsList.add(
                cylinderResponse.toCylinder()
            )
        }
        return cylsList
    }

    fun statusToList(): List<Status>{
        val statusList: ArrayList<Status> = ArrayList()
        for(statusResponse in status){
            statusList.add(
                statusResponse.toStatus()
            )
        }
        return statusList
    }

    fun metaToMetaData():DashMetaData{
        return metaData.toMetaData()
    }

    override fun toString(): String {
        return "DashboardListSearchResponse(cylinders=$cylinders, status=$status, metaData=$metaData, count_of_cylinders=$count_of_cylinders, time_passed=$time_passed)"
    }


}