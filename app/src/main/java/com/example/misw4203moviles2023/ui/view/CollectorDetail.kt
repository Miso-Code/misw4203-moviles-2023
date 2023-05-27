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
import com.example.misw4203moviles2023.adapter.AlbumAdapter
import com.example.misw4203moviles2023.adapter.OnItemClickListener
import com.example.misw4203moviles2023.adapter.OnPerformerClickListener
import com.example.misw4203moviles2023.adapter.PerformerAdapter
import com.example.misw4203moviles2023.databinding.FragmentCollectorDetailBinding
import com.example.misw4203moviles2023.domain.album.model.Album
import com.example.misw4203moviles2023.domain.performer.model.Performer
import com.example.misw4203moviles2023.ui.viewModel.CollectorDetailViewModel

private const val TIMESTAMPT_REGEX_END = 10

class CollectorDetail(private val viewModel: CollectorDetailViewModel? = null) : Fragment() {

    private lateinit var albumRecyclerView: RecyclerView
    private lateinit var albumLayoutManager: LinearLayoutManager
    private lateinit var albumAdapter: AlbumAdapter

    private lateinit var performerRecyclerView: RecyclerView
    private lateinit var performerLayoutManager: LinearLayoutManager
    private lateinit var performerAdapter: PerformerAdapter

    private lateinit var _viewModel: CollectorDetailViewModel

    private var _binding: FragmentCollectorDetailBinding? = null

    private val binding get() = _binding!!
    private lateinit var progressBar: ProgressBar

    private val args: CollectorDetailArgs by navArgs()

    private var actionBar: ActionBar? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentCollectorDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _viewModel = viewModel ?: ViewModelProvider(this)[CollectorDetailViewModel::class.java]
        _viewModel.onCreate(args.collectorId)

        setupViews()
        observeDataChanges(view)
    }

    private fun setupViews() {
        progressBar = binding.progressBarCollectorDetail

        albumRecyclerView = binding.collectorAlbumListRecyclerView
        albumLayoutManager = LinearLayoutManager(context)
        performerRecyclerView = binding.collectorPerformerListRecyclerView
        performerLayoutManager = LinearLayoutManager(context)

        progressBar.visibility = View.VISIBLE
        albumRecyclerView.layoutManager = albumLayoutManager
        albumRecyclerView.visibility = View.GONE

        performerRecyclerView.layoutManager = performerLayoutManager
        performerRecyclerView.visibility = View.GONE

        binding.collectorDescription.visibility = View.GONE
        binding.collectorEmail.visibility = View.GONE
        binding.collectorPhone.visibility = View.GONE
        binding.collectorDetailImageView.visibility = View.GONE
        binding.collectorAlbumTracks.visibility = View.GONE
        binding.collectorPerformerTracks.visibility = View.GONE
        binding.collectorAlbumListRecyclerView.visibility = View.GONE
        binding.collectorPerformerListRecyclerView.visibility = View.GONE
    }

    private fun observeDataChanges(view: View) {
        _viewModel.collectorModel.observe(viewLifecycleOwner) {
            if (it?.albums?.isEmpty() != true) {
                binding.collectorAlbumTracks.visibility = View.VISIBLE
            }
            if (it?.performers?.isEmpty() != true) {
                binding.collectorPerformerTracks.visibility = View.VISIBLE
            }
            binding.collectorDetailImageView.visibility = View.VISIBLE
            binding.collectorPhone.visibility = View.VISIBLE
            binding.collectorEmail.visibility = View.VISIBLE
            progressBar.visibility = View.GONE

            binding.collectorPhone.text = it?.telephone
            binding.collectorEmail.text = it?.email

            actionBar?.title = it?.name

            if (it?.albums?.isEmpty() == false) {
                albumAdapter = AlbumAdapter(
                    requireContext(),
                    it.albums.map { albumAux ->
                        Album(
                            albumAux.id,
                            albumAux.name,
                            albumAux.cover,
                            albumAux.releaseDate.substring(0, TIMESTAMPT_REGEX_END).split("-")
                                .reversed()
                                .joinToString("/"),
                            albumAux.description,
                            albumAux.genre,
                            albumAux.recordLabel,
                            emptyList(),
                        )
                    },
                )
                albumAdapter.setOnItemClickListener(object : OnItemClickListener {
                    override fun onItemClick(position: Int, album: Album) {
                        CollectorDetailDirections.actionCollectorDetailToAlbumDetail(album.id).also { action ->
                            view.findNavController().navigate(action)
                        }
                    }
                })
                albumRecyclerView.adapter = albumAdapter
                albumRecyclerView.visibility = View.VISIBLE
            }

            if (it?.performers?.isEmpty() == false) {
                performerAdapter =
                    PerformerAdapter(
                        requireContext(),
                        it.performers.map { performerAux ->
                            Performer(
                                performerAux.id,
                                performerAux.name,
                                performerAux.description,
                                performerAux.image,
                                emptyList(),
                            )
                        },
                    )
                performerAdapter.setOnItemClickListener(object : OnPerformerClickListener {
                    override fun onItemClick(position: Int, performer: Performer) {
                        CollectorDetailDirections.actionCollectorDetailToPerformerDetail(performer.id)
                            .also { action ->
                                view.findNavController().navigate(action)
                            }
                    }
                })
                performerRecyclerView.adapter = performerAdapter
                performerRecyclerView.visibility = View.VISIBLE
            }
        }
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        actionBar = (activity as? AppCompatActivity)?.supportActionBar
        actionBar?.title = "Cargando..."
    }
}
