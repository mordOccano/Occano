package com.e.occano.ui.main.dashboard

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.RequestManager
import com.e.occano.R
import com.e.occano.models.BlogPost
import com.e.occano.models.GaugeForCalibration
import com.e.occano.models.Ship
import com.e.occano.utils.Constants.Companion.bmep_bool_calib
import com.e.occano.utils.Constants.Companion.bool_ir_recv
import com.e.occano.utils.Constants.Companion.bool_mics_recv
import com.e.occano.utils.Constants.Companion.break_power_bool_calib
import com.e.occano.utils.Constants.Companion.calib_bmep_gauge
import com.e.occano.utils.Constants.Companion.calib_break_power_gauge
import com.e.occano.utils.Constants.Companion.calib_comp_pres_gauge
import com.e.occano.utils.Constants.Companion.calib_engine_speed_gauge
import com.e.occano.utils.Constants.Companion.calib_exhaust_gauge
import com.e.occano.utils.Constants.Companion.calib_firing_pres_gauge
import com.e.occano.utils.Constants.Companion.calib_fuel_gauge
import com.e.occano.utils.Constants.Companion.calib_imep_gauge
import com.e.occano.utils.Constants.Companion.calib_injec_gauge
import com.e.occano.utils.Constants.Companion.calib_load_gauge
import com.e.occano.utils.Constants.Companion.calib_scave_gauge
import com.e.occano.utils.Constants.Companion.calib_torque_gauge
import com.e.occano.utils.Constants.Companion.compression_pressure_bool_calib
import com.e.occano.utils.Constants.Companion.counter_compare_to_local
import com.e.occano.utils.Constants.Companion.exshaust_temperature_bool_calib
import com.e.occano.utils.Constants.Companion.firing_pressure_bool_calib
import com.e.occano.utils.Constants.Companion.fuel_flow_rate_bool_calib
import com.e.occano.utils.Constants.Companion.imep_bool_calib
import com.e.occano.utils.Constants.Companion.injection_timing_bool_calib
import com.e.occano.utils.Constants.Companion.load_bool_calib
import com.e.occano.utils.Constants.Companion.local_ir_recv_tester_previous
import com.e.occano.utils.Constants.Companion.local_mics_recv_previous
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
import com.e.occano.utils.Constants.Companion.rpm_bool_calib
import com.e.occano.utils.Constants.Companion.scavenging_pressure_bool_calib
import com.e.occano.utils.Constants.Companion.sec_bool_counter_compare_to_local
import com.e.occano.utils.Constants.Companion.torque_engine_bool_calib
import com.github.anastr.speedviewlib.AwesomeSpeedometer
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import javax.inject.Inject


