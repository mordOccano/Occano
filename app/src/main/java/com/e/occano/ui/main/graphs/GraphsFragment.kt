package com.e.occano.ui.main.graphs

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.TranslateAnimation
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.RequestManager
import com.e.occano.R
import com.e.occano.utils.Response
import com.e.occano.utils.Constants
import com.e.occano.utils.DataState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.ClassCastException
import javax.inject.Inject

class GraphsFragment
    @Inject
    constructor(
        viewModelFactory: ViewModelProvider.Factory,
        private val requestManager: RequestManager
): BaseGraphFragment(R.layout.fragment_graphs, viewModelFactory)
{

    lateinit var webView: WebView

//    lateinit var stateChangeListener: DataStateChangeListener

//    val webInteractionCallback: WebAppInterfaceGraphs.OnWebInteractionCallback = object :
//        WebAppInterfaceGraphs.OnWebInteractionCallback {
//        override fun onSuccess(email: String) {
//            Log.d(TAG,"onSuccess: a reset link will be sent to $email")
//            onPasswordResetLinkSent()
//        }
//
//        override fun onError(errorMessage: String) {
//            Log.d(TAG,"onError: $errorMessage")
//
//            val dataState = DataState.error<Any>(
//                response = Response(
//                    errorMessage,
//                    ResponseType.Dialog()
//                )
//            )
//            stateChangeListener.onDataStateChange(
//                dataState = dataState
//            )
//
//        }
//
//        override fun onLoading(isLoading: Boolean) {
//            Log.e(TAG,"on loading..")
//            GlobalScope.launch (Dispatchers.Main){
//                stateChangeListener.onDataStateChange(
//                    DataState.loading(isLoading = isLoading, cachedData = null)
//                )
//            }
//        }
//
//    }

    private fun onPasswordResetLinkSent() {
//        GlobalScope.launch(Dispatchers.Main) {
//            parent_view.removeView(webView)
//            webView.destroy()
//
//            val animation = TranslateAnimation(
//                password_reset_done_container.width.toFloat(),
//                0f,
//                0f,
//                0f
//            )
//
//            animation.duration = 500
//            password_reset_done_container.startAnimation(animation)
//            password_reset_done_container.visibility = View.VISIBLE
//        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_graphs,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        webView = view.findViewById(R.id.webview)
//        Log.d(TAG,"ForgotPasswordFragment: ${viewModel}")

//        return_to_launcher_fragment.setOnClickListener{
//            findNavController().popBackStack()
//        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    fun loadWebView(){
//        stateChangeListener.onDataStateChange(
//            DataState.loading(true, cachedData = null)
//        )

        webView.webViewClient = object : WebViewClient(){
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
//                stateChangeListener.onDataStateChange(DataState.loading(false, null))
            }
        }

        webView.loadUrl(Constants.PASSWORD_RESET_URL)
        webView.settings.javaScriptEnabled = true
//        webView.addJavascriptInterface(WebAppInterfaceGraphs(webInteractionCallback),"AndroidTextListener")
    }


    class WebAppInterfaceGraphs
    constructor(
        private val callback: OnWebInteractionCallback
    ){
        private val TAG : String = "AppDebug"

        @JavascriptInterface
        fun onSuccess(email: String){
            callback.onSuccess(email)
        }
        @JavascriptInterface
        fun onError(errorMessage: String){
            callback.onError(errorMessage)
        }
        @JavascriptInterface
        fun onLoading(isLoading: Boolean){
            callback.onLoading(isLoading)
        }




        interface OnWebInteractionCallback{
            fun onSuccess(email: String)

            fun onError(errorMessage: String)

            fun onLoading(isLoading: Boolean)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
//            stateChangeListener = context as DataStateChangeListener
        }catch (e: ClassCastException){
            Log.e(TAG,"$context must implement DataStateChangeListener")
        }
    }
}
