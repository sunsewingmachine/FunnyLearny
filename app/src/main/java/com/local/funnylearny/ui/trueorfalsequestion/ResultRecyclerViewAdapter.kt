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


    private var value : Boolean = false
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val trueOrFalseQuestion = trueOrFalseQuestion[position]
        val answer = answerList[position]
        holder.resultQuestionText.text = trueOrFalseQuestion.question
        if(answer == 0) {
            if (trueOrFalseQuestion.answer) {
                value = false
                holder.selectedAnswerText.text = "Selected Answer : $value"
            }
        } else {
            value = true
            holder.selectedAnswerText.text = "Selected Answer : $value"
        }

        holder.resultCorrectAnswer.text = "Correct Answer :" + " " +trueOrFalseQuestion.answer.toString()
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