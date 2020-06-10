package com.e.occano.fragments.main.alerts

import androidx.fragment.app.FragmentFactory
import androidx.lifecycle.ViewModelProvider
import com.e.occano.di.main.MainScope
import com.e.occano.ui.main.alerts.AccountFragment
import com.e.occano.ui.main.alerts.ChangePasswordFragment
import com.e.occano.ui.main.alerts.UpdateAccountFragment
import com.e.occano.ui.main.alerts.status.StatusFragment
import javax.inject.Inject


@MainScope
class AlertsFragmentFactory
@Inject
constructor(
    private val viewModelFactory: ViewModelProvider.Factory
) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String) =

        when (className) {

            StatusFragment::class.java.name -> {
                StatusFragment(viewModelFactory)
            }
            else -> {
                StatusFragment(viewModelFactory)
            }
        }


}