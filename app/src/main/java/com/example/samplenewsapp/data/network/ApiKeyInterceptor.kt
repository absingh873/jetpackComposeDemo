package com.example.samplenewsapp.data.network

import com.example.samplenewsapp.data.di.NetworkApiKey
import okhttp3.Interceptor
import okhttp3.Response
/*
* this class will act as intercepter to  try request from client with required token and
* module injected using hilt in retrofit module
* */
class ApiKeyInterceptor(@NetworkApiKey private val apiKey: String) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val requestBuilder = originalRequest.newBuilder().header("X-Master-Key",apiKey)
            .addHeader("X-Bin-Meta","false")
        val request = requestBuilder.build()
        return chain.proceed(request)
    }

}