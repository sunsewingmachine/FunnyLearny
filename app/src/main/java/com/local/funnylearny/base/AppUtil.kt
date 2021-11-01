package com.local.funnylearny.base

import android.content.Context

object AppUtil {

    fun getApplicationContext() : Context {
        return BaseApplication.getInstance().applicationContext
    }

}