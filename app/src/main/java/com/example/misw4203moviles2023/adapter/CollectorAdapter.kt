package com.example.misw4203moviles2023.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.misw4203moviles2023.databinding.CollectorRowBinding
import com.example.misw4203moviles2023.domain.collector.model.Collector

interface OnCollectorClickListener {
    fun onItemClick(position: Int, collector: Collector)
}

class CollectorAdapter(
    private val collectorList: List<Collector>
) :
    RecyclerView.Adapter<CollectorAdapter.CollectorViewHolder>() {

    private var onItemClickListener: OnCollectorClickListener? = null

    inner class CollectorViewHolder(private val binding: CollectorRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(collector: Collector) {
            binding.collectorName.text = collector.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectorViewHolder {
        val binding =
            CollectorRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CollectorViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CollectorViewHolder, position: Int) {
        val collector = collectorList[position]
        holder.bind(collector)
        holder.itemView.setOnClickListener {
            onItemClickListener?.onItemClick(position, collector)
        }
    }

    fun setOnItemClickListener(listener: OnCollectorClickListener) {
        onItemClickListener = listener
    }

    override fun getItemCount() = collectorList.size
}
