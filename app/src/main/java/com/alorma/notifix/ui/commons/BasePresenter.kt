package com.alorma.notifix.ui.commons

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.OnLifecycleEvent
import com.alorma.notifix.data.Logger
import io.reactivex.disposables.CompositeDisposable

abstract class BasePresenter<in S : State, in R : Route, in A : Action, in V : BaseView<S, R>>(protected val logger: Logger)
    : LifecycleObserver {

    internal val disposables: CompositeDisposable = CompositeDisposable()

    private lateinit var view: V
    private var lifecycle: Lifecycle? = null

    open infix fun init(view: V) {
        this.view = view
        if (view is LifecycleOwner) {
            attach(view)
        }
    }

    infix fun attach(lifecycle: LifecycleOwner) {
        this.lifecycle = lifecycle.lifecycle.apply {
            addObserver(this@BasePresenter)
        }
    }

    abstract infix fun action(action: A)

    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    open fun onAny() {
        if (lifecycle?.currentState != Lifecycle.State.DESTROYED) {
            logger.d("${this.javaClass.simpleName} - ${lifecycle?.currentState}")
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    open fun onCreate() {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    open fun onStart() {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    open fun onResume() {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    open fun onPause() {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    open fun onStop() {
        destroy()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    open fun onDestroy() {
        lifecycle?.removeObserver(this)
        destroy()
    }

    fun destroy() {
        disposables.clear()
    }

    fun render(state: S) = view.render(state)

    fun navigate(route: R) = view.navigate(route)
}