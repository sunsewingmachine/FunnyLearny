package com.local.funnylearny.ui.lesson

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.local.funnylearny.base.*
import com.local.funnylearny.domain.model.lesson.Lesson
import com.local.funnylearny.domain.usecase.lesson.GetLessons
import com.zoho.campaigns.base.UseCaseHandler

class LessonListViewModel(application: Application) : AndroidViewModel(application) {

    private val useCaseHandler: UseCaseHandler = Injection.provideUseCaseHandler()
    private val getLessons = GetLessons

    private lateinit var viewStateLiveData: MutableLiveData<ViewState>

    fun getViewStateLiveData(): MutableLiveData<ViewState> {
        if (!this::viewStateLiveData.isInitialized) {
            viewStateLiveData = MutableLiveData()
        }
        return viewStateLiveData
    }

    fun getLessons() {

        val requestValue = GetLessons.RequestValue()

        useCaseHandler.execute(
            getLessons,
            requestValue,
            object : UseCase.UseCaseCallBack<GetLessons.ResponseValue> {
                override fun onSuccess(response: GetLessons.ResponseValue) {
                    viewStateLiveData.value = LessonListViewStateLiveData(response.lessons)
                }

                override fun onError(appError: AppError) {

                }

            })
    }


    class LessonListViewStateLiveData(val lessons : List<Lesson>) : ViewState.DataAvailable()

}