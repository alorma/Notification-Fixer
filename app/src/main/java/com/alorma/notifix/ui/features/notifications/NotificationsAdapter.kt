package com.alorma.notifix.ui.features.notifications

import android.support.v4.content.ContextCompat
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import com.alorma.notifix.R
import kotlinx.android.synthetic.main.row_notification.view.*

class NotificationsAdapter(private val onChange: NotificationViewModel.(isChecked: Boolean) -> Unit)
    : RecyclerView.Adapter<NotificationsAdapter.Holder>() {

    private val items = mutableListOf<NotificationViewModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.row_notification, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(items[position], onChange)
    }

    override fun getItemCount(): Int = items.size

    override fun getItemId(position: Int): Long = items[position].id.toLong()

    fun addAll(newItems: List<NotificationViewModel>) {
        if (items.isEmpty()) {
            items.addAll(newItems)
            notifyDataSetChanged()
        } else {
            val diffResult = DiffUtil.calculateDiff(Diff(items, newItems))

            diffResult.dispatchUpdatesTo(this).also {
                with(this.items) {
                    clear()
                    addAll(newItems)
                }
            }
        }
    }

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(viewModel: NotificationViewModel,
                 onChange: NotificationViewModel.(isChecked: Boolean) -> Unit) {
            itemView.switchTitle.apply {
                text = viewModel.title
                setOnCheckedChangeListener(null)
                isChecked = viewModel.checked
                setOnCheckedChangeListener { _: CompoundButton, isChecked: Boolean ->
                    viewModel.onChange(isChecked)
                }
            }
            itemView.colorLabel.apply {
                setBackgroundColor(ContextCompat.getColor(context, viewModel.color))
            }
        }
    }

    class Diff(private val items: List<NotificationViewModel>,
               private val newItems: List<NotificationViewModel>)
        : DiffUtil.Callback() {

        override fun getOldListSize(): Int = items.size

        override fun getNewListSize(): Int = newItems.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                items[oldItemPosition].id == newItems[newItemPosition].id

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = items[oldItemPosition].title == newItems[newItemPosition].title
                && items[oldItemPosition].checked == newItems[newItemPosition].checked
    }
}