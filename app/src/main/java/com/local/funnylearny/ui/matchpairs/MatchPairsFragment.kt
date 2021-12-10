package com.local.funnylearny.ui.matchpairs

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.local.funnylearny.R
import com.local.funnylearny.ui.base.FragmentInteractionListener
import com.local.funnylearny.ui.util.AnimationUtil
import com.local.funnylearny.ui.util.AnimationUtil.doMoveAnimation
import kotlinx.android.synthetic.main.fragment_match_pairs.*
import java.lang.IllegalArgumentException
import kotlin.collections.ArrayList

class MatchPairsFragment : Fragment() {

    var matchPairsInteractionListener : MatchPairsInteractionListener? = null
    var matchPairsTableRecyclerViewAdapter : MatchPairsTableRecyclerViewAdapter? = null
    var answerRecyclerViewAdapter : AnswerRecyclerViewAdapter? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is MatchPairsInteractionListener){
            matchPairsInteractionListener = context
        } else {
            throw IllegalArgumentException("MatchPairFragmentInteractionListener is not implemented in respective activity")
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_match_pairs, container, false)
    }

    private var matchPairList = ArrayList<MatchPair>()
    private var removeMatchPairList = ArrayList<MatchPair>()
    private var answers = ArrayList<String>()
    private var correctAnswers = ArrayList<String>()
    private var checkAnswers = ArrayList<Int>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()

        if (savedInstanceState == null) {

            matchPairList.add(MatchPair("Choose 'INDIAN BANK'", "INDIAN BANK"))
            matchPairList.add(MatchPair("Choose 'YES BANK'", "YES BANK"))
            matchPairList.add(MatchPair("Choose 'AXIS BANK'", "AXIS BANK"))
            matchPairList.add(MatchPair("Choose 'HDFC BANK'", "HDFC BANK"))
            matchPairList.add(MatchPair("Choose 'KVB BANK'", "KVB BANK"))
            matchPairList.add(MatchPair(null, "UCO BANK"))
            matchPairList.add(MatchPair(null, "IOB BANK"))

            prepareAnswerList()
            answers.shuffle()
            removeExtraAnswers()

            adapterAttachment()
            tableAdapterAttachment()
        } else {
            answers = savedInstanceState.getStringArrayList(ANSWERS)!!
            matchPairList = savedInstanceState.getParcelableArrayList<MatchPair>(MATCH_PAIRS) as ArrayList<MatchPair>
            matchPairListAnswerEntryCount = savedInstanceState.getInt(MATCH_PAIR_LIST_ANSWER_ENTRY_COUNT)
            checkAnswers = savedInstanceState.getIntegerArrayList(CHECK_ANSWERS) as ArrayList<Int>
            correctAnswers = savedInstanceState.getStringArrayList(CORRECT_ANSWERS)!!
            adapterAttachment()
            tableAdapterAttachment()
        }

        matchPairsSubmitButton.setOnClickListener {
            checkCorrectAnswer()
        }

    }

    private fun prepareAnswerList() {
        matchPairList.forEach {
            answers.add(it.answer!!)
            if(it.question != null) {
                checkAnswers.add(1)
                correctAnswers.add(it.answer!!)
            } else {
                removeMatchPairList.add(it)
            }
            it.answer = null
        }
    }

    private fun removeExtraAnswers() {
        removeMatchPairList.forEach {
            matchPairList.remove(it)
        }
    }

    private fun checkCorrectAnswer() {
        var areAllAnswersCorrect  = true
        matchPairList.forEachIndexed { index, matchPair ->
            if(correctAnswers[index] != matchPair.answer) {
                areAllAnswersCorrect = false
                checkAnswers[index] = 0
            }
        }

        matchPairsTableRecyclerViewAdapter?.swapCheckAnswersData(checkAnswers)

        if(areAllAnswersCorrect) {
            Toast.makeText(requireContext(),"All answers are correct",Toast.LENGTH_SHORT).show()
        } else {

            Toast.makeText(requireContext(),"All answers are not correct",Toast.LENGTH_SHORT).show()
        }

    }

    private fun adapterAttachment(){
        answersRecyclerView.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL, false)
        answerRecyclerViewAdapter = AnswerRecyclerViewAdapter(
            answers = answers,
            onAnswerClickListener = onAnswerClickListener,
            questionsCount = (answers.size - (matchPairList.size - matchPairListAnswerEntryCount))
        )
        answersRecyclerView.adapter = answerRecyclerViewAdapter
    }

    private fun tableAdapterAttachment(){
        matchPairsTableRecyclerView.layoutManager = LinearLayoutManager(context)
        matchPairsTableRecyclerViewAdapter = MatchPairsTableRecyclerViewAdapter(
            requireContext(),
            matchPairList,
            checkAnswers,
            onMatchPairAnswerClickListener
        )
        matchPairsTableRecyclerView.adapter = matchPairsTableRecyclerViewAdapter
    }

    private fun initToolbar(){
        matchPairsToolBar.setNavigationOnClickListener {
            matchPairsInteractionListener?.onNavigationIconClickedListener()
        }
    }

    var matchPairListAnswerEntryCount: Int = 0
    private val onAnswerClickListener = object : AnswerRecyclerViewAdapter.OnAnswerClickListener {
        override fun onAnswerClickedItem(view : View ,answer: String) {
            for (index in 0 until matchPairList.size) {
                val matchPair = matchPairList[index]
                if (matchPair.answer == null) {
                    matchPair.answer = answer
                    shuttleTextView.text = answer
                    var toView = matchPairsTableRecyclerView.layoutManager?.findViewByPosition(index+1)!!
                    toView = (toView as ConstraintLayout).findViewById<TextView>(R.id.answerContainerView)
                    doMoveAnimation(view,toView,matchPairRootView,shuttleView,object :
                        AnimationUtil.OnAnimationEndListener {
                        override fun onAnimationEnd() {
                            matchPairsTableRecyclerViewAdapter?.changeToNormalView(index + 1)
                        }
                    },false)
                    break
                }
            }
            if (matchPairList.size - 1 != matchPairListAnswerEntryCount) {
                matchPairListAnswerEntryCount++
            }
        }
    }

    private val onMatchPairAnswerClickListener = object : MatchPairsTableRecyclerViewAdapter.OnMatchPairAnswerClickListener{
        override fun onMatchPairAnswerClicked(view: View,answer: String) {
            matchPairListAnswerEntryCount--
            val toView = (answersRecyclerView.layoutManager as LinearLayoutManager).let {
                val position = it.findFirstCompletelyVisibleItemPosition()
                it.findViewByPosition(position)
            }
            shuttleTextView.text = answer
            doMoveAnimation(view,toView!!,matchPairRootView,shuttleView,object :
                AnimationUtil.OnAnimationEndListener {
                override fun onAnimationEnd() {
                    answerRecyclerViewAdapter?.swapData(answer)
                }
            },true)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putStringArrayList(ANSWERS,answers)
        outState.putParcelableArrayList(MATCH_PAIRS,matchPairList)
        outState.putInt(MATCH_PAIR_LIST_ANSWER_ENTRY_COUNT,matchPairListAnswerEntryCount)
        outState.putIntegerArrayList(CHECK_ANSWERS,checkAnswers)
        outState.putStringArrayList(CORRECT_ANSWERS,correctAnswers)

    }

    interface MatchPairsInteractionListener : FragmentInteractionListener{
       // fun onMatchPairsClickeditem(matchPairs: MatchPairs)
    }

    companion object {

        fun newInstance() = MatchPairsFragment()
        const val TAG = "MatchPairsFragment"
        const val ANSWERS = "answers"
        const val MATCH_PAIRS = "matchPairs"
        const val MATCH_PAIR_LIST_ANSWER_ENTRY_COUNT = "matchPairListAnswerEntryCount"
        const val CHECK_ANSWERS = "checkAnswers"
        const val CORRECT_ANSWERS = "correctAnswers"
    }
}