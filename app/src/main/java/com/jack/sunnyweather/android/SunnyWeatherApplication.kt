package com.jack.sunnyweather.android

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

class SunnyWeatherApplication: Application() {
    @SuppressLint("StaticFieldLeak")
    companion object {
        lateinit var context: Context
        const val TOKEN = "bTMbmt5Fc5hMCefe"

    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}