@FlowPreview
@ExperimentalCoroutinesApi
class DashboardFragment
@Inject
constructor(
    viewModelFactory: ViewModelProvider.Factory,
    private val requestManager: RequestManager
): BaseDashboardFragment(R.layout.fragment_dashboard, viewModelFactory),
    BlogListAdapter.Interaction,
    SwipeRefreshLayout.OnRefreshListener,
    View.OnClickListener{


        lateinit var gaugeForCalibration: GaugeForCalibration

        private var api_rpm: Float?=0.0f
        private var api_exhaust_temperature: Float?=0.0f
        private var api_load: Float?=0.0f
        private var api_firing_pressure: Float?=0.0f
        private var api_scavenging_pressure: Float?=0.0f
        private var api_compression_pressure: Float?=0.0f
        private var api_break_power: Float?=0.0f
        private var api_imep: Float?=0.0f
        private var api_torque_engine: Float?=0.0f
        private var api_bmep: Float?=0.0f
        private var api_injection_timing: Float?=0.0f
        private var api_fuel_flow_rate: Float?=0.0f

        private var total_rpm: Float?=0.0f
        private var total_exhaust_temperature: Float?=0.0f
        private var total_load: Float?=0.0f
        private var total_firing_pressure: Float?=0.0f
        private var total_scavenging_pressure: Float?=0.0f
        private var total_compression_pressure: Float?=0.0f
        private var total_break_power: Float?=0.0f
        private var total_imep: Float?=0.0f
        private var total_torque_engine: Float?=0.0f
        private var total_bmep: Float?=0.0f
        private var total_injection_timing: Float?=0.0f
        private var total_fuel_flow_rate: Float?=0.0f

        var time_passed: Long? = 0
        var api_mics_recv: String? = ""
        var api_ir_recv: String? = ""

        var ip: String? = null
        var fromCalibName: String? = ""
        var fromCalibValue:Float = 0.0f
        var currentShip = Ship()


//    private val selectedPressureUnits = false
//private var celsiusTempUnit: Boolean = false

        private var counterForSaveToLog:Int = 0
        var toastCounter:Boolean = false


        var torqueGauge: AwesomeSpeedometer? = null
        var bmepGauge: AwesomeSpeedometer? = null
        var scaveGauge: AwesomeSpeedometer? = null
        var imepGauge: AwesomeSpeedometer? = null
        var breakGauge: AwesomeSpeedometer? = null
        var injecGauge: AwesomeSpeedometer? = null
        var rpmGauge: AwesomeSpeedometer? = null
        var compPresGauge: AwesomeSpeedometer? = null
        var exsahustGauge: AwesomeSpeedometer? = null
        var fuelGauge: AwesomeSpeedometer? = null
        var firingGauge: AwesomeSpeedometer? = null
        var loadGauge: AwesomeSpeedometer? = null

        var gauge = GaugeForCalibration()

        var btnDashCurr:ImageButton? = null
        var btnDasToLog:ImageButton? = null
        var btnToStatus:ImageButton? = null

        //repo
//        lateinit var mLogRepository: LogRepository


        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
//            mLogRepository = LogRepository(context)
        }

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? =// Inflate the layout for this fragment
            inflater.inflate(R.layout.fragment_dashboard, container, false)

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

            setUI(view)

        }

        override fun onResume() {
            super.onResume()
            setUI(view!!)
        }

        fun setUI(v:View){
            getAddress()
            getShipPropreties()
//            requestQueue = Volley.newRequestQueue(context)
            setPointer(v)
            setListeners()
//            getDataFromApi()
        }

        private fun setPointer(v:View) {

            //gauges pointers and init
            torqueGauge =  AwesomeSpeedometer(context!!)
            bmepGauge = AwesomeSpeedometer(context!!)
            scaveGauge = AwesomeSpeedometer(context!!)
            imepGauge = AwesomeSpeedometer(context!!)
            breakGauge = AwesomeSpeedometer(context!!)
            injecGauge = AwesomeSpeedometer(context!!)
            rpmGauge = AwesomeSpeedometer(context!!)
            compPresGauge = AwesomeSpeedometer(context!!)
            exsahustGauge = AwesomeSpeedometer(context!!)
            fuelGauge = AwesomeSpeedometer(context!!)
            firingGauge = AwesomeSpeedometer(context!!)
            loadGauge = AwesomeSpeedometer(context!!)

            torqueGauge = v.findViewById(R.id.torque_gauge)
            bmepGauge = v.findViewById(R.id.bmep_gauge)
            breakGauge = v.findViewById(R.id.break_power_gauge)
            compPresGauge = v.findViewById(R.id.comp_pres_gauge)
            rpmGauge = v.findViewById(R.id.engine_speed_gauge)
            exsahustGauge = v.findViewById(R.id.exhaust_gauge)
            firingGauge = v.findViewById(R.id.firing_pres_gauge)
            fuelGauge = v.findViewById(R.id.fuel_gauge)
            imepGauge = v.findViewById(R.id.imep_gauge)
            injecGauge = v.findViewById(R.id.injec_gauge)
            loadGauge = v.findViewById(R.id.load_gauge)
            scaveGauge = v.findViewById(R.id.scave_gauge)


            v.findViewById<TextView>(R.id.tv_current_ship_name).text = currentShip.vessel.toString()
            v.findViewById<TextView>(R.id.tv_current_ship_engine).text = currentShip.m_e.toString()

        }
        private fun setListeners(){
            torqueGauge!!.setOnClickListener(this)
            injecGauge!!.setOnClickListener(this)
            bmepGauge!!.setOnClickListener(this)
            breakGauge!!.setOnClickListener(this)
            compPresGauge!!.setOnClickListener(this)
            rpmGauge!!.setOnClickListener(this)
            exsahustGauge!!.setOnClickListener(this)
            firingGauge!!.setOnClickListener(this)
            fuelGauge!!.setOnClickListener(this)
            imepGauge!!.setOnClickListener(this)
            loadGauge!!.setOnClickListener(this)
            scaveGauge!!.setOnClickListener(this)
            btnDashCurr!!.setOnClickListener(this)
            btnDasToLog!!.setOnClickListener(this)
            btnToStatus!!.setOnClickListener(this)

            setMaxGauges()

        }
