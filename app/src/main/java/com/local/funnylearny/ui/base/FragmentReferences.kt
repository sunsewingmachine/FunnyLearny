package com.local.funnylearny.ui.base

import com.local.funnylearny.ui.main.MainActivity

object FragmentReferences {

    fun selectFragment(activity: MainActivity,type : Int) {

        when(type) {

            WORD_ARRANGEMENT -> activity.openWordArrangementFragment()
            QUIZ -> activity.openQuizFragment()
            MATCH_PAIRS -> activity.openMatchPairsFragment()
            TRUE_OR_FALSE_QUESTION -> activity.openTrueOrFalseQuestionFragment()
            SENTENCER -> activity.openSentencerFragment()
            MEANING_CONTEST -> activity.openMeaningContestFragment()
            THEMES_AND_COLORS -> activity.openThemesAndColorsFragment()
        }

    }


    private const val WORD_ARRANGEMENT = 101
    private const val QUIZ = 108
    private const val MATCH_PAIRS = 234
    private const val TRUE_OR_FALSE_QUESTION = 104
    private const val SENTENCER  = 204
    private const val MEANING_CONTEST = 208
    private const val THEMES_AND_COLORS = 301
}