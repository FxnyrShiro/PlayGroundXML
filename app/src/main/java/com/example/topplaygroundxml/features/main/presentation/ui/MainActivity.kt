package com.example.topplaygroundxml.features.main.presentation.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.topplaygroundxml.data.Navigator
import com.example.topplaygroundxml.databinding.ActivityMainBinding
import com.example.topplaygroundxml.features.main.presentation.viewmodel.MainViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModel()
    private val navigator: Navigator by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.materialButton.setOnClickListener {
            val intent = navigator.getSecondActivityIntent(this)
            startActivity(intent)
        }

        binding.weatherButton.setOnClickListener {
            val intent = navigator.getWeatherActivityIntent(this)
            startActivity(intent)
        }

        binding.calculatorButton.setOnClickListener {
            val intent = navigator.getCalculatorActivityIntent(this)
            startActivity(intent)
        }
    }
}