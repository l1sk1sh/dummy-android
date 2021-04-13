package com.dummy.android.utils

import java.text.SimpleDateFormat
import java.util.*


fun formatAmount(amount: String): String {
    return "$$amount"
}

fun formatDate(date: String): String {
    val inFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
    val outFormat = SimpleDateFormat("yyyy MMMM dd", Locale.ENGLISH)
    val parsedDate = inFormat.parse(date)
    return if (parsedDate != null) {
        outFormat.format(parsedDate)
    } else {
        date
    }
}