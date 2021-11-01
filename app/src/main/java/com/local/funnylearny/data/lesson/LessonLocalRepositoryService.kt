package com.local.funnylearny.data.lesson

import com.local.funnylearny.base.AppUtil
import com.local.funnylearny.data.FunnyLearnyDataUtil
import com.local.funnylearny.domain.model.lesson.Lesson
import org.apache.commons.io.IOUtils
import org.json.JSONObject

object LessonLocalRepositoryService : LessonLocalRepositoryContract {

    override fun getLessons() : List<Lesson> {

        val lessonList = ArrayList<Lesson>()

        val funnyLearnyString  = FunnyLearnyDataUtil.getFunnyLearnyDataAsString()
        val jsonObject = JSONObject(funnyLearnyString) // convert to object

        jsonObject.keys().forEach {
            val lesson = Lesson(it)
            lessonList.add(lesson)
        }

        return lessonList
    }
}