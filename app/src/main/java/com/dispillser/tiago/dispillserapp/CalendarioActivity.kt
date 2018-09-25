package com.dispillser.tiago.dispillserapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import com.dispillser.tiago.dispillserapp.Model.Header

class CalendarioActivity : AppCompatActivity() {

    private lateinit var headerText : TextView
    private lateinit var headerHelper : Header

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendario)
        headerText = findViewById(R.id.layoutName)
        headerHelper = Header(this)
        headerText.text = "Calendário"
    }
}