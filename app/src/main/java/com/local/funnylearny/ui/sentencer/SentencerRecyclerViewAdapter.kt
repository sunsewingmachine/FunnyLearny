package com.local.funnylearny.ui.sentencer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.local.funnylearny.databinding.SentencerListItemBinding

class SentencerRecyclerViewAdapter(
    private val sentencer: ArrayList<Sentencer>,
    private var onSpeakerImageClickListener: OnSpeakerImageClickListener
) : RecyclerView.Adapter<SentencerRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            SentencerListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val sentencer = sentencer[position]
        holder.meaningTextView.text = sentencer.meaning
        holder.wordTextView.text = sentencer.word
        holder.speakerImage.tag = sentencer
    }

    override fun getItemCount(): Int = sentencer.size

    inner class ViewHolder(binding: SentencerListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var wordTextView : TextView = binding.wordText
        var meaningTextView : TextView = binding.meaningText
        var speakerImage : ImageButton = binding.speakerImage

        init {
            speakerImage.setOnClickListener(speakerImageClickListener)
        }
    }

    val speakerImageClickListener = View.OnClickListener {
        val sentencer = it.tag as Sentencer
        onSpeakerImageClickListener.onSpeakerImageClicked(sentencer)
    }
    interface OnSpeakerImageClickListener {
        fun onSpeakerImageClicked(sentencer : Sentencer)
    }

}