//        private fun getDataFromApi() {
//            newJsonParse()
//            repeatGetData()
//        }
//        private fun repeatGetData() {
////            Run.after(7000,{getDataFromApi()})
//        }
        private fun setMaxGauges() {
            torqueGauge!!.setMaxSpeed(max_torque_gauge)
            bmepGauge!!.setMaxSpeed(max_bmep_gauge)
            breakGauge!!.setMaxSpeed(max_break_power_gauge)
            compPresGauge!!.setMaxSpeed(max_comp_pres_gauge)
            rpmGauge!!.setMaxSpeed(max_engine_speed_gauge)
            exsahustGauge!!.setMaxSpeed(max_exhaust_gauge)
            firingGauge!!.setMaxSpeed(max_firing_pres_gauge)
            fuelGauge!!.setMaxSpeed(max_fuel_gauge)
            imepGauge!!.setMaxSpeed(max_imep_gauge)
            injecGauge!!.setMaxSpeed(max_injec_gauge)
            loadGauge!!.setMaxSpeed(max_load_gauge)
            scaveGauge!!.setMaxSpeed(max_scave_gauge)

            setTicks()
        }
        private fun setTicks() {
            torqueGauge!!.tickNumber=9
            bmepGauge!!.tickNumber=9
            breakGauge!!.tickNumber=9
            compPresGauge!!.tickNumber=9
            rpmGauge!!.tickNumber=9
            exsahustGauge!!.tickNumber=9
            firingGauge!!.tickNumber=9
            fuelGauge!!.tickNumber=9
            imepGauge!!.tickNumber=9
            injecGauge!!.tickNumber=9
            loadGauge!!.tickNumber=9
            scaveGauge!!.tickNumber=9
        }
        private fun newJsonParse() {

//            val url  = "http://"+ip+"/report/"
//            try {
//                val jsonObjectRequest = JsonObjectRequest(
//                    Request.Method.GET, url, null,
//                    Response.Listener { response ->
//                        android.util.Log.d(TAG,"Response: %s".format(response.toString()))
//                        val mLog = Log()
//                        mLog.content = "Response: %s".format(response.toString())+Utility.getCurrentTimeStamp()
//                        saveToLog(mLog)

//
//                    val item= response.getJSONObject("analysis")
//                    api_rpm = item.getDouble("engine_speed").toFloat()
//                    api_exhaust_temperature = item.getDouble("exhaust_temperature").toFloat()
//                    api_load = item.getDouble("load").toFloat()
//                    api_firing_pressure = item.getDouble("firing_pressure").toFloat()
//                    api_scavenging_pressure = item.getDouble("scavenging_pressure").toFloat()
//                    api_compression_pressure = item.getDouble("compression_pressure").toFloat()
//                    api_break_power = item.getDouble("break_power").toFloat()
//                    api_imep = item.getDouble("imep").toFloat()
//                    api_torque_engine = item.getDouble("Torque_engine").toFloat()/1000
//                    api_bmep = item.getDouble("bmep").toFloat()
//                    api_injection_timing = item.getDouble("injection_timing").toFloat()
//                    api_fuel_flow_rate = item.getDouble("fuel_flow_rate").toFloat()
//
//                    try {
//                         time_passed = if(response.getString("time_passed")=="null"){0}else{response.getString("time_passed").toLong()}
//                        setValidDasboardUi(time_passed!!)
////                        Toast.makeText(this.context,time_passed.toString(),Toast.LENGTH_SHORT).show()
//                        }catch (e:Error){
//                        e.printStackTrace()
//                    }
//
//                    try {
//                        val meta_data_item = response.getJSONObject("metadata")
//                        this.api_ir_recv = meta_data_item.getString("ir_recv")
//                        this.api_mics_recv = meta_data_item.getString("mics_recv")
////                        checkSensors(this.api_ir_recv!!, this.api_mics_recv!!)
//                    }
//                    catch (e:Error){
//                        e.printStackTrace()
//                    }
//                    setGraph()
//                    },
//                    Response.ErrorListener { error ->
//
//                        if(error != null && error.message != null)
//                        {
//                            if (toastCounter == false){
//                                if (context !=null){
//                                    Toast.makeText(context,"הי מתוק!! יש בעיה. עבור אל מסך הלוג ותיצור איתי קשר - מרדכי",Toast.LENGTH_SHORT).show()
//                                }
//                                toastCounter=true
//                            }
//                            val mLog = Log()
//                            mLog.content = "Response: %s".format(error.toString())
//                            saveToLog(mLog)
//                        }
//                    }
//                )
//                Single.getInstance(this.context).addToRequestQueue(jsonObjectRequest)
//            }
//            catch (e : Error){
//                e.printStackTrace()
//            }
//
//            getPropertiesFromCalib()
//            setGauges()

        }

        private fun setValidDasboardUi(timePassed: Long) {
            if (timePassed<900000){
                val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
                with (sharedPref.edit()) {
                    putBoolean("algo_time_passed", true)
                    commit()
                }
            }
            else{
                val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
                with (sharedPref.edit()) {
                    putBoolean("algo_time_passed", false)
                    commit()
                }

            }
        }

        fun checkSensors(api_mic:String, api_ir:String){

            if (sec_bool_counter_compare_to_local){
                local_ir_recv_tester_previous = api_ir_recv.toString()
                local_mics_recv_previous = api_mics_recv.toString()
                sec_bool_counter_compare_to_local = false
            }

            counter_compare_to_local++
            if (counter_compare_to_local>7){
                bool_ir_recv = api_ir == local_ir_recv_tester_previous
                local_ir_recv_tester_previous = api_ir
                bool_mics_recv = api_mic==local_mics_recv_previous
                local_mics_recv_previous = api_mic
            }

//            saveSensorsToPrefs()
//        if (local_ir_recv == ""){
//            local_ir_recv = api_ir_recv.toString()
//        }
//        if(local_mics_recv == ""){
//            local_mics_recv = api_mics_recv.toString()
//        }

//        counter_compare_to_local++
//       if (counter_compare_to_local>4){
//           bool_ir_recv = api_ir_recv.equals(local_ir_recv)
//           bool_mics_recv = api_mics_recv.equals(local_mics_recv)
            saveSensorsToPrefs()
            Toast.makeText(context, bool_ir_recv.toString()+bool_mics_recv.toString(),Toast.LENGTH_SHORT).show()
//       }
        }

        private fun saveSensorsToPrefs() {
            val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
            with (sharedPref.edit()) {
                putBoolean("algo_ir_valid", bool_ir_recv)
                putBoolean("algo_mics_valid", bool_mics_recv)
                commit()
            }
//        counter_compare_to_local = 0

        }

        // TODO: refactoring.
