package com.alorma.notifix.ui.commons

interface BaseView<in S : State, in R: Route> {

    fun render(state: S)

    fun navigate(route: R)
}