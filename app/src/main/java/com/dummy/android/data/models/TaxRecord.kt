package com.dummy.android.data.models

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize


@Parcelize
@JsonClass(generateAdapter = true)
data class TaxRecord(
    @Json(name = "Date")
    var date: String,
    @Json(name = "Ticker")
    var owner: String,
    @Json(name = "Client")
    var client: String,
    @Json(name = "Amount")
    var amount: String? = null,
    @Json(name = "Issue")
    var issue: String? = null,
    @Json(name = "Specific_Issue")
    var specificIssue: String? = null
) : Parcelable