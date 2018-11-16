package org.wit.placemark.views.map

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import org.wit.placemark.models.PlacemarkModel
import org.wit.placemark.views.BasePresenter
import org.wit.placemark.views.BaseView

class PlacemarkMapPresenter(view: BaseView) : BasePresenter(view) {

    fun doConfigureMap(map: GoogleMap, listener: GoogleMap.OnMarkerClickListener){
        map.uiSettings.isZoomControlsEnabled = true
        app.placemarks.findAll().forEach {
            val loc = LatLng(it.lat, it.lng)
            val options = MarkerOptions().title(it.title).position(loc)
            map.addMarker(options).tag = it.id
            map.setOnMarkerClickListener(listener)
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, it.zoom))
        }
    }

    fun getPlacemark(marker: Marker) : PlacemarkModel {
        val placemark = app.placemarks.findById(marker.tag as Long)
        return placemark
    }

}