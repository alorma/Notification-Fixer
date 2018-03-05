package com.alorma.notifix.ui.features.trigger.zone

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.location.Location
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alorma.notifix.NotifixApplication.Companion.component
import com.alorma.notifix.R
import com.alorma.notifix.ui.features.trigger.di.CreateTriggerModule
import com.alorma.notifix.ui.utils.toast
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.annotations.Marker
import com.mapbox.mapboxsdk.annotations.MarkerOptions
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.services.android.telemetry.location.AndroidLocationEngine
import com.mapbox.services.android.telemetry.location.LocationEngine
import com.mapbox.services.android.telemetry.location.LocationEngineListener
import com.mapbox.services.android.telemetry.location.LocationEnginePriority
import kotlinx.android.synthetic.main.configure_zone_fragment.*
import javax.inject.Inject


class ConfigureZoneTriggerFragment : DialogFragment(), CreateZoneTriggerView, LocationEngineListener {
    @Inject
    lateinit var presenter: CreateZoneTriggerPresenter

    private lateinit var map: MapboxMap
    private var currentMarker: Marker? = null
    private var locationEngine: LocationEngine? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.let {
            component add CreateTriggerModule(it) inject this
            Mapbox.getInstance(it, getString(R.string.MAPS_KEY))
            presenter init this
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return inflater.inflate(R.layout.configure_zone_fragment, null, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mapView.onCreate(savedInstanceState)

        mapView.getMapAsync {
            it?.let {
                map = it
                presenter action CreateZoneTriggerAction.OnMapReadyAction()
            }
        }

        selectZone.setOnClickListener {
            currentMarker?.position?.let {
                presenter action CreateZoneTriggerAction.SelectedLocation(it.latitude, it.longitude)
            }
        }
    }

    override fun render(state: CreateZoneTriggerState) {
        when (state) {
            is CreateZoneTriggerState.Location -> onLocationState(state)
        }
    }

    private fun onLocationState(state: CreateZoneTriggerState.Location) {
        when (state) {
            is CreateZoneTriggerState.Location.Allowed -> onMapAllowed()
            is CreateZoneTriggerState.Location.Denied -> onMapDenied()
            is CreateZoneTriggerState.Location.DeniedAlways -> onMapDeniedAlways()
        }
    }

    @SuppressLint("MissingPermission")
    private fun onMapAllowed() {
        locationEngine = AndroidLocationEngine(context).apply {
            priority = LocationEnginePriority.HIGH_ACCURACY
            activate()

            lastLocation?.let {
                setupPositionMarker(LatLng(it.latitude, it.longitude))
            } ?: addLocationEngineListener(this@ConfigureZoneTriggerFragment)

        }
        map.addOnMapClickListener { latLng ->
            setupPositionMarker(latLng)
        }
    }

    private fun setupPositionMarker(latLng: LatLng) {
        CameraPosition.Builder().apply {
            target(latLng)
            zoom(15.toDouble())
        }.build().let {
            CameraUpdateFactory.newCameraPosition(it)
        }.run { map.moveCamera(this) }

        locationEngine?.removeLocationEngineListener(this)

        currentMarker?.let {
            map.removeMarker(it)
        }

        currentMarker = MarkerOptions().position(latLng).let {
            map.addMarker(it)
        }
        selectZone.isEnabled = true
    }

    private fun onMapDenied() {
        context?.toast("Location denied")
    }

    private fun onMapDeniedAlways() {
        context?.toast("Location disabled")
    }

    @SuppressLint("MissingPermission")
    override fun onConnected() {
        locationEngine?.requestLocationUpdates()
    }

    override fun onLocationChanged(location: Location?) {
        location?.let {
            setupPositionMarker(LatLng(it.latitude, it.longitude))
        }
    }


    override fun navigate(route: CreateZoneTriggerRoute) {

    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onStop() {
        super.onStop()
        locationEngine?.removeLocationUpdates()
        mapView.onStop()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onDestroy() {
        mapView?.onDestroy()
        locationEngine?.deactivate()
        super.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }
}