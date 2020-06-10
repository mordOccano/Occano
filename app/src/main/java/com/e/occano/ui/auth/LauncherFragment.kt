package com.e.occano.ui.auth


import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController

import com.e.occano.R
import com.e.occano.di.auth.AuthScope
import kotlinx.android.synthetic.main.fragment_launcher.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import javax.inject.Inject

@FlowPreview
@ExperimentalCoroutinesApi
@AuthScope
class LauncherFragment
@Inject
constructor(
    viewModelFactory: ViewModelProvider.Factory
): BaseAuthFragment(R.layout.fragment_launcher, viewModelFactory),View.OnClickListener {

    lateinit var navController: NavController
    var ip:String? = "office.occano.io:4000"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_launcher,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        setListeners(view)

        Log.d(TAG, "LauncherFragment: ${viewModel.hashCode()}")

    }

    private fun setListeners(view: View) {
        view.findViewById<ImageButton>(R.id.login_btn_launcher).setOnClickListener(this)
        view.findViewById<ImageButton>(R.id.btn_set_ip).setOnClickListener(this)
        view.findViewById<ImageButton>(R.id.btn_open_set_ip).setOnClickListener(this)
        view.findViewById<View>(R.id.focusable_view).requestFocus()
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.login_btn_launcher -> {
                login_btn_launcher.isClickable = false
                findNavController().navigate(R.id.action_launcherFragment_to_loginUserFragment)
            }
            R.id.btn_open_set_ip -> {
                et_set_ip.visibility = View.VISIBLE
                btn_open_set_ip.visibility = View.GONE
                btn_set_ip.visibility = View.VISIBLE
                btn_launcher_to_register.visibility = View.GONE
            }
            R.id.btn_set_ip -> {
                btn_set_ip.isClickable = false
                login_btn_launcher.isClickable = false
                if (!et_set_ip.text.isNullOrEmpty()){
//                    StaticAddress.Ip = et_set_ip.text.toString(
                    saveAddressProperties()
                    Toast.makeText(this.context, " הכתובת $ip " + " הוזנה ", Toast.LENGTH_SHORT).show()
                    login_btn_launcher.isClickable = true
                }

                et_set_ip.visibility = View.GONE
                btn_open_set_ip.visibility = View.VISIBLE
                btn_set_ip.visibility = View.GONE
                btn_launcher_to_register.visibility = View.VISIBLE
                btn_set_ip.isClickable = true
            }
            else -> {
                btn_set_ip.isClickable = false
            }
        }
    }

    fun saveAddressProperties(){
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        with (sharedPref.edit()) {
            putString("ip", et_set_ip.text.toString())
            commit()
        }

        getAddress()
    }

    fun getAddress(){
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        val defaultValue = resources.getString(R.string.default_ip)
        ip = sharedPref.getString("ip", defaultValue)
    }

}
