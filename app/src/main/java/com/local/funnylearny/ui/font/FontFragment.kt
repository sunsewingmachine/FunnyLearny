package com.local.funnylearny.ui.font

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.local.funnylearny.R
import com.local.funnylearny.ui.base.FragmentInteractionListener

class FontFragment : Fragment() {

    private var fontFragmentInteractionListener : FontFragmentInteractionListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is FontFragmentInteractionListener){
            fontFragmentInteractionListener = context
        } else {
            throw IllegalArgumentException("FontFragment is not implemented in respective activity")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_font, container, false)
    }

    interface FontFragmentInteractionListener : FragmentInteractionListener

    companion object {

        @JvmStatic
        fun newInstance() = FontFragment()
        const val TAG = "FontFragment"
    }
}