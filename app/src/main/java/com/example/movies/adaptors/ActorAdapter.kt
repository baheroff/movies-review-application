package com.example.movies.adaptors

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movies.data.Actor
import com.example.movies.R

class ActorAdapter(context: Context,
                   var actors: List<Actor>
) : RecyclerView.Adapter<ActorViewHolder>()
{

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorViewHolder {
        return ActorViewHolder(inflater.inflate(R.layout.view_holder_actor, parent, false))
    }

    override fun onBindViewHolder(holder: ActorViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemCount(): Int = actors.size

    private fun getItem(position: Int) = actors[position]

}

class ActorViewHolder(view: View) : RecyclerView.ViewHolder(view){

    private val avatar: ImageView = view.findViewById(R.id.avatar)
    private val name: TextView = view.findViewById(R.id.name)

    fun bind(actor: Actor){
        Glide.with(context)
                .load(actor.imageUrl)
                .into(avatar)
        name.text = actor.name
    }
}

private val RecyclerView.ViewHolder.context
    get() = this.itemView.context