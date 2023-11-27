package com.example.ics26011finalproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        val btnRegister = findViewById<Button>(R.id.btnRegister)
        val btnSignIn = findViewById<TextView>(R.id.btnSignIn)

        btnRegister.setOnClickListener {

            val i = Intent(this, RegisterActivity::class.java)
            startActivity(i)

        }
        btnSignIn.setOnClickListener {

            val i = Intent(this, SignInActivity::class.java)
            startActivity(i)

        }
    }
}