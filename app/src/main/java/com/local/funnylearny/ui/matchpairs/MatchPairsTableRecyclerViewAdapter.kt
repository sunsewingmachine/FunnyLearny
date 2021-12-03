package com.local.funnylearny.ui.matchpairs

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.local.funnylearny.R
import com.local.funnylearny.databinding.MatchpairsTableListItemBinding
import java.util.ArrayList

@SuppressLint("NotifyDataSetChanged")
class MatchPairsTableRecyclerViewAdapter(
    val context : Context,
    private val matchPairList : ArrayList<MatchPair>,
    private var checkAnswerList : ArrayList<Int>,
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
            holder.answerButton.text = ANSWER
            (holder.answerButton as MaterialButton).strokeWidth = 0
            holder.answerButton.setBackgroundColor(ContextCompat.getColor(context,R.color.colorBackground))
        } else {
            val matchPair = matchPairList[position-1]
            holder.questionTextView.text = matchPair.question
            holder.answerButton.text = matchPair.answer

            holder.answerButton.tag = position-1
            if(checkAnswerList[position-1] == 1) {
                holder.answerButton.setBackgroundColor(ContextCompat.getColor(context,R.color.colorBackground))
            } else {
                holder.answerButton.setBackgroundColor(ContextCompat.getColor(context,R.color.wrongAnswer))
            }

            if(matchPair.answer == null) {
                (holder.answerButton as MaterialButton).strokeWidth = 0
                holder.answerButton.setBackgroundColor(ContextCompat.getColor(context,R.color.colorBackground))
            } else {
                (holder.answerButton as MaterialButton).strokeWidth = context.resources.getDimension(R.dimen.stroke_width).toInt()
            }
        }
    }

    internal fun swapCheckAnswersData(checkAnswerList: ArrayList<Int>) {
        this.checkAnswerList = checkAnswerList
        notifyDataSetChanged()
    }

    internal fun changeToNormalView(position: Int) {
        checkAnswerList[position-1] = 1
        notifyItemChanged(position)
    }

    override fun getItemCount(): Int = matchPairList.size+1

    inner class ViewHolder (binding: MatchpairsTableListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        val questionTextView : TextView = binding.questionTextView
        val answerButton : TextView = binding.answerButton
        init {
            answerButton.setOnClickListener(matchPairAnswerClickListener)
        }
    }

    val matchPairAnswerClickListener = View.OnClickListener {
        it as MaterialButton
        if( it.text != ANSWER) {
            val matchPair = matchPairList[it.tag as Int]
            if (matchPair.answer != null) {
                val answer = matchPair.answer
                matchPair.answer = null
                notifyItemChanged((it.tag as Int)+1)
                onMatchPairAnswerClickListener.onMatchPairAnswerClicked(it,answer!!)
            }
        }
    }

    interface OnMatchPairAnswerClickListener{
        fun onMatchPairAnswerClicked(view: View, answer : String)
    }

    companion object {
        const val QUESTION = "Question"
        const val ANSWER = "Answer"
    }

}