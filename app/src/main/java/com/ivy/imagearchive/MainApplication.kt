package com.ivy.imagearchive

import android.app.Application
import com.ivy.imagearchive.utils.PreferenceUtil
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MainApplication: Application() {
    lateinit var prefs: PreferenceUtil

    override fun onCreate() {
        super.onCreate()
        prefs = PreferenceUtil(applicationContext)
        prefs.deleteAllFavoriteItems()
    }
}