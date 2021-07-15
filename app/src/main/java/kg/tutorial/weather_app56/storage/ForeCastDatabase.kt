package kg.tutorial.weather_app56.storage

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import kg.tutorial.weather_app56.ForeCast

@Database(
    entities = [ForeCast::class],
    version = 4,
    exportSchema = false
)
@TypeConverters(ModelConverter::class, CollectionConverter::class)
abstract class ForeCastDatabase : RoomDatabase() {
    abstract fun forecastDao(): ForeCastDao

    companion object {
        const val DB_NAME = "forecastDb"

    }
}