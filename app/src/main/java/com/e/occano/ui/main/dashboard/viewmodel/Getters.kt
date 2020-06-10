package com.e.occano.ui.main.dashboard.viewmodel

import android.net.Uri
import com.e.occano.models.BlogPost
import com.e.occano.models.Cylinder
import com.e.occano.persistence.BlogQueryUtils.Companion.BLOG_FILTER_DATE_UPDATED
import com.e.occano.persistence.BlogQueryUtils.Companion.BLOG_ORDER_DESC
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
//
//@FlowPreview
//@UseExperimental(ExperimentalCoroutinesApi::class)
//fun DashboardViewModel.getIsQueryExhausted(): Boolean {
//    return getCurrentViewStateOrNew().blogFields.isQueryExhausted
//        ?: false
//}
//
//@FlowPreview
//@UseExperimental(ExperimentalCoroutinesApi::class)
//fun DashboardViewModel.getFilter(): String {
//    return getCurrentViewStateOrNew().blogFields.filter
//        ?: BLOG_FILTER_DATE_UPDATED
//}
//
//@FlowPreview
//@UseExperimental(ExperimentalCoroutinesApi::class)
//fun DashboardViewModel.getOrder(): String {
//    return getCurrentViewStateOrNew().blogFields.order
//        ?: BLOG_ORDER_DESC
//}
//
//@FlowPreview
//@UseExperimental(ExperimentalCoroutinesApi::class)
//fun DashboardViewModel.getSearchQuery(): String {
//    return getCurrentViewStateOrNew().blogFields.searchQuery
//        ?: return ""
//}
//
//@FlowPreview
//@UseExperimental(ExperimentalCoroutinesApi::class)
//fun DashboardViewModel.getPage(): Int{
//    return getCurrentViewStateOrNew().blogFields.page
//        ?: return 1
//}
//
//@FlowPreview
//@UseExperimental(ExperimentalCoroutinesApi::class)
//fun DashboardViewModel.getSlug(): String{
//    getCurrentViewStateOrNew().let {
//        it.viewBlogFields.blogPost?.let {
//            return it.slug
//        }
//    }
//    return ""
//}
//
//@FlowPreview
//@UseExperimental(ExperimentalCoroutinesApi::class)
//fun DashboardViewModel.isAuthorOfBlogPost(): Boolean{
//    return getCurrentViewStateOrNew().viewBlogFields.isAuthorOfBlogPost
//        ?: false
//}

@FlowPreview
@UseExperimental(ExperimentalCoroutinesApi::class)
fun DashboardViewModel.getBlogPost(): Cylinder {
    getCurrentViewStateOrNew().let {
        return it.viewDashboardFields.cylinders?.let {
            return it.get(0)
        }?: getDummyCylinder()
    }
}

@FlowPreview
@UseExperimental(ExperimentalCoroutinesApi::class)
fun DashboardViewModel.getDummyCylinder(): Cylinder{
    return Cylinder(1, 1500f , 100f, 80f, 2500f, 85f, 250f,24f,28f,57f,677f,65f,567f)
}
//
//@FlowPreview
//@UseExperimental(ExperimentalCoroutinesApi::class)
//fun DashboardViewModel.getUpdatedBlogUri(): Uri? {
//    getCurrentViewStateOrNew().let {
//        it.updatedBlogFields.updatedImageUri?.let {
//            return it
//        }
//    }
//    return null
//}
