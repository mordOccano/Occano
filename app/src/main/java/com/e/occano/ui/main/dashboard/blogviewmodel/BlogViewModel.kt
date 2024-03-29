package com.e.occano.ui.main.dashboard.blogviewmodel

import android.content.SharedPreferences
import com.e.occano.di.main.MainScope
import com.e.occano.persistence.BlogQueryUtils
import com.e.occano.repository.main.BlogRepositoryImpl
import com.e.occano.session.SessionManager
import com.e.occano.ui.BaseViewModel
import com.e.occano.ui.main.dashboard.blogstate.BlogStateEvent.*
import com.e.occano.ui.main.dashboard.blogstate.BlogViewState
import com.e.occano.utils.*
import com.e.occano.utils.ErrorHandling.Companion.INVALID_STATE_EVENT
import com.e.occano.utils.PreferenceKeys.Companion.BLOG_FILTER
import com.e.occano.utils.PreferenceKeys.Companion.BLOG_ORDER
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType
import okhttp3.RequestBody
import javax.inject.Inject

@ExperimentalCoroutinesApi
@FlowPreview
@MainScope
class BlogViewModel
@Inject
constructor(
    private val sessionManager: SessionManager,
    private val blogRepository: BlogRepositoryImpl,
    private val sharedPreferences: SharedPreferences,
    private val editor: SharedPreferences.Editor
): BaseViewModel<BlogViewState>(){

    init {
        setBlogFilter(
            sharedPreferences.getString(
                BLOG_FILTER,
                BlogQueryUtils.BLOG_FILTER_DATE_UPDATED
            )
        )
        sharedPreferences.getString(
            BLOG_ORDER,
            BlogQueryUtils.BLOG_ORDER_DESC
        )?.let {
            setBlogOrder(
                it
            )
        }
    }

    override fun handleNewData(data: BlogViewState) {

        data.blogFields.let { blogFields ->

            blogFields.blogList?.let { blogList ->
                handleIncomingBlogListData(data)
            }

            blogFields.isQueryExhausted?.let { isQueryExhausted ->
                setQueryExhausted(isQueryExhausted)
            }

        }

        data.viewBlogFields.let { viewBlogFields ->

            viewBlogFields.blogPost?.let { blogPost ->
                setBlogPost(blogPost)
            }

            viewBlogFields.isAuthorOfBlogPost?.let { isAuthor ->
                setIsAuthorOfBlogPost(isAuthor)
            }
        }

        data.updatedBlogFields.let { updatedBlogFields ->

            updatedBlogFields.updatedImageUri?.let { uri ->
                setUpdatedUri(uri)
            }

            updatedBlogFields.updatedBlogTitle?.let { title ->
                setUpdatedTitle(title)
            }

            updatedBlogFields.updatedBlogBody?.let { body ->
                setUpdatedBody(body)
            }
        }
    }

    override fun setStateEvent(stateEvent: StateEvent) {
        if(!isJobAlreadyActive(stateEvent)){
            sessionManager.cachedToken.value?.let { authToken ->
                val job: Flow<DataState<BlogViewState>> = when(stateEvent){

                    is BlogSearchEvent -> {
                        if(stateEvent.clearLayoutManagerState){
                            clearLayoutManagerState()
                        }
                        blogRepository.searchBlogPosts(
                            stateEvent = stateEvent,
                            authToken = authToken,
                            query = getSearchQuery(),
                            filterAndOrder = getOrder() + getFilter(),
                            page = getPage()
                        )
                    }

                    is CheckAuthorOfBlogPost -> {
                        blogRepository.isAuthorOfBlogPost(
                            stateEvent = stateEvent,
                            authToken = authToken,
                            slug = getSlug()
                        )
                    }

                    is DeleteBlogPostEvent -> {
                        blogRepository.deleteBlogPost(
                            stateEvent = stateEvent,
                            authToken = authToken,
                            blogPost = getBlogPost()
                        )
                    }

                    is UpdateBlogPostEvent -> {
                        val title = RequestBody.create(
                            MediaType.parse("text/plain"),
                            stateEvent.title
                        )
                        val body = RequestBody.create(
                            MediaType.parse("text/plain"),
                            stateEvent.body
                        )

                        blogRepository.updateBlogPost(
                            stateEvent = stateEvent,
                            authToken = authToken,
                            slug = getSlug(),
                            title = title,
                            body = body,
                            image = stateEvent.image
                        )
                    }

                    else -> {
                        flow{
                            emit(
                                DataState.error(
                                    response = Response(
                                        message = INVALID_STATE_EVENT,
                                        uiComponentType = UIComponentType.None(),
                                        messageType = MessageType.Error()
                                    ),
                                    stateEvent = stateEvent
                                )
                            )
                        }
                    }
                }
                launchJob(stateEvent, job)
            }?: sessionManager.logout()
        }
    }

    override fun initNewViewState(): BlogViewState {
        return BlogViewState()
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

}