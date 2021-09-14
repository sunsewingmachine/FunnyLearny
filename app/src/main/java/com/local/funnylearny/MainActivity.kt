package com.local.funnylearny

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addFragment(R.id.fragment_container,FunnyLearnyFragment.newInstance(),FunnyLearnyFragment.TAG)

    }
}