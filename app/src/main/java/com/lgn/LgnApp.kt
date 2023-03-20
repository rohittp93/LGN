package com.lgn

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class LgnApp : Application() {
    companion object {
        lateinit  var appContext: Context
    }

    override fun onCreate() {
        super.onCreate()
        LgnApp.appContext = applicationContext
    }

}