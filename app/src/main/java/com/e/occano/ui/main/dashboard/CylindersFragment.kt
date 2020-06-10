package com.e.occano.ui.main.dashboard

import android.os.Bundle
import android.view.*
import android.widget.ImageButton
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.RequestManager
import com.e.occano.R
import com.e.occano.models.BlogPost
import com.github.anastr.speedviewlib.AwesomeSpeedometer
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import javax.inject.Inject


@FlowPreview
@ExperimentalCoroutinesApi
class CylindersFragment
@Inject
constructor(
    viewModelFactory: ViewModelProvider.Factory,
    private val requestManager: RequestManager
): BaseDashboardFragment(R.layout.fragment_cylinders, viewModelFactory),
    BlogListAdapter.Interaction,
    SwipeRefreshLayout.OnRefreshListener,
    View.OnClickListener{


    private var cyl1value:Float? = 0.0f
    private var cyl2value:Float? = 0.0f
    private var cyl3value:Float? = 0.0f
    private var cyl4value:Float? = 0.0f
    private var cyl5value:Float? = 0.0f
    private var cyl6value:Float? = 0.0f
    private var cyl7value:Float? = 0.0f
    private var cyl8value:Float? = 0.0f
    private var cyl9value:Float? = 0.0f
    private var cyl10value:Float? = 0.0f
    private var cyl11value:Float? = 0.0f
    private var cyl12value:Float? = 0.0f

    private var toastCounter:Boolean = false
    private var counterForSaveToLog:Int = 0
//    lateinit var mLogRepository: LogRepository

    var cyl1 : AwesomeSpeedometer? = null
    var cyl2 : AwesomeSpeedometer? = null
    var cyl3 : AwesomeSpeedometer? = null
    var cyl4 : AwesomeSpeedometer? = null
    var cyl5 : AwesomeSpeedometer? = null
    var cyl6 : AwesomeSpeedometer? = null
    var cyl7 : AwesomeSpeedometer? = null
    var cyl8 : AwesomeSpeedometer? = null
    var cyl9 : AwesomeSpeedometer? = null
    var cyl10 : AwesomeSpeedometer? = null
    var cyl11 : AwesomeSpeedometer? = null
    var cyl12 : AwesomeSpeedometer? = null

    var btnToDash : ImageButton? = null

    var ip: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        mLogRepository = LogRepository(context)

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setPointers(view)
        setListeners()
        getDataFromApi()
        setGauges()
        setHasOptionsMenu(true)

    }

    private fun getDataFromApi() {

        //get parsed data from the view model.
//        val url  = "http://"+ip+"/report/"
//        try {
//            val jsonObjectRequest = JsonObjectRequest(
//                Request.Method.GET, url, null,
//                Response.Listener { response ->
//                    Log.d(TAG,"Response: %s".format(response.toString()))
//
//                    //                 Toast.makeText(context,"Response: %s".format(response.toString()),Toast.LENGTH_SHORT).show()
//
//
////                    val item= response.getJSONObject("analysis")
//                    val mLog = com.e.occanosidetest.models.Log()
//                    mLog.content = "Response: %s".format(response.toString())+ Utility.getCurrentTimeStamp()
//                    saveToLog(mLog)
//
//
//                    setGauges()
////                    setGraph()
//                },
//                Response.ErrorListener { error ->
//
//                    if(error != null && error.message != null)
//                    {
//                        if (toastCounter == false){
//                            if (context !=null){
//                                Toast.makeText(context,"הי מתוק!! יש בעיה. עבור אל מסך הלוג ותיצור איתי קשר - מרדכי",
//                                    Toast.LENGTH_SHORT).show()
//                            }
//                            toastCounter=true
//                        }
//                        val mLog = com.e.occanosidetest.models.Log()
//                        mLog.content = "Response: %s".format(error.toString())
//                        saveToLog(mLog)
//
//                    }
//                  }
//            )
//            Single.getInstance(this.context).addToRequestQueue(jsonObjectRequest)
//
//        }
//        catch (e : Error){
//            e.printStackTrace()
//        }


    }

