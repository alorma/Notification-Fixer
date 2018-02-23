package com.alorma.notifix.ui.utils

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.support.v7.widget.CardView
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.DecelerateInterpolator
import com.alorma.notifix.R

class NotificationCardAnimations {

    fun toggleElevation(card: CardView) {
        if (card.cardElevation > 0) {
            card.flat()
        } else {
            card.lift()
        }
    }

    fun CardView.lift(elevation: Float = resources.getDimension(R.dimen.notification_card_elevation),
             toHeight: Int = getCardHeight(),
             margin: Int = resources.getDimension(R.dimen.notification_card_margin).toInt(),
             time: Long = resources.getInteger(R.integer.notification_card_anim_time).toLong()) {
        AnimatorSet().apply {
            duration = time
            playTogether(animateElevation(elevation), animateMargin(margin), animateHeight(toHeight))
        }.start()
    }

    private fun CardView.getCardHeight() =
            resources.getDimension(R.dimen.notification_row_flat_height).toInt() + resources.getDimension(R.dimen.notification_row_expand_height).toInt()

    private fun CardView.flat(toHeight: Int = resources.getDimension(R.dimen.notification_row_flat_height).toInt(),
             time: Long = resources.getInteger(R.integer.notification_card_anim_time).toLong()) {
        AnimatorSet().apply {
            duration = time
            playTogether(animateElevation(0F), animateMargin(0), animateHeight(toHeight))
        }.start()
    }

    private fun CardView.animateElevation(to: Float) = ObjectAnimator.ofFloat(cardElevation, to)
            .apply {
                interpolator = DecelerateInterpolator()
                addUpdateListener {
                    cardElevation = it.animatedValue as Float
                }
            }

    private fun CardView.animateMargin(toMargin: Int): ValueAnimator =
            ObjectAnimator.ofInt((layoutParams as? ViewGroup.MarginLayoutParams)?.leftMargin
                    ?: 0, toMargin)
                    .apply {
                        interpolator = DecelerateInterpolator()
                        addUpdateListener {
                            layoutParams = layoutParams.apply {
                                val value = it.animatedValue as Int
                                (this as? ViewGroup.MarginLayoutParams)?.setMargins(value, value, value, value)
                            }
                        }
                    }

    private fun CardView.animateHeight(toHeight: Int): ValueAnimator =
            ObjectAnimator.ofInt(height, toHeight)
                    .apply {
                        interpolator = AccelerateDecelerateInterpolator()
                        addUpdateListener {
                            layoutParams = layoutParams.apply {
                                height = it.animatedValue as Int
                            }
                        }
                    }
}