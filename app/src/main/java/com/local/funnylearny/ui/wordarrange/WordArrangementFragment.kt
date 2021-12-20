package com.local.funnylearny.ui.wordarrange

import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.google.android.flexbox.FlexboxLayout
import com.google.android.material.button.MaterialButton
import kotlinx.android.synthetic.main.fragment_funny_learny.*
import android.content.Context
import com.local.funnylearny.R
import com.local.funnylearny.ui.util.AnimationUtil

class WordArrangementFragment : Fragment() {

    private var wordList = ArrayList<String>()
    private var clickedAnswerList = ArrayList<String>()
    private var isBoolean : Boolean = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_funny_learny, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val inputString = "I am,from,a,village,in,tamilnadu,india,asia"
        val words = inputString.split(",")
        words.forEach { it ->
            wordList.add(it)
        }

        setSentenceTextView(words)
        generateWordButtons(words)

        checkNowButton.setOnClickListener {
            checkAnswer(clickedAnswerList,wordList)

        }

    }

    private fun setSentenceTextView(words : List<String>)  {
        var sentence = ""
        for(word in words) {
            sentence = sentence.plus(word).plus(" ")
        }
       sentenceTextView.text = sentence
    }


    private fun generateWordButtons(words : List<String>) {
            words.shuffled()
            words.forEachIndexed { _, word ->
                addWordButton(requireContext(),word)
            }
    }

    private fun addWordButton(context : Context, word : String) {

        val wordButton = getButton(context,word)

        wordButton.setOnClickListener {
            val isFromRandomContainer = it.tag as Boolean
            if(isFromRandomContainer) {
                val toView = if(arrangeWordsContainer.childCount == 0) {
                    arrangeWordsContainer
                } else {
                    arrangeWordsContainer.getChildAt(arrangeWordsContainer.childCount-1)
                }
                shuttleButton.text = word
                AnimationUtil.wordArrangmentMoveAnimation(
                    it,
                    toView,
                    rootLayout,
                    shuttleButton,
                    object : AnimationUtil.OnAnimationEndListener {
                    override fun onAnimationEnd() {
                        randomWordsContainer.removeView(it)
                        arrangeWordsContainer.addView(it)
                        clickedAnswerList.add(word)
                        it.tag = false
                    }
                },false,context)
            } else {

                val toView = if(randomWordsContainer.childCount == 0) {
                    randomWordsContainer
                } else {
                    randomWordsContainer.getChildAt(randomWordsContainer.childCount-1)
                }
                shuttleButton.text = word
                AnimationUtil.wordArrangmentMoveAnimation(
                    it,
                    toView,
                    rootLayout,shuttleButton, object : AnimationUtil.OnAnimationEndListener {
                    override fun onAnimationEnd() {
                        arrangeWordsContainer.removeView(it)
                        randomWordsContainer.addView(it)
                        it.tag = true
                    }
                },true,context)
            }
        }
        randomWordsContainer.addView(wordButton)
    }

    private fun getButton(context: Context,word : String) : MaterialButton {
        val wordButton = MaterialButton(context)
        wordButton.text = word
        wordButton.setBackgroundColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.wordButtonColor
            )
        )
        wordButton.setTextColor(Color.WHITE)
        wordButton.cornerRadius = context.resources.getDimension(R.dimen.dimen_4dp).toInt()
        val lp = FlexboxLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        lp.setMargins(16, 16, 16,16)
        wordButton.layoutParams = lp
        wordButton.tag = true
        return wordButton
    }

    private fun checkAnswer(clickedAnswerList : ArrayList<String>, wordList: ArrayList<String>){
        for(index in  0 until  wordList.size-1){
            if(clickedAnswerList[index] != wordList[index]){
                isBoolean = false
                break
            }
        }
        if(isBoolean){
            val message = "Correct"
            showAlertDialog(message)
        } else {
            val message = "Wrong"
            showAlertDialog(message)
        }
    }

    private fun showAlertDialog(message : String){
        val dialogBuilder = AlertDialog.Builder(requireContext(),R.style.DialogSlideAnim)
            .setTitle("Reason")
            .setMessage(message)
            .setPositiveButton("Okay") { dialog, _ ->
                dialog.dismiss()
            }

        val dialog = dialogBuilder.create()
        dialog.setOnShowListener {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(requireContext(),R.color.alertDialogButtonColor))
        }
        dialog.show()
    }

    companion object {

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         */
        const val TAG = "WordArrangementFragment"
        @JvmStatic
        fun newInstance() = WordArrangementFragment()
    }
}