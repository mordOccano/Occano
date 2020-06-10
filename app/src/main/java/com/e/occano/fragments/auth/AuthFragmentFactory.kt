package com.e.occano.fragments.auth

import androidx.fragment.app.FragmentFactory
import androidx.lifecycle.ViewModelProvider
import com.e.occano.di.auth.AuthScope
import com.e.occano.ui.auth.ForgotPasswordFragment
import com.e.occano.ui.auth.LauncherFragment
import com.e.occano.ui.auth.LoginUserFragment
import com.e.occano.ui.auth.RegisterUserFragment
import javax.inject.Inject

@AuthScope
class AuthFragmentFactory
@Inject
constructor(
    private val viewModelFactory: ViewModelProvider.Factory
) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String) =

        when (className) {

            LauncherFragment::class.java.name -> {
                LauncherFragment(viewModelFactory)
            }

            LoginUserFragment::class.java.name -> {
                LoginUserFragment(viewModelFactory)
            }

            RegisterUserFragment::class.java.name -> {
                RegisterUserFragment(viewModelFactory)
            }

            ForgotPasswordFragment::class.java.name -> {
                ForgotPasswordFragment(viewModelFactory)
            }

            else -> {
                LauncherFragment(viewModelFactory)
            }
        }


}
