package com.zoho.campaigns.base

/**
 * Created by muhil-3673 on 06/07/17.
 */
//$Id$
abstract class WorkerThreadRunnable : Runnable {
    // holds the reference to the Thread this Runnable is running.
    // To interrupt/ cancel the task running (in ThreadPoolExecutor)
    private var mThread: Thread? = null

    fun setThread(thread: Thread) {
        mThread = thread
    }

    fun getThread(): Thread? {
        return mThread
    }
}