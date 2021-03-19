package com.example.movies.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.movies.data.Movie
import com.example.movies.adaptors.MoviesAdapter
import com.example.movies.data.JsonMovieRepository
import com.example.movies.R
import kotlinx.coroutines.*

class FragmentMoviesList(context: Context) : Fragment() {

    private var onItemClickListener: OnItemClickListener? = null
    private val moviesRepo = JsonMovieRepository(context)
    private val handlerException = CoroutineExceptionHandler { coroutineContext, throwable ->
        Log.d("SCOPE_EXCEPTION", "${throwable.message}, $throwable")
    }
    private val scope = CoroutineScope(Dispatchers.IO + handlerException)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val list = view.findViewById<RecyclerView>(R.id.moviesList)

        scope.launch{
            val movies = moviesRepo.loadMovies()
            val adapter = context?.let {
                MoviesAdapter(it, movies, onItemClickListener)
            }
            list.adapter = adapter
        }
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_movies_list, container, false)


    interface OnItemClickListener{
        fun onItemClicked(movie: Movie)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is OnItemClickListener){
            onItemClickListener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        onItemClickListener = null
    }
}