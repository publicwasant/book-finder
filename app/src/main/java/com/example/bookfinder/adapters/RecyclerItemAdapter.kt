package com.example.bookfinder.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bookfinder.R
import com.example.bookfinder.models.BookListModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.recycler_item.view.*

class RecyclerItemAdapter(
    private val context: Context,
    private val data: BookListModel
): RecyclerView.Adapter<RecyclerItemAdapter.ViewHolder>() {
    class ViewHolder(
        private val v: View
    ): RecyclerView.ViewHolder(v) {
        val cvBook: CardView = this.v.cv_book
        val tvTitle: TextView = this.v.tv_title
        val tvPublisher: TextView = this.v.tv_publisher
        val tvDescription: TextView = this.v.tv_description
        val ivImage: ImageView = this.v.iv_image
    }

    private var task: ((v: View) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater
            .from(this.context)
            .inflate(R.layout.recycler_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = this.data.items[position]

        holder.cvBook.setOnClickListener {
            this.task?.let {
                it(holder.cvBook)
            }
        }

        holder.tvTitle.text = item.volumeInfo.title
        holder.tvPublisher.text = "Publisher: ${item.volumeInfo.publisher}"
        holder.tvDescription.text = "Description: ${item.volumeInfo.description}"

        Glide.with(this.context)
            .load(item.volumeInfo.imageLinks.smallThumbnail)
            .centerCrop()
            .into(holder.ivImage)
    }

    override fun getItemCount(): Int {
        return this.data.items.size
    }

    fun setOnClickItems(t: (v: View) -> Unit) {
        this.task = t
    }
}