package com.e.occano.ui.main.graphs.state

import com.e.occano.utils.StateEvent
import okhttp3.MultipartBody


sealed class CreateBlogStateEvent: StateEvent {

    data class CreateNewBlogEvent(
        val title: String,
        val body: String,
        val image: MultipartBody.Part
    ): CreateBlogStateEvent() {
        override fun errorInfo(): String {
            return "Unable to create a new blog post."
        }

        override fun toString(): String {
            return "CreateBlogStateEvent"
        }
    }

    class None: CreateBlogStateEvent() {
        override fun errorInfo(): String {
            return "None."
        }
    }
}