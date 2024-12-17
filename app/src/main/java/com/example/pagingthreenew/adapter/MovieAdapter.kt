package com.example.pagingthreenew.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pagingthreenew.R
import com.example.pagingthreenew.models.Movie

class MovieAdapter :
    PagingDataAdapter<Movie, MovieAdapter.MovieViewHolder>(MovieComparator) {

    object MovieComparator : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean =
            oldItem == newItem
    }

    inner class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.nameFilm)
        val poster: ImageView = view.findViewById(R.id.imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        holder.title.text = movie?.title
        if (!movie?.poster_path.isNullOrEmpty()) {
            Glide.with(holder.itemView.context)
                .load("https://image.tmdb.org/t/p/w500${movie.poster_path}")
                .into(holder.poster)
        } else {
            holder.poster.setImageResource(R.drawable.ic_launcher_background) // Замените на ваше изображение по умолчанию
        }
    }
}