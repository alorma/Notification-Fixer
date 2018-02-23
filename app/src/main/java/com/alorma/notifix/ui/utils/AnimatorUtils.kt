package com.alorma.notifix.ui.utils

import android.animation.Animator

class AnimatorUtils(
        private val animationStart: (() -> Unit)? = null,
        private val animationCancel: (() -> Unit)? = null,
        private val animationRepeat: (() -> Unit)? = null,
        private val animationEnd: (() -> Unit)? = null
): Animator.AnimatorListener {
    override fun onAnimationRepeat(animation: Animator?) {
        animationRepeat?.invoke()
    }

    override fun onAnimationEnd(animation: Animator?) {
        animationEnd?.invoke()
    }

    override fun onAnimationCancel(animation: Animator?) {
        animationCancel?.invoke()
    }

    override fun onAnimationStart(animation: Animator?) {
        animationStart?.invoke()
    }
}