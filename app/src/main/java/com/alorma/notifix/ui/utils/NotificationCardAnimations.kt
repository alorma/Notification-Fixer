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
            card.flat(topLayout, expandView)
        } else {
            card.lift(expandView)
        }
    }

    private fun CardView.lift(expandView: View,
                              elevation: Float = resources.getDimension(R.dimen.notification_card_elevation),
                              radius: Float = resources.getDimension(R.dimen.notification_card_radius),
                              margin: Int = resources.getDimension(R.dimen.notification_card_margin).toInt(),
                              time: Long = resources.getInteger(R.integer.notification_card_anim_time).toLong()) {
        val toHeight = resources.getDimension(R.dimen.notification_row_expand_height)

        val anim1 = AnimatorSet().apply {
            playTogether(
                    animateElevation(elevation),
                    animateMargin(margin),
                    animateCardRadius(radius),
                    animateHeight(toHeight.toInt())
            )
        }

        val anim2 = AnimatorSet().apply {
            playTogether(
                    expandView.animateHeight(toHeight.toInt()),
                    expandView.animateAlpha(1f)
            )
        }

        currentAnimation = AnimatorSet().setDuration(time).apply {
            playTogether(anim1, anim2)
        }.also { it.start() }
    }

    private fun CardView.flat(topLayout: View,
                              expandView: View,
                              time: Long = resources.getInteger(R.integer.notification_card_anim_time).toLong()) {

        val anim1 = AnimatorSet().apply {
            playTogether(
                    animateElevation(0F),
                    animateMargin(0),
                    animateCardRadius(0f),
                    animateHeight(topLayout.height)
            )
        }

        val anim2 = AnimatorSet().apply {
            playTogether(
                    expandView.animateAlpha(0f),
                    expandView.animateHeight(0)
            )
        }

        currentAnimation = AnimatorSet().setDuration(time).apply {
            playTogether(anim1, anim2)
        }.also { it.start() }
    }

    private fun CardView.animateElevation(to: Float) = ObjectAnimator.ofFloat(cardElevation, to)
            .apply {
                interpolator = DecelerateInterpolator()
                addUpdateListener {
                    cardElevation = it.animatedValue as Float
                }
            }

    private fun CardView.animateCardRadius(toRadius: Float) = ObjectAnimator.ofFloat(radius, toRadius)
            .apply {
                interpolator = DecelerateInterpolator()
                addUpdateListener {
                    radius = it.animatedValue as Float
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

    private fun View.animateTranslationY(toY: Float): ValueAnimator {
        return ObjectAnimator.ofFloat(0f, toY)
                .apply {
                    interpolator = AccelerateDecelerateInterpolator()
                    addUpdateListener {
                        layoutParams = layoutParams.apply {
                            y = it.animatedValue as Float
                        }
                    }
                }
    }

    private fun View.animateAlpha(toAlpha: Float): ValueAnimator = ObjectAnimator.ofFloat(0f, toAlpha)
            .apply {
                interpolator = AccelerateDecelerateInterpolator()
                addUpdateListener {
                    alpha = it.animatedValue as Float
                }
            }
}