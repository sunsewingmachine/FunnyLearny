package com.local.funnylearny.ui.base

import com.local.funnylearny.ui.main.MainActivity

object FragmentReferences {

    fun selectFragment(activity: MainActivity,type : Int) {

        when(type) {

            WORD_ARRANGEMENT -> activity.openWordArrangementFragment()

        }

    }


    const val WORD_ARRANGEMENT = 101

}