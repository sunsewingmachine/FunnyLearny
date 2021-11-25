package com.local.funnylearny.ui.base

import com.local.funnylearny.ui.main.MainActivity

object FragmentReferences {

    fun selectFragment(activity: MainActivity,type : Int) {

        when(type) {

            WORD_ARRANGEMENT -> activity.openWordArrangementFragment()
            QUIZ -> activity.openQuizFragment()
            MATCHPAIRS -> activity.openMatchPairsFragment()
        }

    }


    const val WORD_ARRANGEMENT = 101
    const val QUIZ = 108
    const val MATCHPAIRS = 234
}