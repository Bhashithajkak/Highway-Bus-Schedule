package com.example.bus_reservation

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.NonNull
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask

class UpdateActivity : AppCompatActivity() {

    private lateinit var updateImage: ImageView
    private lateinit var updateButton: Button
    private lateinit var updateDesc: EditText
    private lateinit var updateTitle: EditText
    private lateinit var updateLang: EditText
    private lateinit var updateDescTime:EditText
    private lateinit var updateLangTime:EditText
    private lateinit var updatePrice:EditText
    private lateinit var updateAvailability:EditText

    private var title: String = ""
    private var desc: String = ""
    private var lang: String = ""
    private var desctime: String=""
    private var langtime:String=""
    private var price:String=""
    private var availability: String=""
    private var imageUrl: String = ""
    private var key: String = ""
    private var oldImageURL: String = ""
    private var uri: Uri? = null
    private lateinit var databaseReference: DatabaseReference
    private lateinit var storageReference: StorageReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)

        updateButton = findViewById(R.id.updateButton)
        updateDesc = findViewById(R.id.updateDesc)
        updateImage = findViewById(R.id.updateImage)
        updateLang = findViewById(R.id.updateLang)
        updateDescTime=findViewById(R.id.updateDescTime)
        updateLangTime=findViewById(R.id.updateLangTime)
        updatePrice=findViewById(R.id.updatePrice)
        updateAvailability=findViewById(R.id.updateAvailability)
        updateTitle = findViewById(R.id.updateTitle)

        val activityResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val data = result.data
                    uri = data?.data
                    updateImage.setImageURI(uri)
                } else {
                    Toast.makeText(this@UpdateActivity, "No Image Selected", Toast.LENGTH_SHORT)
                        .show()
                }
            }

        val bundle = intent.extras
        if (bundle != null) {
            Glide.with(this@UpdateActivity).load(bundle.getString("Image")).into(updateImage)
            updateTitle.setText(bundle.getString("Title"))
            updateDesc.setText(bundle.getString("Description"))
            updateLang.setText(bundle.getString("Language"))
            updateDescTime.setText(bundle.getString("DesTime"))
            updateLangTime.setText((bundle.getString("arrTime")))
            updatePrice.setText((bundle.getString("Price")))
            updateAvailability.setText((bundle.getString("availability")))
            key = bundle.getString("Key") ?: ""
            oldImageURL = bundle.getString("Image") ?: ""
        }

        databaseReference =
            FirebaseDatabase.getInstance().getReference("Android Tutorials").child(key)

        updateImage.setOnClickListener {
            val photoPicker = Intent(Intent.ACTION_PICK)
            photoPicker.type = "image/*"
            activityResultLauncher.launch(photoPicker)
        }

        updateButton.setOnClickListener {
            saveData()
            val intent = Intent(this@UpdateActivity, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun saveData() {
        val builder = AlertDialog.Builder(this@UpdateActivity)
        builder.setCancelable(false)
        builder.setView(R.layout.progress_layout)
        val dialog = builder.create()
        dialog.show()

        if (uri != null) {
            storageReference =
                FirebaseStorage.getInstance().getReference().child("Android Images")
                    .child(uri?.lastPathSegment ?: "")

            storageReference.putFile(uri!!).addOnSuccessListener { taskSnapshot ->
                val uriTask: Task<Uri> = taskSnapshot.storage.downloadUrl
                while (!uriTask.isComplete);
                val urlImage: Uri = uriTask.result!!
                imageUrl = urlImage.toString()
                updateData()
                dialog.dismiss()
            }.addOnFailureListener { e ->
                dialog.dismiss()
                // Handle the failure, for example, show an error message
                Toast.makeText(
                    this@UpdateActivity,
                    "Failed to upload image: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        } else {
            // No image selected, just update other information
            updateData()
            dialog.dismiss()
        }
    }



    private fun updateData() {
        title = updateTitle.text.toString().trim()
        desc = updateDesc.text.toString().trim()
        lang = updateLang.text.toString()
        desctime=updateDescTime.text.toString()
        langtime=updateLangTime.text.toString()
        price=updatePrice.text.toString()
        availability=updateAvailability.text.toString()

        val dataClass = DataClass(title, desc, lang,desctime,langtime,price,availability, imageUrl)

        databaseReference.setValue(dataClass)
            .addOnCompleteListener { task: Task<Void?> ->
                if (task.isSuccessful) {
                    val reference: StorageReference =
                        FirebaseStorage.getInstance().getReferenceFromUrl(oldImageURL)
                    reference.delete()
                    Toast.makeText(this@UpdateActivity, "Updated", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
            .addOnFailureListener { e: Exception ->
                Toast.makeText(this@UpdateActivity, e.message.toString(), Toast.LENGTH_SHORT)
                    .show()
            }
    }
}
