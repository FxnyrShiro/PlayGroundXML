package com.example.topplaygroundxml.features.weather.presentation.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.topplaygroundxml.R
import com.example.topplaygroundxml.databinding.ActivityWeatherDetailBinding

class WeatherDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWeatherDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWeatherDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        displayWeatherData()
    }

    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener { finish() }
    }

    private fun displayWeatherData() {
        val date = intent.getStringExtra(EXTRA_DATE)
        val maxTemp = intent.getDoubleExtra(EXTRA_MAX_TEMP, 0.0)
        val minTemp = intent.getDoubleExtra(EXTRA_MIN_TEMP, 0.0)
        val precipitation = intent.getDoubleExtra(EXTRA_PRECIPITATION, 0.0)
        val weatherCode = intent.getIntExtra(EXTRA_WEATHER_CODE, 0)

        binding.toolbar.title = date
        binding.dateText.text = date
        binding.temperatureText.text = getString(R.string.temperature_detail_template, maxTemp, minTemp)
        binding.precipitationText.text = getString(R.string.precipitation_template, precipitation)

        val (iconRes, colorRes) = getWeatherLook(weatherCode, maxTemp)
        binding.weatherIcon.setImageResource(iconRes)
        binding.root.setBackgroundColor(ContextCompat.getColor(this, colorRes))
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

    companion object {
        const val EXTRA_DATE = "EXTRA_DATE"
        const val EXTRA_MAX_TEMP = "EXTRA_MAX_TEMP"
        const val EXTRA_MIN_TEMP = "EXTRA_MIN_TEMP"
        const val EXTRA_PRECIPITATION = "EXTRA_PRECIPITATION"
        const val EXTRA_WEATHER_CODE = "EXTRA_WEATHER_CODE"
    }
}
