package com.alorma.notifix.ui.utils

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.support.v7.widget.CardView
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import com.alorma.notifix.R

fun CardView.toggleElevation() {
    if (cardElevation > 0) {
        flat()
    } else {
        lift()
    }
}

fun CardView.lift(elevation: Float = resources.getDimension(R.dimen.notification_card_elevation),
                  margin: Int = resources.getDimension(R.dimen.notification_card_margin).toInt(),
                  time: Long = resources.getInteger(R.integer.notification_card_anim_time).toLong()) {
    AnimatorSet().apply {
        duration = time
        playTogether(animateElevation(elevation), animateMargin(0, margin))
    }.start()
}

fun CardView.flat(time: Long = resources.getInteger(R.integer.notification_card_anim_time).toLong()) {
    AnimatorSet().apply {
        duration = time
        val currentMargin = (layoutParams as? ViewGroup.MarginLayoutParams)?.leftMargin ?: 0
        playTogether(animateElevation(0F), animateMargin(currentMargin, 0))
    }.start()
}

private fun CardView.animateElevation(to: Float) = ObjectAnimator.ofFloat(cardElevation, to)
        .apply {
            interpolator = DecelerateInterpolator()
            addUpdateListener {
                cardElevation = it.animatedValue as Float
            }
        }

private fun CardView.animateMargin(fromMargin: Int, toMargin: Int): ValueAnimator =
        ObjectAnimator.ofInt(fromMargin, toMargin)
                .apply {
                    interpolator = DecelerateInterpolator()
                    addUpdateListener {
                        layoutParams = layoutParams.apply {
                            val value = it.animatedValue as Int
                            (this as? ViewGroup.MarginLayoutParams)?.setMargins(value, value, value, value)
                        }
                    }
                }