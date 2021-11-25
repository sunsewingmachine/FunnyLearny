package com.local.funnylearny.ui.quiz

import android.app.AlertDialog
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
import kotlinx.android.synthetic.main.fragment_quiz.*
import java.lang.IllegalArgumentException

class QuizFragment : Fragment() {

    private var quizFragmentInteractionListener : QuizFragmentInteractionListener? = null
    private var previousSelectedOption = 0

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is QuizFragmentInteractionListener){
            quizFragmentInteractionListener = context
        } else {
            throw IllegalArgumentException("PartListFragmentInteractionListener is not implemented in respective activity")
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quiz, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()

        val quizObject = Quiz("Simple tense examples of 'study'",
            "Convert 'Present' to 'Past' Tense","Choose the correct one.",
            "Studying","Studied","Will Study","Studies",
            "You have chosen Present participle of Study",
            "You have chosen correct answer. 'Studied' is a Past tense of Study",
            "You have chosen Future tense of Study",
             "You have chosen Future tense of Study","Studied")

        quizObject.apply {
            questionTextView.text = question
            commandTextView.text = command
            ruleTextView.text = rule
            optionOneTextView.text = optionOne
            optionTwoTextView.text = optionTwo
            optionThreeTextView.text = optionThree
            optionFourTextView.text = optionFour
        }
        optionOneCardView.setOnClickListener {
            changePropertyOfClickedOption(previousSelectedOption,1,quizObject)
            showAlertDialog(1,quizObject)
        }
        optionTwoCardView.setOnClickListener {
            changePropertyOfClickedOption(previousSelectedOption,2,quizObject)
            showAlertDialog(2,quizObject)
        }
        optionThreeCardView.setOnClickListener {
            changePropertyOfClickedOption(previousSelectedOption,3,quizObject)
            showAlertDialog(3,quizObject)
        }
        optionFourCardView.setOnClickListener {
            changePropertyOfClickedOption(previousSelectedOption,4,quizObject)
            showAlertDialog(4,quizObject)
        }
    }

    private fun initToolbar(){
        quizToolBar.setOnClickListener {
            quizFragmentInteractionListener?.onNavigationIconClickedListener()
        }
    }

    private fun showAlertDialog(currentSelectedOption: Int,quiz: Quiz){
        val dialogBuilder = AlertDialog.Builder(requireContext(),R.style.DialogSlideAnim)
            .setTitle("Reason")
            .setMessage(getOptionReason(currentSelectedOption,quiz))
            .setPositiveButton("Okay") { dialog, _ ->
                dialog.dismiss()
            }

        val dialog = dialogBuilder.create()
        dialog.setOnShowListener {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(requireContext(),R.color.alertDialogButtonColor))
        }
        dialog.show()
    }

    private fun changePropertyOfClickedOption(previousSelectedOption : Int, currentSelectedOption : Int, quiz : Quiz) {

        if(previousSelectedOption == 0) {
            changeOfCurrentOptionClicked(currentSelectedOption,getOptionCardView(currentSelectedOption),quiz)
        } else {
            changeOfPreviousOptionClicked(getOptionCardView(previousSelectedOption))
            changeOfCurrentOptionClicked(currentSelectedOption,getOptionCardView(currentSelectedOption),quiz)
        }
        this.previousSelectedOption = currentSelectedOption

    }

    private fun changeOfCurrentOptionClicked(currentSelectedOption: Int, currentOptionCardView : MaterialCardView, quiz: Quiz) {
        if (getQuizOption(currentSelectedOption, quiz) == quiz.answer) {
            currentOptionCardView.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.correctAnswer
                )
            )
        } else {
            currentOptionCardView.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.wrongAnswer
                )
            )
        }
    }

    private fun changeOfPreviousOptionClicked(previousOptionCardView : MaterialCardView) {
        previousOptionCardView.setBackgroundColor(Color.TRANSPARENT)
    }

    private fun getQuizOption(currentSelectedOption: Int,quiz: Quiz) : String {
        return when(currentSelectedOption) {
            1 -> quiz.optionOne
            2 -> quiz.optionTwo
            3 -> quiz.optionThree
            else -> quiz.optionFour
        }
    }

    private fun getOptionReason(currentSelectedOption: Int,quiz: Quiz) : String {
        return when(currentSelectedOption) {
            1 -> quiz.reasonOne
            2 -> quiz.reasonTwo
            3 -> quiz.reasonThree
            else -> quiz.reasonFour
        }
    }

    private fun getOptionCardView(option : Int) : MaterialCardView {
        return when(option) {
            1 -> {
                optionOneCardView
            }
            2 -> {
                optionTwoCardView
            }
            3 -> {
                optionThreeCardView
            }
            else -> {
                optionFourCardView
            }
        }
    }

    interface QuizFragmentInteractionListener : FragmentInteractionListener

    companion object {
        const val TAG = "QuizFragment"
        fun newInstance() = QuizFragment()
    }
}