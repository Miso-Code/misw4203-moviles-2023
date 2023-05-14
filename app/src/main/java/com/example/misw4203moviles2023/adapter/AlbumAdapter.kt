package com.example.misw4203moviles2023.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.misw4203moviles2023.R
import com.example.misw4203moviles2023.data.model.AlbumModel
import com.example.misw4203moviles2023.databinding.AlbumRowBinding
import com.example.misw4203moviles2023.domain.album.model.Album

interface OnItemClickListener {
    fun onItemClick(position: Int, album: Album)
}

class AlbumAdapter(private val context: Context, private val albumList: List<Album>) :
    RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder>() {

    private var onItemClickListener: OnItemClickListener? = null

    inner class AlbumViewHolder(private val binding: AlbumRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(album: Album) {
            binding.albumName.text = album.name
            binding.albumReleaseDate.text = album.releaseDate
            Glide.with(context).load(album.cover).placeholder(R.drawable.ic_album)
                .into(binding.albumImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val binding = AlbumRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AlbumViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        val album = albumList[position]
        holder.bind(album)
        holder.itemView.setOnClickListener {
            onItemClickListener?.onItemClick(position, album)
        }
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        onItemClickListener = listener
    }

    override fun getItemCount() = albumList.size
}
