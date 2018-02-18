package com.alorma.notifix.ui.features.trigger

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alorma.notifix.R
import com.alorma.notifix.domain.model.Trigger
import kotlinx.android.synthetic.main.row_trigger.view.*

class TriggersAdapter : RecyclerView.Adapter<TriggersAdapter.Holder>() {

    private val items = mutableListOf<Trigger>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.row_trigger, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    infix fun addAll(triggers: Collection<Trigger>) {
        items.addAll(triggers)
        notifyDataSetChanged()
    }

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(trigger: Trigger) {
            itemView.icon.setImageResource(trigger.icon)
            itemView.text.text = trigger.text

            itemView.setOnClickListener {  }
        }
    }
}