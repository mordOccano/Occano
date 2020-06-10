package com.e.occano.repository.main

import com.e.occano.di.main.MainScope
import com.e.occano.models.AuthToken
import com.e.occano.ui.main.graphs.state.CreateBlogViewState
import com.e.occano.utils.DataState
import com.e.occano.utils.StateEvent
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody

@FlowPreview
@MainScope
interface CreateBlogRepository {

    fun createNewBlogPost(
        authToken: AuthToken,
        title: RequestBody,
        body: RequestBody,
        image: MultipartBody.Part?,
        stateEvent: StateEvent
    ): Flow<DataState<CreateBlogViewState>>
}
