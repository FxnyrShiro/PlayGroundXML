package com.example.topplaygroundxml.features.weather.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.topplaygroundxml.R
import com.example.topplaygroundxml.databinding.FragmentWeatherDetailBinding

class WeatherDetailFragment : Fragment() {

    private var _binding: FragmentWeatherDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeatherDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()
        displayWeatherData()
    }

    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener { findNavController().popBackStack() }
    }

    private fun displayWeatherData() {
        arguments?.let {
            val date = it.getString(EXTRA_DATE)
            val maxTemp = it.getDouble(EXTRA_MAX_TEMP)
            val minTemp = it.getDouble(EXTRA_MIN_TEMP)
            val precipitation = it.getDouble(EXTRA_PRECIPITATION)
            val weatherCode = it.getInt(EXTRA_WEATHER_CODE)

            binding.toolbar.title = date
            binding.dateText.text = date
            binding.temperatureText.text = getString(R.string.temperature_detail_template, maxTemp, minTemp)
            binding.precipitationText.text = getString(R.string.precipitation_template, precipitation)

            val (iconRes, colorRes) = getWeatherLook(weatherCode, maxTemp)
            binding.weatherIcon.setImageResource(iconRes)
            binding.root.setBackgroundColor(ContextCompat.getColor(requireContext(), colorRes))
        }
    }

    private fun getWeatherLook(code: Int, temp: Double): Pair<Int, Int> {
        val icon = when (code) {
            0 -> R.drawable.ic_sunny
            in 1..3 -> R.drawable.ic_cloudy
            in 51..67 -> R.drawable.ic_rainy
            in 71..77 -> R.drawable.ic_snowy
            else -> R.drawable.ic_cloudy // Default
        }

        val color = when {
            temp < 0 -> R.color.cold_weather_bg
            temp in 0.0..15.0 -> R.color.mild_weather_bg
            temp in 15.1..25.0 -> R.color.warm_weather_bg
            else -> R.color.hot_weather_bg
        }
        return Pair(icon, color)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val EXTRA_DATE = "EXTRA_DATE"
        const val EXTRA_MAX_TEMP = "EXTRA_MAX_TEMP"
        const val EXTRA_MIN_TEMP = "EXTRA_MIN_TEMP"
        const val EXTRA_PRECIPITATION = "EXTRA_PRECIPITATION"
        const val EXTRA_WEATHER_CODE = "EXTRA_WEATHER_CODE"
    }
}