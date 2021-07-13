package kg.tutorial.weather_app56

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity
data class ForeCast(
    val id: Long? = null,
    var lat: Double? = null,
    var lon: Double? = null,
    @PrimaryKey
    var timezone: String,
    var timezone_offset: Long? = 0L,
    var current: CurrentForeCast? = null,
    var hourly: List<HourlyForeCast>? = null,
    var daily: List<DailyForeCast>? = null
)
//{
//    override fun toString(): String {
//        return "ID: ${id?.toString()} \nLAT: ${lat?.toString()} \nLON: {lon?.toString()} " +
//                "\nDESC: ${current?.weather?.get(0)?.description} \n\n"
//    }
//}

data class CurrentForeCast(
    @SerializedName("dt")
    var date: Long? = null,
    var sunrise: Long? = null,
    var sunset: Long? = null,
    var temp: Double? = null,
    var feels_like: Double? = null,
    var humidity: Int? = null,
    var weather: List<Weather>? =null
)

data class Weather(
    var id: Long? = null,
    var description: String? = null,
    var icon: String? = null,
)

data class HourlyForeCast(
    @SerializedName("dt")
    var date: Long? = null,
    var temp: Double? = null,
    var weather: List<Weather>? =null,
    @SerializedName("pop")
    var probability: Double? = null
)

data class DailyForeCast(
    @SerializedName("dt")
    var date: Long? = null,
    @SerializedName("pop")
    var probability: Double? = null,
    var temp: Double? = null,
    var weather: List<Weather>? =null

)

data class Temperature(
    var min: Double? = null,
    var max: Double? = null

)
