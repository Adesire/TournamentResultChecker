package com.lagoscountryclub.squash.lccsquash.data.remote.api.response

data class SuccessResponse<out T>(
    val timestamp: String? = null,
    val statusCode: String? = null,
    val message: String? = null,
    val data: T? = null
)