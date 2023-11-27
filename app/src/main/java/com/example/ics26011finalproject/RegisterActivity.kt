package com.example.ics26011finalproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class RegisterActivity : AppCompatActivity() {

    private lateinit var dbHandler: DatabaseHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        dbHandler = DatabaseHandler(this)

        val btnRegister = findViewById<Button>(R.id.btnRegister)
        val tvSignIn = findViewById<TextView>(R.id.tvSignIn)

        tvSignIn.setOnClickListener {

            val i = Intent(this, SignInActivity::class.java)
            startActivity(i)
        }

        btnRegister.setOnClickListener {

            fun isValidPassword(password: String): Boolean {
                val passwordRegex = Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d@\$!%*?&]{8,}$")
                return passwordRegex.matches(password)
            }

            val emailEditText = findViewById<EditText>(R.id.email)
            val fnameEditText = findViewById<EditText>(R.id.fname)
            val lnameEditText = findViewById<EditText>(R.id.lname)
            val unameEditText = findViewById<EditText>(R.id.uname)
            val passwEditText = findViewById<EditText>(R.id.password)

            val email = emailEditText.text.toString()
            val fname = fnameEditText.text.toString()
            val lname = lnameEditText.text.toString()
            val uname = unameEditText.text.toString()
            val passw = passwEditText.text.toString()

            if (email.trim().isNotEmpty() && fname.trim().isNotEmpty() && lname.trim().isNotEmpty() &&
                uname.trim().isNotEmpty() && passw.trim().isNotEmpty()) {

                if (isValidPassword(passw)) {

                    val registrationSuccess = dbHandler.registerUser(email, fname, lname, uname, passw)

                    if (registrationSuccess != -1L) {
                        Toast.makeText(this, "Registered Successfully", Toast.LENGTH_LONG).show()

                        val i = Intent(this, SignInActivity::class.java)
                        i.putExtra("USERNAME", uname)
                        i.putExtra("FIRST_NAME", fname)
                        i.putExtra("LAST_NAME", lname)
                        i.putExtra("EMAIL", email)
                        startActivity(i)

                        emailEditText.text.clear()
                        fnameEditText.text.clear()
                        lnameEditText.text.clear()
                        unameEditText.text.clear()
                        passwEditText.text.clear()
                    }

                    else {

                        Toast.makeText(this, "Registration Failed. Please try again.", Toast.LENGTH_LONG).show()
                    }

                }

                else {

                    Toast.makeText(this, "Password must contain a small letter, capital letter, number, and special character. Minimum of 8 characters.", Toast.LENGTH_LONG).show()
                }

            }

            else {

                Toast.makeText(this, "Email, First Name, Last Name, Username, and Password cannot be blank", Toast.LENGTH_LONG).show()
            }
        }
    }
}

