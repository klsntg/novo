package com.example.ics26011finalproject

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
<<<<<<< HEAD
import android.widget.Button
import android.widget.TextView
=======
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
>>>>>>> 960c0c65b00dec705ae204f51dfea6753b7a0044

class BookDetailsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_book_details, container, false)
<<<<<<< HEAD

        val btnAddtoLibrary = view.findViewById<Button>(R.id.btnAddtoLibrary)
        btnAddtoLibrary.setOnClickListener {
            val intent = Intent(activity, HomeFragment::class.java)
            startActivity(intent)
        }

        return view
    }
}
=======

        // Retrieve selected book details from arguments
        val selectedBook = arguments?.getSerializable("selectedBook") as? Details

        // Check if selectedBook is not null
        if (selectedBook != null) {
            // Access views in your layout and set their values using selectedBook
            view.findViewById<TextView>(R.id.tvTitle).text = selectedBook.title
            view.findViewById<TextView>(R.id.tvAuthor).text = selectedBook.author
            view.findViewById<TextView>(R.id.tvCategory).text = getCategoryName(selectedBook.categoryId)
            view.findViewById<TextView>(R.id.tvCategory).text = getCategoryName(selectedBook.categoryId)
            view.findViewById<TextView>(R.id.tvRating).text = selectedBook.rating
            view.findViewById<TextView>(R.id.tvPrice).text = selectedBook.price
            view.findViewById<TextView>(R.id.tvDescription).text = selectedBook.description

            // Example using Glide for loading the cover image
            Glide.with(requireContext())
                .load(getImageResource(selectedBook.imageSource))
                .placeholder(R.drawable.logo)
                .error(R.drawable.logo)
                .into(view.findViewById<ImageView>(R.id.cover))
        }

        return view
    }

    private fun getImageResource(imageSource: String): Any {
        return resources.getIdentifier(imageSource, "drawable", requireContext().packageName)
    }
    private fun getCategoryName(categoryId: Int): String {
        // Assuming you have a method to retrieve category name by ID in DatabaseHandler
        return DatabaseHandler(requireContext()).getCategoryNameById(categoryId)
    }
}
>>>>>>> 960c0c65b00dec705ae204f51dfea6753b7a0044
