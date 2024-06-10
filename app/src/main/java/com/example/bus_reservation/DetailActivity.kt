package com.example.bus_reservation
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
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

class DetailActivity : AppCompatActivity() {

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
        setContentView(R.layout.activity_detail)

        detailsDestinationPlace = findViewById(R.id.detailsDestinationPlace)
        detailImage = findViewById(R.id.detailImage)
        detailTitle = findViewById(R.id.detailTitle)
        deleteButton = findViewById(R.id.deleteButton)
        editButton = findViewById(R.id.editButton)
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
            imageUrl = bundle.getStringExtra("Image") ?: ""
            Glide.with(this).load(bundle.getStringExtra("Image")).into(detailImage)
            imageUrl = intent.getStringExtra("Image") ?: ""

            if (imageUrl.isNotEmpty()) {
                // Load the image using Glide
                Glide.with(this@DetailActivity).load(imageUrl).into(detailImage)

            } else {
                Log.e("DeatilActivity", "ImageUrl empty")

            }
        }



        deleteButton.setOnClickListener {
            val reference: DatabaseReference =
                FirebaseDatabase.getInstance().getReference("Android Tutorials")
            val storage: FirebaseStorage = FirebaseStorage.getInstance()

            if (imageUrl.isNotEmpty()) {
                val storageReference: StorageReference = storage.getReferenceFromUrl(imageUrl)
                storageReference.delete().addOnSuccessListener(OnSuccessListener {
                    reference.child(key).removeValue()
                    Toast.makeText(this@DetailActivity, "Deleted", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(applicationContext, MainActivity::class.java))
                    finish()
                }).addOnFailureListener { e ->
                    // Handle the failure to delete storage or database entry
                    Toast.makeText(this@DetailActivity, "Deletion failed: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            } else {
                // Handle the case where imageUrl is empty or null
                Toast.makeText(this@DetailActivity, "Image URL is empty", Toast.LENGTH_SHORT).show()
            }
        }

// ...


        editButton.setOnClickListener {
            val intent = Intent(this@DetailActivity, UpdateActivity::class.java)
                .putExtra("Title", detailTitle.text.toString())
                .putExtra("Description", detailsDeparturePlace.text.toString())
                .putExtra("Language", detailsDestinationPlace.text.toString())
                .putExtra("DesTime",detailDepartureTime.text.toString())
                .putExtra("arrTime",detailArivalTime.text.toString())
                .putExtra("Price",detailPrice.text.toString())
                .putExtra("availability",detailsAvailability.text.toString())

                .putExtra("Image", imageUrl)
                .putExtra("Key", key)
            startActivity(intent)
        }
    }
}
