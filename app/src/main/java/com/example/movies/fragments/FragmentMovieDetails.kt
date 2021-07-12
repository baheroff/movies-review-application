package com.example.movies.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.*
import coil.load
import com.example.movies.adapters.ActorAdapter
import com.example.movies.R
import com.example.movies.database.entities.ActorEntity
import com.example.movies.database.entities.MovieEntity
import com.example.movies.databinding.FragmentMovieDetailsBinding
import com.example.movies.viewmodels.MoviesDetailsViewModel
import com.example.movies.viewmodels.ViewModelFactory

class FragmentMovieDetails : Fragment() {

    private lateinit var binding: FragmentMovieDetailsBinding /////

    private var backTransaction: BackTransaction? = null

    private val viewModel: MoviesDetailsViewModel by viewModels {
        ViewModelFactory(arguments?.getLong("id"))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        setUpListeners()

        viewModel.movie.observe(viewLifecycleOwner, ::defineViewsContent)
        viewModel.actorsList.observe(viewLifecycleOwner, ::setUpActorsAdapter)
        viewModel.eventBackPressed.observe(viewLifecycleOwner, ::goToMainScreen)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BackTransaction) {
            backTransaction = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        backTransaction = null
    }

    private fun defineViewsContent(movie: MovieEntity) {

        val starViews = arrayOf(
            binding.star1,
            binding.star2,
            binding.star3,
            binding.star4,
            binding.star5,
        )

        binding.backPicture.load(
            viewModel.baseImageUrl
                    + "original"
                    + (movie.detailImageUrl ?: movie.imageUrl)
        ) {
            placeholder(R.drawable.loading_animation)
        }

        for (count in starViews.indices) {
            if (count < movie.rating) {
                starViews[count].load(R.drawable.ic_star_icon)
            } else {
                starViews[count].load(R.drawable.ic_star_icon_dark)
            }
        }

        binding.age.text = if (movie.isAdult) "16+" else "13+"
        binding.filmTitle.text = movie.title
        binding.movieGenres.text = movie.genres
        binding.movieReviews.text = getString(
            R.string.movie_num_reviews,
            movie.reviewCount
        )
        binding.description.text = movie.storyline
    }

    private fun setUpActorsAdapter(actors: List<ActorEntity>) {
        binding.recyclerActorsList.adapter = ActorAdapter(actors, viewModel.baseImageUrl)
    }

    private fun setUpListeners() {
        binding.back.setOnClickListener {
            viewModel.onBackPressed()
        }
    }

    private fun goToMainScreen(isPressed: Boolean) {
        if (isPressed) {
            backTransaction?.backToMoviesList()
            viewModel.onBackPressedComplete()
        }
    }

    interface BackTransaction {
        fun backToMoviesList()
    }

    companion object {
        fun newInstance(movieId: Long): FragmentMovieDetails =
            FragmentMovieDetails().apply {
                arguments = Bundle().apply {
                    putLong("id", movieId)
                }
            }
    }
}