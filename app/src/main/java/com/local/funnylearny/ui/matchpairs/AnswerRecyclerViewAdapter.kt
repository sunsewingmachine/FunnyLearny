package com.local.funnylearny.ui.matchpairs

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.local.funnylearny.databinding.AnswerListItemBinding
import com.local.funnylearny.ui.matchpairs.AnswerRecyclerViewAdapter.*
import java.util.ArrayList


class AnswerRecyclerViewAdapter(
    val answers : ArrayList<String>,
    private val onAnswerClickListener: OnAnswerClickListener,
    private var questionsCount : Int
) : RecyclerView.Adapter<ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            AnswerListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val matchPairs = answers[position]
        holder.answerTextView.text = matchPairs
        holder.itemView.tag = Pair(matchPairs,position)
    }

    override fun getItemCount(): Int = answers.size

    inner class ViewHolder(binding: AnswerListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val answerTextView : TextView = binding.answerTextView
        private val answerCardView : MaterialCardView = binding.answerCardView

        init {
            answerCardView.setOnClickListener(answerClickListener)
        }
    }

/*
* */
@SuppressLint("NotifyDataSetChanged")
val answerClickListener = View.OnClickListener {
        if(questionsCount != answers.size) {
            val answer = it.tag as Pair<*, *>
            answers.remove(answer.first)
            onAnswerClickListener.onAnswerClickedItem(it,answer.first as String)
            notifyDataSetChanged()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun swapData(answer: String){
        answers.add(0,answer)
        notifyDataSetChanged()
    }

    interface OnAnswerClickListener {
       fun  onAnswerClickedItem(view: View,answer : String)
    }
}