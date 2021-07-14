package kg.tutorial.weather_app56.storage

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kg.tutorial.weather_app56.DailyForeCast
import kg.tutorial.weather_app56.HourlyForeCast

class CollectionConverter {

    @TypeConverter
    fun fromHourlyForeCastListToJson(list:List<HourlyForeCast>?): String? =
        Gson().toJson(list)

    @TypeConverter
    fun fromJsonToHourlyForeCastList(json:String?): List<HourlyForeCast>? =
        Gson().fromJson(json, object: TypeToken<List<HourlyForeCast>>() {}.type)

    @TypeConverter
    fun fromDailyForeCastListToJson(list:List<DailyForeCast>?): String? =
        Gson().toJson(list)

    @TypeConverter
    fun fromJsonToDailyForeCastList(json:String?): List<DailyForeCast>? =
        Gson().fromJson(json, object: TypeToken<List<DailyForeCast>>() {}.type)
}