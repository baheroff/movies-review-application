package com.example.movies.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.movies.R
import com.example.movies.database.ActorEntity

class ActorAdapter(
    var actors: List<ActorEntity>,
    private val baseImageUrl: String
) : RecyclerView.Adapter<ActorViewHolder>()
{

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ActorViewHolder {
        return ActorViewHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.view_holder_actor, parent, false))
    }

    override fun onBindViewHolder(
        holder: ActorViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position), baseImageUrl)
    }

    override fun getItemCount(): Int = actors.size

    private fun getItem(position: Int) = actors[position]

}

class ActorViewHolder(
    view: View
) : RecyclerView.ViewHolder(view){

    private val avatar: ImageView = view.findViewById(R.id.avatar)
    private val name: TextView = view.findViewById(R.id.name)

    fun bind(
        actor: ActorEntity,
        baseImageUrl: String
    ){
        avatar.load(baseImageUrl
                    + "original"
                    + actor.imageUrl
        ) {
            placeholder(R.drawable.ic_actors_avatar_missing)
            error(R.drawable.ic_actors_avatar_missing)
        }

        name.text = actor.name
    }
}