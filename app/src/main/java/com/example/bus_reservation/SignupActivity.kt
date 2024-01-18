package com.example.bus_reservation

import com.example.bus_reservation.databinding.ActivitySignupBinding



import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

import com.example.bus_reservation.LoginActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth

class SignupActivity : AppCompatActivity() {


    private lateinit var binding: ActivitySignupBinding
    private lateinit var firebaseAuth:FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding=ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth=FirebaseAuth.getInstance()
        val signButton = findViewById<Button>(R.id.signupButton)

        signButton.setOnClickListener{
            val email=binding.signupEmail.text.toString()
            val password=binding.signupPassword.text.toString()
            val confirmPassword=binding.signupConfirm.text.toString()

            if(email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty())
            {
                if(password==confirmPassword)
                {
                    firebaseAuth.createUserWithEmailAndPassword(email,password)
                        .addOnCompleteListener{
                            if(it.isSuccessful)
                            {
                                Toast.makeText(this,"Sign Up Successfull" ,Toast.LENGTH_SHORT).show()
                                val intent=Intent(  this@SignupActivity,LoginActivity::class.java)
                                startActivity(intent)

                            }
                            else
                            {
                                Toast.makeText(this,it.exception.toString(),Toast.LENGTH_SHORT).show()
                            }
                        }

                }else{
                    Toast.makeText(this,"Password does not matched" ,Toast.LENGTH_SHORT).show()
                }


            }else{
                Toast.makeText(this,"Enter Email and password",Toast.LENGTH_SHORT).show()
            }
        }
        binding.loginText.setOnClickListener{
            val loginIntent=Intent(this,LoginActivity::class.java)
            startActivity(loginIntent)

        }


    }


}