package com.example.bus_reservation


import android.content.Intent
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.bus_reservation.databinding.ActivityLoginBinding
import com.example.bus_reservation.MainActivity
import com.example.bus_reservation.SignupActivity

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding=ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth=FirebaseAuth.getInstance()

        binding.loginButton.setOnClickListener{
            val email=binding.loginEmail.text.toString()
            val password=binding.loginPassword.text.toString()

            if(email.isNotEmpty() && password.isNotEmpty()){
                firebaseAuth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Check if the logged-in user is an admin or a regular user
                            val currentUser = firebaseAuth.currentUser
                            if (currentUser != null) {
                                checkUserRole(currentUser.email ?: "")
                            }
                        } else {
                            Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show()
                        }
                    }
            }else{
                Toast.makeText(this,"please enter email and password",Toast.LENGTH_SHORT).show()

            }
        }





        binding.signupText.setOnClickListener{
            startActivity(Intent(this,SignupActivity::class.java))
            finish()
        }


    }

    private fun compareEmail(email: EditText){
        if(email.text.toString().isEmpty()){
            return
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email.text.toString()).matches()){
            return
        }
        firebaseAuth.sendPasswordResetEmail(email.text.toString()).addOnCompleteListener{task ->
            if(task.isSuccessful)
            {
                Toast.makeText(this,"cheak you email" ,Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun checkUserRole(email: String) {

        val isAdmin = email.equals("admin12345@gmail.com", ignoreCase = true)

        if (isAdmin) {
            startActivity(Intent(this, AdminDashbord::class.java))
        } else {

            startActivity(Intent(this,UserBord::class.java))
        }
        finish()
    }





}