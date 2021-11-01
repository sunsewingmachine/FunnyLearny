package com.local.funnylearny.ui.part

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.local.funnylearny.ui.base.FragmentInteractionListener
import com.local.funnylearny.R
import com.local.funnylearny.domain.model.part.Part
import com.local.funnylearny.ui.placeholder.PlaceholderContent
import kotlinx.android.synthetic.main.fragment_part_list.*
import java.lang.IllegalArgumentException

/**
 * A fragment representing a list of Items.
 */
class PartListFragment : Fragment() {

    private var columnCount = 1
    private var partListFragmentInteractionListener : PartListFragmentInteractionListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is PartListFragmentInteractionListener){
            partListFragmentInteractionListener = context
        } else {
            throw IllegalArgumentException("PartListFragmentInteractionListener is not implemented in respective activity")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
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

        val parts = ArrayList<Part>()


        // Set the adapter
        partsRecyclerView.apply {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter = PartListRecyclerViewAdapter(PlaceholderContent.ITEMS,parts,onPartListItemClickListener)
            }
        }

        initToolbar()

    }

    private fun initToolbar() {
        partListToolbar.setNavigationOnClickListener {
            partListFragmentInteractionListener?.onNavigationIconClickedListener()
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

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int = 1) =
            PartListFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}