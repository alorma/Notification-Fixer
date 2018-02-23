package com.alorma.notifix.ui.utils

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.support.v4.view.ViewCompat
import android.support.v7.widget.CardView
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.DecelerateInterpolator
import com.alorma.notifix.R

class NotificationCardAnimations {

    fun toggleElevation(card: CardView, expandView: View) {
        if (card.cardElevation > 0) {
            card.flat(expandView)
        } else {
            card.lift(expandView)
        }
    }

    private fun CardView.lift(expandView: View,
                              elevation: Float = resources.getDimension(R.dimen.notification_card_elevation),
                              margin: Int = resources.getDimension(R.dimen.notification_card_margin).toInt(),
                              time: Long = resources.getInteger(R.integer.notification_card_anim_time).toLong()) {
        AnimatorSet().apply {
            duration = time
            playTogether(animateElevation(elevation), animateMargin(margin))
            addListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(animation: Animator) {}
                override fun onAnimationEnd(animation: Animator) {
                    with(expandView) {
                        animate().alpha(1f)
                                .setDuration(time)
                                .setInterpolator(AccelerateDecelerateInterpolator())
                                .withStartAction {
                                    expandView.visibility = View.VISIBLE
                                }
                                .start()
                    }
                }

                override fun onAnimationCancel(animation: Animator) {}
                override fun onAnimationStart(animation: Animator) {
                    expandView.alpha = 0f
                    expandView.visibility = View.GONE
                }
            })
        }.start()
    }

    private fun CardView.flat(expandView: View,
                              time: Long = resources.getInteger(R.integer.notification_card_anim_time).toLong()) {

        AnimatorSet().apply {
            duration = time
            playTogether(animateElevation(0F), animateMargin(0))
            addListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(animation: Animator) {}
                override fun onAnimationEnd(animation: Animator) {
                    expandView.alpha = 0f
                    expandView.visibility = View.GONE
                }

                override fun onAnimationCancel(animation: Animator) {}
                override fun onAnimationStart(animation: Animator) {}
            })
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