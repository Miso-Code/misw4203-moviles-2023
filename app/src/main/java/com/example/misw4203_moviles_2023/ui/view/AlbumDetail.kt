package com.example.misw4203_moviles_2023.ui.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.misw4203_moviles_2023.R
import com.example.misw4203_moviles_2023.ui.viewModel.AlbumDetailViewModel

class AlbumDetail : Fragment() {

    companion object {
        fun newInstance() = AlbumDetail()
    }

    private lateinit var viewModel: AlbumDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_album_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AlbumDetailViewModel::class.java)
        // TODO: Use the ViewModel
    }

}