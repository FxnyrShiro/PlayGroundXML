package com.example.topplaygroundxml.data

import android.content.Context
import android.content.Intent
import com.example.topplaygroundxml.data.model.DailyData
import com.example.topplaygroundxml.ui.second.SecondActivity
import com.example.topplaygroundxml.ui.weather.WeatherActivity
import com.example.topplaygroundxml.ui.weather.detail.WeatherDetailActivity

class Navigator {

    fun getSecondActivityIntent(context: Context): Intent {
        return Intent(context, SecondActivity::class.java)
    }

    fun getWeatherActivityIntent(context: Context): Intent {
        return Intent(context, WeatherActivity::class.java)
    }

    fun getWeatherDetailActivityIntent(context: Context, dailyData: DailyData, dayIndex: Int): Intent {
        return Intent(context, WeatherDetailActivity::class.java).apply {
            putExtra(WeatherDetailActivity.EXTRA_DATE, dailyData.time[dayIndex])
            putExtra(WeatherDetailActivity.EXTRA_MAX_TEMP, dailyData.temperature_2m_max[dayIndex])
            putExtra(WeatherDetailActivity.EXTRA_MIN_TEMP, dailyData.temperature_2m_min[dayIndex])
            putExtra(WeatherDetailActivity.EXTRA_PRECIPITATION, dailyData.precipitation_sum[dayIndex])
            putExtra(WeatherDetailActivity.EXTRA_WEATHER_CODE, dailyData.weather_code[dayIndex])
        }
    }
}