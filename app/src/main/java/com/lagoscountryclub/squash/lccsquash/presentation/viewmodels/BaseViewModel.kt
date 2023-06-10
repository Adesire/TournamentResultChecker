package com.lagoscountryclub.squash.lccsquash.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel() {

    private val _showLoader = MutableLiveData(false)
    val showLoader: LiveData<Boolean>
        get() = _showLoader

    private val _showError = MutableLiveData("")
    val showError: LiveData<String>
        get() = _showError

    private val _isAdmin = MutableLiveData(false)
    val isAdmin: LiveData<Boolean>
        get() = _isAdmin

    fun setErrorMessage(message: String) {
        _showError.postValue(message)
        _showLoader.postValue(false)
    }

    fun setLoading(load: Boolean) {
        _showLoader.postValue(load)
    }

    fun setAdmin(admin: Boolean) {
        _isAdmin.postValue(admin)
    }

    fun startLoading() {
        _showLoader.postValue(true)
    }

    fun stopLoading() {
        _showLoader.postValue(false)
    }

}