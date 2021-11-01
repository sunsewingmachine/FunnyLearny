package com.local.funnylearny.ui.wordarrange

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
import java.util.*
import android.content.Context
import androidx.transition.TransitionManager
import com.local.funnylearny.R


/**
 * A simple [Fragment] subclass.
 * Use the [WordArrangementFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class WordArrangementFragment : Fragment() {

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

        //initRandomRecyclerView(words)

        setSentenceTextView(words)
        generateWordButtons(words)

    }

    /*private fun initRandomRecyclerView(words: List<String>) {
        randomRecyclerView.layoutManager = StaggeredGridLayoutManager(3,LinearLayoutManager.VERTICAL)
        randomRecyclerView.adapter = WordsRecyclerViewAdapter(requireContext(),words)
    }*/


    private fun initArrangedRecyclerView(words: List<String>) {

    }

    private fun setSentenceTextView(words : List<String>)  {
        var sentence = ""
        for(word in words) {
            sentence = sentence.plus(word).plus(" ")
        }
       sentenceTextView.text = sentence
    }


    private fun generateWordButtons(words : List<String>) {
            Collections.shuffle(words)
            words.forEachIndexed { index, word ->
                addWordButton(requireContext(),word,index)
            }
    }

    private fun addWordButton(context : Context, word : String, index : Int) {
        val wordButton = MaterialButton(context)
        wordButton.text = word
        wordButton.setBackgroundColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.wordButtonColor
            )
        )
        wordButton.setTextColor(Color.WHITE)
        wordButton.cornerRadius = 32
        val lp = FlexboxLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        lp.setMargins(20, 20, 20, 20)
        wordButton.layoutParams = lp
        wordButton.tag = true

        wordButton.setOnClickListener {
            val isFromRandomContainer = it.tag as Boolean
            if(isFromRandomContainer) {
                TransitionManager.beginDelayedTransition(randomWordsContainer)
                randomWordsContainer.removeView(it)
                TransitionManager.beginDelayedTransition(arrangeWordsContainer)
                arrangeWordsContainer.addView(it)
                it.tag = false
            } else {
                TransitionManager.beginDelayedTransition(randomWordsContainer)
                arrangeWordsContainer.removeView(it)
                TransitionManager.beginDelayedTransition(arrangeWordsContainer)
                randomWordsContainer.addView(it)
                it.tag = true
            }
        }
        randomWordsContainer.addView(wordButton)
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