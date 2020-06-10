package com.e.occano.ui.main.alerts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.e.occano.R
import com.e.occano.ui.main.alerts.state.ACCOUNT_VIEW_STATE_BUNDLE_KEY
import com.e.occano.ui.main.alerts.state.AccountStateEvent
import com.e.occano.ui.main.alerts.state.AccountViewState
import com.e.occano.utils.StateMessageCallback
import com.e.occano.utils.SuccessHandling.Companion.RESPONSE_PASSWORD_UPDATE_SUCCESS
import kotlinx.android.synthetic.main.fragment_change_password.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import javax.inject.Inject

@FlowPreview
@ExperimentalCoroutinesApi
class ChangePasswordFragment
@Inject
constructor(
    viewModelFactory: ViewModelProvider.Factory
): BaseAccountFragment(R.layout.fragment_change_password, viewModelFactory) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Restore state after process death
        savedInstanceState?.let { inState ->
            (inState[ACCOUNT_VIEW_STATE_BUNDLE_KEY] as AccountViewState?)?.let { viewState ->
                viewModel.setViewState(viewState)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        update_password_button.setOnClickListener {
            viewModel.setStateEvent(
                AccountStateEvent.ChangePasswordEvent(
                    input_current_password.text.toString(),
                    input_new_password.text.toString(),
                    input_confirm_new_password.text.toString()
                )
            )
        }

        subscribeObservers()
    }

    private fun subscribeObservers(){

        viewModel.numActiveJobs.observe(viewLifecycleOwner, Observer { jobCounter ->
            uiCommunicationListener.displayProgressBar(viewModel.areAnyJobsActive())
        })

        viewModel.stateMessage.observe(viewLifecycleOwner, Observer { stateMessage ->

            stateMessage?.let {

                if(stateMessage.response.message.equals(RESPONSE_PASSWORD_UPDATE_SUCCESS)){
                    uiCommunicationListener.hideSoftKeyboard()
                    findNavController().popBackStack()
                }

                uiCommunicationListener.onResponseReceived(
                    response = it.response,
                    stateMessageCallback = object: StateMessageCallback {
                        override fun removeMessageFromStack() {
                            viewModel.clearStateMessage()
                        }
                    }
                )
            }
        })
    }
}

