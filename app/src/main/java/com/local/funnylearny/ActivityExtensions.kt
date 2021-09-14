package com.local.funnylearny

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

fun FragmentActivity.addFragment(container: Int, fragment: Fragment, tag: String? = null) {
        supportFragmentManager.beginTransaction().add(container, fragment, tag).addToBackStack(tag).commit()
}