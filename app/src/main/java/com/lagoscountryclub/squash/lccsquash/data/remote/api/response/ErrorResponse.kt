package com.lagoscountryclub.squash.lccsquash.data.remote.api.response

data class ErrorResponse(
    val responseCode: String? = null,
    val message: String? = null,
    val error: String? = null
)
