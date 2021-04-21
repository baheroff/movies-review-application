package com.example.movies

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.findNavController
import com.example.movies.data.Genre
import com.example.movies.data.Movie
import com.example.movies.fragments.FragmentMovieDetails
import com.example.movies.fragments.FragmentMoviesList
import com.example.movies.fragments.FragmentMoviesListDirections

class MainActivity : AppCompatActivity(),
                     FragmentMoviesList.OnItemClickListener,
                     FragmentMovieDetails.BackTransaction
{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onItemClicked(
        movieId: Long?
    ) {
        findNavController(R.id.myNavHostFragment)
            .navigate(FragmentMoviesListDirections.actionFragmentMoviesListToFragmentMovieDetails(
                checkNotNull(movieId)
            ))
    }

    override fun backToMoviesList() {
        findNavController(R.id.myNavHostFragment).navigateUp()
    }
}