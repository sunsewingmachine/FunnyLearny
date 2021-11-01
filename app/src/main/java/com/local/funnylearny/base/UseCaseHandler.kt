package com.zoho.campaigns.base

import com.local.funnylearny.base.AppError
import com.local.funnylearny.base.ThreadUtil
import com.local.funnylearny.base.UseCase

//$Id$
class UseCaseHandler(private val useCaseScheduler: UseCaseScheduler) {

    companion object {
        val instance: UseCaseHandler = UseCaseHandler(UseCaseThreadPoolScheduler())
    }

    fun <REQUEST_VALUE : UseCase.RequestValue, RESPONSE_VALUE : UseCase.ResponseValue> execute(
        useCase: UseCase<REQUEST_VALUE, RESPONSE_VALUE>,
        requestValue: REQUEST_VALUE, useCaseCallBack: UseCase.UseCaseCallBack<RESPONSE_VALUE>?) {

        useCase.requestValue = requestValue
        useCase.useCaseCallBack = CallBackUiWrapper(useCaseCallBack, this)
        useCaseScheduler.execute(object : WorkerThreadRunnable() {

            override fun run() {

                ThreadUtil.setLessFavourablePriority()
                setThread(Thread.currentThread())
                useCase.run()
            }
        })
    }

    fun <RESPONSE_VALUE : UseCase.ResponseValue> notifyResponse(response: RESPONSE_VALUE, useCaseCallBack: UseCase.UseCaseCallBack<RESPONSE_VALUE>?) = useCaseScheduler.notifyResponse(response, useCaseCallBack)

    fun <RESPONSE_VALUE : UseCase.ResponseValue> notifyError(appError: AppError, useCaseCallBack: UseCase.UseCaseCallBack<RESPONSE_VALUE>?) = useCaseScheduler.notifyError(appError, useCaseCallBack)

    class CallBackUiWrapper<in RESPONSE_VALUE : UseCase.ResponseValue>(val useCaseCallBack: UseCase.UseCaseCallBack<RESPONSE_VALUE>?, val useCaseHandler: UseCaseHandler) : UseCase.UseCaseCallBack<RESPONSE_VALUE> {

        override fun onSuccess(response: RESPONSE_VALUE) {
            useCaseHandler.notifyResponse(response, useCaseCallBack)
        }

        override fun onError(appError: AppError) {
            useCaseHandler.notifyError(appError, useCaseCallBack)
        }

    }

    /**
     * to cancel all usecases. No usage as of now.
     */
    fun cancelAll() = useCaseScheduler.cancelAll()

    fun cancelRecent() = useCaseScheduler.cancelRecent()

}