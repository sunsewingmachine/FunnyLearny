package com.local.funnylearny.ui.base

import com.local.funnylearny.ui.main.MainActivity

object FragmentReferences {

    fun selectFragment(activity: MainActivity,type : Int) {

        when(type) {

            WORD_ARRANGEMENT -> activity.openWordArrangementFragment()
            QUIZ -> activity.openQuizFragment()
            MATCH_PAIRS -> activity.openMatchPairsFragment()
            LEARNING_ENGLISH -> activity.openLearningEnglishFragment()
        }

    }


    private const val WORD_ARRANGEMENT = 101
    private const val QUIZ = 108
    private const val MATCH_PAIRS = 234
    private const val LEARNING_ENGLISH  = 204
}