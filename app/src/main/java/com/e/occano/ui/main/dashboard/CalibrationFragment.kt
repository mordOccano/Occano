package com.e.occano.ui.main.dashboard

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.bumptech.glide.RequestManager
import com.e.occano.R
import com.e.occano.models.BlogPost
import com.e.occano.models.GaugeForCalibration
import com.e.occano.utils.Constants.Companion.max_bmep_gauge
import com.e.occano.utils.Constants.Companion.max_break_power_gauge
import com.e.occano.utils.Constants.Companion.max_comp_pres_gauge
import com.e.occano.utils.Constants.Companion.max_engine_speed_gauge
import com.e.occano.utils.Constants.Companion.max_exhaust_gauge
import com.e.occano.utils.Constants.Companion.max_firing_pres_gauge
import com.e.occano.utils.Constants.Companion.max_fuel_gauge
import com.e.occano.utils.Constants.Companion.max_imep_gauge
import com.e.occano.utils.Constants.Companion.max_injec_gauge
import com.e.occano.utils.Constants.Companion.max_load_gauge
import com.e.occano.utils.Constants.Companion.max_scave_gauge
import com.e.occano.utils.Constants.Companion.max_torque_gauge
import com.github.anastr.speedviewlib.AwesomeSpeedometer
import com.kevalpatel2106.rulerpicker.RulerValuePicker
import com.kevalpatel2106.rulerpicker.RulerValuePickerListener
import kotlinx.android.synthetic.main.fragment_calibration.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import javax.inject.Inject


