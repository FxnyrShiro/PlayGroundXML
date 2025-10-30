package com.example.topplaygroundxml.features.weather.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.topplaygroundxml.R
import com.example.topplaygroundxml.databinding.FragmentWeatherBinding
import com.example.topplaygroundxml.features.weather.presentation.adapter.WeatherAdapter
import com.example.topplaygroundxml.features.weather.presentation.viewmodel.WeatherState
import com.example.topplaygroundxml.features.weather.presentation.viewmodel.WeatherViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel

class WeatherFragment : Fragment() {

    private var _binding: FragmentWeatherBinding? = null
    private val binding get() = _binding!!

    private val viewModel: WeatherViewModel by viewModel()
    private lateinit var weatherAdapter: WeatherAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeatherBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        weatherAdapter = WeatherAdapter { dailyData, dayIndex ->
            val bundle = bundleOf(
                WeatherDetailFragment.EXTRA_DATE to dailyData.time[dayIndex],
                WeatherDetailFragment.EXTRA_MAX_TEMP to dailyData.temperature_2m_max[dayIndex],
                WeatherDetailFragment.EXTRA_MIN_TEMP to dailyData.temperature_2m_min[dayIndex],
                WeatherDetailFragment.EXTRA_PRECIPITATION to dailyData.precipitation_sum[dayIndex],
                WeatherDetailFragment.EXTRA_WEATHER_CODE to dailyData.weather_code[dayIndex]
            )
            findNavController().navigate(R.id.action_weather_fragment_to_weather_detail_fragment, bundle)
        }
        binding.weatherRecyclerView.adapter = weatherAdapter
    }

    private fun observeViewModel() {
        viewModel.weatherState.onEach { state ->
            binding.progressBar.isVisible = state is WeatherState.Loading
            binding.weatherRecyclerView.isVisible = state is WeatherState.Success

            when (state) {
                is WeatherState.Success -> {
                    weatherAdapter.submitData(state.weatherData.daily)
                }
                is WeatherState.Error -> {
                    Toast.makeText(requireContext(), state.message, Toast.LENGTH_LONG).show()
                }
                else -> { /* No-op */ }
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}