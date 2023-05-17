package com.lagoscountryclub.squash.lccsquash.data.remote.api

import com.google.gson.Gson
import com.lagoscountryclub.squash.lccsquash.data.HttpError
import com.lagoscountryclub.squash.lccsquash.data.NetworkError
import com.lagoscountryclub.squash.lccsquash.data.Resource
import com.lagoscountryclub.squash.lccsquash.data.Success
import kotlinx.coroutines.CancellationException
import okhttp3.ResponseBody
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException

suspend fun <T> makeRepositoryRequest(req: suspend () -> T): Resource<T> = try {
    Success(req())
} catch (e: Exception) {
    Timber.e(e.stackTraceToString())
    when (e) {
        is IOException -> NetworkError
        is HttpException -> {
            val message = if (e.code() == 404) "The current request is not defined by this API."
            else e.getResponse<Any>(Gson()).toString()
            Timber.e("${e.getResponse<Any>(Gson())}")
            HttpError(e.code(), message)
        }
        is CancellationException -> HttpError()
        else -> HttpError(2022, e.localizedMessage ?: "Unexpected error")
    }
}


inline fun <reified T> Throwable.getResponse(gson: Gson): T =
    gson.fromJson(getResponseBody(), T::class.java)


fun Throwable.isHttpException(): Boolean = (this is HttpException && this.code() in 400..599)

fun Throwable.getResponseBody(): String {
    return if (isHttpException()) {
        val body: ResponseBody? =
            (this as HttpException).response()!!.errorBody()
        try {
            val sBody = body?.string()?.let {
                if (it.contains("<html>")) "Service unavailable at the moment" else it
            }
            Timber.e("body $sBody")
            sBody
                ?: "{\"message\": \"An unexpected error occurred {${this.localizedMessage}}, Please try again\"}"
        } catch (e1: Exception) {
            // userResponseResult.setValue(Error(Throwable("An unexpected error occurred")))
            "{\"message\": \"An unexpected error occurred {${this.localizedMessage}}, Please try again\"}"
        } finally {
            body?.close()
        }
    } else
        "{\"message\": \"An unexpected error occurred, Please try again\"}"
}