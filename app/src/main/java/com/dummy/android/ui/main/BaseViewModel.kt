package com.dummy.android.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dummy.android.data.PreferencesDataStore
import timber.log.Timber

abstract class BaseViewModel(
    app: Application,
    private val preferences: PreferencesDataStore
) : AndroidViewModel(app) {

    enum class ApiStatus { LOADING, ERROR, DONE }

    protected val _statusApi = MutableLiveData<ApiStatus>()
    val statusApi: LiveData<ApiStatus>
        get() = _statusApi

    private var _openAds = MutableLiveData<Boolean>()
    val openAds: LiveData<Boolean>
        get() = _openAds

    fun onAdsButtonClicked() {
        Timber.d("Ads button clicked")
        _openAds.value = true
    }
}