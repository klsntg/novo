package com.example.ics26011finalproject

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView


class UserFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val firstName = arguments?.getString("FIRST_NAME") ?: ""
        val lastName = arguments?.getString("LAST_NAME") ?: ""
        val email = arguments?.getString("EMAIL") ?: ""


        val tvName = view.findViewById<TextView>(R.id.tvName)
        val tvEmail = view.findViewById<TextView>(R.id.tvEmail)
        val btnLogout = view.findViewById<Button>(R.id.btnLogout)

        tvName.text = "$firstName $lastName"
        tvEmail.text = email

        btnLogout.setOnClickListener {
            val intent = Intent(requireContext(), WelcomeActivity::class.java)
            startActivity(intent)
        }

    }

}