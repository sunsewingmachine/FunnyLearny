package com.local.funnylearny.ui.trueorfalsequestion

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.local.funnylearny.databinding.TrueOrFalseQuestionListItemBinding

class CardStackViewAdapter(private var trueOrFalseQuestion : ArrayList<TrueOrFalseQuestion>) : RecyclerView.Adapter<CardStackViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(TrueOrFalseQuestionListItemBinding.inflate( LayoutInflater.from(parent.context),
            parent,
            false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val trueOrFalseQuestion = trueOrFalseQuestion[position]
        holder.trueOrFalseQuestionText.text = trueOrFalseQuestion.question
        holder.reasonText.text = trueOrFalseQuestion.reason


    }

    override fun getItemCount(): Int = trueOrFalseQuestion.size


   inner class ViewHolder(binding : TrueOrFalseQuestionListItemBinding) : RecyclerView.ViewHolder(binding.root) {
      var trueOrFalseQuestionText : TextView = binding.trueOrFalseQuestionText
       var reasonText : TextView = binding.reasonText
       var nextButton : MaterialButton  = binding.nextButton
       var trueButton : MaterialButton = binding.trueButton
       var falseButton : MaterialButton = binding.falseButton
    }
}