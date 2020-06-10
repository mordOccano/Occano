package com.e.occano.ui.main.alerts.status

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.e.occano.R
import com.e.occano.models.Status
import com.e.occano.ui.main.alerts.state.ACCOUNT_VIEW_STATE_BUNDLE_KEY
import com.e.occano.ui.main.alerts.state.AccountViewState
import com.e.occano.utils.TopSpacingItemDecoration
import com.e.occano.utils.Utility
import kotlinx.android.synthetic.main.fragment_status.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

import kotlin.random.Random

@FlowPreview
@ExperimentalCoroutinesApi
class StatusFragment
    constructor(viewModelFactory:ViewModelProvider.Factory)
    :BaseAlertsFragment(R.layout.fragment_status,viewModelFactory), AckStatusAdapter.Interaction, NewStatusAdapter.Interaction, View.OnClickListener {

    var statusesList = ArrayList<Status>()
    lateinit var statusNewRVAdapter: NewStatusAdapter
    lateinit var statusAckRVAdapter: AckStatusAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Restore state after process death

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners(view)
        initNewRv()
        initAckRv()
        initRvList()

        this.statusNewRVAdapter.submitList(statusesList)
        this.statusAckRVAdapter.submitList(statusesList)
    }

    private fun setListeners(v:View) {
    }

    private fun initRvList() {
        for (i in 1..20){
            statusesList.add(
                Status(i,2,"Main Title", "sub title", "subTite","sub moria contenta sub more content sub moria contenta sub more content",
                (Random.nextInt(1,100))%3,(i+1)%2,0, Utility.getCurrentTimeStamp().plus(i))
            )
        }
    }

    private fun initAckRv() {
        status_rv_acknowledge_notification.apply {
            layoutManager = LinearLayoutManager(activity)
            val  topSpacingItemDecoration = TopSpacingItemDecoration(10)
            addItemDecoration(topSpacingItemDecoration)
            statusAckRVAdapter =
                AckStatusAdapter(this@StatusFragment)
            adapter = statusAckRVAdapter
        }
    }

    private fun initNewRv() {
        status_rv_new_notification.apply {
            layoutManager = LinearLayoutManager(activity)
            val topSpacingItemDecoration = TopSpacingItemDecoration(10)
            addItemDecoration(topSpacingItemDecoration)
            statusNewRVAdapter =
                NewStatusAdapter(this@StatusFragment)
            adapter = statusNewRVAdapter
        }
    }

    override fun onItemSelected(position: Int, item: Status) {
        statusesList.get(position).kindOfAcknowledge = 0
//        this.statusNewRVAdapter.submitList(statusesList)
//        this.statusAckRVAdapter.submitList(statusesList)
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            else->{
                Toast.makeText(this.context,"try again", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
    }
}