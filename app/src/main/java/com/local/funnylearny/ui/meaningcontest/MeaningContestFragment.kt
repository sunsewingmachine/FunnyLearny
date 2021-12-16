package com.local.funnylearny.ui.meaningcontest

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.local.funnylearny.R
import com.local.funnylearny.ui.base.FragmentInteractionListener
import kotlinx.android.synthetic.main.fragment_meaning_contest.*
import java.lang.IllegalArgumentException

class MeaningContestFragment : Fragment() {

    private var meaningContestFragmentInteractionListener : MeaningContestFragmentInteractionListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is MeaningContestFragmentInteractionListener){
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
    }

    private fun initToolbar(){
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