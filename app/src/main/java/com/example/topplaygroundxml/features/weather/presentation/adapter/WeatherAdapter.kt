package com.example.topplaygroundxml.features.weather.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.topplaygroundxml.R
import com.example.topplaygroundxml.features.weather.domain.model.DailyData
import com.example.topplaygroundxml.databinding.ItemWeatherBinding

class WeatherAdapter(
    private val onDayClicked: (dailyData: DailyData, dayIndex: Int) -> Unit
) : RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>() {

    private var dailyData: DailyData? = null

    fun submitData(data: DailyData) {
        this.dailyData = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val binding = ItemWeatherBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WeatherViewHolder(binding, onDayClicked)
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        dailyData?.let { holder.bind(it, position) }
    }

    override fun getItemCount(): Int = dailyData?.time?.size ?: 0

    class WeatherViewHolder(
        private val binding: ItemWeatherBinding,
        private val onDayClicked: (dailyData: DailyData, dayIndex: Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(dailyData: DailyData, position: Int) {
            val context = binding.root.context
            val tempMax = dailyData.temperature_2m_max[position]
            val tempMin = dailyData.temperature_2m_min[position]
            val weatherCode = dailyData.weather_code[position]

            binding.dateText.text = dailyData.time[position]
            binding.temperatureText.text = context.getString(R.string.temperature_template, tempMax, tempMin)
            binding.precipitationText.text = context.getString(R.string.precipitation_template, dailyData.precipitation_sum[position])

            val (iconRes, colorRes) = getWeatherLook(weatherCode, tempMax)
            binding.weatherIcon.setImageResource(iconRes)
            (binding.root as com.google.android.material.card.MaterialCardView).setCardBackgroundColor(ContextCompat.getColor(context, colorRes))

            binding.root.setOnClickListener {
                onDayClicked(dailyData, adapterPosition)
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
    }
}
