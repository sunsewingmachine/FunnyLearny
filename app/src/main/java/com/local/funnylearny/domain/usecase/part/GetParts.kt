package com.local.funnylearny.domain.usecase.part

import com.local.funnylearny.base.UseCase
import com.local.funnylearny.data.part.PartRepositoryContract
import com.local.funnylearny.data.part.PartRepositoryService
import com.local.funnylearny.domain.model.lesson.Lesson
import com.local.funnylearny.domain.model.part.Part

object GetParts : UseCase<UseCase.RequestValue , GetParts.ResponseValue>() {

    var partRepositoryService : PartRepositoryContract = PartRepositoryService

    override fun executeUseCase(requestValue: UseCase.RequestValue){
        requestValue as RequestValue
        val part : List<Part> = partRepositoryService.getPart(requestValue.lessonName)
        val responseValue = ResponseValue(part)
        useCaseCallBack.onSuccess(responseValue)
    }

    class RequestValue(val lessonName : String) : UseCase.RequestValue

    class ResponseValue(val part : List<Part>) : UseCase.ResponseValue


}