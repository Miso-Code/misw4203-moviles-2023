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
import com.example.misw4203moviles2023.adapter.CollectorAdapter
import com.example.misw4203moviles2023.adapter.OnCollectorClickListener
import com.example.misw4203moviles2023.data.model.CollectorModel
import com.example.misw4203moviles2023.databinding.FragmentCollectorListBinding
import com.example.misw4203moviles2023.ui.viewModel.CollectorListViewModel

class CollectorList : Fragment() {

    companion object {
        fun newInstance() = CollectorList()
    }

    private lateinit var collectorRecyclerView: RecyclerView
    private lateinit var collectorAdapter: CollectorAdapter
    private lateinit var collectorLayoutManager: LinearLayoutManager

    private lateinit var viewModel: CollectorListViewModel
    private var _binding: FragmentCollectorListBinding? = null
    private val binding get() = _binding!!

    private lateinit var progressBar: ProgressBar

    private var actionBar: ActionBar? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentCollectorListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[CollectorListViewModel::class.java]
        viewModel.onCreate()

        progressBar = binding.collectorProgressBar

        collectorRecyclerView = binding.collectorListRecyclerView
        collectorLayoutManager = LinearLayoutManager(context)
        collectorRecyclerView.layoutManager = collectorLayoutManager
        collectorRecyclerView.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
        viewModel.collectorModel.observe(viewLifecycleOwner) {
            collectorAdapter = CollectorAdapter(it ?: emptyList())
            collectorAdapter.setOnItemClickListener(object : OnCollectorClickListener {
                override fun onItemClick(position: Int, collector: CollectorModel) {
                    CollectorListDirections.actionCollectorListToCollectorDetail(collector.id)
                        .also { action ->
                            view.findNavController().navigate(action)
                        }
                }
            })
            collectorRecyclerView.adapter = collectorAdapter
            progressBar.visibility = View.GONE
            collectorRecyclerView.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        actionBar = (activity as? AppCompatActivity)?.supportActionBar
        actionBar?.title = getString(R.string.menu_collector_list)
    }
}
