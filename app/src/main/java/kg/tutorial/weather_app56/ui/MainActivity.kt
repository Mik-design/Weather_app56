package kg.tutorial.weather_app56.ui

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kg.tutorial.weather_app56.ForeCast
import kg.tutorial.weather_app56.R
import kg.tutorial.weather_app56.format
import kg.tutorial.weather_app56.models.Constants
import kg.tutorial.weather_app56.ui.rv.DailyForeCastAdapter
import kg.tutorial.weather_app56.ui.rv.HourlyForeCastAdapter
import org.koin.android.viewmodel.ext.android.getViewModel
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {

    private lateinit var vm: MainViewModel
    private lateinit var dailyForeCastAdapter: DailyForeCastAdapter
    private lateinit var hourlyForeCastAdapter: HourlyForeCastAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        vm = getViewModel(MainViewModel::class)
        setupViews()
        setupRecyclerViews()
        subscribeToLiveData()
    }

    private fun setupViews() {
        var tv_refresh: TextView = findViewById(R.id.tv_refresh)
        tv_refresh.setOnClickListener {
            vm.showLoading()
            vm.getWeatherFromApi()
        }
    }

    private fun setupRecyclerViews() {

        var tv_daily_forecast: RecyclerView = findViewById(R.id.tv_daily_forecast)
        var tv_hourly_forecast: RecyclerView = findViewById(R.id.tv_hourly_forecast)

        dailyForeCastAdapter = DailyForeCastAdapter()
        tv_daily_forecast.adapter = dailyForeCastAdapter

        hourlyForeCastAdapter = HourlyForeCastAdapter()
        tv_hourly_forecast.adapter = hourlyForeCastAdapter
    }

    private fun subscribeToLiveData() {
        vm.getForeCastAsLive().observe(this, Observer {
            it?.let {
                setValuesToViews(it)
                loadWeatherIcon(it)
                setDataToRecyclerView(it)
            }
        })
        vm._isLoading.observe(this, Observer {
            when(it) {
                true -> showLoading()
                false -> hideLoading()
            }
        })
    }

    private fun setDataToRecyclerView(it: ForeCast) {
        it.daily?.let { dailyList ->
            dailyForeCastAdapter.setItems(dailyList)
        }

        it.hourly?.let { hourlyList ->
            hourlyForeCastAdapter.setItems(hourlyList)
        }
    }


    private fun showLoading() {
        var progress: ProgressBar = findViewById(R.id.progress)
        progress.post {
            progress.visibility = View.VISIBLE
        }
    }

    private fun hideLoading() {
        var progress: ProgressBar = findViewById(R.id.progress)
        progress.postDelayed({
            progress.visibility = View.INVISIBLE
        }, 2000)
    }


    private fun setValuesToViews(it: ForeCast) {
        var tv_temperature: TextView = findViewById(R.id.tv_temperature)
        var tv_date: TextView = findViewById(R.id.tv_date)
        var tv_temp_max: TextView = findViewById(R.id.tv_temp_max)
        var tv_temp_min: TextView = findViewById(R.id.tv_temp_min)
        var tv_feels_like: TextView = findViewById(R.id.tv_feels_like)
        var tv_weather: TextView = findViewById(R.id.tv_weather)
        var tv_sunrise: TextView = findViewById(R.id.tv_sunrise)
        var tv_sunset: TextView = findViewById(R.id.tv_sunset)
        var tv_humidity: TextView = findViewById(R.id.tv_humidity)
        var iv_weather_icon: ImageView = findViewById(R.id.iv_weather_icon)

        tv_temperature.text = it.current?.temp?.roundToInt().toString()
        tv_date.text = it.current?.date.format()
        tv_temp_max.text = it.daily?.get(0)?.temp?.roundToInt().toString()
        tv_temp_min.text = it.daily?.get(0)?.temp?.roundToInt().toString()
        tv_feels_like.text = it.current?.feels_like?.roundToInt().toString()
        tv_weather.text = it.current?.weather?.get(0)?.description
        tv_sunrise.text = it.current?.sunrise?.format("hh:mm")
        tv_sunset.text = it.current?.sunset?.format("hh:mm")
        tv_humidity.text = "${it.current?.humidity?.toString()}%"
    }


    private fun loadWeatherIcon(it: ForeCast) {
        var iv_weather_icon: ImageView = findViewById(R.id.iv_weather_icon)

        it.current?.weather?.get(0)?.icon?.let { icon ->
            Glide.with(this)
                .load("${Constants.iconUri}${icon}${Constants.iconFormat}")
                .into(iv_weather_icon)
        }
    }

}








