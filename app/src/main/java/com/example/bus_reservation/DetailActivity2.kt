package com.example.bus_reservation
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.github.clans.fab.FloatingActionButton
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.core.Context
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class DetailActivity2 : AppCompatActivity() {

    private lateinit var detailsDestinationPlace: TextView
    private lateinit var detailTitle: TextView
    private lateinit var detailsDeparturePlace: TextView
    private lateinit var detailImage: ImageView
    private lateinit var detailDepartureTime:TextView
    private lateinit var detailArivalTime:TextView
    private lateinit var detailPrice:TextView
    private lateinit var detailsAvailability:TextView
    private lateinit var deleteButton: FloatingActionButton
    private lateinit var editButton: FloatingActionButton
    private var key: String = ""
    private lateinit var imageUrl: String

    private lateinit var storage: FirebaseStorage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail2)


        val introbutton1=findViewById<Button>(R.id.buttonBooking)
        introbutton1.setOnClickListener{
            val Intent= Intent(this,BookingPage::class.java)
            startActivity(Intent)
        }

        val introbutton=findViewById<Button>(R.id.buttonBooking123)
        introbutton.setOnClickListener{
            val Intent= Intent(this,UserBooking::class.java)
            startActivity(Intent)
        }

        val imageView: ImageView = findViewById(R.id.back)
        imageView.setOnClickListener {
            // Handle the click event, you can navigate to a new activity here
            startActivity(Intent(this, UserBord::class.java))
        }

        detailsDestinationPlace = findViewById(R.id.detailsDestinationPlace)

        detailTitle = findViewById(R.id.detailTitle)

        detailsDeparturePlace = findViewById(R.id.detailsDeparturePlace)
        detailDepartureTime=findViewById(R.id.departureTime)
        detailArivalTime=findViewById(R.id.arivalTime)
        detailPrice=findViewById(R.id.price)
        detailsAvailability=findViewById(R.id.availability )


        storage = FirebaseStorage.getInstance()

        val bundle = intent

        if (bundle != null) {
            detailsDeparturePlace.text = bundle.getStringExtra("Description")
            detailTitle.text = bundle.getStringExtra("Title")
            detailsDestinationPlace.text = bundle.getStringExtra("Language")
            detailDepartureTime.text=bundle.getStringExtra("DesTime")
            detailArivalTime.text=bundle.getStringExtra("arrTime")
            detailPrice.text=bundle.getStringExtra("Price")
            detailsAvailability.text=bundle.getStringExtra("availability")
            key = bundle.getStringExtra("Key") ?: ""


        }

    }
}
