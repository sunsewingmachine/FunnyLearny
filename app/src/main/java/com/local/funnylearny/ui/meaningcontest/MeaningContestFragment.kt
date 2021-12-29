package com.local.funnylearny.ui.meaningcontest


import android.content.Context
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
import android.animation.AnimatorSet
import android.app.AlertDialog
import android.os.Handler
import android.text.Spannable
import android.util.Log
import com.local.funnylearny.ui.quiz.Quiz
import kotlinx.android.synthetic.main.fragment_quiz.*

import android.widget.TextView

import android.text.style.ForegroundColorSpan

import android.text.style.RelativeSizeSpan

import android.text.SpannableString
import kotlinx.android.synthetic.main.result_list_item.*


class MeaningContestFragment : Fragment() {

    private var meaningContestFragmentInteractionListener: MeaningContestFragmentInteractionListener? = null
    private var meaningContestList = ArrayList<MeaningContest>()
    private var count = 0
    private var player1Score = 0
    private var player2Score = 0
    private var isAnswerSelected = false
    private var mCountDownTimer : CountDownTimer? = null

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
        showAlertDialog()

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
        playerOneScore.text = context?.getString(R.string.initial_score,"0")
        playerTwoScore.text = context?.getString(R.string.initial_score,"0")

        meaningContestOptionOneCardView.setOnClickListener {
            if(!isAnswerSelected)
                checkAnswer(1, count ,meaningContestList)
        }

        meaningContestOptionTwoCardView.setOnClickListener {
            if(!isAnswerSelected)
            checkAnswer(2, count, meaningContestList)
        }

        meaningContestOptionThreeCardView.setOnClickListener {
            if(!isAnswerSelected)
            checkAnswer(3, count, meaningContestList)
        }

        meaningContestOptionFourCardView.setOnClickListener {
            if(!isAnswerSelected)
            checkAnswer(4, count, meaningContestList)
        }
        timerTextView.text = getSpannedSec("10s")
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
        val randomMilliSec = Random.nextInt(2,9)
        startCountDownTimerWork(randomElement, randomMilliSec)
    }

    private fun getSpannedSec(string : String) : Spannable {
        val ss1 = SpannableString(string)
        ss1.setSpan(null, 0, 1, 0) // set color
        return ss1
    }


    private fun startCountDownTimerWork(randomElement: String, randomMilliSec: Int) {
        mCountDownTimer = object : CountDownTimer(10000, 1000) {
            var countDown = 10
            override fun onTick(millisUntilFinished: Long) {
                meaningContestProgressBar.progress += 10
                timerTextView.text = getSpannedSec((countDown - 1).toString().plus("s"))
                countDown--
                val pb = meaningContestProgressBar.progress
                val rms = randomMilliSec * 10
                Log.i("startCountDownTimerWork","$pb $rms")
                if (rms == pb) {
                    if (meaningContestList[count].answer == randomElement) {
                        player2Score += if(randomMilliSec < 5) {
                            10
                        } else {
                            5
                        }
                        playerTwoScore.text = context?.getString(R.string.initial_score,player2Score.toString())
                        meaningContestPlayerTwoRandomText.text = context?.getString(R.string.player_two_correct_answer)
                    } else {
                        meaningContestPlayerTwoRandomText.text = context?.getString(R.string.player_two_wrong_answer)
                    }
                }
            }

            override fun onFinish() {
                meaningContestProgressBar.progress = 100
                countDown = 10
                startNextQuestion()
                timerTextView.text = getSpannedSec("10s")
                meaningContestPlayerTwoRandomText.text = context?.getString(R.string.waiting)
                meaningContestPlayerOneRandomText.text = context?.getString(R.string.waiting)
            }
        }
    }

    private fun showAlertDialog(){
        val dialogBuilder = AlertDialog.Builder(requireContext(),R.style.DialogSlideAnim)
            .setMessage("Let's Start")
            .setCancelable(false)
            .setPositiveButton("START") { _, _ ->
               mCountDownTimer?.start()
            }

        val dialog = dialogBuilder.create()
        dialog.setOnShowListener {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(requireContext(),R.color.alertDialogButtonColor))
        }
        dialog.show()
    }

    private fun startNextQuestion() {
        if (count != meaningContestList.size - 1) {
            doFadeInAndOutAnimation(questionLayout,questionLayout)
            Handler().postDelayed({
                mCountDownTimer?.start()
            },500)
            bindDataOnViews(++count)
        } else {
            resultLayout.visibility = View.VISIBLE

            if(player1Score > player2Score) {
                resultImageView.setImageDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.win))
            } else {
                resultImageView.setImageDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.lose))
            }
            doFadeInAndOutAnimation(questionLayout,resultLayout)
            questionLayout.visibility = View.GONE
            meaningContestPlayerTwoRandomText.visibility = View.GONE
            meaningContestPlayerOneRandomText.visibility = View.GONE
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
        isAnswerSelected = false
        if (count != 0) {
            prepareOpponentAndStart()
        }
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

    private fun checkAnswer(currentPosition: Int, count: Int, meaningContestList: ArrayList<MeaningContest>) {
        isAnswerSelected = true
        if (meaningContestList[count].answer == getOption(currentPosition, meaningContestList, count)) {
            player1Score += if((meaningContestProgressBar.progress/10) < 5) {
                10
            } else {
                5
            }
            playerOneScore.text = context?.getString(R.string.initial_score,player1Score.toString())
            meaningContestPlayerOneRandomText.text = context?.getString(R.string.player_one_correct_answer)
            getOptionCardView(currentPosition).setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.correctAnswer
                )
            )
        } else {
            meaningContestPlayerOneRandomText.text = context?.getString(R.string.player_one_wrong_answer)
            getOptionCardView(currentPosition).setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.wrongAnswer))
            val cardView = getCorrectAnswerCardView(meaningContestList[count])
            cardView.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.correctAnswer
                )
            )
        }
    }

    private fun getCorrectAnswerCardView(meaningContest: MeaningContest) : MaterialCardView {
        return when(meaningContest.answer) {
            meaningContest.optionOne -> meaningContestOptionOneCardView
            meaningContest.optionTwo -> meaningContestOptionTwoCardView
            meaningContest.optionThree -> meaningContestOptionThreeCardView
            else -> meaningContestOptionFourCardView
        }
    }

    private fun getOption(currentPosition: Int, meaningContestList: ArrayList<MeaningContest>, count: Int): String {
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

    override fun onDestroy() {
        super.onDestroy()
        mCountDownTimer?.cancel()
    }

    interface MeaningContestFragmentInteractionListener : FragmentInteractionListener

    companion object {

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() = MeaningContestFragment()
        const val TAG = "MeaningContestFragment"
    }
}