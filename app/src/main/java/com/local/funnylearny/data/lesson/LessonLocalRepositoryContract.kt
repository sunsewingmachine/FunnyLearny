package com.local.funnylearny.data.lesson

import com.local.funnylearny.domain.model.lesson.Lesson

interface LessonLocalRepositoryContract {
    fun getLessons() : List<Lesson>
}