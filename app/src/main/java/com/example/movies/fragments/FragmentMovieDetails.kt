package com.example.movies.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movies.adaptors.ActorAdapter
import com.example.movies.data.Movie
import com.example.movies.R
import com.example.movies.data.Movies
import com.example.movies.models.MoviesDetailsViewModel

class FragmentMovieDetails(
    private val movie: Movie
) : Fragment() {

    private var backTransaction: BackTransaction? = null
    private val viewModel: MoviesDetailsViewModel = MoviesDetailsViewModel()

    private var backgroundPicture: ImageView? = null
    private var age: TextView? = null
    private var title: TextView? = null
    private var genres: TextView? = null
    private var reviews: TextView? = null
    private var description: TextView? = null
    private var recyclerActors: RecyclerView? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews(view)
        defineView()
        setUpActorsAdapter()
        setUpListeners()

        viewModel.eventBackPressed.observe(this.viewLifecycleOwner, this::goToMainScreen)
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
        backgroundPicture = null
        age = null
        title = null
        genres = null
        reviews = null
        description = null
        recyclerActors?.adapter = null
        recyclerActors = null
    }

    private fun initViews(view: View) {
        backgroundPicture = view.findViewById(R.id.back_pict)
        age = view.findViewById(R.id.age)
        title = view.findViewById(R.id.film)
        genres = view.findViewById(R.id.tag)
        reviews = view.findViewById(R.id.movie_reviews)
        description = view.findViewById(R.id.description)
        recyclerActors = view.findViewById(R.id.actors_list)
    }

    private fun defineView() {
        Glide.with(this.context)
            .load(movie.detailImageUrl)
            .into(backgroundPicture)
        title?.text = movie.title
        genres?.text = movie.genres.joinToString { it.name }
        reviews?.text = getString(R.string.movie_num_reviews, movie.reviewCount)
        description?.text = movie.storyLine
    }

    private fun setUpActorsAdapter() {
        recyclerActors?.adapter = context?.let {
            ActorAdapter(it, movie.actors)
        }
    }

    private fun setUpListeners() {
        view?.findViewById<TextView>(R.id.back)?.apply {
            setOnClickListener {
                viewModel.onBackPressed()
            }
        }
    }

    private fun goToMainScreen(isPressed: Boolean) {
        if (isPressed) {
            backTransaction?.backToMoviesList()
            viewModel.onBackPressedComplete()
        }
    }

    interface BackTransaction{
        fun backToMoviesList()
    }

}