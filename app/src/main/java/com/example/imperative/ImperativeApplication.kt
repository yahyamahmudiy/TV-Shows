package com.example.imperative

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ImperativeApplication:Application() {

    override fun onCreate() {
        super.onCreate()
        initViews()
    }

    private fun initViews() {

    }
}