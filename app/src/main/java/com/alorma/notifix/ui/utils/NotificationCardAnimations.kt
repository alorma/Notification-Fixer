package com.alorma.notifix.ui.utils

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.support.v7.widget.CardView
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.DecelerateInterpolator
import com.alorma.notifix.R

class NotificationCardAnimations {

    private var currentAnimation: AnimatorSet? = null

    fun toggleElevation(card: CardView, topLayout: View, expandView: View) {
        currentAnimation?.cancel()
        if (card.cardElevation > 0) {
            card.flat {
                with(expandView) {
                    val time: Long = resources.getInteger(R.integer.notification_card_anim_time).toLong()

                    AnimatorSet().setDuration(time).apply {
                        playTogether(
                                card.animateHeight(topLayout.height),
                                animateHeight(0))
                        start()
                    }
                }
            }
        } else {
            card.lift(topLayout, expandView)
        }
    }

    private fun CardView.lift(topLayout: View,
                              expandView: View,
                              elevation: Float = resources.getDimension(R.dimen.notification_card_elevation),
                              margin: Int = resources.getDimension(R.dimen.notification_card_margin).toInt(),
                              time: Long = resources.getInteger(R.integer.notification_card_anim_time).toLong()) {
        val toHeight = resources.getDimension(R.dimen.notification_row_expand_height).toInt()
        currentAnimation = AnimatorSet().setDuration(time).apply {

            playTogether(
                    animateElevation(elevation),
                    animateMargin(margin),
                    animateHeight(topLayout.height + toHeight),
                    expandView.animateHeight(toHeight))
            start()
        }
    }

    private fun CardView.flat(time: Long = resources.getInteger(R.integer.notification_card_anim_time).toLong(),
                              animationStart: () -> Unit) {

        AnimatorSet().apply {
            duration = time
            playTogether(animateElevation(0F), animateMargin(0))
            addListener(AnimatorUtils(animationStart = animationStart))
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

    private fun View.animateHeight(toHeight: Int): ValueAnimator =
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