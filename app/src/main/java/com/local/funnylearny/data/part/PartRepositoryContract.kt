package com.local.funnylearny.data.part

import com.local.funnylearny.domain.model.part.Part

interface PartRepositoryContract {

    fun getPart(Lesson : String) : List<Part>

    interface PartRepositoryCallback{
        fun onDataFetchSuccess()
        fun onErrorOccurred()
    }
}