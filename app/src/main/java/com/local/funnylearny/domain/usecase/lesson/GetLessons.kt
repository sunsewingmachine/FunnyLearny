package com.local.funnylearny.domain.usecase.lesson

import com.local.funnylearny.base.UseCase
import com.local.funnylearny.data.lesson.LessonRepositoryContract
import com.local.funnylearny.data.lesson.LessonRepositoryService
import com.local.funnylearny.domain.model.lesson.Lesson


/*
class A() {

    ArrayList<String> arrList;

    A(ArrayList<String> arrList) {

        this.arrList = arrList;

    }

}
ArrayList<String> arrList = new ArrayList<String>()
arrList.add("madhan")

A aclass = new A(arrList)
priny(aclass.a)
*/


object GetLessons : UseCase<UseCase.RequestValue, GetLessons.ResponseValue>() {

    var lessonRepositoryService : LessonRepositoryContract = LessonRepositoryService

    override fun executeUseCase(requestValue : UseCase.RequestValue) {

        val lessons : List<Lesson> = lessonRepositoryService.getLessons()
        val responseValue = ResponseValue(lessons)
        useCaseCallBack.onSuccess(responseValue)

    }

    class RequestValue() : UseCase.RequestValue

    class ResponseValue(val lessons : List<Lesson>) : UseCase.ResponseValue


}