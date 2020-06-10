package com.e.occano.di

import android.app.Application
import com.e.occano.di.auth.AuthComponent
import com.e.occano.di.main.MainComponent
import com.e.occano.session.SessionManager
import com.e.occano.ui.BaseActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        SubComponentsModule::class
    ]
)
interface AppComponent  {

    val sessionManager: SessionManager

    @Component.Builder
    interface Builder{

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(baseActivity: BaseActivity)

    fun authComponent(): AuthComponent.Factory

    fun mainComponent(): MainComponent.Factory

}


