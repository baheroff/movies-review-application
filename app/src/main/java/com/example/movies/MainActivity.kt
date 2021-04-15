package com.example.movies

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelStoreOwner
import com.example.movies.data.Genre
import com.example.movies.data.Movie
import com.example.movies.fragments.FragmentMovieDetails
import com.example.movies.fragments.FragmentMoviesList

class MainActivity : AppCompatActivity(),
                     FragmentMoviesList.OnItemClickListener,
                     FragmentMovieDetails.BackTransaction
{

    companion object{
        const val MOVIE_DETAILS_TAG = "AVENGERS"
        const val MOVIES_LIST = "MOVIES"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.main_container, FragmentMoviesList.newInstance(), MOVIES_LIST)
                .commit()
        }
    }

    override fun onItemClicked(
        movieId: Long?
    ) {
        Log.e("TAGAT", "MAIN ACTIVITY ${movieId}")
        if (movieId != null) {
            supportFragmentManager.beginTransaction()
                .add(
                    R.id.main_container,
                    FragmentMovieDetails.newInstance(movieId),
                    MOVIE_DETAILS_TAG
                )
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(MOVIE_DETAILS_TAG)
                .commit()
        }
    }

    override fun backToMoviesList() {
        supportFragmentManager.popBackStack()
    }
}