package com.local.funnylearny.data.part

import com.local.funnylearny.domain.model.part.Part

interface PartLocalRepositoryContract {
    fun getPart(lesson : String) : List<Part>
}