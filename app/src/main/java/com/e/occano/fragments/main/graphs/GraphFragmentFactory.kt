package com.e.occano.fragments.main.graphs

import androidx.fragment.app.FragmentFactory
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.RequestManager
import com.e.occano.di.main.MainScope
import com.e.occano.ui.main.alerts.AccountFragment
import com.e.occano.ui.main.dashboard.BlogFragment
import com.e.occano.ui.main.dashboard.UpdateBlogFragment
import com.e.occano.ui.main.dashboard.ViewBlogFragment
import com.e.occano.ui.main.graphs.CreateBlogFragment
import javax.inject.Inject

@MainScope
class GraphFragmentFactory
@Inject
constructor(
    private val viewModelFactory: ViewModelProvider.Factory,
    private val requestManager: RequestManager
) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String) =

        when (className) {

            CreateBlogFragment::class.java.name -> {
                CreateBlogFragment(viewModelFactory, requestManager)
            }

            else -> {
                CreateBlogFragment(viewModelFactory, requestManager)
            }
        }


}