package com.local.funnylearny.base

import android.app.Application


class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        baseApplication = this
    }

    companion object {
        lateinit var baseApplication: BaseApplication
        fun getInstance(): BaseApplication = baseApplication
    }

}