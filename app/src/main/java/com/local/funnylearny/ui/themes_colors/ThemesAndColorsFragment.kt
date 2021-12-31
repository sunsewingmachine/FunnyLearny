package com.local.funnylearny.ui.themes_colors

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.local.funnylearny.R
import com.local.funnylearny.ui.base.FragmentInteractionListener
import kotlinx.android.synthetic.main.fragment_themes__colors.*


class ThemesAndColorsFragment : Fragment() {

    private var themesAndColorsFragmentInteractionListener : ThemesAndColorsFragmentInteractionListener? = null

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
            requireActivity().setTheme(R.style.ColorOne)
        }
        themesAndColorsSecondColor.setOnClickListener {
            requireActivity().setTheme(R.style.ColorTwo)
        }
        themesAndColorsThirdColor.setOnClickListener {
            requireActivity().setTheme(R.style.ColorThree)
        }
        themesAndColorsFourthColor.setOnClickListener {
            requireActivity().setTheme(R.style.ColorFour)
        }
        themesAndColorsFifthColor.setOnClickListener {
            requireActivity().setTheme(R.style.ColorFive)
        }
    }

    private fun initToolBar() {
        themesAndColorsToolBar.setNavigationOnClickListener {
            themesAndColorsFragmentInteractionListener?.onNavigationIconClickedListener()
        }
    }

    interface ThemesAndColorsFragmentInteractionListener : FragmentInteractionListener

    companion object {

        @JvmStatic
        fun newInstance() = ThemesAndColorsFragment()
        const val TAG = "ThemesAndColorsFragment"

    }
}