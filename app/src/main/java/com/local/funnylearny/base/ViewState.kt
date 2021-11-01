package com.local.funnylearny.base

sealed class ViewState {

    object Loading : ViewState()
    data class Error(var errorMessageId: Int,
                     val shouldShowInErrorScreen: Boolean,
                     var appError: AppError = AppError(),
                     var hasShownAlready : Boolean = false) : ViewState()
    open class DataAvailable : ViewState()
    data class DataAndError(val dataAvailable: DataAvailable,val error: Error) : ViewState()

}
