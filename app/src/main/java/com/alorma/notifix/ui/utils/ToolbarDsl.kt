package com.alorma.notifix.ui.utils;

import android.support.v7.widget.Toolbar
import com.alorma.notifix.R

@DslMarker
annotation class RiseDsl

@RiseDsl
class ItemBuilder {

    lateinit var action: () -> Unit
    var id: Int = 0

    fun build() = id to action
}

@RiseDsl
class ToolbarBuilder {

    lateinit var toolbar: Toolbar
    var back: (() -> Unit)? = null
    var backIcon: Int? = null
    var title: Int = 0
    var menu: Int = 0
    var items = mutableListOf<Pair<Int, () -> Unit>>()

    fun build(): Toolbar {

        if (title != 0) {
            toolbar.title = toolbar.resources.getString(title)
        }

        if (menu != 0) {
            toolbar.inflateMenu(menu)
        }

        back?.let { backAction ->
            toolbar.setNavigationIcon(backIcon ?: R.drawable.ic_arrow)
            toolbar.setNavigationOnClickListener {
                backAction()
            }
        }

        toolbar.setOnMenuItemClickListener {
            items.firstOrNull { item -> it.itemId == item.first }
                    ?.second
                    ?.invoke()
            true
        }

        return toolbar
    }

    fun item(id: Int = 0, setup: ItemBuilder.() -> Unit) {
        val builder = ItemBuilder()
        builder.id = id
        builder.setup()
        items.add(builder.build())
    }
}

@RiseDsl
fun toolbarDSL(setup: ToolbarBuilder.() -> Unit) {
    with(ToolbarBuilder()) {
        setup()
        build()
    }
}

fun Toolbar.dsl(setup: ToolbarBuilder.() -> Unit) {
    with(ToolbarBuilder()) {
        toolbar = this@dsl
        setup()
        build()
    }
}