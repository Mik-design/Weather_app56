package kg.tutorial.weather_app56.network

import io.reactivex.Observable
import io.reactivex.Single
import kg.tutorial.weather_app56.ForeCast
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("onecall?lat=42.8746&lon=74.5698&exclude=minutely&appid=92fd06d4bd25395363fb601e3765b529&lang=ru&unit=metric")
    fun fetchWeather(): Observable<ForeCast>

    @GET("onecall")
    fun fetchWeatherUsingQuery(
        @Query("lat") lat:Double = 42.876,
        @Query("lon") lon:Double = 74.5692,
        @Query("exclude") exclude:String = "minutely",
        @Query("appid") appid:String = "92fd06d4bd25395363fb601e3765b529&lang=ru&unit=metric",
        @Query("lang") lang:String = "ru",
        @Query("units") units:String = "metric"
    ): Single<ForeCast>

}

