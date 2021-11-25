package com.local.funnylearny.ui.lesson

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.local.funnylearny.ui.base.FragmentInteractionListener
import com.local.funnylearny.R
import com.local.funnylearny.base.ViewState
import com.local.funnylearny.domain.model.lesson.Lesson
import com.local.funnylearny.ui.placeholder.PlaceholderContent
import kotlinx.android.synthetic.main.fragment_lesson_list.*
import java.lang.IllegalArgumentException

/**
 * A fragment representing a list of Items.
 */
class LessonListFragment : Fragment() {

    private var columnCount = 1
    private var lessonListFragmentInteractionListener : LessonListFragmentInteractionListener? = null
    private var lessonListRecyclerViewAdapter : LessonListRecyclerViewAdapter? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is LessonListFragmentInteractionListener) {
            lessonListFragmentInteractionListener = context
        } else {
            throw IllegalArgumentException("LessonListFragmentInteractionListener is not implemented in respective activity")
        }
     }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_lesson_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }

        val viewModel = ViewModelProvider(this).get(LessonListViewModel::class.java)
        viewModel.getViewStateLiveData().observe(viewLifecycleOwner, lessonListObserver)

        viewModel.getLessons()
        initToolbar()
    }

    private val lessonListObserver: Observer<ViewState> = Observer {

        when(it) {

            is ViewState.DataAvailable -> {

                lessonRecyclerView.layoutManager = when {
                        columnCount <= 1 -> LinearLayoutManager(context)
                        else -> GridLayoutManager(context, columnCount)
                    }
                lessonRecyclerView.adapter =  LessonListRecyclerViewAdapter(
                    (it as LessonListViewModel.LessonListViewStateLiveData).lessons,
                    onLessonListItemClickListener
                )


            }

            else -> {}
        }
    }

    private fun initToolbar() {
        lessonListToolbar.setNavigationOnClickListener {
            lessonListFragmentInteractionListener?.onNavigationIconClickedListener()
        }
    }

    private val onLessonListItemClickListener = object :
        LessonListRecyclerViewAdapter.OnLessonListItemClickListener {
        override fun onLessonListItemClicked(lesson: Lesson) {
            lessonListFragmentInteractionListener?.onLessonListItemClicked(lesson)
        }
    }

    interface LessonListFragmentInteractionListener : FragmentInteractionListener {
        fun onLessonListItemClicked(lesson: Lesson)
    }

    companion object {

        const val TAG = "LessonListFragment"
        const val ARG_COLUMN_COUNT = "column-count"

        @JvmStatic
        fun newInstance(columnCount: Int = 1) =
            LessonListFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}