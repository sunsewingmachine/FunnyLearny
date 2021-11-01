package com.zoho.campaigns.base

import com.local.funnylearny.base.AppError
import com.local.funnylearny.base.UseCase

/**
 * Created by muhil-3673 on 05/07/17.
 */
//$Id$
interface UseCaseScheduler {
    fun execute(runnable: Runnable)
    fun <RESPONSE : UseCase.ResponseValue> notifyResponse(response: RESPONSE, useCaseCallBack: UseCase.UseCaseCallBack<RESPONSE>?)
    fun <RESPONSE : UseCase.ResponseValue> notifyError(error: AppError, useCaseCallBack: UseCase.UseCaseCallBack<RESPONSE>?)
    fun cancelAll()
    fun cancelRecent()
}