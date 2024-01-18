package com.example.bus_reservation

import android.app.Activity
import android.app.AlertDialog
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
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import java.text.DateFormat
import java.util.*

class UploadActivity : AppCompatActivity() {

    private lateinit var uploadImage: ImageView
    private lateinit var saveButton: Button
    private lateinit var uploadTopic: EditText
    private lateinit var uploadDesc: EditText
    private lateinit var uploadLang: EditText
    private lateinit var uploadDescTime:EditText
    private lateinit var uploadLangTime:EditText
    private lateinit var uploadPrice:EditText
    private lateinit var uploadAvailability:EditText
    private var imageURL: String = ""
    private var uri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload)

        uploadImage = findViewById(R.id.uploadImage)
        uploadDesc = findViewById(R.id.uploadDesc)
        uploadTopic = findViewById(R.id.uploadTopic)
        uploadLang = findViewById(R.id.uploadLang)
        uploadDescTime=findViewById(R.id.uploadDescTime)
        uploadLangTime=findViewById(R.id.uploadLangTime)
        uploadPrice=findViewById((R.id.uploadPrice))
        uploadAvailability=findViewById(R.id.uploadAvailability)
        saveButton = findViewById(R.id.saveButton)

        val activityResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val data: Intent? = result.data
                    uri = data?.data
                    uploadImage.setImageURI(uri)
                } else {
                    Toast.makeText(this@UploadActivity, "No Image Selected", Toast.LENGTH_SHORT).show()
                }
            }

        uploadImage.setOnClickListener {
            val photoPicker = Intent(Intent.ACTION_PICK)
            photoPicker.type = "image/*"
            activityResultLauncher.launch(photoPicker)
        }

        saveButton.setOnClickListener {
            saveData()
        }
    }

    private fun saveData() {
        val storageReference: StorageReference = FirebaseStorage.getInstance().getReference().child("Android Images")
            .child(uri?.lastPathSegment?:"")

        val builder = AlertDialog.Builder(this@UploadActivity)
        builder.setCancelable(false)
        builder.setView(R.layout.progress_layout)
        val dialog: AlertDialog = builder.create()
        dialog.show()

        storageReference.putFile(uri!!).addOnSuccessListener(OnSuccessListener<UploadTask.TaskSnapshot> { taskSnapshot ->
            val uriTask: Task<Uri> = taskSnapshot.storage.downloadUrl
            while (!uriTask.isComplete);
            val urlImage: Uri = uriTask.result!!
            imageURL = urlImage.toString()
            uploadData()
            dialog.dismiss()
        }).addOnFailureListener(OnFailureListener { e ->
            dialog.dismiss()
        })
    }

    private fun uploadData() {
        val title: String = uploadTopic.text.toString()
        val desc: String = uploadDesc.text.toString()
        val lang: String = uploadLang.text.toString()
        val desctime: String=uploadDescTime.text.toString()
        val langtime: String=uploadLangTime.text.toString()
        val price: String=uploadPrice.text.toString()
        val availability: String=uploadAvailability.text.toString()

        val dataClass = DataClass(title, desc, lang,desctime,langtime,price,availability, imageURL)

        // We are changing the child from title to currentDate,
        // because we will be updating title as well and it may affect child value.

        val currentDate: String = DateFormat.getDateTimeInstance().format(Calendar.getInstance().time)

        FirebaseDatabase.getInstance().getReference("Android Tutorials").child(currentDate)
            .setValue(dataClass).addOnCompleteListener(OnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this@UploadActivity, "Saved", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }).addOnFailureListener(OnFailureListener { e ->
                Toast.makeText(this@UploadActivity, e.message.toString(), Toast.LENGTH_SHORT).show()
            })
    }
}
