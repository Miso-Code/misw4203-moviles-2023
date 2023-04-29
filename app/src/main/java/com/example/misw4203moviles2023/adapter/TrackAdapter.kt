package com.example.misw4203moviles2023.adapter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.misw4203moviles2023.data.model.TrackModel
import com.example.misw4203moviles2023.databinding.TrackRowBinding

class TrackAdapter(private val trackList: List<TrackModel>) :
    RecyclerView.Adapter<TrackAdapter.TrackViewHolder>() {

    inner class TrackViewHolder(private val binding: TrackRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(track: TrackModel) {
            binding.trackName.text = track.name
            binding.trackDuration.text = track.duration
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val binding = TrackRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TrackViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        val track = trackList[position]
        holder.bind(track)
    }

    override fun getItemCount() = trackList.size
}
