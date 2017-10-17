package com.alexandercasal.devslopes.smackchat.controller

import android.app.Application
import com.alexandercasal.devslopes.smackchat.utils.SharedPrefs

/**
 * Created by Alexander on 10/16/2017.
 */
class App : Application() {

    companion object {
        lateinit var prefs: SharedPrefs
    }

    override fun onCreate() {
        prefs = SharedPrefs(applicationContext)
        super.onCreate()
    }
}