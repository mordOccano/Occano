package com.e.occano.ui.main.graphs

import android.net.Uri
import com.e.occano.di.main.MainScope
import com.e.occano.repository.main.CreateBlogRepositoryImpl
import com.e.occano.session.SessionManager
import com.e.occano.ui.BaseViewModel
import com.e.occano.ui.main.graphs.state.CreateBlogStateEvent.*
import com.e.occano.ui.main.graphs.state.CreateBlogViewState
import com.e.occano.ui.main.graphs.state.CreateBlogViewState.*
import com.e.occano.utils.*
import com.e.occano.utils.ErrorHandling.Companion.INVALID_STATE_EVENT
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType
import okhttp3.RequestBody
import javax.inject.Inject

@UseExperimental(ExperimentalCoroutinesApi::class)
@FlowPreview
@MainScope
class CreateBlogViewModel
@Inject
constructor(
    val createBlogRepository: CreateBlogRepositoryImpl,
    val sessionManager: SessionManager
): BaseViewModel<CreateBlogViewState>() {


    override fun handleNewData(data: CreateBlogViewState) {

        setNewBlogFields(
            data.blogFields.newBlogTitle,
            data.blogFields.newBlogBody,
            data.blogFields.newImageUri
        )
    }

    override fun setStateEvent(stateEvent: StateEvent) {
        sessionManager.cachedToken.value?.let { authToken ->
            val job: Flow<DataState<CreateBlogViewState>> = when(stateEvent){

                is CreateNewBlogEvent -> {
                    val title = RequestBody.create(
                        MediaType.parse("text/plain"),
                        stateEvent.title
                    )
                    val body = RequestBody.create(
                        MediaType.parse("text/plain"),
                        stateEvent.body
                    )

                    createBlogRepository.createNewBlogPost(
                        stateEvent = stateEvent,
                        authToken = authToken,
                        title = title,
                        body = body,
                        image = stateEvent.image
                    )
                }

                else -> {
                    flow{
//                        emit(
//                            DataState.error(
//                                response = Response(
//                                    message = INVALID_STATE_EVENT,
//                                    uiComponentType = UIComponentType.None(),
//                                    messageType = MessageType.Error()
//                                ),
//                                stateEvent = stateEvent
//                            )
//                        )
                    }
                }
            }
            launchJob(stateEvent, job)
        }?: sessionManager.logout()
    }

    override fun initNewViewState(): CreateBlogViewState {
        return CreateBlogViewState()
    }

    fun setNewBlogFields(title: String?, body: String?, uri: Uri?){
        val update = getCurrentViewStateOrNew()
        val newBlogFields = update.blogFields
        title?.let{ newBlogFields.newBlogTitle = it }
        body?.let{ newBlogFields.newBlogBody = it }
        uri?.let{ newBlogFields.newImageUri = it }
        update.blogFields = newBlogFields
        setViewState(update)
    }

    fun clearNewBlogFields(){
        val update = getCurrentViewStateOrNew()
        update.blogFields = NewBlogFields()
        setViewState(update)
    }

    override fun onCleared() {
        super.onCleared()
        cancelActiveJobs()
    }

}