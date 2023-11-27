package com.example.ics26011finalproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class SignInActivity : AppCompatActivity() {

    private lateinit var dbHandler: DatabaseHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        dbHandler = DatabaseHandler(this)

        val btnLogIn = findViewById<Button>(R.id.btnLogIn)
        val tvRegister = findViewById<TextView>(R.id.tvRegister)

        btnLogIn.setOnClickListener {
            val usernameEditText = findViewById<EditText>(R.id.username)
            val passwordEditText = findViewById<EditText>(R.id.password)

            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (username.trim().isNotEmpty() && password.trim().isNotEmpty()) {

                val success = dbHandler.loginUser(username, password)

                if (success) {
                    val user = dbHandler.getUserInfo(username)

                    if (user != null) {
                        Toast.makeText(this, "Login Successful", Toast.LENGTH_LONG).show()

                        val intent = Intent(this, UserProfileActivity::class.java)
                        intent.putExtra("USERNAME", user.username)
                        intent.putExtra("FIRST_NAME", user.firstName)
                        intent.putExtra("LAST_NAME", user.lastName)
                        intent.putExtra("EMAIL", user.email)

                        startActivity(intent)

                        usernameEditText.text.clear()
                        passwordEditText.text.clear()

                        finish()
                    } else {
                        Toast.makeText(this, "User information not found", Toast.LENGTH_LONG).show()
                    }
                } else {
                    Toast.makeText(
                        this,
                        "Login Unsuccessful. Check your credentials.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            } else {
                Toast.makeText(this, "Username or Password cannot be blank", Toast.LENGTH_LONG)
                    .show()
            }
        }

        tvRegister.setOnClickListener {

            val i = Intent(this, RegisterActivity::class.java)
            startActivity(i)

        }

        btnLogIn.setOnClickListener {

            val i = Intent(this, NavbarActivity::class.java)
            startActivity(i)

        }
    }
}

