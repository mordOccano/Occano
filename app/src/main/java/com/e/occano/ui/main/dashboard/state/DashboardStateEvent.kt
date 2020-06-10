package com.e.occano.ui.main.dashboard.state

import com.e.occano.utils.StateEvent
import okhttp3.MultipartBody

sealed class DashboardStateEvent: StateEvent {

    class dashboardSearchEvent(
        val clearLayoutManagerState: Boolean = true
    ) : DashboardStateEvent() {
        override fun errorInfo(): String {
            return "Error searching for blog posts."
        }

        override fun toString(): String {
            return "BlogSearchEvent"
        }
    }

    class CheckIfPermitToCalib: DashboardStateEvent() {
        override fun errorInfo(): String {
            return "Error checking if you are the author of this blog post."
        }

        override fun toString(): String {
            return "CheckAuthorOfBlogPost"
        }

    }

    class DeleteGaugeEvent: DashboardStateEvent() {
        override fun errorInfo(): String {
            return "Error deleting that blog post."
        }

        override fun toString(): String {
            return "DeleteBlogPostEvent"
        }
    }

    data class CalibrationEvent(
        val title: String,
        val body: String,
        val image: MultipartBody.Part?
    ): DashboardStateEvent() {
        override fun errorInfo(): String {
            return "Error updating that blog post."
        }

        override fun toString(): String {
            return "UpdateBlogPostEvent"
        }

    }

    class None: DashboardStateEvent() {
        override fun errorInfo(): String {
            return "None."
        }
    }
}