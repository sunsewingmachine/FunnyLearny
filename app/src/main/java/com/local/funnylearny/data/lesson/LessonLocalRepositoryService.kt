package com.local.funnylearny.data.lesson

import com.local.funnylearny.base.AppUtil
import com.local.funnylearny.domain.model.lesson.Lesson
import org.apache.commons.io.IOUtils
import org.json.JSONObject

object LessonLocalRepositoryService : LessonLocalRepositoryContract {

    override fun getLessons() : List<Lesson> {

        val lessonList = ArrayList<Lesson>()
        val inputStream = AppUtil.getApplicationContext().assets.open("funnylearny.json")
        val lessonString  = IOUtils.toString(inputStream, "UTF-8")
        val jsonObject = JSONObject(lessonString)

        jsonObject.keys().forEach {
            val lesson = Lesson(it)
            lessonList.add(lesson)
        }

        return lessonList
    }
}