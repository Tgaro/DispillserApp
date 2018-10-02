package com.dispillser.tiago.dispillserapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.dispillser.tiago.dispillserapp.Model.Header

class CalendarioActivity : AppCompatActivity() {

    private lateinit var headerText : TextView
    private lateinit var headerHelper : Header
    private lateinit var calendarioBack : RelativeLayout
    private lateinit var btnCalendario : ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendario)
        headerText = findViewById(R.id.layoutName)
        headerHelper = Header(this)
        btnCalendario = findViewById(R.id.btAlarme)
        calendarioBack = findViewById(R.id.calendario)
        btnCalendario.setBackgroundColor(resources.getColor(R.color.colorPrimaryLight))
        calendarioBack.setBackgroundColor(resources.getColor(R.color.colorPrimaryLight))
        headerText.text = "Calendário"
    }
}