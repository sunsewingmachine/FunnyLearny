package com.local.funnylearny.base

/**
 * Use cases are the entry points to the domain layer.

 * @param <REQUEST_VALUE> the request value
 *
 * @param <RESPONSE_VALUE> the response value
 */
abstract class UseCase<REQUEST_VALUE : UseCase.RequestValue, RESPONSE_VALUE : UseCase.ResponseValue> {

    lateinit var requestValue: REQUEST_VALUE
    lateinit var useCaseCallBack: UseCaseCallBack<RESPONSE_VALUE>

    fun run() {
        executeUseCase(requestValue)
    }

    abstract fun executeUseCase(requestValue: REQUEST_VALUE)

    /**
     * data passed to a request
     */
    interface RequestValue

    /**
     * data received from a request
     */
    interface ResponseValue

    interface UseCaseCallBack<in RESPONSE_VALUE> {
        fun onSuccess(response: RESPONSE_VALUE)
        fun onError(appError: AppError)
    }

}