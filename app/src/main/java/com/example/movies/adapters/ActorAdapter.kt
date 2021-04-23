package com.example.movies.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.movies.R
import com.example.movies.database.ActorEntity
import com.example.movies.databinding.ViewHolderActorBinding

class ActorAdapter(
    var actors: List<ActorEntity>,
    private val baseImageUrl: String
) : RecyclerView.Adapter<ActorViewHolder>()
{

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ActorViewHolder {
        val binding = ViewHolderActorBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ActorViewHolder(binding)
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
    private val binding: ViewHolderActorBinding
) : RecyclerView.ViewHolder(binding.root){

    fun bind(
        actor: ActorEntity,
        baseImageUrl: String
    ){
        binding.avatar.load(baseImageUrl
                    + "original"
                    + actor.imageUrl
        ) {
            placeholder(R.drawable.ic_actors_avatar_missing)
            error(R.drawable.ic_actors_avatar_missing)
        }

        binding.name.text = actor.name
    }
}