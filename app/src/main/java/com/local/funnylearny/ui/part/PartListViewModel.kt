package com.local.funnylearny.ui.part

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.local.funnylearny.base.Injection
import com.local.funnylearny.base.ViewState
import com.local.funnylearny.domain.usecase.part.GetParts
import com.zoho.campaigns.base.UseCaseHandler

class PartListViewModel(application: Application) : AndroidViewModel(application){

    private val useCaseHandler: UseCaseHandler = Injection.provideUseCaseHandler()
    private val getPart = GetParts
    private lateinit var viewStateLiveData: MutableLiveData<ViewState>

    fun getViewStateLiveData() : MutableLiveData<ViewState>{

        if(!this::viewStateLiveData.isInitialized){
            viewStateLiveData = MutableLiveData()
        }
        return viewStateLiveData
    }

    fun getPart(){

        val requestValue = GetParts.RequestValue()

        useCaseHandler.execute( getPart,requestValue,)
    }
}