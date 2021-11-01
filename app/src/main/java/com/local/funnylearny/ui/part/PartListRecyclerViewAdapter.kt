package com.local.funnylearny.ui.part

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.material.card.MaterialCardView

import com.local.funnylearny.ui.placeholder.PlaceholderContent.PlaceholderItem
import com.local.funnylearny.databinding.PartListItemBinding
import com.local.funnylearny.domain.model.lesson.Lesson
import com.local.funnylearny.domain.model.part.Part

/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
class PartListRecyclerViewAdapter(
        private val values: List<PlaceholderItem>,
        private val parts: List<Part>,
        private val onPartListItemClickListener: OnPartListItemClickListener
) : RecyclerView.Adapter<PartListRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            PartListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        val part = parts[position]
        holder.partName.text = part.partName
        holder.itemView.tag = part
    }

    override fun getItemCount(): Int = parts.size

    inner class ViewHolder(binding: PartListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val partName: TextView = binding.partName
        private val partListItemCardView : MaterialCardView = binding.partListItemCardView
        init {
            partListItemCardView.setOnClickListener(partListItemClickListener)
        }
    }

    val partListItemClickListener = View.OnClickListener {
        val part = it.tag as Part
        onPartListItemClickListener.onPartListItemClicked(part)
    }

    interface OnPartListItemClickListener {
        fun onPartListItemClicked(part : Part)
    }

}