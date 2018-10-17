package org.wit.placemark.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_placemark.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast
import org.wit.placemark.R
import org.wit.placemark.helpers.readImage
import org.wit.placemark.helpers.readImageFromPath
import org.wit.placemark.helpers.showImagePicker
import org.wit.placemark.main.MainApp
import org.wit.placemark.models.Location
import org.wit.placemark.models.PlacemarkModel

class PlacemarkActivity : AppCompatActivity(), AnkoLogger {

  lateinit var app: MainApp
  val IMAGE_REQUEST = 1
  val LOCATION_REQUEST = 2
  var location = Location(52.245696, -7.139102, 15f)
  var placemark = PlacemarkModel()


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_placemark)
    toolbarAdd.title = title
    setSupportActionBar(toolbarAdd)
    info("Placemark Activity started..")

    app = application as MainApp
    var edit = false

    if (intent.hasExtra("placemark_edit")) {
      edit = true
      placemark = intent.extras.getParcelable<PlacemarkModel>("placemark_edit")
      placemarkTitle.setText(placemark.title)
      description.setText(placemark.description)
      placemarkImage.setImageBitmap(readImageFromPath(this, placemark.image))
      btnAdd.setText(R.string.save_placemark)
      if(!placemark.image.isEmpty()){
        chooseImage.setText(R.string.button_changeImage)
      }

    }


    btnAdd.setOnClickListener() {
      placemark.title = placemarkTitle.text.toString()
      placemark.description = description.text.toString()
      placemark.lat = location.lat
      placemark.lng = location.lng
      placemark.zoom = location.zoom
      if (placemark.title.isEmpty()) {
        toast(R.string.enter_placemark_title)
      } else {
        if (edit) {
          app.placemarks.update(placemark.copy())
        } else {
          app.placemarks.create(placemark.copy())
        }
      }
      info("add Button Pressed: $placemark")
      setResult(AppCompatActivity.RESULT_OK)
      finish()
    }

    chooseImage.setOnClickListener {
      showImagePicker(this, IMAGE_REQUEST)
    }

    placemarkLocation.setOnClickListener {
      val location = Location(52.245696, -7.139102, 15f)
      if (placemark.zoom != 0f) {
        location.lat =  placemark.lat
        location.lng = placemark.lng
        location.zoom = placemark.zoom
      }
      startActivityForResult(intentFor<MapsActivity>().putExtra("location", location), LOCATION_REQUEST)
    }

    placemarkDelete.setOnClickListener {
      app.placemarks.delete(placemark.copy())
      setResult(AppCompatActivity.RESULT_OK)
      finish()
    }
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_placemark, menu)
    return super.onCreateOptionsMenu(menu)
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    when (item?.itemId) {
      R.id.item_cancel -> {
        finish()
      }
    }
    return super.onOptionsItemSelected(item)
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    when (requestCode) {
      IMAGE_REQUEST -> {
        if (data != null) {
          placemark.image = data.getData().toString()
          placemarkImage.setImageBitmap(readImage(this, resultCode, data))
        }
      }
      LOCATION_REQUEST -> {
        if (data != null){
          location = data.extras.getParcelable<Location>("location")
          placemark.lat = location.lat
          placemark.lng = location.lng
          placemark.zoom = location.zoom
        }
      }
    }
  }
}
