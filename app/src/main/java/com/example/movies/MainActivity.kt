package com.example.movies

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentTransaction
import com.example.movies.fragments.FragmentMovieDetails
import com.example.movies.fragments.FragmentMoviesList

private const val MOVIE_DETAILS_TAG = "com.example.movies.TAG_DETAILS"
private const val MOVIES_LIST_TAG = "com.example.movies.TAG_LIST"

class MainActivity : AppCompatActivity(),
    FragmentMoviesList.OnItemClickListener,
    FragmentMovieDetails.BackTransaction {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.takeIf { savedInstanceState == null }?.beginTransaction()
            ?.add(R.id.main_container, FragmentMoviesList.newInstance(), MOVIES_LIST_TAG)
            ?.commit()
    }

    override fun onItemClicked(
        movieId: Long?
    ) {
        movieId?.let {
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