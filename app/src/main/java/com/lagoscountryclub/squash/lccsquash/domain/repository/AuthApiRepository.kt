package com.lagoscountryclub.squash.lccsquash.domain.repository

import com.lagoscountryclub.squash.lccsquash.data.Resource
import com.lagoscountryclub.squash.lccsquash.data.remote.api.request.LoginRequest
import com.lagoscountryclub.squash.lccsquash.data.remote.api.response.UserResponse

interface AuthApiRepository {
    suspend fun login(loginRequest: LoginRequest): Resource<UserResponse>
}