package com.local.funnylearny.base

import android.os.Process

/**
 * Created by muhil-3673 on 06/07/17.
 */
object ThreadUtil {

    fun setLessFavourablePriority() {
        // see: https://www.youtube.com/watch?v=NwFXVsM15Co&index=9&list=PLWz5rJ2EKKc9CBxr3BVjPTPoDPLdPIFCE
        android.os.Process.setThreadPriority(Process.THREAD_PRIORITY_DEFAULT + Process.THREAD_PRIORITY_LESS_FAVORABLE)
    }

}