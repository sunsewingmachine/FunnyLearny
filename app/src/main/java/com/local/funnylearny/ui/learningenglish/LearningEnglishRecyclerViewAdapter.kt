package com.local.funnylearny.ui.learningenglish

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.local.funnylearny.databinding.LearningEnglishListItemBinding
import com.local.funnylearny.ui.learningenglish.LearningEnglish as LearningEnglish

class LearningEnglishRecyclerViewAdapter(private val learningEnglish : ArrayList<LearningEnglish>) : RecyclerView.Adapter<LearningEnglishRecyclerViewAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LearningEnglishListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val learningEnglish = learningEnglish[position]
        holder.meaningTextView.text = learningEnglish.meaning
        holder.wordTextView.text = learningEnglish.word
    }

    override fun getItemCount(): Int = learningEnglish.size

  inner class ViewHolder (binding: LearningEnglishListItemBinding) : RecyclerView.ViewHolder(binding.root){
      var wordTextView : TextView = binding.wordTextView
       var meaningTextView : TextView = binding.meaningTextView

    }

}