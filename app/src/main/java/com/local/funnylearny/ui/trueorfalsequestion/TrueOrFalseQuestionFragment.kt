package com.local.funnylearny.ui.trueorfalsequestion

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.local.funnylearny.R
import com.local.funnylearny.ui.base.FragmentInteractionListener
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import kotlinx.android.synthetic.main.fragment_true_or_false_question.*
import java.lang.IllegalArgumentException

class TrueOrFalseQuestionFragment : Fragment() {

    private var trueOrFalseQuestionFragmentInteractionListener : TrueOrFalseQuestionFragmentInteractionListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is TrueOrFalseQuestionFragmentInteractionListener){
            trueOrFalseQuestionFragmentInteractionListener = context
        } else {
            throw IllegalArgumentException("TrueOrFalseQuestionFragmentInteractionListener is not implemented in respective activity")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_true_or_false_question, container, false)
    }

    private var trueOrFalseQuestionList = ArrayList<TrueOrFalseQuestion>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolBar()
        trueOrFalseQuestionList.add(TrueOrFalseQuestion(
            "100","First ODI (Cricket) in India was played in Ahemadabad","True","Ahmedabad had tha honour of hosting the first One Day International match played in India on November 25, 1981. India, captained by Sunil Gavaskar"
        ))
        trueOrFalseQuestionList.add(TrueOrFalseQuestion("101"," The first captain of the first ODI Indian team was Sunil Gavaskar","False","The first captain of the first ODI Indian team was Ajit Wadekar"))
        trueOrFalseQuestionList.add(TrueOrFalseQuestion("102","The Ramayana was written by Tulsidas","False","The Ramayana was written by Valmiki"))
        trueOrFalseQuestionList.add(TrueOrFalseQuestion("103","Shakespeare wrote 37 plays","True","Between about 1590 and 1613, Shakespeare wrote at least 37 plays"))
        trueOrFalseQuestionList.add(TrueOrFalseQuestion("104","Sound waves travel faster than light","False","Light travels faster than sound waves"))
        adapterAttachment()
    }

    private fun adapterAttachment(){
        trueOrFalseQuestionCardStackView.layoutManager = CardStackLayoutManager()
        trueOrFalseQuestionCardStackView.adapter = CardStackViewAdapter(trueOrFalseQuestion = trueOrFalseQuestion)
    }

    private fun initToolBar(){
        trueOrFalseQuestionToolBar.setNavigationOnClickListener {
            trueOrFalseQuestionFragmentInteractionListener?.onNavigationIconClickedListener()
        }
    }

    interface TrueOrFalseQuestionFragmentInteractionListener : FragmentInteractionListener

    companion object {
        @JvmStatic
        fun newInstance() = TrueOrFalseQuestionFragment()
        const val TAG = "TrueOrFalseQuestionFragment"
    }
}