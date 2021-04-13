package com.dummy.android.ui.main.taxes.detail

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dummy.android.data.models.TaxRecord

class DetailViewModelFactory(
    private val taxRecord: TaxRecord,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(taxRecord, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
