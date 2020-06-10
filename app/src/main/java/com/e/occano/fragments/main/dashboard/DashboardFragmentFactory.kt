package com.e.occano.fragments.main.dashboard

import androidx.fragment.app.FragmentFactory
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.RequestManager
import com.e.occano.di.main.MainScope
import com.e.occano.ui.main.dashboard.BlogFragment
import com.e.occano.ui.main.dashboard.UpdateBlogFragment
import com.e.occano.ui.main.dashboard.ViewBlogFragment
import javax.inject.Inject

@MainScope
class DashboardFragmentFactory
@Inject
constructor(
    private val viewModelFactory: ViewModelProvider.Factory,
    private val requestManager: RequestManager
) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String) =

        when (className) {

            BlogFragment::class.java.name -> {
                BlogFragment(viewModelFactory, requestManager)
            }

            ViewBlogFragment::class.java.name -> {
                ViewBlogFragment(viewModelFactory, requestManager)
            }

            UpdateBlogFragment::class.java.name -> {
                UpdateBlogFragment(viewModelFactory, requestManager)
            }

            else -> {
                BlogFragment(viewModelFactory, requestManager)
            }
        }


}