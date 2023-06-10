package com.lagoscountryclub.squash.lccsquash.data.remote.api.interceptors

import com.lagoscountryclub.squash.lccsquash.data.remote.AuthApiService
import com.lagoscountryclub.squash.lccsquash.data.remote.api.ApiSessionManager
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(
    private val sessionManager: ApiSessionManager
) : Interceptor {

    private var pathsToExclude = arrayOf(
        "/${AuthApiService.LOGIN}"
    )

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()

        val url: String = chain.request().url.encodedPath
        if (!pathsToExclude.any { url.contains(it) }) {
            val accessToken = sessionManager.token
            accessToken?.let {
                requestBuilder.addHeader("Authorization", "Bearer $it")
            }
        }

        return chain.proceed(requestBuilder.build())
    }
}
