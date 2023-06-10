package com.lagoscountryclub.squash.lccsquash.data.repository

import com.lagoscountryclub.squash.lccsquash.data.Resource
import com.lagoscountryclub.squash.lccsquash.data.remote.AuthApiService
import com.lagoscountryclub.squash.lccsquash.data.remote.api.ApiSessionManager
import com.lagoscountryclub.squash.lccsquash.data.remote.api.makeRepositoryRequest
import com.lagoscountryclub.squash.lccsquash.data.remote.api.request.LoginRequest
import com.lagoscountryclub.squash.lccsquash.data.remote.api.response.UserResponse
import com.lagoscountryclub.squash.lccsquash.domain.repository.AuthApiRepository
import com.pixplicity.easyprefs.library.Prefs
import javax.inject.Inject

class AuthApiRepositoryImpl @Inject constructor(
    private val apiService: AuthApiService,
    private val sessionManager: ApiSessionManager
) : AuthApiRepository {

    override suspend fun login(loginRequest: LoginRequest): Resource<UserResponse> =
        makeRepositoryRequest {
            apiService.login(loginRequest).data!!.apply {
                Prefs.clear()
                sessionManager.token = this.accessToken
                sessionManager.refreshToken = this.refreshToken
                sessionManager.userResponse = this.userResponse
            }.userResponse
        }

}