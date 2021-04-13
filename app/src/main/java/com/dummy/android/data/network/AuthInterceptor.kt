package com.dummy.android.data.network

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val authToken: String) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val builder = original.newBuilder()
            .header("Authorization", "Token $authToken")
        val request = builder.build()
        val response = chain.proceed(request)
        if (response.code == 401) {
            // TODO "401 response handling"
        }

        return response
    }
}
