package com.example.topplaygroundxml.features.weather.presentation.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.example.topplaygroundxml.data.Navigator
import com.example.topplaygroundxml.databinding.ActivityWeatherBinding
import com.example.topplaygroundxml.features.weather.presentation.viewmodel.WeatherState
import com.example.topplaygroundxml.features.weather.presentation.viewmodel.WeatherViewModel
import com.example.topplaygroundxml.features.weather.presentation.adapter.WeatherAdapter
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class WeatherActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWeatherBinding
    private val viewModel: WeatherViewModel by viewModel()
    private val navigator: Navigator by inject()
    private lateinit var weatherAdapter: WeatherAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWeatherBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupRecyclerView()
        observeWeatherState()
    }

    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun setupRecyclerView() {
        weatherAdapter = WeatherAdapter { dailyData, dayIndex ->
            val intent = navigator.getWeatherDetailActivityIntent(this, dailyData, dayIndex)
            startActivity(intent)
        }
        binding.weatherRecyclerView.adapter = weatherAdapter
    }

    private fun observeWeatherState() {
        lifecycleScope.launch {
            viewModel.weatherState.collect { state ->
                when (state) {
                    is WeatherState.Loading -> {
                        binding.progressBar.isVisible = true
                        binding.weatherRecyclerView.isVisible = false
                    }
                    is WeatherState.Success -> {
                        binding.progressBar.isVisible = false
                        binding.weatherRecyclerView.isVisible = true
                        weatherAdapter.submitData(state.weatherData.daily)
                    }
                    is WeatherState.Error -> {
                        binding.progressBar.isVisible = false
                        binding.weatherRecyclerView.isVisible = false
                        Toast.makeText(this@WeatherActivity, state.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}
