package com.e.occano.ui

import com.e.occano.utils.Response
import com.e.occano.utils.StateMessageCallback


interface UICommunicationListener {

    fun onResponseReceived(
        response: Response,
        stateMessageCallback: StateMessageCallback
    )

    fun displayProgressBar(isLoading: Boolean)

    fun expandAppBar()

    fun hideSoftKeyboard()

    fun isStoragePermissionGranted(): Boolean
}