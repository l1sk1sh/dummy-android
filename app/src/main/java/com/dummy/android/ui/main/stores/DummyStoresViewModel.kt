package com.dummy.android.ui.main.stores

import android.app.Application
import com.dummy.android.R
import com.dummy.android.data.PreferencesDataStore
import com.dummy.android.ui.main.BaseViewModel

class DummyStoresViewModel(
    app: Application,
    preferences: PreferencesDataStore
) : BaseViewModel(app, preferences) {

    val underConstructionText = R.string.dummy_stores_desc
}