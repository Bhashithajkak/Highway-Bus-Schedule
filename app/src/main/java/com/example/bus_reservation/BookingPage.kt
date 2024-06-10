package com.example.bus_reservation


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.auth.FirebaseAuth


class BookingPage : AppCompatActivity() {

    private lateinit var saveButton: Button
    private lateinit var uploadName: EditText
    private lateinit var uploadEmail: EditText
    private lateinit var uploadPhoneNumber: EditText
    private lateinit var uploadFrom: EditText
    private lateinit var uploadTo: EditText
    private lateinit var uploadBusName: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking_page)
        supportActionBar?.hide()

        uploadName = findViewById(R.id.uploadName)
        uploadEmail = findViewById(R.id.uploadEmail)
        uploadPhoneNumber = findViewById(R.id.uploadPhoneNumber)
        uploadFrom = findViewById(R.id.uploadFrom)
        uploadTo = findViewById(R.id.uploadTo)
        uploadBusName = findViewById(R.id.uploadBusName)
        saveButton = findViewById(R.id.saveButtonBooking)

        saveButton.setOnClickListener {
            saveData()
        }




    }

    private fun saveData() {
        val name: String = uploadName.text.toString()
        val email: String = uploadEmail.text.toString()
        val phonenumber: String = uploadPhoneNumber.text.toString()
        val from: String = uploadFrom.text.toString()
        val to: String = uploadTo.text.toString()
        val busname: String = uploadBusName.text.toString()

        // Get the current authenticated user UID
        val currentUserUID = FirebaseAuth.getInstance().currentUser?.uid

        if (currentUserUID != null) {
            val bookingClass = BookingClass(name, email, phonenumber, from, to, busname)

            // Use push() to generate a unique key for each entry
            val entryKey = FirebaseDatabase.getInstance().getReference("booking details")
                .child("users")
                .child(currentUserUID)
                .push()
                .key

            if (entryKey != null) {
                // Save data with the generated key
                FirebaseDatabase.getInstance().getReference("booking details")
                    .child("users")
                    .child(currentUserUID)
                    .child(entryKey)
                    .setValue(bookingClass)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this@BookingPage, "Saved data", Toast.LENGTH_SHORT).show()
                            Log.e("booking data", bookingClass.toString())
                            finish()
                        }
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(this@BookingPage, e.message.toString(), Toast.LENGTH_SHORT).show()
                    }
            }
        } else {
            // Handle the case where the current user is null (not authenticated)
            Toast.makeText(this@BookingPage, "User not authenticated", Toast.LENGTH_SHORT).show()
        }
    }



}
