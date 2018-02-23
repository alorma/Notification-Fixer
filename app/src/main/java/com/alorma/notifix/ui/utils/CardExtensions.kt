package com.alorma.notifix.ui.utils

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.support.v7.widget.CardView
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.DecelerateInterpolator
import com.alorma.notifix.R

class NotificationCardAnimations {

    fun toggleElevation(card: CardView, expandView: View? = null) {
        if (card.cardElevation > 0) {
            card.flat()
        } else {
            card.lift(expandView)
        }
    }

    private fun CardView.lift(expandView: View? = null,
                              elevation: Float = resources.getDimension(R.dimen.notification_card_elevation),
                              margin: Int = resources.getDimension(R.dimen.notification_card_margin).toInt(),
                              time: Long = resources.getInteger(R.integer.notification_card_anim_time).toLong()) {

        expandView?.let {
            it.visibility = VISIBLE
        }

        val expandHeight = resources.getDimension(R.dimen.notification_row_expand_height).toInt()
        val toHeight = resources.getDimension(R.dimen.notification_card_elevation).toInt() + expandHeight

        AnimatorSet().apply {
            duration = time
            val expandViewAnim = expandView?.animateAlpha(1f, View.VISIBLE)

            if (expandViewAnim != null) {
                playTogether(animateElevation(elevation), animateMargin(margin), animateHeight(toHeight), expandViewAnim)
            } else {
                playTogether(animateElevation(elevation), animateMargin(margin), animateHeight(toHeight))
            }
        }.start()
    }

    private fun CardView.flat(expandView: View? = null,
                              toHeight: Int = resources.getDimension(R.dimen.notification_row_flat_height).toInt(),
                              time: Long = resources.getInteger(R.integer.notification_card_anim_time).toLong()) {
        expandView?.let {
            it.visibility = INVISIBLE
        }

        AnimatorSet().apply {
            duration = time
            val expandViewAnim = expandView?.animateAlpha(0f, View.GONE)

            if (expandViewAnim != null) {
                playTogether(animateElevation(0F), animateMargin(0), animateHeight(toHeight), expandViewAnim)
            } else {
                playTogether(animateElevation(0F), animateMargin(0), animateHeight(toHeight))
            }
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

    private fun View.animateAlpha(to: Float, newVisibility: Int) = ObjectAnimator.ofFloat(alpha, to)
            .apply {
                interpolator = DecelerateInterpolator()
                addUpdateListener {
                    alpha = it.animatedValue as Float
                }
                addListener(object : Animator.AnimatorListener {
                    override fun onAnimationRepeat(animation: Animator?) {}
                    override fun onAnimationEnd(animation: Animator?) {
                        visibility = newVisibility
                    }

                    override fun onAnimationCancel(animation: Animator?) {}
                    override fun onAnimationStart(animation: Animator?) {}
                })
            }
}