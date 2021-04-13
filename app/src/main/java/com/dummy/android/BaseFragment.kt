package com.dummy.android

import androidx.fragment.app.Fragment
import com.dummy.android.ui.BaseActivity

abstract class BaseFragment : Fragment() {

    fun openBillingFromAd() {
        (activity as BaseActivity).openBilling()
    }
}