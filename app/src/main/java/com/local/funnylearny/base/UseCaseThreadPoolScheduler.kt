package com.zoho.campaigns.base

import android.os.Handler
import android.os.Looper
import com.local.funnylearny.base.AppError
import com.local.funnylearny.base.UseCase
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

/**
 * Created by muhil-3673 on 06/07/17.
 */
//$Id$
class UseCaseThreadPoolScheduler : UseCaseScheduler {

    companion object {

        private const val THREAD_POOL_SIZE = 8
        private const val MAX_THREAD_POOL_SIZE = 16
        private const val TIMEOUT = 30
    }

    // to post the response/ error in UI main thread.
    private val mHandler = Handler(Looper.getMainLooper())

    private val mThreadPoolExecutor: ThreadPoolExecutor

    private val workQueue = LinkedBlockingQueue<Runnable>()

    init {
        // ArrayBlockingQueue uses array internally, and it has a fixed capacity. so if the number of tasks
        // added is more than the capacity, then it will throw RejectedExecutionException. To avoid that,
        // we can set the capacity to Integer.MAX_VALUE, but it is waste of space.
        // LinkedBlockingQueue doesnt have fixed size, and its backed by LinkedList. So adding a new task
        // means creating a node dynamically, which is more overhead than adding a task in ArrayBlockingQueue.
        mThreadPoolExecutor = ThreadPoolExecutor(THREAD_POOL_SIZE, MAX_THREAD_POOL_SIZE, TIMEOUT.toLong(), TimeUnit.SECONDS, workQueue)
    }

    override fun execute(runnable: Runnable) {
        mThreadPoolExecutor.execute(runnable)
    }

    override fun <RESPONSE : UseCase.ResponseValue> notifyResponse(response: RESPONSE, useCaseCallBack: UseCase.UseCaseCallBack<RESPONSE>?) {
        mHandler.post { useCaseCallBack?.onSuccess(response) }
    }

    override fun <RESPONSE : UseCase.ResponseValue> notifyError(error: AppError, useCaseCallBack: UseCase.UseCaseCallBack<RESPONSE>?) {
        mHandler.post { useCaseCallBack?.onError(error) }
    }

    override fun cancelAll() {
        /*
         * Creates an array of Runnables that's the same size as the
         * thread pool work queue
         */
        val runnableArray = arrayOfNulls<Runnable>(workQueue.size)
        // Populates the array with the Runnables in the queue
        workQueue.toTypedArray()

        /*
         * Iterates over the array of Runnables and interrupts each one's Thread.
         */
        synchronized(mHandler) {
            // Iterates over the array of tasks
            for (aRunnableArray in runnableArray) {
                // Gets the current thread
                // if the Thread exists, post an interrupt to it
                (aRunnableArray as WorkerThreadRunnable).getThread()?.interrupt()
            }
        }
    }

    override fun cancelRecent() {
        val runnable = workQueue.poll()
        if (runnable != null) {
            (runnable as WorkerThreadRunnable).getThread()?.interrupt()
        }
    }
}