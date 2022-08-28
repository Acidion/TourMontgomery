package com.homegrownhospitality.tourmontgomery.findnearbyplacesar

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class Main : AppCompatActivity() {

    private lateinit var spinnerFragment : Spinner
    private var locSearchType : String = "restaurant"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!isSupportedDevice()) {
            return
        }

        setContentView(R.layout.activity_main)

        spinnerFragment = findViewById(R.id.locType)
        ArrayAdapter.createFromResource(
            this,
            R.array.locTypes,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerFragment.adapter = adapter
        }

        val button: Button = findViewById(R.id.btnOpenAR)
        button.setOnClickListener {
            val intent = Intent(this, ArView::class.java)
            intent.putExtra("placeType", locSearchType)
            startActivity(intent)
        }
    }

    private fun setUpSpinner () {
        spinnerFragment.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>,
                                        view: View, position: Int, id: Long) {
                locSearchType = parent.getItemAtPosition(position).toString()
                when (locSearchType) {
                    "Art Gallery" -> locSearchType = "art_gallery"
                    "Bus Station" -> locSearchType = "bus_station"
                    "Food (Takeaway)" -> locSearchType = "meal_takeaway"
                    "Night Club" ->  locSearchType = "night_club"
                    "Parks" -> locSearchType = "park"
                    "Tourist Attractions" -> locSearchType = "tourist_attraction"
                    else -> {
                        locSearchType = locSearchType.toLowerCase()
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>){

            }
        }
    }
    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    private fun isSupportedDevice(): Boolean {
        val activityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val openGlVersionString = activityManager.deviceConfigurationInfo.glEsVersion
        if (openGlVersionString.toDouble() < 3.0) {
            Toast.makeText(this, "Sceneform requires OpenGL ES 3.0 or later", Toast.LENGTH_LONG)
                .show()
            finish()
            return false
        }
        return true
    }
}