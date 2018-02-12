package com.alorma.notifix.ui.widget

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.annotation.ColorInt
import android.support.v4.content.ContextCompat
import android.support.v4.graphics.drawable.DrawableCompat
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.alorma.notifix.R
import kotlinx.android.synthetic.main.color_selector_layout.view.*
import kotlinx.android.synthetic.main.row_color_selector.view.*

class ColorSelectorWidget @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private lateinit var adapter: ColorsAdapter
    lateinit var selectedColor: ColorItem

    init {
        inflate(context, R.layout.color_selector_layout, this)
        isInEditMode
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        val colorGridColumns = resources.getInteger(R.integer.grid_colors)
        colorsRecycler.layoutManager = GridLayoutManager(context, colorGridColumns)
        val list = ColorsProvider().get().also {
            selectedColor = it[0]
        }
        adapter = ColorsAdapter(list, selectedColor) {
            this.selectedColor = it
            adapter.selectedColor = this.selectedColor
            adapter.notifyDataSetChanged()
        }
        colorsRecycler.adapter = adapter
    }

    class ColorsAdapter(private val colors: List<ColorItem>,
                        var selectedColor: ColorItem,
                        private val update: (ColorItem) -> Unit) : RecyclerView.Adapter<ColorsAdapter.ColorHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorHolder = ColorHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.row_color_selector, parent, false))

        override fun onBindViewHolder(holder: ColorHolder, position: Int) {
            holder.bind(colors[position], selectedColor, update)
        }

        override fun getItemCount(): Int = colors.size

        class ColorHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            fun bind(@ColorInt color: ColorItem,
                     selectedColor: ColorItem,
                     update: (ColorItem) -> Unit) {
                itemView.colorCircle.apply {
                    background = getCircleDrawable(color)

                    if (color.color == selectedColor.color) {
                        setImageResource(getCheck(color))
                    } else {
                        setImageDrawable(null)
                    }
                }
                itemView.setOnClickListener {
                    update(color)
                    itemView.colorCircle.setImageResource(getCheck(color))
                }
            }

            private fun getCheck(color: ColorItem): Int {
                return if (color.whiteMark) {
                    R.drawable.ic_check
                } else {
                    R.drawable.ic_check_black
                }
            }

            private fun getCircleDrawable(color: ColorItem): Drawable? =
                    ContextCompat.getDrawable(itemView.context, R.drawable.color_selector_base)?.apply {
                        mutate().apply {
                            val colorInt = ContextCompat.getColor(itemView.context, color.color)
                            DrawableCompat.setTint(this, colorInt)
                        }
                    }
        }
    }
}