package com.dummy.android.ui

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.dummy.android.R
import com.dummy.android.ui.main.BaseViewModel
import com.dummy.android.utils.formatAmount
import com.dummy.android.utils.formatDate


@BindingAdapter("app:apiStatus")
fun bindStatus(statusImageView: ImageView, status: BaseViewModel.ApiStatus?) {

    when (status) {
        BaseViewModel.ApiStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }
        BaseViewModel.ApiStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_baseline_wifi_off_24)
        }
        BaseViewModel.ApiStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }
    }
}

@BindingAdapter("app:amount_formatted")
fun TextView.setAmountFormatted(amount: String?) {
    if (amount?.isNotBlank() == true) {
        text = formatAmount(amount)
    }
}

@BindingAdapter("app:issue_formatted")
fun TextView.setIssueFormatted(issue: String?) {
    if (issue?.isNotBlank() == true) {
        text = issue
    }
}

@BindingAdapter("app:date_formatted")
fun TextView.setDateFormatted(date: String?) {
    if (date?.isNotBlank() == true) {
        text = formatDate(date)
    }
}
