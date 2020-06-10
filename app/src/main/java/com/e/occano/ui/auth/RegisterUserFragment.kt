package com.e.occano.ui.auth


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController

import com.e.occano.R
import com.e.occano.di.auth.AuthScope
import com.e.occano.ui.auth.state.AuthStateEvent
import com.e.occano.ui.auth.state.RegistrationFields
import kotlinx.android.synthetic.main.fragment_login_user.*
import kotlinx.android.synthetic.main.fragment_user_register.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import javax.inject.Inject

@FlowPreview
@ExperimentalCoroutinesApi
@AuthScope
class RegisterUserFragment
@Inject
constructor(
    viewModelFactory: ViewModelProvider.Factory
): BaseAuthFragment(R.layout.fragment_user_register, viewModelFactory),
 View.OnClickListener {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListeners()

        Log.d(TAG, "RegisterUserFragment: ${viewModel.hashCode()}")

        subscribeObservers()

    }

    fun subscribeObservers(){
        viewModel.viewState.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            it.registrationFields?.let {
                it.registration_email?.let {et_email_register.setText(it)}
                it.registration_username?.let {et_email_register.setText(it)}
                it.registration_password?.let {et_pass_register.setText(it)}
                it.registration_confirm_password?.let {et_valid_pass_register.setText(it)}
            }
        })
    }

    fun register(){
        viewModel.setStateEvent(
            AuthStateEvent.RegisterAttemptEvent(
                et_email_register.text.toString(),
                et_email_register.text.toString(),
                et_pass_register.text.toString(),
                et_valid_pass_register.text.toString()
            )
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.setRegistrationFields(
            RegistrationFields(
                et_email_register.text.toString(),
                et_email_register.text.toString(),
                et_pass_register.text.toString(),
                et_valid_pass_register.text.toString()
            )
        )

    }

    private fun setListeners() {
        buttonRegister.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.buttonRegister -> {
                register()
                findNavController().navigate(R.id.action_loginUserFragment_to_loginVesselFragment)
            }

        }

    }


}
