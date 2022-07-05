package com.example.weatherappkotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class SelectionCity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selection_city)
        val editCity = findViewById<EditText>(R.id.etcity)
        val btnShow = findViewById<Button>(R.id.btnshow)
        btnShow.setOnClickListener {
            val city:String = editCity.text.toString().trim()
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("city",city)
            editCity.text = null
            startActivity(intent)

        }
    }
}