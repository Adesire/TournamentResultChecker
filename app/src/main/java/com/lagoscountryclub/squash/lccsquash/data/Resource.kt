package com.lagoscountryclub.squash.lccsquash.data

sealed class Resource<out T>
data class Success<T>(val data: T) : Resource<T>()
data class HttpError(val code: Int? = null, val message: String? = null) : Resource<Nothing>()
object NetworkError : Resource<Nothing>()
