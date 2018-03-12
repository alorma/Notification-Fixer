package com.alorma.notifix.ui.features.notifications

import android.support.v4.content.ContextCompat
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.ImageView
import com.alorma.notifix.R
import com.alorma.notifix.ui.utils.GlideApp
import com.alorma.notifix.ui.utils.NotificationCardAnimations
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.snapshotter.MapSnapshotter
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

        private val animations = NotificationCardAnimations()

        fun bind(viewModel: NotificationViewModel,
                 onChange: NotificationViewModel.(isChecked: Boolean) -> Unit) {

            with(itemView) {
                title.text = viewModel.title
                notificationSwitch.apply {
                    setOnCheckedChangeListener(null)
                    isChecked = viewModel.checked
                    setOnCheckedChangeListener { _: CompoundButton, isChecked: Boolean ->
                        viewModel.onChange(isChecked)
                    }
                }
                colorLabel.apply {
                    setBackgroundColor(ContextCompat.getColor(context, viewModel.color))
                }

                title.setOnClickListener {
                    animations.toggleElevation(notificationCard, topLayout, expandedLayout)
                }

                viewModel.trigger?.let {
                    when (it) {
                        is TriggerViewModel.Phone -> {
                            it.avatar?.let { showAvatar(triggerImg, it) }
                            triggerTypeIcon.setImageResource(R.drawable.ic_phone_in_talk)
                        }
                        is TriggerViewModel.Sms -> {
                            it.avatar?.let { showAvatar(triggerImg, it) }
                            triggerTypeIcon.setImageResource(R.drawable.ic_sms)
                        }
                        is TriggerViewModel.Time -> triggerTypeIcon.setImageResource(R.drawable.ic_av_timer)
                        is TriggerViewModel.Zone -> {
                            //showZone(triggerImg, it)
                            triggerTypeIcon.setImageResource(R.drawable.ic_location)
                        }
                    }
                } ?: hideTrigger(triggerImg, triggerTypeIcon)
            }
        }

        private fun showAvatar(imageView: ImageView, avatar: String) {
            GlideApp.with(imageView)
                    .load(avatar)
                    .transform(CircleCrop())
                    .into(imageView)
        }

        private fun showZone(imageView: ImageView, state: TriggerViewModel.Zone) {
            val options = MapSnapshotter.Options(imageView.width, imageView.height).apply {
                withPixelRatio(1)
                withCameraPosition(CameraPosition.Builder().apply {
                    target(LatLng(state.lat, state.lon))
                    zoom(15.toDouble())
                }.build())
            }

            MapSnapshotter(imageView.context, options).start {
                GlideApp.with(imageView)
                        .load(it.bitmap)
                        .transform(CircleCrop())
                        .into(imageView)

            }
        }

        private fun hideTrigger(triggerImg: ImageView, triggerTypeIcon: ImageView) {
            triggerImg.visibility = View.GONE
            triggerTypeIcon.visibility = View.GONE
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