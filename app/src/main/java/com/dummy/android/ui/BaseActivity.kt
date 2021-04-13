package com.dummy.android.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.dummy.android.ui.billing.BillingActivity
import com.dummy.android.ui.settings.SettingsActivity

abstract class BaseActivity : AppCompatActivity() {

    fun openSettings() {
        startActivity(Intent(this, SettingsActivity::class.java))
    }

    fun openBilling() {
        startActivity(Intent(this, BillingActivity::class.java))
    }
}