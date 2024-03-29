package com.e.occano

import android.app.Application
import com.e.occano.di.AppComponent
import com.e.occano.di.DaggerAppComponent
import com.e.occano.di.auth.AuthComponent
import com.e.occano.di.main.MainComponent


class BaseApplication : Application(){

    lateinit var appComponent: AppComponent

    private var authComponent: AuthComponent? = null

    private var mainComponent: MainComponent? = null

    override fun onCreate() {
        super.onCreate()
        initAppComponent()
    }

    fun releaseMainComponent(){
        mainComponent = null
    }

    fun mainComponent(): MainComponent {
        if(mainComponent == null){
            mainComponent = appComponent.mainComponent().create()
        }
        return mainComponent as MainComponent
    }

    fun releaseAuthComponent(){
        authComponent = null
    }

    fun authComponent(): AuthComponent {
        if(authComponent == null){
            authComponent = appComponent.authComponent().create()
        }
        return authComponent as AuthComponent
    }

    fun initAppComponent(){
            appComponent = DaggerAppComponent.builder()
            .application(this)
            .build()
    }
}