package com.local.funnylearny.ui.trueorfalsequestion

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.local.funnylearny.databinding.ResultListItemBinding

class ResultRecyclerViewAdapter(var trueOrFalseQuestion: ArrayList<TrueOrFalseQuestion>,
                    var answerList : ArrayList<Int>) : RecyclerView.Adapter<ResultRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ResultRecyclerViewAdapter.ViewHolder {
        return ViewHolder(
            ResultListItemBinding.inflate(
                LayoutInflater.from(parent.context),
            parent,
            false
        )
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val trueOrFalseQuestion = trueOrFalseQuestion[position]
        val answer = answerList[position]
        holder.resultQuestionText.text = trueOrFalseQuestion.question
        if(answer == 0){
           holder.selectedAnswerText.text = "Selected Answer :" + " " + true
        } else {
            holder.selectedAnswerText.text = "Selected Answer :" + " " + false
        }

    }

    override fun getItemCount(): Int = trueOrFalseQuestion.size

     class ViewHolder(binding: ResultListItemBinding) : RecyclerView.ViewHolder(binding.root) {
         var resultQuestionText : TextView = binding.resultQuestionText
         var selectedAnswerText : TextView = binding.selectedAnswerText
         var resultReasonLayout : LinearLayout = binding.resultReasonLayout
         var resultReasonText : TextView = binding.resultReasonText
         var resultCorrectAnswer : TextView = binding.resultCorrectAnswer
    }
}