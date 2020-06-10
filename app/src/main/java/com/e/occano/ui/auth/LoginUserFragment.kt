package com.e.occano.ui.auth


import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController

import com.e.occano.R
import com.e.occano.di.auth.AuthScope
import com.e.occano.ui.auth.state.AuthStateEvent.*
import com.e.occano.ui.auth.state.LoginFields
import kotlinx.android.synthetic.main.fragment_login_user.*

import kotlinx.android.synthetic.main.fragment_launcher.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import javax.inject.Inject

@FlowPreview
@ExperimentalCoroutinesApi
@AuthScope
class LoginUserFragment
@Inject
constructor(
    viewModelFactory: ViewModelProvider.Factory
): BaseAuthFragment(R.layout.fragment_login_user, viewModelFactory)
,View.OnClickListener {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
        Log.d(TAG, "LoginUserFragment: ${viewModel.hashCode()}")

        subscribeObservers()
    }

    fun subscribeObservers(){
        viewModel.viewState.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            it.loginFields?.let {
                it.login_email?.let {et_email_login.setText(it)}
                it.login_password?.let {et_pass_login.setText(it)}
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.setLoginFields(
            LoginFields(
                et_email_login.text.toString(),
                et_pass_login.text.toString()
            )
        )

    }

    private fun setListeners() {
        button_login.setOnClickListener(this)
        forgot_password.setOnClickListener(this)
    }

    override fun onClick(v: View?) {

        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        val defaultValue = resources.getString(R.string.default_ip)
        val ip = sharedPref.getString("ip", defaultValue)

        when(v!!.id){
            R.id.button_login -> {
                loginUser()
//                Toast.makeText(context,
//                    ip, Toast.LENGTH_LONG).show()
                findNavController().navigate(R.id.action_loginUserFragment_to_loginVesselFragment)
            }
            R.id.forgot_password -> {
                findNavController().navigate((R.id.action_loginUserFragment_to_forgotPasswordFragment))
            }
        }
    }

    fun loginUser(){
        viewModel.setStateEvent(
            LoginAttemptEvent(
                et_email_login.text.toString(),
                et_pass_login.text.toString()
            )
        )
    }

//    fun getAddress(){
//        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
//        val defaultValue = resources.getString(R.string.default_ip)
//        val ip = sharedPref.getString("ip", defaultValue)
//    }
}