package com.local.funnylearny.ui.learningenglish

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.local.funnylearny.R
import com.local.funnylearny.ui.base.FragmentInteractionListener
import kotlinx.android.synthetic.main.fragment_learning_english.*
import java.lang.IllegalArgumentException

class LearningEnglishFragment : Fragment() {

    private var learningEnglishFragmentInteractionListener : LearningEnglishFragmentInteractionListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is LearningEnglishFragmentInteractionListener){
            learningEnglishFragmentInteractionListener = context
        } else {
            throw IllegalArgumentException("LearningEnglishFragmentInteractionListener is not implemented in respective activity")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_learning_english, container, false)
    }

    private val learningEnglish = ArrayList<LearningEnglish>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
        learningEnglish.add(LearningEnglish("How","yeppadi","something"))
        learningEnglish.add(LearningEnglish("is","", "something"))
        learningEnglish.add(LearningEnglish("your","ungal","something"))
        learningEnglish.add(LearningEnglish("name?","peyar?","something"))
        learningEnglish.add(LearningEnglish("daddy","thanthai","something"))
        learningEnglish.add(LearningEnglish("now?","ippodhu?","something"))
        adapterAttachment()
    }

    private fun adapterAttachment(){
        learningEnglishRecyclerView.layoutManager = LinearLayoutManager(context)
        learningEnglishRecyclerView.adapter = LearningEnglishRecyclerViewAdapter(learningEnglish)
    }

    private fun initToolbar(){
        learningEnglishToolBar.setNavigationOnClickListener {
            learningEnglishFragmentInteractionListener?.onNavigationIconClickedListener()
        }
    }

    interface LearningEnglishFragmentInteractionListener : FragmentInteractionListener

    companion object {
        @JvmStatic
        fun newInstance() = LearningEnglishFragment()
        const val TAG = "LearningEnglishFragment"
    }
}