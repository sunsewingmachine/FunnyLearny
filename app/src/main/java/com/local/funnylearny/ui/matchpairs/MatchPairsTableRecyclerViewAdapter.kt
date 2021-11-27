package com.local.funnylearny.ui.matchpairs

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
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
            holder.answerTextView.text = ANSWER
            holder.answerTextView.setBackgroundColor(ContextCompat.getColor(context,R.color.colorBackground))
        } else {
            val matchPairsList = matchPairList[position-1]
            holder.questionTextView.text = matchPairsList.question
            holder.answerTextView.text = matchPairsList.answer
            holder.answerTextView.tag = position-1
            if(checkAnswerList[position-1] == 1 ) {
                holder.answerTextView.setBackgroundColor(ContextCompat.getColor(context,R.color.colorBackground))
            } else {
                holder.answerTextView.setBackgroundColor(ContextCompat.getColor(context,R.color.wrongAnswer))
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
        val answerTextView : TextView = binding.answerTextView
        init {
            answerTextView.setOnClickListener(matchPairAnswerClickListener)
        }
    }

    val matchPairAnswerClickListener = View.OnClickListener {
        it as TextView
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