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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.misw4203moviles2023.R
import com.example.misw4203moviles2023.adapter.AlbumAdapter
import com.example.misw4203moviles2023.adapter.OnItemClickListener
import com.example.misw4203moviles2023.databinding.FragmentAlbumListBinding
import com.example.misw4203moviles2023.domain.album.model.Album
import com.example.misw4203moviles2023.ui.viewModel.AlbumListViewModel

class AlbumList(private val viewModel: AlbumListViewModel? = null) : Fragment() {

    private lateinit var albumRecyclerView: RecyclerView
    private lateinit var albumAdapter: AlbumAdapter
    private lateinit var albumLayoutManager: LinearLayoutManager

    private lateinit var _viewModel: AlbumListViewModel
    private var _binding: FragmentAlbumListBinding? = null
    private val binding get() = _binding!!

    private lateinit var progressBar: ProgressBar

    private var actionBar: ActionBar? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentAlbumListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _viewModel = viewModel ?: ViewModelProvider(this)[AlbumListViewModel::class.java]
        _viewModel.onCreate()

        progressBar = binding.progressBar

        albumRecyclerView = binding.albumListRecyclerView
        albumLayoutManager = LinearLayoutManager(context)
        albumRecyclerView.layoutManager = albumLayoutManager
        albumRecyclerView.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
        _viewModel.albumModel.observe(viewLifecycleOwner) {
            albumAdapter = AlbumAdapter(requireContext(), it ?: emptyList())
            albumAdapter.setOnItemClickListener(object : OnItemClickListener {
                override fun onItemClick(position: Int, album: Album) {
                    AlbumListDirections.actionAlbumListToAlbumDetail(album.id).also { action ->
                        view.findNavController().navigate(action)
                    }
                }
            })
            albumRecyclerView.adapter = albumAdapter
            progressBar.visibility = View.GONE
            albumRecyclerView.visibility = View.VISIBLE
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        @Suppress("DEPRECATION")
        super.onActivityCreated(savedInstanceState)
        actionBar = (activity as? AppCompatActivity)?.supportActionBar
        actionBar?.title = getString(R.string.menu_album_list)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