//    boolean readedPressureUnits = sharedPreferences.getBoolean("selectPressureUnit", true);  //true = bar, false = psi
//    if (readedPressureUnits != selectedPressureUnits) {
//        selectedPressureUnits = readedPressureUnits;
//        pressureFactor = selectedPressureUnits ? 1 : (float) 14.5037738;
//        pressureUnit = selectedPressureUnits ? "bar" : "psi";
//        pressureMin = selectedPressureUnits ? -3 : -30;
//        pressureMax = selectedPressureUnits ? 3 : 30;
//    }
//
//    boolean readedTempUnit = sharedPreferences.getBoolean("selectTemperatureUnit", true);  //true = celcius, false = fahrenheit
//    if (readedTempUnit != celsiusTempUnit) {
//        celsiusTempUnit = readedTempUnit;
//        temperatureUnit = getString(celsiusTempUnit ? R.string.unit_c : R.string.unit_f);
//    }
//
//    boolean readedPowerUnits = sharedPreferences.getBoolean("selectPowerUnit", true);  //true = kw, false = ps
//    if (powerUnits == null || readedPowerUnits != powerUnits) {
//        powerUnits = readedPowerUnits;
//        powerFactor = powerUnits ? 1 : 1.35962f;
//    }


//        private fun saveToLog(log: Log) {
//            //in order to save ~
//            counterForSaveToLog ++
//            if (counterForSaveToLog > 9){
//                this.mLogRepository.insertLogTask(log)
//                counterForSaveToLog = 0
//            }
////        Toast.makeText(context,counterForSaveToLog.toString(),Toast.LENGTH_SHORT).show()
//        }

