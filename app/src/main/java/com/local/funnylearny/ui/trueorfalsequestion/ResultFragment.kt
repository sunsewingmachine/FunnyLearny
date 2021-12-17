package com.local.funnylearny.ui.trueorfalsequestion

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.local.funnylearny.R
import com.local.funnylearny.ui.base.FragmentInteractionListener
import kotlinx.android.synthetic.main.fragment_result.*
import java.lang.IllegalArgumentException

class ResultFragment : Fragment() {

    private var resultFragmentInteractionListener : ResultFragmentInteractionListener? = null
    private lateinit var trueOrFalseQuestionList : ArrayList<TrueOrFalseQuestion>
    private lateinit var answerList : ArrayList<Int>

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is ResultFragmentInteractionListener){
            resultFragmentInteractionListener = context
        } else {
            throw IllegalArgumentException("ResultFragmentInteractionListener is not implemented in respective activity")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_result, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolBar()
        arguments?. let {
            trueOrFalseQuestionList = it.getParcelableArrayList(TRUE_OR_FALSE_QUESTION_LIST)!!
            answerList = it.getIntegerArrayList(ANSWER_LIST)!!
        }
        adapterAttachment()
    }

    private fun adapterAttachment(){
        resultRecyclerView.layoutManager = LinearLayoutManager(context)
        resultRecyclerView.adapter = ResultRecyclerViewAdapter(trueOrFalseQuestionList,answerList)
    }

    private fun initToolBar(){
        resultToolBar.setNavigationOnClickListener {
            resultFragmentInteractionListener?.onNavigationIconClickedListener()
        }
    }

    interface ResultFragmentInteractionListener : FragmentInteractionListener

    companion object {

        @JvmStatic
        fun newInstance(trueOrFalseQuestionList : ArrayList<TrueOrFalseQuestion>, answerList : ArrayList<Int>) =
            ResultFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(TRUE_OR_FALSE_QUESTION_LIST , trueOrFalseQuestionList)
                    putIntegerArrayList(ANSWER_LIST , answerList)
                }
            }
        const val TAG = "ResultFragment"
        const val TRUE_OR_FALSE_QUESTION_LIST = "trueOrFalseQuestionList"
        const val ANSWER_LIST = "answerList"
    }
}