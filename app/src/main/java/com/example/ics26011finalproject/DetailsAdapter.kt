package com.example.ics26011finalproject
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class DetailsAdapter(private val onItemClick: (Details) -> Unit) : RecyclerView.Adapter<DetailsAdapter.DetailsViewHolder>() {

    private var books: List<Details> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_list, parent, false)
        return DetailsViewHolder(view)
    }

    override fun onBindViewHolder(holder: DetailsViewHolder, position: Int) {
        val currentBook = books[position]
        holder.bind(currentBook)

        holder.itemView.setOnClickListener{
            onItemClick(currentBook)
        }
    }

    override fun getItemCount(): Int = books.size

    fun setBooks(books: List<Details>) {
        this.books = books
        notifyDataSetChanged()


    }

    class DetailsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.tvTitle)
        private val authorTextView: TextView = itemView.findViewById(R.id.tvAuthor)
        private val coverImageView: ImageView = itemView.findViewById(R.id.cover)

        fun bind(details: Details) {
            titleTextView.text = details.title
            authorTextView.text = details.author

            Glide.with(itemView.context)
                .load(getImageResource(details.imageSource)) // Assuming details.imageSource is a resource name (e.g., "drawable/book1")
                .placeholder(R.drawable.logo) // Placeholder image while loading
                .error(R.drawable.logo) // Error image if loading fails
                .into(coverImageView)
        }

        private fun getImageResource(imageSource: String): Any {
            return itemView.context.resources.getIdentifier(imageSource, "drawable", itemView.context.packageName)
        }
    }
}