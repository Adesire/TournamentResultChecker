package com.lagoscountryclub.squash.lccsquash.data.remote.api

import com.google.gson.Gson
import com.pixplicity.easyprefs.library.Prefs
import timber.log.Timber
import javax.inject.Inject

class ApiSessionManager @Inject constructor(private val gson: Gson) {

    init {
        Timber.i("ApiSessionManager init")
    }

    var token: String?
        set(value) = Prefs.putString(Constants.TOKEN, value)
        get() = Prefs.getString(Constants.TOKEN, "")
}