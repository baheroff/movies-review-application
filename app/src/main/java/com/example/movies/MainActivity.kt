package com.example.movies

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelStoreOwner
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
            FragmentMoviesList().apply {
                supportFragmentManager.beginTransaction()
                        .add(R.id.main_container, this, MOVIES_LIST)
                        .commit()
            }
        }
    }

    override fun onItemClicked() {
        FragmentMovieDetails().apply {
            supportFragmentManager.beginTransaction()
                    .add(R.id.main_container, this, MOVIE_DETAILS_TAG)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .addToBackStack(MOVIE_DETAILS_TAG)
                    .commit()
        }
    }

    override fun backToMoviesList() {
        supportFragmentManager.popBackStack()
    }
}