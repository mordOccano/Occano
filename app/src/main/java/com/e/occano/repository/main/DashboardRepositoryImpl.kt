package com.e.occano.repository.main

import android.util.Log
import com.e.occano.api.GenericResponse
import com.e.occano.api.main.ApiMainService
import com.e.occano.api.main.responses.BlogCreateUpdateResponse
import com.e.occano.api.main.responses.BlogListSearchResponse
import com.e.occano.api.main.responses.DashboardListSearchResponse
import com.e.occano.di.main.MainScope
import com.e.occano.models.AuthToken
import com.e.occano.models.BlogPost
import com.e.occano.models.Cylinder
import com.e.occano.persistence.BlogPostDao
import com.e.occano.persistence.DashboardDao
import com.e.occano.persistence.returnOrderedBlogQuery
import com.e.occano.repository.NetworkBoundResource
import com.e.occano.repository.buildError
import com.e.occano.repository.safeApiCall
import com.e.occano.session.SessionManager
import com.e.occano.ui.main.dashboard.blogstate.BlogViewState
import com.e.occano.ui.main.dashboard.blogstate.BlogViewState.*
import com.e.occano.ui.main.dashboard.state.DashboardViewState
import com.e.occano.utils.*
import com.e.occano.utils.ErrorHandling.Companion.ERROR_UNKNOWN
import com.e.occano.utils.SuccessHandling.Companion.RESPONSE_HAS_PERMISSION_TO_EDIT
import com.e.occano.utils.SuccessHandling.Companion.RESPONSE_NO_PERMISSION_TO_EDIT
import com.e.occano.utils.SuccessHandling.Companion.SUCCESS_BLOG_DELETED
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

