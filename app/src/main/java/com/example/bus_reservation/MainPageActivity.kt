package com.example.bus_reservation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainPageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_main_page)

        val introbutton=findViewById<Button>(R.id.Introbutton)
        introbutton.setOnClickListener{
            val Intent= Intent(this,AlluserViewpage::class.java)
            startActivity(Intent)
        }
    }
}