package com.local.funnylearny.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.local.funnylearny.R
import com.local.funnylearny.ui.wordarrange.WordArrangementFragment
import com.local.funnylearny.ui.base.addFragment
import com.local.funnylearny.domain.model.lesson.Lesson
import com.local.funnylearny.ui.lesson.LessonListFragment
import com.local.funnylearny.domain.model.part.Part
import com.local.funnylearny.ui.base.FragmentReferences
import com.local.funnylearny.ui.sentencer.SentencerFragment
import com.local.funnylearny.ui.matchpairs.MatchPairsFragment
import com.local.funnylearny.ui.meaningcontest.MeaningContestFragment
import com.local.funnylearny.ui.part.PartListFragment
import com.local.funnylearny.ui.quiz.QuizFragment
import com.local.funnylearny.ui.trueorfalsequestion.TrueOrFalseQuestionFragment

class MainActivity : AppCompatActivity() ,
    LessonListFragment.LessonListFragmentInteractionListener,
    PartListFragment.PartListFragmentInteractionListener,
    QuizFragment.QuizFragmentInteractionListener,
    MatchPairsFragment.MatchPairsInteractionListener,
    TrueOrFalseQuestionFragment.TrueOrFalseQuestionFragmentInteractionListener,
    SentencerFragment.SentencerFragmentInteractionListener,
    MeaningContestFragment.MeaningContestFragmentInteractionListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if(savedInstanceState == null) {
            openLessonListFragment()
        }
    }

    private fun openLessonListFragment() {
        addFragment(R.id.fragment_container, LessonListFragment.newInstance(), LessonListFragment.TAG)
    }

    override fun onLessonListItemClicked(lesson: Lesson) {
        addFragment(R.id.fragment_container, PartListFragment.newInstance(lessonName = lesson.name), PartListFragment.TAG)
    }

    override fun onNavigationIconClickedListener() {
        onBackPressed()
    }

    override fun onPartListItemClicked(part: Part) {
       FragmentReferences.selectFragment(this,part.partType)
    }

    internal fun openWordArrangementFragment() {
        addFragment(
                R.id.fragment_container,
                WordArrangementFragment.newInstance(),
                WordArrangementFragment.TAG
        )
    }

    internal fun openQuizFragment(){
        addFragment(
            R.id.fragment_container,
            QuizFragment.newInstance(),
            QuizFragment.TAG
        )
    }

    internal fun openMatchPairsFragment(){
        addFragment(
            R.id.fragment_container,
            MatchPairsFragment.newInstance(),
            MatchPairsFragment.TAG
        )
    }

    internal fun openTrueOrFalseQuestionFragment() {
        addFragment(
            R.id.fragment_container,
            TrueOrFalseQuestionFragment.newInstance(),
            TrueOrFalseQuestionFragment.TAG
        )
    }

    internal fun openSentencerFragment(){
        addFragment(
            R.id.fragment_container,
            SentencerFragment.newInstance(),
            SentencerFragment.TAG
        )
    }

    internal fun openMeaningContestFragment(){
        addFragment(
            R.id.fragment_container,
            MeaningContestFragment.newInstance(),
            MeaningContestFragment.TAG
        )
    }


    override fun onBackPressed() {
        super.onBackPressed()

        if(supportFragmentManager.backStackEntryCount == 0) {
            finish()
        }

    }

}