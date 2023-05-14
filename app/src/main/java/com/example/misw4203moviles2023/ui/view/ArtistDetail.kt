package com.example.misw4203moviles2023.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.misw4203moviles2023.R
import com.example.misw4203moviles2023.ui.viewModel.PerformerDetailViewModel

class ArtistDetail : Fragment() {

    private var actionBar: ActionBar? = null

    companion object {
        fun newInstance() = ArtistDetail()
    }

    private lateinit var viewModel: PerformerDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_performer_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[PerformerDetailViewModel::class.java]
        // TODO Use the ViewModel
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        actionBar = (activity as AppCompatActivity?)!!.supportActionBar
        actionBar?.title = "Cargando..."
    }
}
