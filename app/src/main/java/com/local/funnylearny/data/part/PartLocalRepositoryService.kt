package com.local.funnylearny.data.part

import com.local.funnylearny.base.AppConstants
import com.local.funnylearny.base.AppUtil
import com.local.funnylearny.data.FunnyLearnyDataUtil
import com.local.funnylearny.domain.model.lesson.Lesson
import com.local.funnylearny.domain.model.part.Part
import org.apache.commons.io.IOUtils
import org.json.JSONObject

object PartLocalRepositoryService : PartLocalRepositoryContract {
    override fun getPart(lesson: String): List<Part> {

        val partList = ArrayList<Part>()

        val funnyLearnyString = FunnyLearnyDataUtil.getFunnyLearnyDataAsString()
        val jsonObject = JSONObject(funnyLearnyString)

        jsonObject.keys().forEach {
            if (it == lesson) {

                val partJSONArray = jsonObject.getJSONArray(it)

                (0 until partJSONArray.length()).forEach { i ->
                    var partJSONObject = partJSONArray.getJSONObject(i)
                    var partId = partJSONObject.getString(AppConstants.PartConstants.PART_ID)
                    var partType = partJSONObject.getInt(AppConstants.PartConstants.PART_TYPE)
                    var partStatus = partJSONObject.getInt(AppConstants.PartConstants.PART_STATUS)
                    var partMisc = partJSONObject.getString(AppConstants.PartConstants.PART_MISC)
                    val part = Part(partId, partType, partStatus, partMisc)
                    partList.add(part)
                }
            }
        }
        return partList
    }
}