package com.local.funnylearny.ui.quiz

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.google.android.material.card.MaterialCardView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.local.funnylearny.R
import com.local.funnylearny.ui.base.FragmentInteractionListener
import kotlinx.android.synthetic.main.fragment_quiz.*
import java.lang.IllegalArgumentException
import android.content.DialogInterface

import android.content.DialogInterface.OnShowListener

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
            option1TextView.text = option1
            option2TextView.text = option2
            option3TextView.text = option3
            option4TextView.text = option4
        }
        option1CardView.setOnClickListener {
            changePropertyOfClickedOption(previousSelectedOption,1,quizObject)
            showAlertDialog(1,quizObject)
        }
        option2CardView.setOnClickListener {
            changePropertyOfClickedOption(previousSelectedOption,2,quizObject)
            showAlertDialog(2,quizObject)
        }
        option3CardView.setOnClickListener {
            changePropertyOfClickedOption(previousSelectedOption,3,quizObject)
            showAlertDialog(3,quizObject)
        }
        option4CardView.setOnClickListener {
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
                    R.color.green
                )
            )
        } else {
            currentOptionCardView.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.red
                )
            )
        }
    }

    private fun changeOfPreviousOptionClicked(previousOptionCardView : MaterialCardView) {
        previousOptionCardView.setBackgroundColor(Color.TRANSPARENT)
    }

    private fun getQuizOption(currentSelectedOption: Int,quiz: Quiz) : String {
        return when(currentSelectedOption) {
            1 -> quiz.option1
            2 -> quiz.option2
            3 -> quiz.option3
            else -> quiz.option4
        }
    }

    private fun getOptionReason(currentSelectedOption: Int,quiz: Quiz) : String {
        return when(currentSelectedOption) {
            1 -> quiz.reason1
            2 -> quiz.reason2
            3 -> quiz.reason3
            else -> quiz.reason4
        }
    }

    private fun getOptionCardView(option : Int) : MaterialCardView {
        return when(option) {
            1 -> {
                option1CardView
            }
            2 -> {
                option2CardView
            }
            3 -> {
                option3CardView
            }
            else -> {
                option4CardView
            }
        }
    }

    interface QuizFragmentInteractionListener : FragmentInteractionListener

    companion object {

        fun newInstance() = QuizFragment()
    }
}