package org.wit.placemark.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import kotlinx.android.synthetic.main.activity_placemark.*
import org.jetbrains.anko.toast
import org.wit.placemark.R
import org.wit.placemark.models.PlacemarkModel

class PlacemarkActivity : AppCompatActivity(), AnkoLogger {
  var placemark = PlacemarkModel()
  val placemarks = ArrayList<PlacemarkModel>()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_placemark)
    info("Placemark Activity started..")

    btnAdd.setOnClickListener() {
      val placemarkTitle = placemarkTitle.text.toString()
      val placemarkDescription = placemarkDescription.text.toString()

      if (placemarkTitle.isNotEmpty() && placemarkDescription.isNotEmpty()) {
        placemark.title = placemarkTitle
        placemark.description = placemarkDescription
        placemarks.add(placemark)
        info("Place Mark Added : $placemarks")
      } else {
        if (placemarkTitle.isEmpty()) {
          toast("Please add a Title")
        } else {
          toast("Please add a Description")
        }

      }
    }
  }
}


