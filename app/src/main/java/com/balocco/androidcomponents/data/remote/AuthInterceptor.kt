package com.balocco.androidcomponents.data.remote

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

private const val LANGUAGE_PARAM_KEY = "language"
private const val LANGUAGE_PARAM_VALUE = "en-US" // should be passed based on device locale
private const val API_KEY_PARAM_KEY = "api_key"
private const val API_KEY_PARAM_VALUE = "410bc004d3f349e1e2a687516fa6b866"

class AuthInterceptor() : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val originalUrl = original.url
        val newUrl = originalUrl.newBuilder()
            .addQueryParameter(LANGUAGE_PARAM_KEY, LANGUAGE_PARAM_VALUE)
            .addQueryParameter(API_KEY_PARAM_KEY, API_KEY_PARAM_VALUE)
            .build()

        val requestBuilder = original.newBuilder().url(newUrl)
        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}