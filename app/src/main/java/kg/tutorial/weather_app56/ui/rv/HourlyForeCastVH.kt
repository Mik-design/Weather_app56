package kg.tutorial.weather_app56.ui.rv

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kg.tutorial.weather_app56.HourlyForeCast
import kg.tutorial.weather_app56.R
import kg.tutorial.weather_app56.format
import kg.tutorial.weather_app56.models.Constants
import kotlin.math.roundToInt


class HourlyForeCastVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
    lateinit var tv_time: TextView
    lateinit var tv_temp: TextView
    lateinit var tv_precipitation: TextView
    lateinit var iv_weather_icon_one: ImageView


    fun bind(item: HourlyForeCast) {

        itemView.run {
            tv_time.text = item.date.format("hh:mm")
            item.probability?.let {
                tv_precipitation.text = "${(it * 100).roundToInt()}%"
            }

            tv_temp.text = item.temp?.roundToInt()?.toString()


            Glide.with(itemView.context)
                .load("${Constants.iconUri}${item.weather?.get(0)?.icon}${Constants.iconFormat}")
                .into(iv_weather_icon_one)
        }
    }

    companion object {
        fun create(parent: ViewGroup): HourlyForeCastVH {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_hourly_forecast, parent, false)

            return HourlyForeCastVH(view)
        }
    }
}