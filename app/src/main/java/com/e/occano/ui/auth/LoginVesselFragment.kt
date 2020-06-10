package com.e.occano.ui.auth


import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.*

import com.e.occano.R
import com.e.occano.di.auth.AuthScope
import com.e.occano.models.Ship
import com.e.occano.ui.auth.state.LoginAsVesselFields
import com.e.occano.ui.main.MainActivity
import com.e.occano.utils.TopSpacingItemDecoration
import kotlinx.android.synthetic.main.fragment_login.*
import kotlin.collections.ArrayList
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import javax.inject.Inject



@FlowPreview
@ExperimentalCoroutinesApi
@AuthScope
class LoginVesselFragment
@Inject
constructor(
    viewModelFactory: ViewModelProvider.Factory
): BaseAuthFragment(R.layout.fragment_login, viewModelFactory)
,LoginVesselAdapter.Interaction, View.OnClickListener{

    private var imo: String? = null
    var ip:String? = null
    var selectedShip = Ship()
    var ship1 = Ship()
    var ship2 = Ship()
    var ship3 = Ship()
    var ship4 = Ship()
    var ship5 = Ship()
    var ship6 = Ship()
    var ship7 = Ship()
    var ship8 = Ship()

    private var requestQueue: RequestQueue? = null
    var shipList =  ArrayList<Ship>()
    lateinit var loginAdapter:LoginVesselAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getAddress()
        setListeners(view)
        initRv()
        initList()

        this.loginAdapter.submitList(shipList)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.setLoginAsVesselFields(
            LoginAsVesselFields(
                selectedShip.vessel,
                selectedShip.m_e,
                selectedShip.displacement_engine,
                selectedShip.Number_of_cylinders,
                selectedShip.Stroke_bore_ratio,
                selectedShip.Diameter_of_piston_in_cm,
                selectedShip.Concept,
                selectedShip.Design,
                selectedShip.IMO,
                selectedShip.AIS_Vessel_Type,
                selectedShip.Year_Built,
                selectedShip.Length_Overall,
                selectedShip.Breadth_Extreme,
                selectedShip.Deadweight,
                selectedShip.Gross_Tonnage
            )
        )

    }


    private fun setListeners(view: View) {
        view.findViewById<ImageButton>(R.id.button_login_vessel).setOnClickListener(this)
    }

    private fun initRv() {
        rv_login.apply {
            layoutManager = LinearLayoutManager(activity)
            val topSpacingItemDecoration = TopSpacingItemDecoration(10)
            addItemDecoration(topSpacingItemDecoration)
            loginAdapter = LoginVesselAdapter(this@LoginVesselFragment)
            adapter = loginAdapter
        }

    }

    private fun initList() {


        //TODO(
        // 1. check if the vessel_db is exist
        // 2. read the params from csv file
        // 3. parsing that csv to java object there is no meaning now for something else
        // 4. further display for the user just the vessels are belong his current duty)



        ship1 = Ship(0, "ANAFI WARRIOR",	"MITSUI MAN B&W 6S60 MC-C","4071.5",6,"s","60","c",	"c","9370848",	"Tanker","2009","243.8","42","107593","60205",false)
        ship2 = Ship(1, "GREEN WARRIOR",	"MITSUI MAN B&W 6S60 MC-C","4071.5",6,	"s",	"60"	,"c"	,"c"	,"9514169"	,"Tanker"	,"2011"	,"229"	,"42.04"	,"104626"	,"56326", false)
        ship3 = Ship(2, "PATMOS WARRIOR","D.U. SULZER 6RTA58T","3830",6,"4.17","58","","","9337418",	"Tanker",	"2007","239","42.03","105572","56172",false)
        ship4 = Ship(3, "SYROS WARRIOR","MITSUI MAN B&W 6S60 MC-C","4071.5",6,"s",	"60",	"c"	,"c"	,"9370850"	,"Tanker"	,"2009"	,"243.8",	"42","107687",	"60205",false)
        ship5 = Ship(4, "DILIGENT WARRIOR",	"HYUNDAI MAN B&W 6G70 ME-C9.5",	"7518.3",	6,	"g",	"70",	"e",	"c",	"9750050",	"Tanker",	"2016",	"274.22","48",  "149992",	"81287", false)
        ship6 = Ship(5, "FAITHFUL WARRIOR",	"HYUNDAI MAN B&W 6G70 ME-C9.5",	"7518.3",	6,	"g",	"70",	"e",	"c",	"9750062",	"Tanker",	"2016",	"274.22","48",  "149992",	"81287", false)
        ship7 = Ship(6, "PRUDENT WARRIOR",	"HYUNDAI MAN B&W 6G70 ME-C9.5",	"7518.3",	6,	"g",	"70",	"e",	"c",	"9753545",	"Tanker",	"2017",	"274",	   "48","149992",	"81287", false)
        ship8 = Ship(7, "RELIABLE WARRIOR",	"HYUNDAI MAN B&W 6G70 ME-C9.5",	"7518.3",	6,	"g",	"70",	"e",	"c",	"9753557",	"Tanker",	"2017",	"274.22","48",  "149992",	"81287", false)

        shipList.add(ship1)
        shipList.add(ship2)
        shipList.add(ship3)
        shipList.add(ship4)
        shipList.add(ship5)
        shipList.add(ship6)
        shipList.add(ship7)
        shipList.add(ship8)
    }

    override fun onItemSelected(position: Int, item: Ship) {

        for (stamShip: Ship in shipList){
            stamShip.isSelected = false
        }
        item.isSelected = true
        selectedShip = item

        loginAdapter.notifyDataSetChanged()
    }



    override fun onClick(v: View?) {

        when(v!!.id){
            R.id.button_login_vessel -> {
                try {
                    loginShip()
                    saveShipProperties()
                    navMainActivity()
                }  catch (e:Error){
                    e.printStackTrace()
                }
            }
        }
    }

    private fun navMainActivity() {
        val intent = Intent(context, MainActivity::class.java)
        startActivity(intent)
    }


    private fun loginShip() {
        viewModel.setLoginAsVesselFields(
            LoginAsVesselFields(
                selectedShip.vessel,
                selectedShip.m_e,
                selectedShip.displacement_engine,
                selectedShip.Number_of_cylinders,
                selectedShip.Stroke_bore_ratio,
                selectedShip.Diameter_of_piston_in_cm,
                selectedShip.Concept,
                selectedShip.Design,
                selectedShip.IMO,
                selectedShip.AIS_Vessel_Type,
                selectedShip.Year_Built,
                selectedShip.Length_Overall,
                selectedShip.Breadth_Extreme,
                selectedShip.Deadweight,
                selectedShip.Gross_Tonnage
            )
        )
    }

    fun getAddress(){
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        val defaultValue = resources.getString(R.string.default_ip)
        ip = sharedPref.getString("ip", defaultValue)
    }

    fun saveShipProperties(){
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        with (sharedPref.edit()) {
            putString("nameOfCurrentShip", selectedShip.vessel)
            putString("nameOfCurrentEngine", selectedShip.m_e)
            commit()
        }
    }
}