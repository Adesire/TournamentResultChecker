package com.lagoscountryclub.squash.lccsquash.domain.usecases

import com.lagoscountryclub.squash.lccsquash.data.HttpError
import com.lagoscountryclub.squash.lccsquash.data.NetworkError
import com.lagoscountryclub.squash.lccsquash.data.Resource
import com.lagoscountryclub.squash.lccsquash.data.Success
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import timber.log.Timber

abstract class BaseUseCase {

    protected val coroutineContext = Dispatchers.IO
    lateinit var onError: (message: String?) -> Unit

    private fun <T> parseResource(resource: Resource<T>, result: (response: T) -> Unit) {
        when (resource) {
            is Success -> {
                result.invoke(resource.data)
            }
            is HttpError -> {
                logError("HttpError")
                onError.invoke(resource.message)
            }
            is NetworkError -> {
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

    protected fun logError(message: String) {
        Timber.e(message)
    }
}