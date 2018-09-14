package org.wit.placemark

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import kotlinx.android.synthetic.main.activity_placemark.*
import org.jetbrains.anko.toast

class PlacemarkActivity : AppCompatActivity(), AnkoLogger {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_placemark)
    info("Placemark Activity started..")

    btnAdd.setOnClickListener() {
      val placemarkTitle = placemarkTitle.text.toString()
      if (placemarkTitle.isNotEmpty()) {
        info("add Button Pressed: $placemarkTitle")
      } else {
        toast ("Please Enter a title")
      }
    }
  }
}

