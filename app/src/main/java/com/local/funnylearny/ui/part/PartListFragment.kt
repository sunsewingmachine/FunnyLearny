package com.local.funnylearny.ui.part

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
import com.local.funnylearny.domain.model.part.Part
import com.local.funnylearny.ui.lesson.LessonListViewModel
import com.local.funnylearny.ui.placeholder.PlaceholderContent
import kotlinx.android.synthetic.main.fragment_part_list.*
import java.lang.IllegalArgumentException

/**
 * A fragment representing a list of Items.
 */
class PartListFragment : Fragment() {

    private var columnCount = 1
    private var partListFragmentInteractionListener : PartListFragmentInteractionListener? = null
    private var lessonName : String? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is PartListFragmentInteractionListener){
            partListFragmentInteractionListener = context
        } else {
            throw IllegalArgumentException("PartListFragmentInteractionListener is not implemented in respective activity")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_part_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
            lessonName = it.getString(LESSON_NAME)
        }

        val viewModel = ViewModelProvider(this).get(PartListViewModel::class.java)
        viewModel.getViewStateLiveData().observe(viewLifecycleOwner, partListObserver )

        viewModel.getParts(lessonName!!)
        initToolbar()
    }

    private fun initToolbar() {
        partListToolbar.setNavigationOnClickListener {
            partListFragmentInteractionListener?.onNavigationIconClickedListener()
        }
    }

    private val partListObserver: Observer<ViewState> = Observer {

        when(it) {
            is ViewState.DataAvailable -> {
                partsRecyclerView.layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                partsRecyclerView.adapter =  PartListRecyclerViewAdapter(
                        PlaceholderContent.ITEMS,
                        (it as PartListViewModel.PartListViewStateLiveData).parts,
                        onPartListItemClickListener
                )
            }
            else -> {}
        }
    }

    private val onPartListItemClickListener = object :
        PartListRecyclerViewAdapter.OnPartListItemClickListener {
        override fun onPartListItemClicked(part: Part) {
            partListFragmentInteractionListener?.onPartListItemClicked(part)
        }
    }

    interface PartListFragmentInteractionListener : FragmentInteractionListener {
        fun onPartListItemClicked(part: Part)
    }

    companion object {

        const val TAG = "PartListFragment"

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"
        const val LESSON_NAME = "lessonName"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int = 1,lessonName : String) =
            PartListFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                    putString(LESSON_NAME,lessonName)
                }
            }
    }
}