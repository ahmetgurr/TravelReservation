package com.example.travelreservation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.travelreservation.databinding.ActivityLoginBinding
import com.example.travelreservation.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        auth = Firebase.auth

    }

    fun signUpClicked(view: View) {
        val email = binding.emailText.text.toString()
        val password = binding.passwordText.text.toString()

        if (email.equals("") || password.equals("")) {
            // If email or password is empty, show error message
            Toast.makeText(this, "Email and password are required!", Toast.LENGTH_LONG).show()
        } else {
            // If email and password are not empty, try to login
            auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
                // If login is successful, go to FeedActivity
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }.addOnFailureListener { exception ->
                // If login is not successful, show error message
                Toast.makeText(this, exception.localizedMessage, Toast.LENGTH_LONG).show()
            }
        }
    }

    fun goBack(view: View){
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }



}