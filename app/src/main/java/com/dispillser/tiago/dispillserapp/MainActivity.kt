package com.dispillser.tiago.dispillserapp

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.dispillser.tiago.dispillserapp.Model.Header
import android.content.Intent
import android.bluetooth.BluetoothSocket
import android.widget.*
import com.dispillser.tiago.dispillserapp.R.color.colorPrimary
import com.dispillser.tiago.dispillserapp.R.color.colorPrimaryLight
import java.io.IOException
import java.util.*


@SuppressLint("SetTextI18n")
class MainActivity : AppCompatActivity()  {

    private lateinit var headerText : TextView
    private lateinit var bodyText : TextView
    private lateinit var headerHelper : Header
    private lateinit var btnBluetooth : Button
    private lateinit var homeBack : RelativeLayout
    private lateinit var btnHome : ImageButton

    private lateinit var bluetooth : BluetoothAdapter
    private var ConnectSuccess = true
    private val isBtConnected = false
    val myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        headerText = findViewById(R.id.layoutName)
        btnBluetooth = findViewById(R.id.btnBluetooth)
        bodyText = findViewById(R.id.inicioBody)
        homeBack = findViewById(R.id.home)
        btnHome = findViewById(R.id.btInicio)
        headerHelper = Header(this)
        headerText.text = "Início"
        homeBack.setBackgroundColor(resources.getColor(colorPrimaryLight))
        btnHome.setBackgroundColor(resources.getColor(colorPrimaryLight))
        setOnClicks()

        val address = intent.getStringExtra("BLUETOOTH")

        if(address == null){

        }else{
            Toast.makeText(applicationContext, "Conectando bluetooth", Toast.LENGTH_SHORT).show()
            try {
                connectBt(address)
                Toast.makeText(applicationContext, "Dispositivo conectado", Toast.LENGTH_SHORT).show()
                bodyText.text = "Conectado"
            }catch (e: Exception){
                Toast.makeText(applicationContext, "Não foi possível conectar: " + e.localizedMessage, Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }

    private fun setOnClicks(){
        btnBluetooth.setOnClickListener{
            bluetooth = BluetoothAdapter.getDefaultAdapter()

            if (!bluetooth.isEnabled) {
                val requestBluetooth = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                startActivityForResult(requestBluetooth, 0)
            }else{
                val intentBluetooth = Intent(this, BluetoothActivity::class.java)
                startActivity(intentBluetooth)
                this.finish()
            }
        }
    }

    private fun connectBt(address:String) {
        var btSocket: BluetoothSocket? = null
        if (btSocket == null || !isBtConnected) {
                bluetooth = BluetoothAdapter.getDefaultAdapter()
                val dispositivo = bluetooth.getRemoteDevice(address)
                btSocket = dispositivo.createInsecureRfcommSocketToServiceRecord(myUUID)
                BluetoothAdapter.getDefaultAdapter().cancelDiscovery()
                btSocket.connect()
        }
    }
}

