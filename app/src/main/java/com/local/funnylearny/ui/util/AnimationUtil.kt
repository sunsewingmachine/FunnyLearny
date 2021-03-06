package com.local.funnylearny.ui.util

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Rect
import android.view.View
import android.view.animation.DecelerateInterpolator
import com.google.android.material.button.MaterialButton
import com.local.funnylearny.R
import android.animation.AnimatorListenerAdapter




object AnimationUtil {

    fun doMoveAnimation(
        fromView: View,
        toView: View,
        rootView: View,
        shuttleView: View,
        onAnimationEndListener: OnAnimationEndListener,
        isBackAnimation: Boolean
    ) {
        val fromRect = Rect()
        val toRect = Rect()
        fromView.getGlobalVisibleRect(fromRect)
        toView.getGlobalVisibleRect(toRect)
        val animatorSet: AnimatorSet = getViewToViewScalingAnimator(
            rootView,
            shuttleView,
            fromRect,
            toRect,
            isBackAnimation
        )
        animatorSet.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {
                shuttleView.visibility = View.VISIBLE
                fromView.visibility = View.INVISIBLE
            }

            override fun onAnimationEnd(animation: Animator) {
                shuttleView.visibility = View.GONE
                fromView.visibility = View.VISIBLE
                onAnimationEndListener.onAnimationEnd()
            }

            override fun onAnimationCancel(animation: Animator) {}
            override fun onAnimationRepeat(animation: Animator) {}
        })
        animatorSet.start()
    }

    private fun getViewToViewScalingAnimator(
        parentView: View,
        viewToAnimate: View,
        fromViewRect: Rect,
        toViewRect: Rect,
        isBackAnimation: Boolean
    ): AnimatorSet {

        // get all coordinates at once
        val parentViewRect = Rect()
        val viewToAnimateRect = Rect()
        parentView.getGlobalVisibleRect(parentViewRect)
        viewToAnimate.getGlobalVisibleRect(viewToAnimateRect)
        viewToAnimate.scaleX = 1f
        viewToAnimate.scaleY = 1f

        val toViewTotalWidth = toViewRect.right - toViewRect.left
        val fromViewTotalWidth = fromViewRect.right - fromViewRect.left
        var toStartPosition =
            ((toViewTotalWidth - fromViewTotalWidth) / 2 + toViewRect.left).toFloat()

        toStartPosition = if (!isBackAnimation) {
            toStartPosition
        } else {
            (toViewRect.left - parentViewRect.left).toFloat()
        }

        // moving of the object on X-axis
        val translateAnimatorX: ObjectAnimator = ObjectAnimator.ofFloat(
            viewToAnimate,
            "X",
            (fromViewRect.left - parentViewRect.left).toFloat(),
            toStartPosition
        )

        // moving of the object on Y-axis
        val translateAnimatorY: ObjectAnimator = ObjectAnimator.ofFloat(
            viewToAnimate,
            "Y",
            (fromViewRect.top - parentViewRect.top).toFloat(),
            (toViewRect.top - parentViewRect.top).toFloat()
        )
        val animatorSet = AnimatorSet()
        animatorSet.interpolator = DecelerateInterpolator(1f)
        animatorSet.duration = 200 // can be decoupled for each animator separately
        animatorSet.startDelay = 0 // can be decoupled for each animator separately
        animatorSet.playTogether(
            translateAnimatorX,
            translateAnimatorY
        )
        return animatorSet
    }

    fun wordArrangementMoveAnimation(
        fromView: View,
        toView: View,
        rootView: View,
        shuttleView: View,
        onAnimationEndListener: OnAnimationEndListener,
        isBackAnimation: Boolean,
        context: Context
    ) {

        val animatorSet: AnimatorSet = getViewToViewScalingWordAnimator(
            rootView,
            shuttleView,
            fromView,
            toView,
            isBackAnimation,
            context
        )
        animatorSet.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {
                shuttleView.visibility = View.VISIBLE
                fromView.visibility = View.INVISIBLE
            }

            override fun onAnimationEnd(animation: Animator) {
                fromView.visibility = View.VISIBLE
                shuttleView.visibility = View.GONE
                onAnimationEndListener.onAnimationEnd()
            }

            override fun onAnimationCancel(animation: Animator) {}
            override fun onAnimationRepeat(animation: Animator) {}
        })
        animatorSet.start()
    }

    private fun getViewToViewScalingWordAnimator(
        parentView: View,
        viewToAnimate: View,
        fromView: View,
        toView: View,
        isBackAnimation: Boolean,
        context: Context
    ): AnimatorSet {

        val fromRect = Rect()
        val toRect = Rect()
        fromView.getGlobalVisibleRect(fromRect)
        toView.getGlobalVisibleRect(toRect)

        // get all coordinates at once
        val parentViewRect = Rect()
        val viewToAnimateRect = Rect()
        parentView.getGlobalVisibleRect(parentViewRect)
        viewToAnimate.getGlobalVisibleRect(viewToAnimateRect)
        viewToAnimate.scaleX = 1f
        viewToAnimate.scaleY = 1f


        var toStartPosition = if (toView is MaterialButton) {
            toRect.right + 30
        } else {
            toRect.left + 16
        }

        var toTopPosition = (toRect.top - parentViewRect.top) + 16

        if ((toView is MaterialButton) && (viewToAnimate.width > (parentViewRect.right-toRect.right))) {
            toStartPosition = parentViewRect.left + 20
            toTopPosition += toView.height + 20
        }

        // moving of the object on X-axis
        val translateAnimatorX: ObjectAnimator = ObjectAnimator.ofFloat(
            viewToAnimate,
            "X",
            (fromRect.left - parentViewRect.left).toFloat(),
            toStartPosition.toFloat()
        )

        // moving of the object on Y-axis
        val translateAnimatorY: ObjectAnimator = ObjectAnimator.ofFloat(
            viewToAnimate,
            "Y",
            (fromRect.top - parentViewRect.top).toFloat(),
            toTopPosition.toFloat()
        )
        val animatorSet = AnimatorSet()
        animatorSet.interpolator = DecelerateInterpolator(1f)
        animatorSet.duration = 400 // can be decoupled for each animator separately
        animatorSet.startDelay = 0 // can be decoupled for each animator separately
        animatorSet.playTogether(
            translateAnimatorX,
            translateAnimatorY
        )
        return animatorSet
    }

    interface OnAnimationEndListener {
        fun onAnimationEnd()
    }

}