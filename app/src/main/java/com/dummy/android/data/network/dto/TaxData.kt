package com.dummy.android.data.network.dto

import com.dummy.android.data.models.TaxRecord
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NetworkTaxes(
    @Json(name = "Date")
    var date: String,
    @Json(name = "Owner")
    var owner: String,
    @Json(name = "Client")
    var client: String,
    @Json(name = "Amount")
    var amount: String? = null,
    @Json(name = "Issue")
    var issue: String? = null,
    @Json(name = "Specific_Issue")
    var specificIssue: String? = null
)

fun NetworkTaxes.asDomainModel(): TaxRecord {
    return TaxRecord(
        date = date,
        owner = owner,
        client = client,
        amount = amount,
        issue = issue,
        specificIssue = specificIssue
    )
}

fun List<NetworkTaxes>.asDomainModel(): List<TaxRecord> {
    return map {
        it.asDomainModel()
    }
}