package com.local.funnylearny.ui.matchpairs

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.graphics.Rect
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.RelativeLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout

object MatchPairUtil {

    fun getViewToViewScalingAnimator(
        parentView: View,
        viewToAnimate: View,
        fromViewRect: Rect,
        toViewRect: Rect,
        duration: Long,
        startDelay: Long,
        isBackAnimation : Boolean
    ): AnimatorSet {
        // get all coordinates at once
        val parentViewRect = Rect()
        val viewToAnimateRect = Rect()
        parentView.getGlobalVisibleRect(parentViewRect)
        viewToAnimate.getGlobalVisibleRect(viewToAnimateRect)
        viewToAnimate.scaleX = 1f
        viewToAnimate.scaleY = 1f

        // rescaling of the object on X-axis
        val valueAnimatorWidth = ValueAnimator.ofInt(fromViewRect.width(), toViewRect.width())
        valueAnimatorWidth.addUpdateListener { // Get animated width value update
            val newWidth = valueAnimatorWidth.animatedValue as Int

            // Get and update LayoutParams of the animated view
            val lp = viewToAnimate.layoutParams as CoordinatorLayout.LayoutParams
            lp.width = newWidth
            viewToAnimate.layoutParams = lp
        }

        // rescaling of the object on Y-axis
        val valueAnimatorHeight = ValueAnimator.ofInt(fromViewRect.height(), toViewRect.height())
        valueAnimatorHeight.addUpdateListener { // Get animated width value update
            val newHeight = valueAnimatorHeight.animatedValue as Int

            // Get and update LayoutParams of the animated view
            val lp = viewToAnimate.layoutParams as CoordinatorLayout.LayoutParams
            lp.height = newHeight
            viewToAnimate.layoutParams = lp
        }

        val toViewTotalWidth = toViewRect.right - toViewRect.left
        val fromViewTotalWidth = fromViewRect.right - fromViewRect.left
        var toStartPosition = ((toViewTotalWidth - fromViewTotalWidth)/2 + toViewRect.left).toFloat()

        toStartPosition =  if(!isBackAnimation) {
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
        animatorSet.duration = duration // can be decoupled for each animator separately
        animatorSet.startDelay = startDelay // can be decoupled for each animator separately
        animatorSet.playTogether(
            translateAnimatorX,
            translateAnimatorY
        )
        return animatorSet
    }

}