package com.local.funnylearny.ui.matchpairs

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MatchPair(var question : String?, var answer : String?) : Parcelable
