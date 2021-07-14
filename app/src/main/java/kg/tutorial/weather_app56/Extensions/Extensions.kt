package kg.tutorial.weather_app56

import java.text.SimpleDateFormat
import java.util.*

fun Long?.format (pattern: String? = "dd/mm/yyyy"): String {
    this?.let {
        val seed = SimpleDateFormat (pattern, Locale.getDefault())
        return seed.format(Date(this * 1000))
    }
    return ""
}