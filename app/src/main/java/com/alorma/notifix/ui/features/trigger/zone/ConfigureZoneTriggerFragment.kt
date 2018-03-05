package com.alorma.notifix.ui.features.trigger.zone

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.TypeEvaluator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.location.Location
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.view.animation.FastOutSlowInInterpolator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnticipateOvershootInterpolator
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
import com.mapbox.services.android.telemetry.location.LocationEngine
import com.mapbox.services.android.telemetry.location.LocationEngineListener
import com.mapbox.services.android.telemetry.location.LocationEnginePriority
import com.mapbox.services.android.telemetry.location.LocationEngineProvider
import kotlinx.android.synthetic.main.configure_zone_fragment.*
import javax.inject.Inject


class ConfigureZoneTriggerFragment : DialogFragment(), CreateZoneTriggerView, LocationEngineListener {

    companion object {
        private const val ANIMATION_DELAY_FACTOR = 1.5
    }

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
        locationEngine = LocationEngineProvider(context).obtainBestLocationEngineAvailable().apply {
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

        locationEngine?.removeLocationEngineListener(this)

        currentMarker?.let {
            map.removeMarker(it)
            animateMapPosition(latLng)
        } ?: moveDirectTo(latLng)

        currentMarker = MarkerOptions().position(latLng).let {
            map.addMarker(it)
        }
        selectZone.isEnabled = true
    }

    private fun animateMapPosition(latLng: LatLng) {
        createMapAnimator(map.cameraPosition, createCameraPosition(latLng)).start()
    }

    private fun moveDirectTo(latLng: LatLng) {
        createCameraPosition(latLng).let {
            CameraUpdateFactory.newCameraPosition(it)
        }.run { map.moveCamera(this) }
    }

    private fun createCameraPosition(latLng: LatLng): CameraPosition {
        return CameraPosition.Builder().apply {
            target(latLng)
            zoom(15.toDouble())
        }.build()
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

    private fun createMapAnimator(currentPosition: CameraPosition, targetPosition: CameraPosition): Animator {
        val animatorSet = AnimatorSet()
        animatorSet.play(createLatLngAnimator(currentPosition.target, targetPosition.target))
        animatorSet.play(createZoomAnimator(currentPosition.zoom, targetPosition.zoom))
        return animatorSet
    }

    private fun createLatLngAnimator(currentPosition: LatLng, targetPosition: LatLng): Animator {
        val latLngAnimator = ValueAnimator.ofObject(LatLngEvaluator(), currentPosition, targetPosition)
        latLngAnimator.duration = (1000 * ANIMATION_DELAY_FACTOR).toLong()
        latLngAnimator.interpolator = FastOutSlowInInterpolator()
        latLngAnimator.addUpdateListener { animation -> map.setLatLng(animation.animatedValue as LatLng) }
        return latLngAnimator
    }

    private fun createZoomAnimator(currentZoom: Double, targetZoom: Double): Animator {
        val zoomAnimator = ValueAnimator.ofFloat(currentZoom.toFloat(), targetZoom.toFloat())
        zoomAnimator.duration = (2200 * ANIMATION_DELAY_FACTOR).toLong()
        zoomAnimator.startDelay = (600 * ANIMATION_DELAY_FACTOR).toLong()
        zoomAnimator.interpolator = AnticipateOvershootInterpolator()
        zoomAnimator.addUpdateListener { animation ->
            val animatedValue = animation.animatedValue as Float
            map.setZoom(animatedValue.toDouble())
        }
        return zoomAnimator
    }

    private class LatLngEvaluator : TypeEvaluator<LatLng> {

        private val latLng = LatLng()

        override fun evaluate(fraction: Float, startValue: LatLng, endValue: LatLng): LatLng {
            latLng.latitude = startValue.latitude + ((endValue.latitude - startValue.latitude) * fraction)
            latLng.longitude = startValue.longitude + ((endValue.longitude - startValue.longitude) * fraction);
            return latLng
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