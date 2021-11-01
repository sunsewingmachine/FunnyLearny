package com.local.funnylearny.ui.part

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.local.funnylearny.base.AppError
import com.local.funnylearny.base.Injection
import com.local.funnylearny.base.UseCase
import com.local.funnylearny.base.ViewState
import com.local.funnylearny.domain.model.lesson.Lesson
import com.local.funnylearny.domain.model.part.Part
import com.local.funnylearny.domain.usecase.part.GetParts
import com.local.funnylearny.ui.lesson.LessonListViewModel
import com.zoho.campaigns.base.UseCaseHandler

class PartListViewModel(application: Application) : AndroidViewModel(application) {

    private val useCaseHandler: UseCaseHandler = Injection.provideUseCaseHandler()
    private val getParts = GetParts
    private lateinit var viewStateLiveData: MutableLiveData<ViewState>

    fun getViewStateLiveData() : MutableLiveData<ViewState>{

        if(!this::viewStateLiveData.isInitialized){
            viewStateLiveData = MutableLiveData()
        }
        return viewStateLiveData
    }

    fun getParts(lessonName : String){

        val requestValue = GetParts.RequestValue(lessonName)

        useCaseHandler.execute(
                getParts,
                requestValue,
                object : UseCase.UseCaseCallBack<GetParts.ResponseValue> {
                    override fun onSuccess(response: GetParts.ResponseValue) {
                        viewStateLiveData.value = PartListViewStateLiveData(response.part)
                    }
                    override fun onError(appError: AppError) {
                    }
                })
    }


    class PartListViewStateLiveData(val parts : List<Part>) : ViewState.DataAvailable()
}