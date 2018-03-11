package com.alorma.notifix.ui.features.trigger.preview

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alorma.notifix.NotifixApplication.Companion.component
import com.alorma.notifix.R
import com.alorma.notifix.ui.utils.GlideApp
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.mapbox.mapboxsdk.camera.CameraPosition
import kotlinx.android.synthetic.main.trigger_preview.*
import javax.inject.Inject
import com.mapbox.mapboxsdk.constants.Style.MAPBOX_STREETS
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.snapshotter.MapSnapshotter


class TriggerPreviewWidget : Fragment(), TriggerPreviewView {

    companion object {
        private const val EXTRA_ID = "extra_id"

        fun newInstance(triggerId: Long) = TriggerPreviewWidget().apply {
            arguments = Bundle().apply {
                putLong(EXTRA_ID, triggerId)
            }
        }
    }

    @Inject
    lateinit var presenter: TriggerPreviewPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        component inject this
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.trigger_preview, null, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter init this

        arguments?.getLong(EXTRA_ID)?.let {
            loadTrigger(it)
        }
    }

    private fun loadTrigger(it: Long) {
        presenter action TriggerPreviewAction.LoadTrigger(it)
    }

    override fun render(state: TriggerPreviewState) {
        when (state) {
            is TriggerPreviewState.Success -> when (state) {
                is TriggerPreviewState.Success.Phone -> showPhoneContact(state)
                is TriggerPreviewState.Success.Sms -> showSmsContact(state)
                is TriggerPreviewState.Success.Time -> showTime(state)
                is TriggerPreviewState.Success.Zone -> showZone(state)
            }
        }
    }

    private fun showPhoneContact(state: TriggerPreviewState.Success.Phone) {
        showContact(state.photo)
        setTriggerIcon(R.drawable.ic_phone_in_talk)
    }

    private fun showSmsContact(state: TriggerPreviewState.Success.Sms) {
        showContact(state.photo)
        setTriggerIcon(R.drawable.ic_sms)
    }

    private fun showContact(avatar: String?) {
        avatar?.let {
            GlideApp.with(triggerImg)
                    .load(avatar)
                    .transform(CircleCrop())
                    .into(triggerImg)
        }
    }

    private fun showTime(state: TriggerPreviewState.Success.Time) {
        setTriggerIcon(R.drawable.ic_av_timer)
    }

    private fun showZone(state: TriggerPreviewState.Success.Zone) {
        setTriggerIcon(R.drawable.ic_location)

        context?.let {
            val options = MapSnapshotter.Options(triggerImg.width, triggerImg.height).apply {
                withPixelRatio(1)
                withCameraPosition(CameraPosition.Builder().apply {
                    target(LatLng(state.lat, state.lon))
                    zoom(15.toDouble())
                }.build())
            }

            MapSnapshotter(it, options).start {
                GlideApp.with(triggerImg)
                        .load(it.bitmap)
                        .transform(CircleCrop())
                        .into(triggerImg)

            }
        }
    }

    private fun setTriggerIcon(icon: Int) {
        triggerTypeIcon.setImageResource(icon)
    }

    override fun navigate(route: TriggerPreviewRoute) {

    }
}