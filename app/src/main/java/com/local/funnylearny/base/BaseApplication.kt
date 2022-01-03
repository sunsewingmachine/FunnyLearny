package com.local.funnylearny.base

import android.app.Application
import com.local.funnylearny.R


class BaseApplication : Application() {

    var isReCreated = false

    override fun onCreate() {
        super.onCreate()
        baseApplication = this
    }

    companion object {
        lateinit var baseApplication: BaseApplication
        fun getInstance(): BaseApplication = baseApplication
    }

}