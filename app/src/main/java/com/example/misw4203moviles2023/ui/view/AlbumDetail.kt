package com.example.misw4203moviles2023.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.misw4203_moviles_2023.adapter.TrackAdapter
import com.example.misw4203moviles2023.databinding.FragmentAlbumDetailBinding
import com.example.misw4203moviles2023.ui.viewModel.AlbumDetailViewModel

class AlbumDetail : Fragment() {

    companion object {
        fun newInstance() = AlbumDetail()
    }

    private lateinit var trackRecyclerView: RecyclerView
    private lateinit var trackAdapter: TrackAdapter
    private lateinit var trackLayoutManager: LinearLayoutManager

    private lateinit var viewModel: AlbumDetailViewModel

    private var _binding: FragmentAlbumDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var progressBar: ProgressBar

    private val args: AlbumDetailArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentAlbumDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(AlbumDetailViewModel::class.java)
        viewModel.onCreate(args.albumId)

        progressBar = binding.progressBarAlbumDetail

        trackRecyclerView = binding.trackListRecyclerView
        trackLayoutManager = LinearLayoutManager(context)
        trackRecyclerView.layoutManager = trackLayoutManager
        trackRecyclerView.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
        viewModel.albumModel.observe(viewLifecycleOwner) {
            binding.albumDescription.text = it?.description
            binding.albumGenre.text = "Género del álbum: ${it?.genre}"
            binding.albumRecordLabel.text = "${it?.recordLabel}"

            Glide.with(requireContext())
                .load(it?.cover)
                .placeholder(R.drawable.ic_album)
                .into(binding.albumDetailImageView)

            trackAdapter = TrackAdapter(requireContext(), it?.tracks ?: emptyList())
            trackRecyclerView.adapter = trackAdapter
            progressBar.visibility = View.GONE
            trackRecyclerView.visibility = View.VISIBLE
        }
    }
}
