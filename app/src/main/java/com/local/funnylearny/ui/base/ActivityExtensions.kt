package com.local.funnylearny.ui.base

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.local.funnylearny.R

fun FragmentActivity.addFragment(container: Int, fragment: Fragment, tag: String? = null) {
    supportFragmentManager.beginTransaction().apply {
        setCustomAnimations(R.animator.slide_up, 0, 0, R.animator.slide_down)
        add(container, fragment, tag).addToBackStack(tag).commit()
    }
}