package com.example.ics26011finalproject

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

class BookDetailsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_book_details, container, false)

        val btnAddtoLibrary = view.findViewById<Button>(R.id.btnAddtoLibrary)
        btnAddtoLibrary.setOnClickListener {
            val intent = Intent(activity, HomeFragment::class.java)
            startActivity(intent)
        }

        return view
    }
}
