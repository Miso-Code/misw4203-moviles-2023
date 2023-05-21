package com.example.misw4203moviles2023.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.misw4203moviles2023.R
import com.example.misw4203moviles2023.adapter.AlbumAdapter
import com.example.misw4203moviles2023.adapter.OnItemClickListener
import com.example.misw4203moviles2023.databinding.FragmentPerformerDetailBinding
import com.example.misw4203moviles2023.domain.album.model.Album
import com.example.misw4203moviles2023.ui.viewModel.PerformerDetailViewModel

class PerformerDetail(private val viewModel: PerformerDetailViewModel? = null) : Fragment() {

    companion object {
        fun newInstance() = PerformerDetail()
    }

    private lateinit var albumRecyclerView: RecyclerView
    private lateinit var albumAdapter: AlbumAdapter
    private lateinit var albumLayoutManager: LinearLayoutManager

    private lateinit var _viewModel: PerformerDetailViewModel

    private var _binding: FragmentPerformerDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var progressBar: ProgressBar

    private val args: PerformerDetailArgs by navArgs()

    private var actionBar: ActionBar? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentPerformerDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _viewModel = viewModel ?: ViewModelProvider(this)[PerformerDetailViewModel::class.java]
        _viewModel.onCreate(args.performerId)

        progressBar = binding.progressBarPerformerDetail

        albumRecyclerView = binding.performerListRecyclerView
        albumLayoutManager = LinearLayoutManager(context)
        albumRecyclerView.layoutManager = albumLayoutManager
        albumRecyclerView.visibility = View.GONE
        progressBar.visibility = View.VISIBLE

        binding.performerDescription.visibility = View.GONE
        binding.performerAlbums.visibility = View.GONE
        binding.performerDetailImageView.visibility = View.GONE

        _viewModel.performerModel.observe(viewLifecycleOwner) {
            if (it?.albums?.isEmpty() != true) {
                binding.performerAlbums.visibility = View.VISIBLE
            }

            binding.performerDescription.visibility = View.VISIBLE
            binding.performerDetailImageView.visibility = View.VISIBLE

            binding.performerDescription.text = it?.description

            actionBar?.title = it?.name

            Glide.with(requireContext())
                .load(it?.image)
                .placeholder(R.drawable.ic_artist)
                .into(binding.performerDetailImageView)

            albumAdapter = AlbumAdapter(requireContext(), it?.albums ?: emptyList())
            albumAdapter.setOnItemClickListener(object : OnItemClickListener {
                override fun onItemClick(position: Int, album: Album) {
                    PerformerDetailDirections.actionPerformerDetailToAlbumDetail(album.id)
                        .also { action ->
                            view.findNavController().navigate(action)
                        }
                }
            })
            albumRecyclerView.adapter = albumAdapter
            progressBar.visibility = View.GONE
            albumRecyclerView.visibility = View.VISIBLE
        }
    }

    @Suppress("DEPRECATION")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        actionBar = (activity as? AppCompatActivity)?.supportActionBar
        actionBar?.title = "Cargando..."
    }
}
