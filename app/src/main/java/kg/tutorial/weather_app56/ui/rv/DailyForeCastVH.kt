package kg.tutorial.weather_app56.ui.rv

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kg.tutorial.weather_app56.DailyForeCast
import kg.tutorial.weather_app56.R
import kg.tutorial.weather_app56.format
import kg.tutorial.weather_app56.models.Constants
import kotlin.math.roundToInt

class DailyForeCastVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
    lateinit var tv_weekday: TextView
    lateinit var tv_precipitation: TextView
    lateinit var iv_temp_max: TextView
    lateinit var iv_temp_min: TextView
    lateinit var iv_weather_icon_two: ImageView

    fun bind(item: DailyForeCast) {

        itemView.run {
            tv_weekday.text = item.date.format("dd/MM")
            item.probability?.let {
                tv_precipitation.text = "${(it * 100).roundToInt()}%"
            }

//            iv_temp_max.text = item.temp?.max?.roundToInt()?.toString()
//            iv_temp_max.text = item.temp?.min?.roundToInt()?.toString()



            Glide.with(itemView.context)
                .load("${Constants.iconUri}${item.weather?.get(0)?.icon}${Constants.iconFormat}")
                .into(iv_weather_icon_two)
        }
    }

    companion object {
        fun create(parent: ViewGroup): DailyForeCastVH {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_daily_forecast, parent, false)

            return DailyForeCastVH(view)
        }
    }
}

