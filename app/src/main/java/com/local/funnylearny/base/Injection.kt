package com.local.funnylearny.base

import com.zoho.campaigns.base.UseCaseHandler

object Injection {

    fun provideUseCaseHandler(): UseCaseHandler = UseCaseHandler.instance

}