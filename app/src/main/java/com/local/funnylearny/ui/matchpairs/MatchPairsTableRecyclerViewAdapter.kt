package com.local.funnylearny.ui.matchpairs

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.local.funnylearny.databinding.MatchpairsTableListItemBinding
import java.util.ArrayList

class MatchPairsTableRecyclerViewAdapter(
    private val matchPairsList : ArrayList<MatchPair>,
    private val onMatchPairAnswerClickListener : OnMatchPairAnswerClickListener
) : RecyclerView.Adapter<MatchPairsTableRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            MatchpairsTableListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if (position == 0){
            holder.questionTextView.text = QUESTION
            holder.answerTextView.text = ANSWER
        } else {
            val matchPairsList = matchPairsList[position-1]
            holder.questionTextView.text = matchPairsList.question
            holder.answerTextView.text = matchPairsList.answer
            holder.answerTextView.tag = position-1
        }

    }

    override fun getItemCount(): Int = matchPairsList.size+1

    inner class ViewHolder (binding: MatchpairsTableListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        val questionTextView : TextView = binding.questionTextView
        val answerTextView : TextView = binding.answerTextView
        init {
            answerTextView.setOnClickListener(matchPairAnswerClickListener)
        }
    }

    val matchPairAnswerClickListener = View.OnClickListener {
        it as TextView
        if( it.text != ANSWER) {
            val matchPair = matchPairsList[it.tag as Int]
            if (matchPair.answer != null) {
                val answer = matchPair.answer
                matchPair.answer = null
                notifyDataSetChanged()
                onMatchPairAnswerClickListener.onMatchPairAnswerClicked(answer!!)
            }
        }
    }

    interface OnMatchPairAnswerClickListener{
        fun onMatchPairAnswerClicked(answer : String)
    }

    companion object {
        const val QUESTION = "Question"
        const val ANSWER = "Answer"
    }

}