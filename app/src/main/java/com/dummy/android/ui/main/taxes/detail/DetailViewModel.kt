package com.dummy.android.ui.main.taxes.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dummy.android.data.models.TaxRecord


class DetailViewModel(
    taxRecord: TaxRecord,
    app: Application
) : AndroidViewModel(app) {

    private var _selectedRecord = MutableLiveData<TaxRecord>()
    val selectedRecord: LiveData<TaxRecord>
        get() = _selectedRecord

    init {
        _selectedRecord.value = taxRecord
    }
}
