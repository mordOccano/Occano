package com.e.occano.ui.main.dashboard.viewmodel

import android.content.SharedPreferences
import com.e.occano.di.main.MainScope
import com.e.occano.persistence.BlogQueryUtils
import com.e.occano.repository.main.DashboardRepository
import com.e.occano.session.SessionManager
import com.e.occano.ui.BaseViewModel
import com.e.occano.ui.main.dashboard.blogstate.BlogStateEvent.*
import com.e.occano.ui.main.dashboard.state.DashboardStateEvent
import com.e.occano.utils.*
import com.e.occano.utils.PreferenceKeys.Companion.BLOG_FILTER
import com.e.occano.utils.PreferenceKeys.Companion.BLOG_ORDER
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType
import okhttp3.RequestBody
import javax.inject.Inject
import com.e.occano.ui.main.dashboard.state.DashboardViewState

@ExperimentalCoroutinesApi
@FlowPreview
@MainScope
class DashboardViewModel
@Inject
constructor(
    private val sessionManager: SessionManager,
    private val dashboardRepository: DashboardRepository,
    private val sharedPreferences: SharedPreferences,
    private val editor: SharedPreferences.Editor
): BaseViewModel<DashboardViewState>(){
//
//    init {
//        setBlogFilter(
//            sharedPreferences.getString(
//                BLOG_FILTER,
//                BlogQueryUtils.BLOG_FILTER_DATE_UPDATED
//            )
//        )
//        sharedPreferences.getString(
//            BLOG_ORDER,
//            BlogQueryUtils.BLOG_ORDER_DESC
//        )?.let {
//            setBlogOrder(
//                it
//            )
//        }
//    }



    override fun setStateEvent(stateEvent: StateEvent) {
        if(!isJobAlreadyActive(stateEvent)){
            sessionManager.cachedToken.value?.let { authToken ->
                val job: Flow<DataState<DashboardViewState>> = when(stateEvent){

                    is DashboardStateEvent.dashboardSearchEvent -> {
                        if(stateEvent.clearLayoutManagerState){
//                            clearLayoutManagerState()
                        }
                        dashboardRepository.getDashboard(
                            stateEvent = stateEvent
                        )
                    }

//                    is CheckAuthorOfBlogPost -> {
//                        dasboardRepository.isAuthorOfBlogPost(
//                            stateEvent = stateEvent,
//                            authToken = authToken,
//                            slug = getSlug()
//                        )
//                    }
//
//                    is DeleteBlogPostEvent -> {
//                        dasboardRepository.deleteBlogPost(
//                            stateEvent = stateEvent,
//                            authToken = authToken,
//                            blogPost = getBlogPost()
//                        )
//                    }
//
//                    is UpdateBlogPostEvent -> {
//                        val title = RequestBody.create(
//                            MediaType.parse("text/plain"),
//                            stateEvent.title
//                        )
//                        val body = RequestBody.create(
//                            MediaType.parse("text/plain"),
//                            stateEvent.body
//                        )
//
//                        dasboardRepository.updateBlogPost(
//                            stateEvent = stateEvent,
//                            authToken = authToken,
//                            slug = getSlug(),
//                            title = title,
//                            body = body,
//                            image = stateEvent.image
//                        )
//                    }

                    else -> {
                        flow{
//                            emit(
//                                DataState.error(
//                                    response = Response(
//                                        message = INVALID_STATE_EVENT,
//                                        uiComponentType = UIComponentType.None(),
//                                        messageType = MessageType.Error()
//                                    ),
//                                    stateEvent = stateEvent
//                                )
//                            )
                        }
                    }
                }
                launchJob(stateEvent, job)
            }
        }
    }

    override fun initNewViewState(): DashboardViewState {
        return DashboardViewState()
    }

    fun saveFilterOptions(filter: String, order: String){
        editor.putString(BLOG_FILTER, filter)
        editor.apply()

        editor.putString(BLOG_ORDER, order)
        editor.apply()
    }

    override fun onCleared() {
        super.onCleared()
        cancelActiveJobs()
    }

    override fun handleNewData(data: DashboardViewState) {
        TODO("Not yet implemented")
    }

}