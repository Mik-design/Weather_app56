package kg.tutorial.weather_app56.repo

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kg.tutorial.weather_app56.ForeCast
import kg.tutorial.weather_app56.network.WeatherApi
import kg.tutorial.weather_app56.storage.ForeCastDatabase


class WeatherRepo(
    private val db: ForeCastDatabase,
    private val weatherApi: WeatherApi
) {
    fun getWeatherFromApi(): Single<ForeCast> {
        return weatherApi.fetchWeather()
            .subscribeOn(Schedulers.io())
            .map {
                db.forecastDao().insert(it)
                it
            }
            .observeOn(AndroidSchedulers.mainThread())
    }
    fun getForeCastFromDbAsLive() = db.forecastDao().getAll()
}
