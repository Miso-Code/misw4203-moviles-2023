package com.example.misw4203moviles2023.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.misw4203moviles2023.R
import com.example.misw4203moviles2023.ui.viewModel.CollectorListViewModel

class CollectorList : Fragment() {

    companion object {
        fun newInstance() = CollectorList()
    }

    private lateinit var viewModel: CollectorListViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_collector_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[CollectorListViewModel::class.java]
        // TODO Use the ViewModel
    }
}
