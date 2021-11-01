package com.local.funnylearny.data.lesson

import com.local.funnylearny.domain.model.lesson.Lesson

interface LessonRepositoryContract {

    fun getLessons() : List<Lesson>

    interface LessonRepositoryCallBack {
        fun onDataFetchSuccess()
        fun onErrorOccurred()
    }

}