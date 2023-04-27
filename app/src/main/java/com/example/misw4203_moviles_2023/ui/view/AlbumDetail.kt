package com.example.misw4203_moviles_2023.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.misw4203_moviles_2023.databinding.FragmentAlbumDetailBinding
import com.example.misw4203_moviles_2023.ui.viewModel.AlbumDetailViewModel

class AlbumDetail : Fragment() {

    companion object {
        fun newInstance() = AlbumDetail()
    }

    private lateinit var viewModel: AlbumDetailViewModel

    private var _binding: FragmentAlbumDetailBinding? = null
    private val binding get() = _binding!!

    private val args: AlbumDetailArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAlbumDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(AlbumDetailViewModel::class.java)
        // TODO: Use the ViewModel

        binding.albumDetailTextView.text = "Album Detail ${args.albumId}"
    }

}