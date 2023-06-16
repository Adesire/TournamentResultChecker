package com.lagoscountryclub.squash.lccsquash.domain.usecases

import com.auth0.android.jwt.JWT
import com.lagoscountryclub.squash.lccsquash.data.HttpError
import com.lagoscountryclub.squash.lccsquash.data.NetworkError
import com.lagoscountryclub.squash.lccsquash.data.Resource
import com.lagoscountryclub.squash.lccsquash.data.Success
import com.lagoscountryclub.squash.lccsquash.data.remote.api.ApiSessionManager
import com.pixplicity.easyprefs.library.Prefs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import timber.log.Timber
import java.util.*

abstract class BaseUseCase(private val sessionManager: ApiSessionManager) {

    protected val coroutineContext = Dispatchers.IO
    lateinit var onError: (message: String?) -> Unit
    lateinit var onLoading: (loading: Boolean) -> Unit

    private fun <T> parseResource(resource: Resource<T>, result: (response: T) -> Unit) {
        onLoading.invoke(true)
        when (resource) {
            is Success -> {
                onLoading.invoke(false)
                result.invoke(resource.data)
            }
            is HttpError -> {
                onLoading.invoke(false)
                logError("HttpError")
                val message = resource.message ?: ""
                if (message.contains("Token Expired")) {
                    Prefs.clear()
                    onError.invoke("Please login again")
                    return
                }
                onError.invoke(message)
            }
            is NetworkError -> {
                onLoading.invoke(false)
                logError("Network Error")
                onError.invoke("Network Error")
            }
        }
    }

    fun <T> Flow<Resource<T>>.evaluateOnEach(action: (T) -> Unit): Flow<Resource<T>> {
        return onEach { r ->
            parseResource(r) {
                action.invoke(it)
            }
        }
    }

    fun startLoading() {
        onLoading.invoke(true)
    }

    protected fun logError(message: String) {
        Timber.e(message)
    }

    fun checkJwtTokenExpired() =
        sessionManager.token.isNullOrEmpty() || JWT(sessionManager.token!!).expiresAt!!.time <= Date().time

    fun getUser() = sessionManager.userResponse

}