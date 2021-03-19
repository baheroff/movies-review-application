package com.example.movies.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movies.adaptors.ActorAdapter
import com.example.movies.data.Movie
import com.example.movies.R

class FragmentMovieDetails(private val movie: Movie) : Fragment() {

    private var backTransaction: BackTransaction? = null

    private val actors = movie.actors

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val backgroundPicture: ImageView = view.findViewById(R.id.back_pict)
        val age: TextView = view.findViewById(R.id.age)
        val title: TextView = view.findViewById(R.id.film)
        val genres: TextView = view.findViewById(R.id.tag)
        val reviews: TextView = view.findViewById(R.id.movie_reviews)
        val description: TextView = view.findViewById(R.id.description)

        Glide.with(this.context)
                .load(movie.detailImageUrl)
                .into(backgroundPicture)
        title.text = movie.title
        genres.text = movie.genres.joinToString { it.name }
        reviews.text = getString(R.string.movie_num_reviews, movie.reviewCount)
        description.text = movie.storyLine

        view.findViewById<TextView>(R.id.back).apply {
            setOnClickListener {
                backTransaction?.backToMoviesList()
            }
        }

        val list = view.findViewById<RecyclerView>(R.id.actors_list)
        val adapter = context?.let { ActorAdapter(it, actors) }
        list.adapter = adapter
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_movie_details, container, false)


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is BackTransaction){
            backTransaction = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        backTransaction = null
    }

    interface BackTransaction{
        fun backToMoviesList()
    }

}