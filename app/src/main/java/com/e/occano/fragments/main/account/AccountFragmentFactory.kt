package com.e.occano.fragments.main.account

import androidx.fragment.app.FragmentFactory
import androidx.lifecycle.ViewModelProvider
import com.e.occano.di.main.MainScope
import com.e.occano.ui.main.alerts.AccountFragment
import com.e.occano.ui.main.alerts.ChangePasswordFragment
import com.e.occano.ui.main.alerts.UpdateAccountFragment
import javax.inject.Inject


@MainScope
class AccountFragmentFactory
@Inject
constructor(
    private val viewModelFactory: ViewModelProvider.Factory
) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String) =

        when (className) {

            AccountFragment::class.java.name -> {
                AccountFragment(viewModelFactory)
            }

            ChangePasswordFragment::class.java.name -> {
                ChangePasswordFragment(viewModelFactory)
            }

            UpdateAccountFragment::class.java.name -> {
                UpdateAccountFragment(viewModelFactory)
            }

            else -> {
                AccountFragment(viewModelFactory)
            }
        }


}