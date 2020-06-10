package com.e.occano.di.main

import androidx.fragment.app.FragmentFactory
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import com.e.occano.fragments.main.account.AccountFragmentFactory
import com.e.occano.fragments.main.alerts.AlertsFragmentFactory
import com.e.occano.fragments.main.blog.BlogFragmentFactory
import com.e.occano.fragments.main.createBlog.CreateBlogFragmentFactory
import com.e.occano.fragments.main.dashboard.DashboardFragmentFactory
import com.e.occano.fragments.main.graphs.GraphFragmentFactory
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
object MainFragmentsModule {

    @JvmStatic
    @MainScope
    @Provides
    @Named("AccountFragmentFactory")
    fun provideAccountFragmentFactory(
        viewModelFactory: ViewModelProvider.Factory
    ): FragmentFactory {
        return AccountFragmentFactory(
            viewModelFactory
        )
    }

    @JvmStatic
    @MainScope
    @Provides
    @Named("BlogFragmentFactory")
    fun provideBlogFragmentFactory(
        viewModelFactory: ViewModelProvider.Factory,
        requestManager: RequestManager
    ): FragmentFactory {
        return BlogFragmentFactory(
            viewModelFactory,
            requestManager
        )
    }

    @JvmStatic
    @MainScope
    @Provides
    @Named("CreateBlogFragmentFactory")
    fun provideCreateBlogFragmentFactory(
        viewModelFactory: ViewModelProvider.Factory,
        requestManager: RequestManager
    ): FragmentFactory {
        return CreateBlogFragmentFactory(
            viewModelFactory,
            requestManager
        )
    }



    @JvmStatic
    @MainScope
    @Provides
    @Named("AlertsFragmentFactory")
    fun provideAlertFragmentFactory(
        viewModelFactory: ViewModelProvider.Factory
    ): FragmentFactory {
        return AlertsFragmentFactory(
            viewModelFactory
        )
    }

    @JvmStatic
    @MainScope
    @Provides
    @Named("DashboardFragmentFactory")
    fun provideDashboardFragmentFactory(
        viewModelFactory: ViewModelProvider.Factory,
        requestManager: RequestManager
    ): FragmentFactory {
        return DashboardFragmentFactory(
            viewModelFactory,
            requestManager
        )
    }

    @JvmStatic
    @MainScope
    @Provides
    @Named("GraphFragmentFactory")
    fun provideGraphFragmentFactory(
        viewModelFactory: ViewModelProvider.Factory,
        requestManager: RequestManager
    ): FragmentFactory {
        return GraphFragmentFactory(
            viewModelFactory,
            requestManager
        )
    }

}