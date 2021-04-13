package com.dummy.android.ui.main.taxes

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dummy.android.DummyAndroidApplication
import com.dummy.android.R
import com.dummy.android.data.PreferencesDataStore
import com.dummy.android.data.models.TaxRecord
import com.dummy.android.data.network.DummyApi
import com.dummy.android.data.network.dto.asDomainModel
import com.dummy.android.ui.main.BaseViewModel
import com.dummy.android.utils.hasInternetConnection
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber

class DummyTaxesViewModel(
    app: Application,
    private val preferences: PreferencesDataStore
) : BaseViewModel(app, preferences) {

    private var _records = MutableLiveData<List<TaxRecord>>()

    private var _filteredRecords = MutableLiveData<List<TaxRecord>>()
    val filteredRecords: LiveData<List<TaxRecord>>
        get() = _filteredRecords

    private var _searchedRecords = MutableLiveData<List<TaxRecord>>()
    val searchedRecords: LiveData<List<TaxRecord>>
        get() = _searchedRecords

    private val _eventFailure = MutableLiveData<String?>()
    val eventFailure: LiveData<String?>
        get() = _eventFailure

    private var _showRecordDetails = MutableLiveData<TaxRecord?>()
    val showRecordDetails: LiveData<TaxRecord?>
        get() = _showRecordDetails

    private var _filterButtonClicked = MutableLiveData<Boolean>()
    val filterButtonClicked: LiveData<Boolean>
        get() = _filterButtonClicked

    private var _searchButtonClicked = MutableLiveData<Boolean>()
    val searchButtonClicked: LiveData<Boolean>
        get() = _searchButtonClicked

    private val _statusSearchApi = MutableLiveData<ApiStatus>()
    val statusSearchApi: LiveData<ApiStatus>
        get() = _statusSearchApi

    val filterWord = MutableLiveData<String?>()
    val searchWord = MutableLiveData<String?>()

    private val noInternetText =
        getApplication<DummyAndroidApplication>().getString(R.string.no_internet)
    private val cannotBeBlank =
        getApplication<DummyAndroidApplication>().getString(R.string.cannot_be_blank)
    private val noRecordsFound =
        getApplication<DummyAndroidApplication>().getString(R.string.no_records_found)

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        _filterButtonClicked.value = false
        _searchButtonClicked.value = false

        Timber.d("Fetching historical list of records for dummy taxes...")

        coroutineScope.launch {
            if (hasInternetConnection(getApplication<DummyAndroidApplication>())) {
                val getEntityDeferred = DummyApi.RETROFIT_SERVICE.getLatestTaxesAsync()

                try {
                    _statusApi.value = ApiStatus.LOADING
                    val result = getEntityDeferred.await()
                    _statusApi.value = ApiStatus.DONE

                    if (result.isNotEmpty()) {
                        _records.value = result.asDomainModel()
                        _filteredRecords.value = result.asDomainModel()
                    }
                } catch (t: Throwable) {
                    _statusApi.value = ApiStatus.ERROR
                    _eventFailure.value = t.localizedMessage
                    Timber.e(t)
                }
            } else {
                _statusApi.value = ApiStatus.ERROR
                _eventFailure.value = noInternetText
            }
        }
    }

    fun filterLatestTaxes() {
        _filterButtonClicked.value = true

        if (filterWord.value?.isNotBlank() == true) {
            _filteredRecords.value =
                _records.value?.filter { it.owner.equals(filterWord.value, true) }
        } else {
            _filteredRecords.value = _records.value
        }
    }

    fun searchTaxesByTicker() {
        _searchButtonClicked.value = true

        Timber.d("Searching taxes by ticker ${searchWord.value}...")

        if (searchWord.value.isNullOrBlank()) {
            _eventFailure.value = cannotBeBlank
            return
        }

        coroutineScope.launch {
            if (hasInternetConnection(getApplication<DummyAndroidApplication>())) {
                val getEntityDeferred =
                    DummyApi.RETROFIT_SERVICE.getRecordsByOwnerAsync(searchWord.value!!)

                try {
                    _statusSearchApi.value = ApiStatus.LOADING
                    val result = getEntityDeferred.await()
                    _statusSearchApi.value = ApiStatus.DONE

                    if (result.isNotEmpty()) {
                        _searchedRecords.value = result.asDomainModel()
                    }
                } catch (t: Throwable) {
                    _statusSearchApi.value = ApiStatus.ERROR
                    _eventFailure.value = noRecordsFound
                    Timber.e(t)
                }
            } else {
                _statusSearchApi.value = ApiStatus.ERROR
                _eventFailure.value = noInternetText
            }
        }
    }

    fun onDetailsClicked(record: TaxRecord) {
        _showRecordDetails.value = record
    }

    fun onDetailsClickedComplete() {
        _showRecordDetails.value = null
    }

    fun onFailureComplete() {
        _eventFailure.value = null
    }
}