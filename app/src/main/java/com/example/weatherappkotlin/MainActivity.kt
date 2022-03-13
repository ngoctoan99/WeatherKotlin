package com.example.weatherappkotlin

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import org.json.JSONObject
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    val CITY: String = "nha trang,vn"
    val API: String ="aa5cee96c064630348f31a89f08f65bf"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        weatherTask().execute()
    }
    inner class weatherTask(): AsyncTask<String, Void, String>(){
        override fun onPreExecute() {
            super.onPreExecute()
            findViewById<ProgressBar>(R.id.load).visibility = View.VISIBLE
            findViewById<RelativeLayout>(R.id.mhchinh).visibility = View.GONE
            findViewById<TextView>(R.id.error).visibility = View.GONE
        }

        override fun doInBackground(vararg params: String?): String? {
            var response:String?
            try{
                response = URL("https://api.openweathermap.org/data/2.5/weather?q=$CITY&units=metric&appid=$API")
                    .readText(Charsets.UTF_8)
            }catch (s: Exception){
                response = null
            }
            return response
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            try {
                val jsonObject = JSONObject(result)
                val main = jsonObject.getJSONObject("main")
                val sys = jsonObject.getJSONObject("sys")
                val  wind = jsonObject.getJSONObject("wind")
                val weather = jsonObject.getJSONArray("weather").getJSONObject(0)
                val updateAt: Long = jsonObject.getLong("dt")
                val updaterAtText = "Updated at: " + SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(Date(updateAt*1000))
                val temp = main.getString("temp") + "°C"
                val tempMin = "Min Temp: " + main.getString("temp_min") + "°C"
                val tempMax = "Max Temp: " + main.getString("temp_max") + "°C"
                val pressure = main.getString("pressure")
                val humidity = main.getString("humidity")
                val sunrise:Long = sys.getLong("sunrise")
                val sunset:Long = sys.getLong("sunset")
                val windSpeed = wind.getString("speed")
                val weatherDescription = weather.getString("description")
                val address = jsonObject.getString("name")+", "+sys.getString("country")
                findViewById<TextView>(R.id.address).text = address
                findViewById<TextView>(R.id.updates).text = updaterAtText
                findViewById<TextView>(R.id.status).text = weatherDescription.capitalize()
                findViewById<TextView>(R.id.temp).text = temp
                findViewById<TextView>(R.id.temp_min).text = tempMin
                findViewById<TextView>(R.id.temp_max).text = tempMax
                findViewById<TextView>(R.id.sunnyrise).text = SimpleDateFormat("hh:mm a",Locale.ENGLISH).format(
                    Date(sunrise*1000)
                )
                findViewById<TextView>(R.id.sunnyset).text = SimpleDateFormat("hh:mm a",Locale.ENGLISH).format(Date(sunset*1000))
                findViewById<TextView>(R.id.wind).text = windSpeed
                findViewById<TextView>(R.id.pressure).text = pressure
                findViewById<TextView>(R.id.rain).text = humidity
                findViewById<ProgressBar>(R.id.load).visibility = View.GONE
                findViewById<RelativeLayout>(R.id.mhchinh).visibility = View.VISIBLE

            }catch (e : java.lang.Exception){
                findViewById<ProgressBar>(R.id.load).visibility = View.GONE
                findViewById<TextView>(R.id.error).visibility = View.VISIBLE
            }
        }
    }
}