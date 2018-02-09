package com.alorma.notifix.ui.commons

import io.reactivex.disposables.CompositeDisposable

abstract class BasePresenter<in S : State, in R : Route, in V : BaseView<S, R>> {

    internal val disposables: CompositeDisposable = CompositeDisposable()
    private lateinit var view: V

    open infix fun init(view: V) {
        this.view = view
    }

    abstract infix fun onAction(action: Action)

    fun destroy() = disposables.clear()

    fun render(state: S) = view.render(state)

    fun navigate(route: R) = view.navigate(route)
}