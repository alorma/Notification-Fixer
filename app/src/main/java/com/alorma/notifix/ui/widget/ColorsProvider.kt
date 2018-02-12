package com.alorma.notifix.ui.widget

import android.support.annotation.ColorRes
import com.alorma.notifix.R

data class ColorItem(@ColorRes val color: Int, val whiteMark: Boolean)

class ColorsProvider {

    fun get() = mutableListOf<ColorItem>().apply {
        add(ColorItem(R.color.red, true))
        add(ColorItem(R.color.pink, true))
        add(ColorItem(R.color.purple, true))
        add(ColorItem(R.color.deep_purple, true))
        add(ColorItem(R.color.indigo, true))
        add(ColorItem(R.color.blue, false))
        add(ColorItem(R.color.light_blue, false))
        add(ColorItem(R.color.cyan, false))
        add(ColorItem(R.color.teal, true))
        add(ColorItem(R.color.green, false))
        add(ColorItem(R.color.light_green, false))
        add(ColorItem(R.color.lime, false))
        add(ColorItem(R.color.yellow, false))
        add(ColorItem(R.color.amber, false))
        add(ColorItem(R.color.orange, false))
        add(ColorItem(R.color.deep_orange, false))
        add(ColorItem(R.color.brown, true))
        add(ColorItem(R.color.grey, false))
        add(ColorItem(R.color.blue_grey, true))
        add(ColorItem(R.color.white, false))
        add(ColorItem(R.color.black, true))
    }.toList()
}