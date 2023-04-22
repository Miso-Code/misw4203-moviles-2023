package com.example.misw4203_moviles_2023.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.misw4203_moviles_2023.R
import com.example.misw4203_moviles_2023.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}