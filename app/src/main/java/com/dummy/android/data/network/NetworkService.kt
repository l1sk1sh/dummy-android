package com.dummy.android.data.network

import com.dummy.android.data.PreferencesDataStore
import com.dummy.android.data.network.dto.NetworkTaxes
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import okhttp3.Protocol
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import java.util.concurrent.TimeUnit


private const val BASE_URL = "https://api.dummy.com/"

private var moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val okHttpClientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
    .protocols(listOf(Protocol.HTTP_1_1))
    .connectTimeout(45, TimeUnit.MINUTES)
    .readTimeout(45, TimeUnit.SECONDS)
    .writeTimeout(3, TimeUnit.MINUTES)

private val retrofitBuilder: Retrofit.Builder = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .client(okHttpClientBuilder.build())
    .baseUrl(BASE_URL)

private var retrofit: Retrofit = retrofitBuilder.build()

private fun <S> createService(
    serviceClass: Class<S>,
    authToken: String?
): S {
    authToken?.let {
        val interceptor = AuthInterceptor(authToken)
        if (!okHttpClientBuilder.interceptors().contains(interceptor)) {
            okHttpClientBuilder.addInterceptor(interceptor)
            retrofitBuilder.client(okHttpClientBuilder.build())
            retrofit = retrofitBuilder.build()
        }
    }

    return retrofit.create(serviceClass)
}

interface DummyApiService {

    @GET("api/taxes")
    fun getLatestTaxesAsync():
            Deferred<List<NetworkTaxes>>

    @GET("api/taxes/{owner}")
    fun getRecordsByOwnerAsync(@Path(value = "owner", encoded = true) owner: String):
            Deferred<List<NetworkTaxes>>
}

object DummyApi {
    val RETROFIT_SERVICE: DummyApiService by lazy {
        createService(
            DummyApiService::class.java,
            PreferencesDataStore.getAuthToken()
        )
    }
}
