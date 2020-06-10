package com.e.occano.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import com.bumptech.glide.RequestManager
import com.e.occano.BaseApplication
import com.e.occano.R
import com.e.occano.models.AUTH_TOKEN_BUNDLE_KEY
import com.e.occano.models.AuthToken
import com.e.occano.ui.BaseActivity
import com.e.occano.ui.auth.AuthActivity
import com.e.occano.ui.main.alerts.BaseAccountFragment
import com.e.occano.ui.main.alerts.ChangePasswordFragment
import com.e.occano.ui.main.alerts.UpdateAccountFragment
import com.e.occano.ui.main.dashboard.*
import com.e.occano.ui.main.graphs.BaseCreateBlogFragment
import com.e.occano.utils.BOTTOM_NAV_BACKSTACK_KEY
import com.e.occano.utils.BottomNavController
import com.e.occano.utils.BottomNavController.*
import com.e.occano.utils.setUpNavigation
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.progress_bar
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import javax.inject.Inject
import javax.inject.Named

@ExperimentalCoroutinesApi
@FlowPreview
class MainActivity : BaseActivity(),
    OnNavigationGraphChanged,
    OnNavigationReselectedListener
{

    @Inject
    @Named("AccountFragmentFactory")
    lateinit var accountFragmentFactory: FragmentFactory

    @Inject
    @Named("AlertsFragmentFactory")
    lateinit var alertsFragmentFactory: FragmentFactory

    @Inject
    @Named("DashboardFragmentFactory")
    lateinit var dashboardFragmentFactory: FragmentFactory

    @Inject
    @Named("GraphFragmentFactory")
    lateinit var graphFragmentFactory: FragmentFactory


    @Inject
    @Named("BlogFragmentFactory")
    lateinit var blogFragmentFactory: FragmentFactory

    @Inject
    @Named("CreateBlogFragmentFactory")
    lateinit var createBlogFragmentFactory: FragmentFactory

    private lateinit var bottomNavigationView: BottomNavigationView

    private val bottomNavController by lazy(LazyThreadSafetyMode.NONE) {
        BottomNavController(
            this,
            R.id.main_fragments_container,
            R.id.menu_nav_blog,
            this
        )
    }

    override fun onGraphChange() {
        expandAppBar()
    }

    override fun onReselectNavItem(
        navController: NavController,
        fragment: Fragment
    ){
        Log.d(TAG, "logInfo: onReSelectItem")
        when(fragment){

            is ViewBlogFragment -> {
                navController.navigate(R.id.action_calibrationFragment_to_dashboardFragment)
            }

            is UpdateBlogFragment -> {
                navController.navigate(R.id.action_calibrationFragment_to_dashboardFragment)
            }

            is UpdateAccountFragment -> {
                navController.navigate(R.id.action_calibrationFragment_to_dashboardFragment)
            }

            is ChangePasswordFragment -> {
                navController.navigate(R.id.action_calibrationFragment_to_dashboardFragment)
            }

            else -> {
                // do nothing
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when(item?.itemId){
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun inject() {
        (application as BaseApplication).mainComponent()
            .inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        inject()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupActionBar()
        setupBottomNavigationView(savedInstanceState)

        subscribeObservers()
        restoreSession(savedInstanceState)
    }

    private fun setupBottomNavigationView(savedInstanceState: Bundle?){
        bottomNavigationView = findViewById(R.id.bottom_navigation_view)
        bottomNavigationView.setUpNavigation(bottomNavController, this)
        if (savedInstanceState == null) {
            bottomNavController.setupBottomNavigationBackStack(null)
            bottomNavController.onNavigationItemSelected()
        }
        else{
            (savedInstanceState[BOTTOM_NAV_BACKSTACK_KEY] as IntArray?)?.let { items ->
                val backstack = BackStack()
                backstack.addAll(items.toTypedArray())
                bottomNavController.setupBottomNavigationBackStack(backstack)
            }
        }
    }

    private fun restoreSession(savedInstanceState: Bundle?){
        savedInstanceState?.get(AUTH_TOKEN_BUNDLE_KEY)?.let{ authToken ->
            sessionManager.setValue(authToken as AuthToken)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        // save auth token
        outState.putParcelable(AUTH_TOKEN_BUNDLE_KEY, sessionManager.cachedToken.value)

        // save backstack for bottom nav
        outState.putIntArray(BOTTOM_NAV_BACKSTACK_KEY, bottomNavController.navigationBackStack.toIntArray())
    }

    fun subscribeObservers(){

        sessionManager.cachedToken.observe(this, Observer{ authToken ->
            Log.d(TAG, "MainActivity, subscribeObservers: ViewState: ${authToken}")
            if(authToken == null || authToken.account_pk == -1 || authToken.token == null){
                navAuthActivity()
                finish()
            }
        })
    }

    override fun expandAppBar() {
        findViewById<AppBarLayout>(R.id.app_bar).setExpanded(true)
    }

    override fun onBackPressed() = bottomNavController.onBackPressed()

    private fun setupActionBar(){
        setSupportActionBar(tool_bar)
    }

    private fun navAuthActivity(){
        val intent = Intent(this, AuthActivity::class.java)
        startActivity(intent)
        finish()
        (application as BaseApplication).releaseMainComponent()
    }

    override fun displayProgressBar(bool: Boolean){
        if(bool){
            progress_bar.visibility = View.VISIBLE
        }
        else{
            progress_bar.visibility = View.GONE
        }
    }


}