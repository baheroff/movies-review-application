package com.example.movies

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentTransaction
import com.example.movies.fragments.FragmentMovieDetails
import com.example.movies.fragments.FragmentMoviesList

const val MOVIE_DETAILS_TAG = "AVENGERS"
const val MOVIES_LIST_TAG = "MOVIES"

class MainActivity : AppCompatActivity(),
                     FragmentMoviesList.OnItemClickListener,
                     FragmentMovieDetails.BackTransaction
{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.main_container, FragmentMoviesList.newInstance(), MOVIES_LIST_TAG)
                .commit()
        }
    }

    override fun onItemClicked(
        movieId: Long?
    ) {
        if (movieId != null) {
            supportFragmentManager.beginTransaction()
                .add(
                    R.id.main_container,
                    FragmentMovieDetails.newInstance(movieId),
                    MOVIE_DETAILS_TAG
                )
                .addToBackStack(MOVIE_DETAILS_TAG)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit()
        }
    }

    override fun backToMoviesList() {
        supportFragmentManager.popBackStack()
    }
}