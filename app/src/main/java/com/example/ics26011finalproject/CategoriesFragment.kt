package com.example.ics26011finalproject

import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView


class CategoriesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_categories, container, false)

        view.findViewById<CardView>(R.id.cvNonFic).setOnClickListener {
            navigateToListFragment(getCategoryId("Non-Fiction"))
        }

        view.findViewById<CardView>(R.id.cvClassics).setOnClickListener {
            navigateToListFragment(getCategoryId("Classics"))
        }

        view.findViewById<CardView>(R.id.cvFantasy).setOnClickListener {
            navigateToListFragment(getCategoryId("Fantasy"))
        }

        view.findViewById<CardView>(R.id.cvYoungAdult).setOnClickListener {
            navigateToListFragment(getCategoryId("Young Adult"))
        }

        view.findViewById<CardView>(R.id.cvCrime).setOnClickListener {
            navigateToListFragment(getCategoryId("Crime"))
        }

        view.findViewById<CardView>(R.id.cvHorror).setOnClickListener {
            navigateToListFragment(getCategoryId("Horror"))
        }

        view.findViewById<CardView>(R.id.cvSciFi).setOnClickListener {
            navigateToListFragment(getCategoryId("Sci-Fi"))
        }

        view.findViewById<CardView>(R.id.cvDrama).setOnClickListener {
            navigateToListFragment(getCategoryId("Drama"))
        }

        return view
    }

    private fun navigateToListFragment(categoryId: Int) {
        val listFragment = ListFragment()
        val bundle = Bundle().apply {
            putInt("categoryId", categoryId)
        }
        listFragment.arguments = bundle

        fragmentManager?.beginTransaction()
            ?.replace(R.id.frame_main, listFragment)
            ?.addToBackStack(null)
            ?.commit()
    }

    private fun getCategoryId(categoryName: String): Int {
        return DatabaseHandler(requireContext()).getCategoryID(categoryName, readableDatabase)
    }

    private val readableDatabase: SQLiteDatabase
        get() = DatabaseHandler(requireContext()).readableDatabase
}
