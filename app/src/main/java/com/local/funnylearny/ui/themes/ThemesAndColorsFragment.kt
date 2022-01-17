package com.local.funnylearny.ui.themes

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.local.funnylearny.R
import com.local.funnylearny.base.BaseApplication
import com.local.funnylearny.ui.base.FragmentInteractionListener
import kotlinx.android.synthetic.main.fragment_themes__colors.*


class ThemesAndColorsFragment : Fragment() {

    private var themesAndColorsFragmentInteractionListener : ThemesAndColorsFragmentInteractionListener? = null
    private var click : Int = 0

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is ThemesAndColorsFragmentInteractionListener){
            themesAndColorsFragmentInteractionListener = context
        } else {
            throw IllegalArgumentException("ThemesAndColorsFragment is not implemented in respective activity")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_themes__colors, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolBar()
        themesAndColorsFirstColor.setOnClickListener {
            click = 1
            BaseApplication.baseApplication.isReCreated = true
            requireActivity().recreate()
        }
        themesAndColorsSecondColor.setOnClickListener {
            click = 2
            BaseApplication.baseApplication.isReCreated = true
            requireActivity().recreate()
        }
        themesAndColorsThirdColor.setOnClickListener {
            click = 3
            BaseApplication.baseApplication.isReCreated = true
            requireActivity().recreate()
        }
        themesAndColorsFourthColor.setOnClickListener {
            click = 4
            BaseApplication.baseApplication.isReCreated = true
            requireActivity().recreate()
        }
        themesAndColorsFifthColor.setOnClickListener {
            click = 5
            BaseApplication.baseApplication.isReCreated = true
            requireActivity().recreate()
        }
    }

    private fun initToolBar() {
        themesAndColorsToolBar.setNavigationOnClickListener {
            themesAndColorsFragmentInteractionListener?.onNavigationIconClickedListener()
        }
    }

    interface ThemesAndColorsFragmentInteractionListener : FragmentInteractionListener {
    }

    companion object {

        @JvmStatic
        fun newInstance() = ThemesAndColorsFragment()
        const val TAG = "ThemesAndColorsFragment"

    }
}