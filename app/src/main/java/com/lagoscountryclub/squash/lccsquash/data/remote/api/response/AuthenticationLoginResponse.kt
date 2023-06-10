package com.lagoscountryclub.squash.lccsquash.data.remote.api.response

data class AuthenticationLoginResponse(
    val accessToken: String,
    val accessTokenExpireTime: Long,
    val refreshToken: String,
    val refreshTokenExpireTime: Long,
    val userResponse: UserResponse
)