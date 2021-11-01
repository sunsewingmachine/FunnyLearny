package com.local.funnylearny.data.lesson

import com.local.funnylearny.domain.model.lesson.Lesson

object LessonRepositoryService : LessonRepositoryContract {

    var lessonLocalRepositoryContract: LessonLocalRepositoryContract = LessonLocalRepositoryService

    override fun getLessons(): List<Lesson> {
        return lessonLocalRepositoryContract.getLessons()
    }

}