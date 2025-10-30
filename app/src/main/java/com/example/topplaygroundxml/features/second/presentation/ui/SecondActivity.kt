package com.example.topplaygroundxml.features.second.presentation.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.topplaygroundxml.databinding.ActivitySecondBinding
import com.example.topplaygroundxml.features.second.presentation.viewmodel.SecondViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SecondActivity: AppCompatActivity() {

    private lateinit var binding: ActivitySecondBinding
    private val viewModel: SecondViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)


        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.button.setOnClickListener {
            finish()
        }

    }
}