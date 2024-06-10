package com.example.bus_reservation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.cardview.widget.CardView

class AdminDashbord : AppCompatActivity() {
    private lateinit var clothingCard: CardView
    private lateinit var busdetails: CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Remove the action bar
        supportActionBar?.hide()





        setContentView(R.layout.activity_admin)

        clothingCard = findViewById(R.id.clothingCard)

        clothingCard.setOnClickListener {
            val intent = Intent(this@AdminDashbord, MainActivity::class.java)
            startActivity(intent)
        }

        busdetails = findViewById(R.id.busdetails)

        busdetails.setOnClickListener {
            val intent = Intent(this@AdminDashbord, AllBookingData::class.java)
            startActivity(intent)
        }
    }
}