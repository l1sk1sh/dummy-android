package com.dummy.android.ui.main.socials

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dummy.android.data.PreferencesDataStore

class DummySocialsViewModelFactory(
    private val application: Application,
    private val preferences: PreferencesDataStore
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DummySocialsViewModel::class.java)) {
            return DummySocialsViewModel(application, preferences) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}