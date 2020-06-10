package com.e.occano.ui.main.alerts.state

import android.os.Parcelable
import com.e.occano.models.AccountProperties
import kotlinx.android.parcel.Parcelize

const val ACCOUNT_VIEW_STATE_BUNDLE_KEY = "com.e.occano.ui.main.account.state.AccountViewState"

@Parcelize
class AccountViewState(

    var accountProperties: AccountProperties? = null

) : Parcelable