package com.local.funnylearny.ui.trueorfalsequestion

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.local.funnylearny.databinding.TrueOrFalseQuestionListItemBinding

class CardStackViewAdapter(
    var trueOrFalseQuestion: ArrayList<TrueOrFalseQuestion>,
    private val onTrueOrFalseQuestionAdapterListener: OnTrueOrFalseQuestionAdapterListener
) : RecyclerView.Adapter<CardStackViewAdapter.ViewHolder>() {

    private var isRefreshing = false

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            TrueOrFalseQuestionListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val trueOrFalseQuestion = trueOrFalseQuestion[position]
        holder.apply {
            trueOrFalseQuestionText.text = trueOrFalseQuestion.question
            reasonText.text = trueOrFalseQuestion.reason
            if (isRefreshing) {
                //Change into container visibility handling
                trueButton.visibility = View.GONE
                falseButton.visibility = View.GONE
                reasonLayout.visibility = View.VISIBLE
                reasonText.text = trueOrFalseQuestion.reason
                isRefreshing = false
            } else {
                trueButton.visibility = View.VISIBLE
                falseButton.visibility = View.VISIBLE
                reasonLayout.visibility = View.GONE
            }
        }
    }

    override fun getItemCount(): Int = trueOrFalseQuestion.size


    inner class ViewHolder(binding: TrueOrFalseQuestionListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var trueOrFalseQuestionText: TextView = binding.trueOrFalseQuestionText
        var reasonText: TextView = binding.reasonText
        var trueButton: MaterialButton = binding.trueButton
        var falseButton: MaterialButton = binding.falseButton
        var reasonLayout : LinearLayout = binding.reasonLayout

        init {
            trueButton.setOnClickListener {
                onTrueButtonClicked(absoluteAdapterPosition)
            }
            falseButton.setOnClickListener {
                onFalseButtonClicked(absoluteAdapterPosition)
            }
        }
    }

    private fun onTrueButtonClicked(position: Int) {
        if (trueOrFalseQuestion[position].answer) {
            onTrueOrFalseQuestionAdapterListener.onCorrectAnswerClicked()
        } else {
            isRefreshing = true
            notifyItemChanged(position)
            onTrueOrFalseQuestionAdapterListener.onWrongAnswerClicked(position)
        }
    }

    private fun onFalseButtonClicked(position: Int) {
        if (!trueOrFalseQuestion[position].answer) {
            onTrueOrFalseQuestionAdapterListener.onCorrectAnswerClicked()
        } else {
            isRefreshing = true
            notifyItemChanged(position)
            onTrueOrFalseQuestionAdapterListener.onWrongAnswerClicked(position)
        }
    }

    interface OnTrueOrFalseQuestionAdapterListener {
        fun onCorrectAnswerClicked()
        fun onWrongAnswerClicked(position: Int)
    }

}