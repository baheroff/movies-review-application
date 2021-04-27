package com.example.movies.adapters

import androidx.recyclerview.widget.DiffUtil
import com.example.movies.database.MovieEntity

class MoviesDiffUtilCallback(
    private val oldList: List<MovieEntity>,
    private val newList: List<MovieEntity>
) : DiffUtil.Callback(){

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].id == newList[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition] == newList[newItemPosition]
}