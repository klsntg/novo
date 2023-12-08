package com.example.ics26011finalproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class ListFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: DetailsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list, container, false)

        recyclerView = view.findViewById(R.id.rvlist)
        adapter = DetailsAdapter{selectedBook ->
            val bookDetailsFragment = BookDetailsFragment()
            val bundle = Bundle()
            bundle.putSerializable("selectedBook", selectedBook)
            bookDetailsFragment.arguments = bundle

            val fragmentTransaction = parentFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.frame_main, bookDetailsFragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()

        }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val categoryId = arguments?.getInt("categoryId") ?: -1
        if (categoryId != -1) {
            val books = DatabaseHandler(requireContext()).getBooksByCategory(categoryId)
            adapter.setBooks(books)
        }

        return view
    }
}
