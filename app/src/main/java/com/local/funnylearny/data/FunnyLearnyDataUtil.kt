package com.local.funnylearny.data

import com.local.funnylearny.base.AppUtil
import org.apache.commons.io.IOUtils

object FunnyLearnyDataUtil {

    internal fun getFunnyLearnyDataAsString() : String {
        val inputStream = AppUtil.getApplicationContext().assets.open("funnylearny.json") //get a jason file as inputstream
        return IOUtils.toString(inputStream, "UTF-8")  //convert to string
    }

}