//TODO: refactoring.
//pause the request on pause the fragment ->
//    override fun onPause() {
//        super.onPause()
//        Single.getInstance().
//    }

        fun setGauges() {
            //import from shared pref the valur from calibrate and equal to the current value
            total_torque_engine = if (torque_engine_bool_calib!!){
                api_torque_engine
            } else{
                calib_torque_gauge
            }

            total_bmep = if (bmep_bool_calib!!){
                api_bmep
            } else{
                calib_bmep_gauge
            }

            total_break_power = if (break_power_bool_calib!!){
                api_break_power
            }else{
                calib_break_power_gauge
            }
            total_compression_pressure = if (compression_pressure_bool_calib!!){
                api_compression_pressure
            } else{
                calib_comp_pres_gauge
            }

            total_rpm = if (rpm_bool_calib!!){
                api_rpm
            }else{
                calib_engine_speed_gauge
            }

            total_exhaust_temperature = if(exshaust_temperature_bool_calib!!){
                api_exhaust_temperature}
            else{
                calib_exhaust_gauge
            }

            total_firing_pressure = if (firing_pressure_bool_calib!!){
                api_firing_pressure
            }else{
                calib_firing_pres_gauge
            }

            total_fuel_flow_rate = if (fuel_flow_rate_bool_calib!!){
                api_fuel_flow_rate
            }else{
                calib_fuel_gauge
            }

            total_imep = if (imep_bool_calib!!){
                api_imep
            }else{
                calib_imep_gauge
            }

            total_injection_timing = if (injection_timing_bool_calib!!){
                api_injection_timing
            } else {
                calib_injec_gauge
            }

            total_load = if (load_bool_calib!!){
                api_load
            } else {
                calib_load_gauge
            }

            total_scavenging_pressure = if (scavenging_pressure_bool_calib!!){
                api_scavenging_pressure
            } else{
                calib_scave_gauge
            }

            torqueGauge?.speedTo(total_torque_engine!!)
            bmepGauge?.speedTo(total_bmep!!)
            breakGauge?.speedTo(total_break_power!!)
            compPresGauge?.speedTo(total_compression_pressure!!)
            rpmGauge?.speedTo(total_rpm!!)
            exsahustGauge?.speedTo(total_exhaust_temperature!!)
            firingGauge?.speedTo(total_firing_pressure!!)
            fuelGauge?.speedTo(total_fuel_flow_rate!!)
            imepGauge?.speedTo(total_imep!!)
            injecGauge?.speedTo(total_injection_timing!!)
            loadGauge?.speedTo(total_load!!)
            scaveGauge?.speedTo(total_scavenging_pressure!!)
        }

        override fun onClick(v: View?) {

            when(v!!.id){
                R.id.torque_gauge -> {
                    if(!(TextUtils.isEmpty(total_torque_engine.toString()))){
//                    var gauge = GaugeForCalibration()
                        gauge.viewed_field = "torque_gauge"
                        gauge.viewed_value = torqueGauge!!.speed

                        savePropertiesToCalib(gauge)
//                    val bundle = bundleOf("gauge_from_dashboard" to gauge)


//                    findNavController().navigate(R.id.action_dashFragment_to_calibrationFragmentZ,bundle)
//                        findNavController().navigate(R.id.action_dashFragment_to_subDadhboardContainer)
                    }
                    else{
                        Toast.makeText(this.context, "try Again", Toast.LENGTH_SHORT).show()
                    }
                }
                R.id.exhaust_gauge -> {
                    if(!TextUtils.isEmpty(total_torque_engine.toString())){
//                    var gauge = GaugeForCalibration()
                        gauge.viewed_field = "exhaust_gauge"
                        gauge.viewed_value = exsahustGauge!!.speed

                        savePropertiesToCalib(gauge)
//                    val bundle = bundleOf("gauge_from_dashboard" to gauge)

//                        findNavController().navigate(R.id.action_dashFragment_to_subDadhboardContainer)
                    }
                    else{
                        Toast.makeText(this.context, "try Again", Toast.LENGTH_SHORT).show()
                    }
                }
                R.id.load_gauge -> {
                    if(!TextUtils.isEmpty(total_load.toString())){
//                    var gauge = GaugeForCalibration()

                        gauge.viewed_field = "load_gauge"
                        gauge.viewed_value = loadGauge!!.speed

                        savePropertiesToCalib(gauge)


//                        findNavController().navigate(R.id.action_dashFragment_to_subDadhboardContainer)
                    }
                    else{
                        Toast.makeText(this.context, "try Again", Toast.LENGTH_SHORT).show()
                    }
                }
                R.id.firing_pres_gauge -> {
                    if(!TextUtils.isEmpty(total_firing_pressure.toString())){
//                    var gauge = GaugeForCalibration()

                        gauge.viewed_field = "firing_pres_gauge"
                        gauge.viewed_value = firingGauge!!.speed

                        savePropertiesToCalib(gauge)

//                        findNavController().navigate(R.id.action_dashFragment_to_subDadhboardContainer)
                    }
                    else{
                        Toast.makeText(this.context, "try Again", Toast.LENGTH_SHORT).show()
                    }
                }
                R.id.comp_pres_gauge -> {
                    if(!TextUtils.isEmpty(total_compression_pressure.toString())){

//                    var gauge = GaugeForCalibration()
                        gauge.viewed_field = "comp_pres_gauge"
                        gauge.viewed_value = compPresGauge!!.speed

                        savePropertiesToCalib(gauge)

//                        findNavController().navigate(R.id.action_dashFragment_to_subDadhboardContainer)
                    }
                    else{
                        Toast.makeText(this.context, "try Again", Toast.LENGTH_SHORT).show()
                    }
                }
                R.id.scave_gauge -> {
                    if(!TextUtils.isEmpty(total_scavenging_pressure.toString())){

//                    var gauge = GaugeForCalibration()
                        gauge.viewed_field = "scave_gauge"
                        gauge.viewed_value = scaveGauge!!.speed

                        savePropertiesToCalib(gauge)

//                        findNavController().navigate(R.id.action_dashFragment_to_subDadhboardContainer)
                    }
                    else{
                        Toast.makeText(this.context, "try Again", Toast.LENGTH_SHORT).show()
                    }
                }
                R.id.bmep_gauge -> {
                    if(!TextUtils.isEmpty(total_bmep.toString())){
//                    var gauge = GaugeForCalibration()
                        gauge.viewed_field = "bmep_gauge"
                        gauge.viewed_value = bmepGauge!!.speed

                        savePropertiesToCalib(gauge)

//                        findNavController().navigate(R.id.action_dashFragment_to_subDadhboardContainer)
                    }
                    else{
                        Toast.makeText(this.context, "try Again", Toast.LENGTH_SHORT).show()
                    }
                }
                R.id.break_power_gauge -> {
                    if(!TextUtils.isEmpty(total_break_power.toString())){
//                    var gauge = GaugeForCalibration()
                        gauge.viewed_field = "break_power_gauge"
                        gauge.viewed_value = breakGauge!!.speed

                        savePropertiesToCalib(gauge)

//                        findNavController().navigate(R.id.action_dashFragment_to_subDadhboardContainer)
                    }
                    else{
                        Toast.makeText(this.context, "try Again", Toast.LENGTH_SHORT).show()
                    }
                }
                R.id.engine_speed_gauge -> {
                    if(!TextUtils.isEmpty(total_rpm.toString())){
//                    var gauge = GaugeForCalibration()
                        gauge.viewed_field = "engine_speed_gauge"
                        gauge.viewed_value = rpmGauge!!.speed

                        savePropertiesToCalib(gauge)

//                        findNavController().navigate(R.id.action_dashFragment_to_subDadhboardContainer)
                    }
                    else{
                        Toast.makeText(this.context, "try Again", Toast.LENGTH_SHORT).show()
                    }
                }
                R.id.fuel_gauge -> {
                    if(!TextUtils.isEmpty(total_fuel_flow_rate.toString())){
//                    var gauge = GaugeForCalibration()
                        gauge.viewed_field = "fuel_gauge"
                        gauge.viewed_value = fuelGauge!!.speed

                        savePropertiesToCalib(gauge)

//                        findNavController().navigate(R.id.action_dashFragment_to_subDadhboardContainer)
                    }
                    else{
                        Toast.makeText(this.context, "try Again", Toast.LENGTH_SHORT).show()
                    }
                }
                R.id.imep_gauge -> {
                    if(!TextUtils.isEmpty(total_imep.toString())){
//                    var gauge = GaugeForCalibration()
                        gauge.viewed_field = "imep_gauge"
                        gauge.viewed_value = imepGauge!!.speed

                        savePropertiesToCalib(gauge)

//                        findNavController().navigate(R.id.action_dashFragment_to_subDadhboardContainer)
                    }
                    else{
                        Toast.makeText(this.context, "try Again", Toast.LENGTH_SHORT).show()
                    }
                }
                R.id.injec_gauge -> {
                    if(!TextUtils.isEmpty(total_injection_timing.toString())) {
//                    var gauge = GaugeForCalibration()
                        gauge.viewed_field ="injec_gauge"
                        gauge.viewed_value = injecGauge!!.speed

                        savePropertiesToCalib(gauge)

//                        findNavController().navigate(R.id.action_dashFragment_to_subDadhboardContainer)
                    }
                    else{ Toast.makeText(this.context, "try Again", Toast.LENGTH_SHORT).show()
                    }
                }
                else ->{
                    Toast.makeText(this.context, "try Again", Toast.LENGTH_SHORT).show()
                }


            }

        }

        fun getAddress(){
            val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
            val defaultValue = resources.getString(R.string.default_ip)
            ip = sharedPref.getString("ip", defaultValue)
        }

        fun getShipPropreties(){
            val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
            val defaultShipName = ""
            val defaultValue = ""
            currentShip.vessel = sharedPref.getString("nameOfCurrentShip",defaultShipName)
            currentShip.m_e = sharedPref.getString("nameOfCurrentEngine", defaultValue)

        }

        fun getPropertiesFromCalib(){
            val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
            val defaultName = gauge.viewed_field
            val defaultValue = gauge.viewed_value
            fromCalibName = sharedPref.getString("nameOfGaugeFromCalib",defaultName)
            fromCalibValue = sharedPref.getFloat("valueFromCalib", defaultValue)

            when (fromCalibName){
                "torque_gauge"-> {
                    torque_engine_bool_calib = false
                    calib_torque_gauge = fromCalibValue
                }
                "load_gauge"-> {
                    load_bool_calib = false
                    calib_load_gauge = fromCalibValue
                }
                "firing_pres_gauge"-> {
                    firing_pressure_bool_calib = false
                    calib_firing_pres_gauge = fromCalibValue
                }
                "bmep_gauge"-> {
                    bmep_bool_calib = false
                    calib_bmep_gauge = fromCalibValue
                }
                "break_power_gauge"-> {
                    break_power_bool_calib = false
                    calib_break_power_gauge = fromCalibValue

                }
                "comp_pres_gauge"-> {
                    compression_pressure_bool_calib = false
                    calib_comp_pres_gauge = fromCalibValue
                }
                "engine_speed_gauge"-> {
                    rpm_bool_calib = false
                    calib_engine_speed_gauge = fromCalibValue
                }
                "exhaust_gauge"-> {
                    exshaust_temperature_bool_calib = false
                    calib_exhaust_gauge = fromCalibValue
                }
                "fuel_gauge"-> {
                    fuel_flow_rate_bool_calib = false
                    calib_fuel_gauge = fromCalibValue
                }
                "imep_gauge"-> {
                    imep_bool_calib = false
                    calib_imep_gauge = fromCalibValue
                    //TODO: update the unit
                }
                "injec_gauge"-> {
                    injection_timing_bool_calib = false
                    calib_injec_gauge = fromCalibValue
                }
                "scave_gauge"-> {
                    scavenging_pressure_bool_calib = false
                    calib_scave_gauge = fromCalibValue
                }
                else -> {
                    load_bool_calib = false
                    calib_load_gauge = fromCalibValue
                }
            }
            setGauges()
        }

        fun savePropertiesToCalib(gaugeForCalibration: GaugeForCalibration){
            val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
            with (sharedPref.edit()) {
                putString("nameOfGaugeFromdash", gaugeForCalibration.viewed_field)
                putFloat("valueOfGaugeFromdash", gaugeForCalibration.viewed_value)
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



