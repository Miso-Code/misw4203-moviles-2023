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
import com.example.misw4203moviles2023.adapter.OnPerformerClickListener
import com.example.misw4203moviles2023.adapter.PerformerAdapter
import com.example.misw4203moviles2023.databinding.FragmentPerformerListBinding
import com.example.misw4203moviles2023.domain.performer.model.Performer
import com.example.misw4203moviles2023.ui.viewModel.PerformerListViewModel

class PerformerList(private val viewModel: PerformerListViewModel? = null) : Fragment() {

    private lateinit var performerRecyclerView: RecyclerView
    private lateinit var performerAdapter: PerformerAdapter
    private lateinit var performerLayoutManager: LinearLayoutManager

    private lateinit var _viewModel: PerformerListViewModel
    private var _binding: FragmentPerformerListBinding? = null
    private val binding get() = _binding!!

    private lateinit var progressBar: ProgressBar

    private var actionBar: ActionBar? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentPerformerListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _viewModel = viewModel ?: ViewModelProvider(this)[PerformerListViewModel::class.java]
        _viewModel.onCreate()

        progressBar = binding.performerProgressBar

        performerRecyclerView = binding.performerListRecyclerView
        performerLayoutManager = LinearLayoutManager(context)
        performerRecyclerView.layoutManager = performerLayoutManager
        performerRecyclerView.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
        _viewModel.performerModel.observe(viewLifecycleOwner) {
            performerAdapter = PerformerAdapter(requireContext(), it ?: emptyList())
            performerAdapter.setOnItemClickListener(object : OnPerformerClickListener {
                override fun onItemClick(position: Int, performer: Performer) {
                    PerformerListDirections.actionPerformerListToPerformerDetail(performer.id)
                        .also { action ->
                            view.findNavController().navigate(action)
                        }
                }
            })
            performerRecyclerView.adapter = performerAdapter
            progressBar.visibility = View.GONE
            performerRecyclerView.visibility = View.VISIBLE
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        @Suppress("DEPRECATION")
        super.onActivityCreated(savedInstanceState)
        actionBar = (activity as? AppCompatActivity)?.supportActionBar
        actionBar?.title = getString(R.string.menu_performer_list)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
