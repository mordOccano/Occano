package com.e.occano.ui.main.dashboard.state

import android.net.Uri
import android.os.Parcelable
import com.e.occano.models.Cylinder
import com.e.occano.models.Status
import kotlinx.android.parcel.Parcelize

const val BLOG_VIEW_STATE_BUNDLE_KEY = "com.e.occano.ui.main.dashboard.state.DashboardViewState"

@Parcelize
data class DashboardViewState (

    // ViewDashboardFragment vars
    var viewDashboardFields: ViewDashboardFields = ViewDashboardFields()

    // UpdateBlogFragment vars
//    var updatedAnalysisFields: UpdatedAnalysisFields = UpdatedAnalysisFields()

): Parcelable {


    @Parcelize
    data class ViewDashboardFields(
        var cylinders: List<Cylinder>?=null,
        var status: Status? = null
    ) : Parcelable

//    @Parcelize
//    data class UpdatedAnalysisFields(
//        var updatedCylinder: Cylinder? = null,
//        var updatedStatus: Status? = null
//    ) : Parcelable
}
