package com.local.funnylearny.base

data class AppError(var errorType: ErrorType = ErrorType.OTHER, var errorMessage: String = "") {

    var errorCode = -1

    override fun toString(): String {

        var errorType = "AppError: " 

        when (this.errorType) {

            ErrorType.NO_NETWORK -> errorType += "NO_NETWORK"

            ErrorType.TIMEOUT -> errorType += "TIMEOUT" 

            ErrorType.PARSE -> errorType += "PARSE" 

            ErrorType.INVALID_ACCESS_TOKEN -> errorType += "INVALID_ACCESS_TOKEN" 

            ErrorType.NO_LOCAL_DATA -> errorType += "NO_LOCAL_DATA" 

            ErrorType.NO_SERVER_DATA -> errorType += "NO_SERVER_DATA" 

            ErrorType.SERVER -> errorType += "SERVER"

            else -> errorType += "OTHER -> $errorCode"
        }

        return errorType
    }

}