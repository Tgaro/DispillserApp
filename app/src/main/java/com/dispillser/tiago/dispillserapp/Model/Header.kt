package com.dispillser.tiago.dispillserapp.Model

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import com.dispillser.tiago.dispillserapp.CalendarioActivity
import com.dispillser.tiago.dispillserapp.ListaPacienteActivity
import com.dispillser.tiago.dispillserapp.MainActivity
import com.dispillser.tiago.dispillserapp.R

class Header(activity: Activity) : AppCompatActivity() {

    private var homeButton: ImageButton = activity.findViewById<View>(R.id.btInicio) as ImageButton
    private var patientButton: ImageButton = activity.findViewById<View>(R.id.btPacientes) as ImageButton
    private var calendarButton: ImageButton = activity.findViewById<View>(R.id.btAlarme) as ImageButton

    init {
        homeButton.setOnClickListener{
            if(activity.localClassName =="MainActivity")

            else{
                val intentHome = Intent(activity, MainActivity::class.java)
                activity.startActivity(intentHome)
            }
        }

        patientButton.setOnClickListener{
            if(activity.localClassName =="ListaPacienteActivity")

            else{
                val intentPatient = Intent(activity, ListaPacienteActivity::class.java)
                activity.startActivity(intentPatient)
            }
        }

        calendarButton.setOnClickListener{
            if(activity.localClassName =="CalendarioActivity")

            else{
                val intentCalendar = Intent(activity, CalendarioActivity::class.java)
                activity.startActivity(intentCalendar)
            }
        }
    }
}

