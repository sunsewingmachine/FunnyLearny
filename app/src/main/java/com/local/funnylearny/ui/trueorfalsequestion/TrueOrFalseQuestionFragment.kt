package com.local.funnylearny.ui.trueorfalsequestion

import android.content.Context
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import com.local.funnylearny.R
import com.local.funnylearny.ui.base.FragmentInteractionListener
import com.yuyakaido.android.cardstackview.*
import kotlinx.android.synthetic.main.fragment_true_or_false_question.*
import java.lang.IllegalArgumentException

class TrueOrFalseQuestionFragment : Fragment() {

    private var trueOrFalseQuestionFragmentInteractionListener: TrueOrFalseQuestionFragmentInteractionListener? =
        null
    private lateinit var cardStackLayoutManager: CardStackLayoutManager

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is TrueOrFalseQuestionFragmentInteractionListener) {
            trueOrFalseQuestionFragmentInteractionListener = context
        } else {
            throw IllegalArgumentException("TrueOrFalseQuestionFragmentInteractionListener is not implemented in respective activity")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_true_or_false_question, container, false)
    }

    private var trueOrFalseQuestionList = ArrayList<TrueOrFalseQuestion>()
    private var answerList = ArrayList<Int>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolBar()
        trueOrFalseQuestionList.add(
            TrueOrFalseQuestion(
                "100",
                "First ODI (Cricket) in India was played in Ahemadabad",
                true,
                "Ahmedabad had tha honour of hosting the first One Day International match played in India on November 25, 1981. India, captained by Sunil Gavaskar"
            )
        )
        trueOrFalseQuestionList.add(
            TrueOrFalseQuestion(
                "101",
                " The first captain of the first ODI Indian team was Sunil Gavaskar",
                false,
                "The first captain of the first ODI Indian team was Ajit Wadekar"
            )
        )
        trueOrFalseQuestionList.add(
            TrueOrFalseQuestion(
                "102",
                "The Ramayana was written by Tulsidas",
                false,
                "The Ramayana was written by Valmiki"
            )
        )
        trueOrFalseQuestionList.add(
            TrueOrFalseQuestion(
                "103",
                "Shakespeare wrote 37 plays",
                true,
                "Between about 1590 and 1613, Shakespeare wrote at least 37 plays"
            )
        )
        trueOrFalseQuestionList.add(
            TrueOrFalseQuestion(
                "104",
                "Sound waves travel faster than light",
                false,
                "Light travels faster than sound waves"
            )
        )
        adapterAttachment()

        nextButton.setOnClickListener {
            val setting = SwipeAnimationSetting.Builder()
                .setDirection(Direction.Right)
                .setDuration(Duration.Normal.duration)
                .setInterpolator(AccelerateInterpolator())
                .build()
            cardStackLayoutManager.setSwipeAnimationSetting(setting)
            trueOrFalseQuestionCardStackView.swipe()
            nextButton.visibility = View.GONE
        }
    }

    private fun adapterAttachment() {
        cardStackLayoutManager = CardStackLayoutManager(context)
        trueOrFalseQuestionCardStackView.layoutManager = cardStackLayoutManager
        cardStackLayoutManager.setStackFrom(StackFrom.Top)
        cardStackLayoutManager.setVisibleCount(4)
        cardStackLayoutManager.setSwipeableMethod(SwipeableMethod.Automatic)
        trueOrFalseQuestionCardStackView.adapter = TrueOrFalseQuestionCardStackViewAdapter(trueOrFalseQuestionList,object : TrueOrFalseQuestionCardStackViewAdapter.OnTrueOrFalseQuestionAdapterListener {
            override fun onCorrectAnswerClicked(position: Int) {
                val setting = SwipeAnimationSetting.Builder()
                    .setDirection(Direction.Left)
                    .setDuration(Duration.Slow.duration)
                    .setInterpolator(AccelerateInterpolator())
                    .build()
                cardStackLayoutManager.setSwipeAnimationSetting(setting)
                trueOrFalseQuestionCardStackView.swipe()
                answerList.add(1)
                if(position == trueOrFalseQuestionList.size-1) {
                    popBackStackAndOpenResultFragment()
                }

            }
            override fun onWrongAnswerClicked(position : Int) {
                if(position != trueOrFalseQuestionList.size-1) {
                    nextButton.visibility = View.VISIBLE
                } else {
                    popBackStackAndOpenResultFragment()
                }
                answerList.add(0)
            }
        })
    }

    private fun popBackStackAndOpenResultFragment() {

        requireActivity().supportFragmentManager.popBackStack()
        trueOrFalseQuestionFragmentInteractionListener?.onOpenResultFragment(
            trueOrFalseQuestionList,
            answerList
        )

    }

    private fun initToolBar() {
        trueOrFalseQuestionToolBar.setNavigationOnClickListener {
            trueOrFalseQuestionFragmentInteractionListener?.onNavigationIconClickedListener()
        }
    }

    interface TrueOrFalseQuestionFragmentInteractionListener : FragmentInteractionListener{
        fun onOpenResultFragment(trueOrFalseQuestionList : ArrayList<TrueOrFalseQuestion> , answerList : ArrayList<Int>)
    }

    companion object {
        @JvmStatic
        fun newInstance() = TrueOrFalseQuestionFragment()
        const val TAG = "TrueOrFalseQuestionFragment"
    }
}