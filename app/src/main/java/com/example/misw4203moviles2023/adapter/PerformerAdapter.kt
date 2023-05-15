package com.example.misw4203moviles2023.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.misw4203moviles2023.R
import com.example.misw4203moviles2023.databinding.PerformerRowBinding
import com.example.misw4203moviles2023.domain.performer.model.Performer

interface OnPerformerClickListener {
    fun onItemClick(position: Int, performer: Performer)
}

class PerformerAdapter(
    private val context: Context,
    private val performerList: List<Performer>
) :
    RecyclerView.Adapter<PerformerAdapter.PerformerViewHolder>() {

    private var onPerformerItemClick: OnPerformerClickListener? = null

    inner class PerformerViewHolder(private val binding: PerformerRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(performer: Performer) {
            binding.performerName.text = performer.name
            Glide.with(context).load(performer.image).placeholder(R.drawable.ic_artist)
                .into(binding.performerImage)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PerformerViewHolder {
        val binding =
            PerformerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PerformerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PerformerViewHolder, position: Int) {
        val performer = performerList[position]
        holder.bind(performer)
        holder.itemView.setOnClickListener {
            onPerformerItemClick?.onItemClick(position, performer)
        }
    }

    fun setOnItemClickListener(listener: OnPerformerClickListener) {
        onPerformerItemClick = listener
    }

    override fun getItemCount() = performerList.size
}
