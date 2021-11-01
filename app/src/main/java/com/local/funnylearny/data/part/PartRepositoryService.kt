package com.local.funnylearny.data.part

import com.local.funnylearny.domain.model.part.Part

object PartRepositoryService : PartLocalRepositoryContract, PartRepositoryContract {

    var partLocalRepositoryContract : PartLocalRepositoryContract = PartLocalRepositoryService

   override fun getPart(lesson : String) : List<Part>{
       return partLocalRepositoryContract.getPart(lesson)
   }
}