package com.dummy.android

import com.dummy.android.data.PreferencesDataStore
import timber.log.Timber

class DummyAndroidApplication : android.app.Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        PreferencesDataStore.init(applicationContext)
    }
}