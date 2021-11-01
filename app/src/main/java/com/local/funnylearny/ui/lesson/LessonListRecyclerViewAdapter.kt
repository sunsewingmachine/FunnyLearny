package com.local.funnylearny.ui.lesson

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.material.card.MaterialCardView
import com.local.funnylearny.databinding.LessonListItemBinding
import com.local.funnylearny.domain.model.lesson.Lesson
import com.local.funnylearny.ui.placeholder.PlaceholderContent.PlaceholderItem

/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
class LessonListRecyclerViewAdapter(
    private val values: List<PlaceholderItem>,
    private val lessons : List<Lesson>,
    private val onLessonListItemClickListener: OnLessonListItemClickListener
) : RecyclerView.Adapter<LessonListRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            LessonListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        val lesson = lessons[position]
        holder.lessonName.text = lesson.name
        holder.itemView.tag = lesson
    }

    override fun getItemCount(): Int = lessons.size

    inner class ViewHolder(binding: LessonListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val lessonName: TextView = binding.lessonName
        private val lessonListItemCardView : MaterialCardView = binding.lessonListItemCardView
        init {
            lessonListItemCardView.setOnClickListener(lessonListItemClickListener)
        }
    }

    val lessonListItemClickListener = View.OnClickListener {
        val lesson = it.tag as Lesson
        onLessonListItemClickListener.onLessonListItemClicked(lesson)
    }

    interface OnLessonListItemClickListener {
        fun onLessonListItemClicked(lesson : Lesson)
    }


}