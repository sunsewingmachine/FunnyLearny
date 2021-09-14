package com.local.funnylearny

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.google.android.flexbox.FlexboxLayout
import com.google.android.material.button.MaterialButton
import kotlinx.android.synthetic.main.fragment_funny_learny.*
import java.util.*

/**
 * A simple [Fragment] subclass.
 * Use the [FunnyLearnyFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FunnyLearnyFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_funny_learny, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val inputString = "I am,from,a,village,in,tamilnadu,india,asia"
        val words = inputString.split(",")
        Collections.shuffle(words)

        for(word in words) {
            val button = MaterialButton(requireContext())
            button.text = word
            button.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.buttonColor))
            button.setTextColor(Color.WHITE)
            button.cornerRadius = 32
            val lp = FlexboxLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            lp.setMargins(20,20,20,20)
            button.layoutParams = lp

            button.setOnClickListener {
                arrangedTextView.text = arrangedTextView.text.toString().plus(button.text).plus("   ")
            }

            flexBoxLayout.addView(button)
        }

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         */
        const val TAG = "FunnyLearnyFragment"
        @JvmStatic
        fun newInstance() = FunnyLearnyFragment()
    }
}