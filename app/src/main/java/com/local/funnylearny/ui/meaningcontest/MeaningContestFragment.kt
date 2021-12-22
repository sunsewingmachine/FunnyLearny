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

class MeaningContestFragment : Fragment() {

    private var meaningContestFragmentInteractionListener: MeaningContestFragmentInteractionListener? =
        null
    private var meaningContestList = ArrayList<MeaningContest>()
    private var count = 0

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
                "Studying", "Studied", "Will Study", "Studies","Studied"
            )
        )
        meaningContestList.add(
            MeaningContest(
                "Simple tense examples of 'play'", "Convert 'Present' to 'Past' Tense",
                "Choose the correct one.", "Playing", "Play", "Played", "Will Play","Played"
            )
        )
        meaningContestList.add(
            MeaningContest(
                "Simple tense examples of 'study'",
                "Convert 'Present' to 'Past' Tense", "Choose the correct one.",
                "Studying", "Studied", "Will Study", "Studies","Studied"
            )
        )

        bindDataOnViews(count)
        meaningContestOptionOneCardView.setOnClickListener {
            checkAnswer(1,count,meaningContestList)
           /* if(count != meaningContestList.size-1) {
                //bindDataOnViews()
            }*/
        }

        meaningContestOptionTwoCardView.setOnClickListener {
            checkAnswer(2,count,meaningContestList)
            /*if(count != meaningContestList.size-1) {
                //bindDataOnViews(++count)
            }*/
        }

        meaningContestOptionThreeCardView.setOnClickListener {
            checkAnswer(3,count,meaningContestList)
            /*if(count != meaningContestList.size-1) {
                //bindDataOnViews(++count)
            }*/
        }

        meaningContestOptionFourCardView.setOnClickListener {
            checkAnswer(4,count,meaningContestList)
            /*if(count != meaningContestList.size-1) {
               // bindDataOnViews(++count)
            }*/
        }
    }

    private fun bindDataOnViews(count : Int) {
        meaningContestQuestionText.text = meaningContestList[count].question
        meaningContestCommandText.text = meaningContestList[count].command
        meaningContestRuleText.text = meaningContestList[count].rule
        meaningContestOptionOneText.text = meaningContestList[count].optionOne
        meaningContestOptionTwoText.text = meaningContestList[count].optionTwo
        meaningContestOptionThreeText.text = meaningContestList[count].optionThree
        meaningContestOptionFourText.text = meaningContestList[count].optionFour
    }

    private fun checkAnswer(currentPosition: Int,count : Int, meaningContestList: ArrayList<MeaningContest>){
        if(meaningContestList[count].answer == getOption(currentPosition,meaningContestList,count)){
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

    private fun getOption(currentPosition: Int,meaningContestList: ArrayList<MeaningContest>,count: Int) : String {
        return when(currentPosition) {
            1 -> meaningContestList[count].optionOne
            2 -> meaningContestList[count].optionTwo
            3 -> meaningContestList[count].optionThree
            else -> meaningContestList[count].optionFour
        }
    }

    private fun getOptionCardView(currentPosition : Int) : MaterialCardView {
        return when(currentPosition) {
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