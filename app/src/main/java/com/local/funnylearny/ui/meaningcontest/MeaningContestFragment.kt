package com.local.funnylearny.ui.meaningcontest


import android.animation.Animator
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.google.android.material.card.MaterialCardView
import com.local.funnylearny.R
import com.local.funnylearny.ui.base.FragmentInteractionListener
import kotlinx.android.synthetic.main.fragment_meaning_contest.*
import java.lang.IllegalArgumentException
import android.os.CountDownTimer
import kotlin.collections.ArrayList
import kotlin.random.Random
import android.animation.ObjectAnimator
import android.os.Handler
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet


class MeaningContestFragment : Fragment() {

    private var meaningContestFragmentInteractionListener: MeaningContestFragmentInteractionListener? =
        null
    private var meaningContestList = ArrayList<MeaningContest>()
    private var count = 0
    private var isAnswerSelected = false

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MeaningContestFragmentInteractionListener) {
            meaningContestFragmentInteractionListener = context
        } else {
            throw IllegalArgumentException("MeaningContestFragmentInteractionListener is not implemented in respective activity")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_meaning_contest, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()

        meaningContestList.add(
            MeaningContest(
                "Simple tense examples of 'study'",
                "Convert 'Present' to 'Past' Tense", "Choose the correct one.",
                "Studying", "Studied", "Will Study", "Studies", "Studied"
            )
        )
        meaningContestList.add(
            MeaningContest(
                "Simple tense examples of 'play'", "Convert 'Present' to 'Past' Tense",
                "Choose the correct one.", "Playing", "Play", "Played", "Will Play", "Played"
            )
        )
        meaningContestList.add(
            MeaningContest(
                "Simple tense examples of 'study'",
                "Convert 'Present' to 'Past' Tense", "Choose the correct one.",
                "Studying", "Studied", "Will Study", "Studies", "Studied"
            )
        )


        bindDataOnViews(count)
        meaningContestOptionOneCardView.setOnClickListener {
            if (!isAnswerSelected)
                checkAnswer(1, count, meaningContestList)
        }

        meaningContestOptionTwoCardView.setOnClickListener {
            if (!isAnswerSelected)
                checkAnswer(2, count, meaningContestList)
        }

        meaningContestOptionThreeCardView.setOnClickListener {
            if (!isAnswerSelected)
                checkAnswer(3, count, meaningContestList)
        }

        meaningContestOptionFourCardView.setOnClickListener {
            if (!isAnswerSelected)
                checkAnswer(4, count, meaningContestList)
        }

        prepareOpponentAndStart()

    }

    private fun prepareOpponentAndStart() {
        val list = listOf(
            meaningContestList[count].optionOne,
            meaningContestList[count].optionTwo,
            meaningContestList[count].optionThree,
            meaningContestList[count].optionFour
        )
        val randomAnswer = Random.nextInt(4)
        val randomElement = list[randomAnswer]
        val randomMilliSec = Random.nextInt(9)
        progressBar(randomElement, randomMilliSec)
    }


    private fun progressBar(randomElement: String, randomMilliSec: Int) {
        val mCountDownTimer = object : CountDownTimer(10000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                meaningContestProgressBar.progress += 10
                if (randomMilliSec * 10 == meaningContestProgressBar.progress) {
                    meaningContestRandomText.visibility = View.VISIBLE
                    if (meaningContestList[count].answer == randomElement) {
                        meaningContestRandomText.text = "Player 2 Chosen Correct Answer."
                    } else {
                        meaningContestRandomText.text = "Player 2 Chosen Wrong Answer."
                    }
                }
            }

            override fun onFinish() {
                meaningContestProgressBar.progress = 100
                startNextQuestion()
            }
        }
        mCountDownTimer.start()
    }

    private fun startNextQuestion() {
        if (count != meaningContestList.size - 1) {
            doFadeInAndOutAnimation(questionLayout,questionLayout)
            bindDataOnViews(++count)
        } else {
            resultLayout.visibility = View.VISIBLE
            doFadeInAndOutAnimation(questionLayout,resultLayout)
        }
    }

    private fun bindDataOnViews(count: Int) {
        meaningContestProgressBar.progress = 0
        meaningContestQuestionText.text = meaningContestList[count].question
        meaningContestCommandText.text = meaningContestList[count].command
        meaningContestRuleText.text = meaningContestList[count].rule
        meaningContestOptionOneText.text = meaningContestList[count].optionOne
        meaningContestOptionTwoText.text = meaningContestList[count].optionTwo
        meaningContestOptionThreeText.text = meaningContestList[count].optionThree
        meaningContestOptionFourText.text = meaningContestList[count].optionFour
        setTransparentBackground(meaningContestOptionOneCardView)
        setTransparentBackground(meaningContestOptionTwoCardView)
        setTransparentBackground(meaningContestOptionThreeCardView)
        setTransparentBackground(meaningContestOptionFourCardView)
        meaningContestRandomText.visibility = View.GONE
        isAnswerSelected = false
        if (count != 0) prepareOpponentAndStart()
    }

    private fun doFadeInAndOutAnimation(fadeOutLayout : View,fadeInLayout : View) {
        val fadeOut = ObjectAnimator.ofFloat(fadeOutLayout, "alpha", 1f, .0f)
        fadeOut.duration = 500

        val fadeIn = ObjectAnimator.ofFloat(fadeInLayout, "alpha", .0f, 1f)
        fadeIn.duration = 500

        val mAnimationSet = AnimatorSet()

        mAnimationSet.play(fadeIn).after(fadeOut)

        mAnimationSet.start()
    }

    private fun setTransparentBackground(cardView: MaterialCardView) {
        cardView.setBackgroundColor(
            ContextCompat.getColor(
                requireContext(),
                android.R.color.transparent
            )
        )
    }

    private fun checkAnswer(
        currentPosition: Int,
        count: Int,
        meaningContestList: ArrayList<MeaningContest>
    ) {
        isAnswerSelected = true
        if (meaningContestList[count].answer == getOption(
                currentPosition,
                meaningContestList,
                count
            )
        ) {
            getOptionCardView(currentPosition).setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.correctAnswer
                )
            )
        } else {
            getOptionCardView(currentPosition).setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.wrongAnswer
                )
            )
        }
    }

    private fun getOption(
        currentPosition: Int,
        meaningContestList: ArrayList<MeaningContest>,
        count: Int
    ): String {
        return when (currentPosition) {
            1 -> meaningContestList[count].optionOne
            2 -> meaningContestList[count].optionTwo
            3 -> meaningContestList[count].optionThree
            else -> meaningContestList[count].optionFour
        }
    }

    private fun getOptionCardView(currentPosition: Int): MaterialCardView {
        return when (currentPosition) {
            1 -> {
                meaningContestOptionOneCardView
            }
            2 -> {
                meaningContestOptionTwoCardView
            }
            3 -> {
                meaningContestOptionThreeCardView
            }
            else -> {
                meaningContestOptionFourCardView
            }
        }
    }

    private fun initToolbar() {
        meaningContestToolBar.setNavigationOnClickListener {
            meaningContestFragmentInteractionListener?.onNavigationIconClickedListener()
        }
    }

    interface MeaningContestFragmentInteractionListener : FragmentInteractionListener

    companion object {

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() = MeaningContestFragment()
        const val TAG = "MeaningContestFragment"
    }
}