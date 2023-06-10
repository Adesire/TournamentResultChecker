package com.lagoscountryclub.squash.lccsquash.data.remote

import com.lagoscountryclub.squash.lccsquash.data.remote.api.request.LoginRequest
import com.lagoscountryclub.squash.lccsquash.data.remote.api.response.AuthenticationLoginResponse
import com.lagoscountryclub.squash.lccsquash.data.remote.api.response.SuccessResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {

    @POST(LOGIN)
    suspend fun login(@Body body: LoginRequest): SuccessResponse<AuthenticationLoginResponse>

    companion object {
        private const val LOGIN_BASE = "v1/auth"
        const val LOGIN = "$LOGIN_BASE/login"
    }
}