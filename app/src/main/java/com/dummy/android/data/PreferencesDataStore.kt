package com.dummy.android.data

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.dummy.android.R
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import timber.log.Timber
import java.util.concurrent.TimeUnit

@Suppress("SameParameterValue")
object PreferencesDataStore {

    // TODO
    @SuppressLint("StaticFieldLeak")
    private val remoteConfig: FirebaseRemoteConfig = FirebaseRemoteConfig.getInstance()

    lateinit var sharedPreferences: SharedPreferences

    init {
        remoteConfig.setDefaultsAsync(R.xml.default_config)
        refreshConfig(TimeUnit.HOURS.toSeconds(24))
    }

    fun init(context: Context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
    }

    fun isPaid(): Boolean = sharedPreferences.getBoolean(KEY_PAID, false)

    fun setPaid() {
        sharedPreferences.edit().putBoolean(KEY_PAID, true).apply()
    }

    fun getAuthToken(): String {
        return remoteConfig.getString(KEY_AUTH_TOKEN)
    }

    private fun refreshConfig(cacheExpirationSeconds: Long) {
        remoteConfig.fetch(cacheExpirationSeconds)
            .addOnSuccessListener {
                Timber.d("Remote config fetched.")
                remoteConfig.activate()
            }
    }

    private const val KEY_PAID = "paid"
    private const val KEY_AUTH_TOKEN = "dummy_token"
}