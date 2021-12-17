package com.local.funnylearny.ui.trueorfalsequestion

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TrueOrFalseQuestion(var id : String , var question : String, var answer : Boolean , var reason : String) : Parcelable