//    private fun saveToLog(mLog: com.e.occanosidetest.models.Log) {
//        counterForSaveToLog ++
//        if (counterForSaveToLog > 9){
//            this.mLogRepository.insertLogTask(mLog)
//            counterForSaveToLog = 0
//        }
//
//    }

    private fun setPointers(v:View) {
        cyl1 = v.findViewById(R.id.cyl1)
        cyl2 = v.findViewById(R.id.cyl2)
        cyl3 = v.findViewById(R.id.cyl3)
        cyl4 = v.findViewById(R.id.cyl4)
        cyl5 = v.findViewById(R.id.cyl5)
        cyl6 = v.findViewById(R.id.cyl6)
        cyl7 = v.findViewById(R.id.cyl7)
        cyl8 = v.findViewById(R.id.cyl8)
        cyl9 = v.findViewById(R.id.cyl9)
        cyl10 = v.findViewById(R.id.cyl10)
        cyl11 = v.findViewById(R.id.cyl11)
        cyl12 = v.findViewById(R.id.cyl12)

        cyl1 = AwesomeSpeedometer(context!!)
        cyl2 = AwesomeSpeedometer(context!!)
        cyl3 = AwesomeSpeedometer(context!!)
        cyl4 = AwesomeSpeedometer(context!!)
        cyl5 = AwesomeSpeedometer(context!!)
        cyl6 = AwesomeSpeedometer(context!!)
        cyl7 = AwesomeSpeedometer(context!!)
        cyl8 = AwesomeSpeedometer(context!!)
        cyl9 = AwesomeSpeedometer(context!!)
        cyl10 = AwesomeSpeedometer(context!!)
        cyl11 = AwesomeSpeedometer(context!!)
        cyl12 = AwesomeSpeedometer(context!!)

        btnToDash = v.findViewById(R.id.btn_cyl_to_dash)

    }

    private fun setListeners() {
        cyl1!!.setOnClickListener(this)
        cyl2!!.setOnClickListener(this)
        cyl3!!.setOnClickListener(this)
        cyl4!!.setOnClickListener(this)
        cyl5!!.setOnClickListener(this)
        cyl6!!.setOnClickListener(this)
        cyl7!!.setOnClickListener(this)
        cyl8!!.setOnClickListener(this)
        cyl9!!.setOnClickListener(this)
        cyl10!!.setOnClickListener(this)
        cyl11!!.setOnClickListener(this)
        cyl12!!.setOnClickListener(this)

        btnToDash!!.setOnClickListener(this)

    }

    private fun setMaxGauges(maxValue:Float) {
        cyl1!!.setMaxSpeed(maxValue)
        cyl2!!.setMaxSpeed(maxValue)
        cyl3!!.setMaxSpeed(maxValue)
        cyl4!!.setMaxSpeed(maxValue)
        cyl5!!.setMaxSpeed(maxValue)
        cyl6!!.setMaxSpeed(maxValue)
        cyl7!!.setMaxSpeed(maxValue)
        cyl8!!.setMaxSpeed(maxValue)
        cyl9!!.setMaxSpeed(maxValue)
        cyl10!!.setMaxSpeed(maxValue)
        cyl11!!.setMaxSpeed(maxValue)
        cyl12!!.setMaxSpeed(maxValue)

        setTicks()
    }

    private fun setTicks() {
        cyl1!!.tickNumber = 9
        cyl2!!.tickNumber = 9
        cyl3!!.tickNumber = 9
        cyl4!!.tickNumber = 9
        cyl5!!.tickNumber = 9
        cyl6!!.tickNumber = 9
        cyl7!!.tickNumber = 9
        cyl8!!.tickNumber = 9
        cyl9!!.tickNumber = 9
        cyl10!!.tickNumber = 9
        cyl11!!.tickNumber = 9
        cyl12!!.tickNumber = 9
    }

    private fun setGauges() {



        cyl1!!.speedTo(cyl1value!!)
        cyl2!!.speedTo(cyl2value!!)
        cyl3!!.speedTo(cyl3value!!)
        cyl4!!.speedTo(cyl4value!!)
        cyl5!!.speedTo(cyl5value!!)
        cyl6!!.speedTo(cyl6value!!)
        cyl7!!.speedTo(cyl7value!!)
        cyl8!!.speedTo(cyl8value!!)
        cyl9!!.speedTo(cyl9value!!)
        cyl10!!.speedTo(cyl10value!!)
        cyl11!!.speedTo(cyl11value!!)
        cyl12!!.speedTo(cyl12value!!)
    }


    override fun onClick(v: View?) {
        when(v!!.id){

            R.id.btn_cyl_to_dash ->{
//                if (findNavController().currentDestination?.id == R.id.cylindersFragment) {
//                    findNavController().navigate(R.id.action_cylindersFragment_to_dashFragment)}
//                if (findNavController().currentDestination?.id == R.id.subDadhboardContainer){
//                    findNavController().navigate(R.id.action_subDadhboardContainer_to_dashFragment)
                }
            }
        }



        override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
            // TODO("Check if user is permitted to calibrate")
            val isAuthorOfBlogPost = true
            if(isAuthorOfBlogPost){
                inflater.inflate(R.menu.edit_view_menu, menu)
            }
        }

        override fun onOptionsItemSelected(item: MenuItem): Boolean {
            // TODO("Check if user is permitted to calibrate")
            val isAuthorOfBlogPost = true
            if(isAuthorOfBlogPost){
                when(item.itemId){
                    R.id.edit -> {
                        navUpdateBlogFragment()
                        return true
                    }
                }
            }
            return super.onOptionsItemSelected(item)
        }

        private fun navUpdateBlogFragment(){
            findNavController().navigate(R.id.action_viewBlogFragment_to_updateBlogFragment)
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











