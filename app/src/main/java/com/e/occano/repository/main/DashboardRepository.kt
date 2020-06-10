package com.e.occano.repository.main

import com.e.occano.di.main.MainScope
import com.e.occano.models.AuthToken
import com.e.occano.models.BlogPost
import com.e.occano.ui.main.dashboard.blogstate.BlogViewState
import com.e.occano.ui.main.dashboard.state.DashboardViewState
import com.e.occano.utils.DataState
import com.e.occano.utils.StateEvent
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody

@FlowPreview
@MainScope
interface DashboardRepository {

    fun getDashboard(
        stateEvent: StateEvent
    ): Flow<DataState<DashboardViewState>>

//    fun isAuthorOfBlogPost(
//        authToken: AuthToken,
//        slug: String,
//        stateEvent: StateEvent
//    ): Flow<DataState<DashboardViewState>>
//
//    fun deleteBlogPost(
//        authToken: AuthToken,
//        blogPost: BlogPost,
//        stateEvent: StateEvent
//    ): Flow<DataState<DashboardViewState>>

//    fun updateBlogPost(
//        authToken: AuthToken,
//        slug: String,
//        title: RequestBody,
//        body: RequestBody,
//        image: MultipartBody.Part?,
//        stateEvent: StateEvent
//    ): Flow<DataState<DashboardViewState>>

}