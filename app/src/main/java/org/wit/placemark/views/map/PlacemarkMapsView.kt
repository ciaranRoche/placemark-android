package org.wit.placemark.views.map

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import org.wit.placemark.R
import kotlinx.android.synthetic.main.activity_placemark_maps.*
import kotlinx.android.synthetic.main.content_placemark_maps.*
import org.jetbrains.anko.AnkoLogger
import org.wit.placemark.helpers.readImageFromPath


class PlacemarkMapsView : AppCompatActivity(), GoogleMap.OnMarkerClickListener, AnkoLogger {

  lateinit var map: GoogleMap
  lateinit var presenter: PlacemarkMapPresenter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_placemark_maps)
    setSupportActionBar(toolbarMaps)
    presenter = PlacemarkMapPresenter(this)

    mapView.onCreate(savedInstanceState)
    mapView.getMapAsync{
      map = it
      configureMap()
    }
  }

  override fun onDestroy() {
    super.onDestroy()
    mapView.onDestroy()
  }

  override fun onLowMemory() {
    super.onLowMemory()
    mapView.onLowMemory()
  }

  override fun onPause() {
    super.onPause()
    mapView.onPause()
  }

  override fun onResume() {
    super.onResume()
    mapView.onResume()
  }

  override fun onSaveInstanceState(outState: Bundle?) {
    super.onSaveInstanceState(outState)
    mapView.onSaveInstanceState(outState)
  }

  fun configureMap() {
    presenter.doConfigureMap(map, this)
  }

  override fun onMarkerClick(marker: Marker): Boolean {
    val placemark = presenter.getPlacemark(marker)
    currentTitle.text = placemark.title
    currentDescription.text = placemark.description
    placemarkImageView.setImageBitmap(readImageFromPath(this, placemark.image))
    return true
  }

}