@FlowPreview
@ExperimentalCoroutinesApi
class CalibrationFragment
@Inject
constructor(
    viewModelFactory: ViewModelProvider.Factory,
    private val requestManager: RequestManager
): BaseDashboardFragment(R.layout.fragment_calibration, viewModelFactory),
    BlogListAdapter.Interaction,
    SwipeRefreshLayout.OnRefreshListener,
    View.OnClickListener{

    lateinit var gaugeForCalibration: GaugeForCalibration
    lateinit var rulerValuePicker: RulerValuePicker
     var ip:String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_calibration,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initValue()
        setListeners()
        getAddress()
        getGaugeToCalib()
        initUI()
        setGauges()
        litenToRule()
    }

    override fun onResume() {
        super.onResume()
        initValue()
        initUI()
        setGauges()
    }

    private fun setListeners() {
        view!!.findViewById<ImageButton>(R.id.send_data_to_calibration).setOnClickListener(this)
        view!!.findViewById<ImageButton>(R.id.btn_calib_to_dash).setOnClickListener(this)
        view!!.findViewById<ImageButton>(R.id.send_data_to_calibration).isClickable = false    }

    private fun initValue() {
        gaugeForCalibration = GaugeForCalibration()
        getGaugeToCalib()
        calib_d_gauge_name.text = gaugeForCalibration.viewed_field
    }

    private fun initUI(){
        rulerValuePicker = view!!.findViewById(R.id.height_ruler_picker)
        rulerValuePicker.setMinMaxValue(0,100)
        view!!.findViewById<TextView>(R.id.tv_num_data_to_calibrate).text = gaugeForCalibration.viewed_value.toString()
        view!!.findViewById<AwesomeSpeedometer>(R.id.calib_gauge).speedTo(tv_num_data_to_calibrate.text.toString().toFloat())

    }

    private fun litenToRule() {
        height_ruler_picker.setValuePickerListener(object : RulerValuePickerListener {
            override fun onValueChange(value: Int) {
                tv_num_data_to_calibrate.text = value.toString()
                calib_gauge.speedTo(value.toFloat())
                send_data_to_calibration.isClickable = true
            }

            override fun onIntermediateValueChange(selectedValue: Int) {
                //Value changed but the user is still scrolling the ruler.
                //This value is not final value. You can utilize this value to display the current selected value.
                send_data_to_calibration.isClickable = false
                tv_num_data_to_calibrate.text = selectedValue.toString()
                calib_gauge.speedTo(selectedValue.toFloat())
            }
        })
    }

    private fun setGauges() {
//        calib_gauge.speedTo(this.gaugeForCalibration.value)
//        setGaugeCali()

        when (this.gaugeForCalibration.viewed_field){
            "torque_gauge"-> {
                rulerValuePicker.setMinMaxValue(0, max_torque_gauge.toInt())
                rulerValuePicker.selectValue(this.gaugeForCalibration.viewed_value.toInt())
                calib_d_gauge_name.text = "torque"
                calib_gauge.setUnit("KN/m")
                calib_gauge.setMaxSpeed(max_torque_gauge)
//                return
            }
            "load_gauge"-> {
                calib_d_gauge_name.text = "load"
                calib_gauge.setUnit("%")
                calib_gauge.setMaxSpeed(max_load_gauge)
                rulerValuePicker.setMinMaxValue(0, max_load_gauge.toInt())
                rulerValuePicker.selectValue(this.gaugeForCalibration.viewed_value.toInt())
//                return
            }
            "firing_pres_gauge"-> {
                calib_d_gauge_name.text = "firing pressure"
                calib_gauge.setUnit("bar")
                calib_gauge.setMaxSpeed(max_firing_pres_gauge)
                rulerValuePicker.setMinMaxValue(0, max_firing_pres_gauge.toInt())
                rulerValuePicker.selectValue(this.gaugeForCalibration.viewed_value.toInt())
//                return

            }
            "bmep_gauge"-> {
                calib_d_gauge_name.text = "bmep"
                calib_gauge.setUnit("bar")
                calib_gauge.setMaxSpeed(max_bmep_gauge)
                rulerValuePicker.setMinMaxValue(0, max_bmep_gauge.toInt())
                rulerValuePicker.selectValue(this.gaugeForCalibration.viewed_value.toInt())
//                return

            }
            "break_power_gauge"-> {
                calib_d_gauge_name.text = "break power"
                calib_gauge.setUnit("Kw")
                calib_gauge.setMaxSpeed(max_break_power_gauge)
                rulerValuePicker.setMinMaxValue(0, max_break_power_gauge.toInt())
                rulerValuePicker.selectValue(this.gaugeForCalibration.viewed_value.toInt())
//                return

            }
            "comp_pres_gauge"-> {
                calib_d_gauge_name.text = "comppression presssure"
                calib_gauge.setUnit("bar")
                calib_gauge.setMaxSpeed(max_comp_pres_gauge)
                rulerValuePicker.setMinMaxValue(0, max_comp_pres_gauge.toInt())
                rulerValuePicker.selectValue(this.gaugeForCalibration.viewed_value.toInt())
//                return

            }
            "engine_speed_gauge"-> {
                calib_d_gauge_name.text = "engine speed"
                calib_gauge.setUnit("Rpm")
                calib_gauge.setMaxSpeed(max_engine_speed_gauge)
                //
                rulerValuePicker.setMinMaxValue(0, max_engine_speed_gauge.toInt())
                rulerValuePicker.selectValue(this.gaugeForCalibration.viewed_value.toInt())
//                return


            }
            "exhaust_gauge"-> {
                calib_d_gauge_name.text = "exhaust"
                calib_gauge.setUnit("C°")
                calib_gauge.setMaxSpeed(max_exhaust_gauge)
                rulerValuePicker.setMinMaxValue(0, max_exhaust_gauge.toInt())
                rulerValuePicker.selectValue(this.gaugeForCalibration.viewed_value.toInt())
//                return


            }
            "fuel_gauge"-> {
                calib_d_gauge_name.text = "fuel"
                calib_gauge.setUnit("Kg/h")
                calib_gauge.setMaxSpeed(max_fuel_gauge)
                rulerValuePicker.setMinMaxValue(0, max_fuel_gauge.toInt())
                rulerValuePicker.selectValue(this.gaugeForCalibration.viewed_value.toInt())
//                return


            }
            "imep_gauge"-> {
                calib_d_gauge_name.text = "imep"
                calib_gauge.setUnit("")
                calib_gauge.setMaxSpeed(max_imep_gauge)
                rulerValuePicker.setMinMaxValue(0, max_imep_gauge.toInt())
                rulerValuePicker.selectValue(this.gaugeForCalibration.viewed_value.toInt())
//                return


                //TODO: update the unit
            }
            "injec_gauge"-> {
                calib_d_gauge_name.text = "injec"
                calib_gauge.setUnit("bar")
                calib_gauge.setMaxSpeed(max_injec_gauge)
                calib_gauge.trembleDegree = 0.01f
                rulerValuePicker.setMinMaxValue(0, max_injec_gauge.toInt())
                rulerValuePicker.selectValue(this.gaugeForCalibration.viewed_value.toInt())
//                return


            }
            "scave_gauge"-> {
                calib_d_gauge_name.text = "scave"
                calib_gauge.setUnit("bar")
                calib_gauge.setMaxSpeed(max_scave_gauge)
                rulerValuePicker.setMinMaxValue(0, max_scave_gauge.toInt())
                rulerValuePicker.selectValue(this.gaugeForCalibration.viewed_value.toInt())
//                return


            }
            else -> {
                calib_d_gauge_name.text = this.gaugeForCalibration.viewed_field
                calib_gauge.setUnit("")
                calib_gauge.setMaxSpeed(this.gaugeForCalibration.viewed_value*2)
                rulerValuePicker.setMinMaxValue(0,(this.gaugeForCalibration.viewed_value*2).toInt())
                rulerValuePicker.selectValue(this.gaugeForCalibration.viewed_value.toInt())
//                return


            }

        }
        calib_gauge.tickNumber = 9
    }

    override fun onClick(v: View?) {
        when (v!!.id){
            R.id.send_data_to_calibration -> {
                sendDataForCalib()
                Toast.makeText(this.context,"manual correction sent", Toast.LENGTH_SHORT).show()

//                if (findNavController().currentDestination?.id == R.id.calibrationFragmentZ) {
//                    findNavController().navigate(R.id.action_calibrationFragmentZ_to_dashFragment)}
//                else if (findNavController().currentDestination?.id==R.id.subDadhboardContainer){
//                    findNavController().navigate(R.id.action_subDadhboardContainer_to_dashFragment)
                }
//                var gauge = GaugeForCalibration(
//                    this.gaugeForCalibration.viewed_field,
//                    tv_num_data_to_calibrate.text.toString().toFloat()
//                )
//                val bundle = bundleOf("gauge_from_manual" to gauge)
//                findNavController().navigate(R.id.action_calibrationFragmentZ_to_subDadhboardContainer
////                    ,bundle
//                )


            }

//            R.id.btn_calib_to_dash
//        -> {
//                if (findNavController().currentDestination?.id == R.id.calibrationFragmentZ) {
//                    findNavController().navigate(R.id.action_calibrationFragmentZ_to_dashFragment)}
//                else if (findNavController().currentDestination?.id==R.id.subDadhboardContainer){
//                    findNavController().navigate(R.id.action_subDadhboardContainer_to_dashFragment)
//                }
//            }

//            else-> Toast.makeText(this.context,"clicked", Toast.LENGTH_SHORT).show()

//        }
    }

    fun sendDataForCalib(){
        val requestUrl = "http://"+ip+"/manual_correction/"
        val stringRequest: StringRequest = object : StringRequest(
            Method.POST,
            requestUrl,
            Response.Listener { _ ->
                //                    Log.e(
//                        "Volley Result",
//                        "" + response
//                    ) //the response contains the result from the server, a json string or any other object returned by your server
            },
            Response.ErrorListener { error ->
                error.printStackTrace() //log the error resulting from the request for diagnosis/debugging
            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val postMap: MutableMap<String, String> = HashMap()
                postMap["viewed_field"] = gaugeForCalibration.viewed_field
                postMap["viewed_value"] = gaugeForCalibration.viewed_value.toString()
                postMap["manual_corrected_value"] = tv_num_data_to_calibrate.text.toString()
//                Toast.makeText(context,postMap.toString(),Toast.LENGTH_SHORT).show()
                return postMap
            }
        }
//        Single.getInstance(this.context).addToRequestQueue(stringRequest)
        saveCalibToSharedPref()
    }

    fun getAddress(){
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        val defaultValue = resources.getString(R.string.default_ip)
        ip = sharedPref.getString("ip", defaultValue)
    }

    fun getGaugeToCalib(){
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        var defaultName = "scave_gauge"
        var defaultValue = 0.0f
        gaugeForCalibration.viewed_field = sharedPref.getString("nameOfGaugeFromdash", defaultName).toString()
        gaugeForCalibration.viewed_value = sharedPref.getFloat("valueOfGaugeFromdash",defaultValue)
    }

    fun saveCalibToSharedPref(){
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        with (sharedPref.edit()) {
            val defaultValue = if (!tv_num_data_to_calibrate.text.toString().isEmpty()){
                tv_num_data_to_calibrate.text.toString().toFloat()
            }
            else if (!tv_num_data_to_calibrate.text.toString().toFloat().isNaN()){
                tv_num_data_to_calibrate.text.toString().toFloat()
            }
            else{
                0.0f
            }
            putFloat("valueFromCalib", defaultValue)
            putString("nameOfGaugeFromCalib", gaugeForCalibration.viewed_field)
            commit()
        }
    }

    override fun onItemSelected(position: Int, item: BlogPost) {
        TODO("Not yet implemented")
    }

    override fun restoreListPosition() {
        TODO("Not yet implemented")
    }

    override fun onRefresh() {
        TODO("Not yet implemented")
    }


}