@FlowPreview
@MainScope
class DashboardRepositoryImpl
@Inject
constructor(
    val apiMainService: ApiMainService,
    val dashboardDao: DashboardDao,
    val sessionManager: SessionManager
): DashboardRepository
{

    private val TAG: String = "AppDebug"

    override fun getDashboard(stateEvent: StateEvent
    ): Flow<DataState<DashboardViewState>> {
        return object: NetworkBoundResource<DashboardListSearchResponse, List<Cylinder>, DashboardViewState>(
            dispatcher = IO,
            stateEvent = stateEvent,
            apiCall = {
                apiMainService.searchListCylinder(
                )
            },
            cacheCall = {
                dashboardDao.getAllCylinders()
            }
        ){
            override suspend fun updateCache(networkObject: DashboardListSearchResponse) {
                val cylList = networkObject.cylToList()
                withContext(IO) {
                    for(cyl in cylList){
                        try{
                            // Launch each insert as a separate job to be executed in parallel
                            launch {
                                Log.d(TAG, "updateLocalDb: inserting cylinder: ${cyl}")
                                dashboardDao.insert(cyl)
                            }
                        }catch (e: Exception){
                            Log.e(TAG, "updateLocalDb: error updating cache data on cylinder with id: ${cyl.numOfCylInEngine}. " +
                                    "${e.message}")
                            // Could send an error report here or something but I don't think you should throw an error to the UI
                            // Since there could be many blog posts being inserted/updated.
                        }
                    }
                }
            }
//
//            override fun handleCacheSuccess(resultObj: List<BlogPost>): DataState<BlogViewState> {
//                val viewState =
//                    DashboardViewState(
//
//                    )
//                return DataState.data(
//                    response = null,
//                    data = viewState,
//                    stateEvent = stateEvent
//                )
//            }


            override fun handleCacheSuccess(resultObj: List<Cylinder>): DataState<DashboardViewState> {
                val dashboardViewState =
                    DashboardViewState(
                        DashboardViewState.ViewDashboardFields(
                            resultObj, null
                        )
                    )
                return DataState.data(
                    response = null,
                    data = dashboardViewState,
                    stateEvent = stateEvent
                )
            }

        }.result


    }

//    override fun isAuthorOfBlogPost(
//        authToken: AuthToken,
//        slug: String,
//        stateEvent: StateEvent
//    ) = flow {
//        val apiResult = safeApiCall(IO){
//            apiMainService.isAuthorOfBlogPost(
//                "Token ${authToken.token!!}",
//                slug
//            )
//        }
//        emit(
//            object: ApiResponseHandler<BlogViewState, GenericResponse>(
//                response = apiResult,
//                stateEvent = stateEvent
//            ){
//                override suspend fun handleSuccess(resultObj: GenericResponse): DataState<BlogViewState> {
//                    val viewState =
//                        BlogViewState(
//                            viewBlogFields = ViewBlogFields(
//                                isAuthorOfBlogPost = false
//                            )
//                        )
//                    return when {
//
//                        resultObj.response.equals(RESPONSE_NO_PERMISSION_TO_EDIT) -> {
//                            DataState.data(
//                                response = null,
//                                data = viewState,
//                                stateEvent = stateEvent
//                            )
//                        }
//
//                        resultObj.response.equals(RESPONSE_HAS_PERMISSION_TO_EDIT) -> {
//                            viewState.viewBlogFields.isAuthorOfBlogPost = true
//                            DataState.data(
//                                response = null,
//                                data = viewState,
//                                stateEvent = stateEvent
//                            )
//                        }
//
//                        else -> {
//                            buildError(
//                                ERROR_UNKNOWN,
//                                UIComponentType.None(),
//                                stateEvent
//                            )
//                        }
//                    }
//                }
//            }.getResult()
//        )
//    }
//
//    override fun deleteBlogPost(
//        authToken: AuthToken,
//        blogPost: BlogPost,
//        stateEvent: StateEvent
//    ) =  flow {
//        val apiResult = safeApiCall(IO){
//            apiMainService.deleteBlogPost(
//                "Token ${authToken.token!!}",
//                blogPost.slug
//            )
//        }
//        emit(
//            object: ApiResponseHandler<BlogViewState, GenericResponse>(
//                response = apiResult,
//                stateEvent = stateEvent
//            ){
//                override suspend fun handleSuccess(resultObj: GenericResponse): DataState<BlogViewState> {
//
//                    if(resultObj.response == SUCCESS_BLOG_DELETED){
//                        dashboardDao.deleteBlogPost(blogPost)
//                        return DataState.data(
//                            response = Response(
//                                message = SUCCESS_BLOG_DELETED,
//                                uiComponentType = UIComponentType.Toast(),
//                                messageType = MessageType.Success()
//                            ),
//                            stateEvent = stateEvent
//                        )
//                    }
//                    else{
//                        return buildError(
//                            ERROR_UNKNOWN,
//                            UIComponentType.Dialog(),
//                            stateEvent
//                        )
//                    }
//                }
//            }.getResult()
//        )
//    }
//
//    override fun updateBlogPost(
//        authToken: AuthToken,
//        slug: String,
//        title: RequestBody,
//        body: RequestBody,
//        image: MultipartBody.Part?,
//        stateEvent: StateEvent
//    ) = flow{
//
//        val apiResult = safeApiCall(IO){
//            apiMainService.updateBlog(
//                "Token ${authToken.token!!}",
//                slug,
//                title,
//                body,
//                image
//            )
//        }
//        emit(
//            object: ApiResponseHandler<BlogViewState, BlogCreateUpdateResponse>(
//                response = apiResult,
//                stateEvent = stateEvent
//            ){
//                override suspend fun handleSuccess(resultObj: BlogCreateUpdateResponse): DataState<BlogViewState> {
//
//                    val updatedBlogPost = resultObj.toBlogPost()
//
//                    dashboardDao.updateBlogPost(
//                        updatedBlogPost.pk,
//                        updatedBlogPost.title,
//                        updatedBlogPost.body,
//                        updatedBlogPost.image
//                    )
//
//                    return DataState.data(
//                        response = Response(
//                            message = resultObj.response,
//                            uiComponentType = UIComponentType.Toast(),
//                            messageType = MessageType.Success()
//                        ),
//                        data = BlogViewState(
//                            viewBlogFields = ViewBlogFields(
//                                blogPost = updatedBlogPost
//                            ),
//                            updatedBlogFields = UpdatedBlogFields(
//                                updatedBlogTitle = updatedBlogPost.title,
//                                updatedBlogBody = updatedBlogPost.body,
//                                updatedImageUri = null
//                            )
//                        ),
//                        stateEvent = stateEvent
//                    )
//
//                }
//
//            }.getResult()
//        )
//    }



}
