package kg.tutorial.weather_app56.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kg.tutorial.weather_app56.ForeCast
import kg.tutorial.weather_app56.R
import kg.tutorial.weather_app56.format
import kg.tutorial.weather_app56.models.Constants
import kg.tutorial.weather_app56.network.WeatherClient
import kg.tutorial.weather_app56.storage.ForeCastDatabase
import kotlin.math.roundToInt

@SuppressLint("CheckResult")
class MainActivity : AppCompatActivity() {
//    lateinit var tv_temperature: TextView
//    lateinit var tv_date: TextView
//    lateinit var tv_temp_max: TextView
//    lateinit var tv_temp_min: TextView
//    lateinit var tv_feels_like: TextView
//    lateinit var tv_weather: TextView
//    lateinit var tv_sunrise: TextView
//    lateinit var tv_sunset: TextView
//    lateinit var tv_humidity: TextView
//    lateinit var iv_weather_icon: ImageView
//    lateinit var tv_refresh:TextView

    private val db by lazy {
        ForeCastDatabase.getInstance(applicationContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupViews()
        getWeatherFromApi()
        subscribeToLiveData()
    }

    private fun setupViews() {
        var tv_refresh: TextView = findViewById(R.id.tv_refresh)
        tv_refresh.setOnClickListener {
            showLoading()
            getWeatherFromApi()
        }
    }

    private fun showLoading() {
        var progress: ProgressBar = findViewById(R.id.progress)
        progress.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        var progress: ProgressBar = findViewById(R.id.progress)
        progress.visibility = View.GONE
    }

    private fun getWeatherFromApi() {
        WeatherClient.weatherApi.fetchWeather()
            .subscribeOn(Schedulers.io())
            .doOnTerminate { hideLoading() }
            .map {
                db.forecastDao().insert(it)
                it
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                hideLoading()
            },
                {
                    hideLoading()
                    Toast.makeText(this@MainActivity, it.message, Toast.LENGTH_LONG).show()
                })
    }


    private fun subscribeToLiveData() {

        db.forecastDao().getAll().observe(this, Observer {
            it?.let {
                setValuesToViews(it)
                loadWeatherIcon(it)
            }
            })
    }


    private fun loadWeatherIcon(it: ForeCast) {
        var iv_weather_icon: ImageView = findViewById(R.id.iv_weather_icon)

        it.current?.weather?.get(0)?.icon?.let { icon ->
            Glide.with(this)
                .load("${Constants.iconUri}${icon}${Constants.iconFormat}")
                .into(iv_weather_icon)
        }
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
}








