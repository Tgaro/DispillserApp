package com.dispillser.tiago.dispillserapp
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.*


class BluetoothActivity : AppCompatActivity(){

    private lateinit var cancelarButton: ImageButton
    private lateinit var btList : ListView
    private lateinit var bluetooth : BluetoothAdapter
    private var pairedDevices: Set<BluetoothDevice>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bluetooth_list)
        cancelarButton = findViewById(R.id.bluetoothCancelar)
        btList = findViewById(R.id.btList)
        pairedDevicesList()
        setOnClicks()
    }

    private fun pairedDevicesList() {
        bluetooth = BluetoothAdapter.getDefaultAdapter()
        pairedDevices = bluetooth.bondedDevices
        val list = arrayListOf<String>()

        if ((pairedDevices as MutableSet<BluetoothDevice>?)!!.size > 0) {
            for (bt in (pairedDevices as MutableSet<BluetoothDevice>?)!!) {
                list.add(bt.name + "\n" + bt.address) //Get the device's name and the address
            }
        } else {
            Toast.makeText(applicationContext, "No Paired Bluetooth Devices Found.", Toast.LENGTH_LONG).show()
        }

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, list)
        btList.adapter = adapter

        btList.onItemClickListener = AdapterView.OnItemClickListener{ _, v, _, _ ->

            val info = (v as TextView).text.toString()
            val address = info.substring(info.length - 17)

            val i = Intent(this, MainActivity::class.java)
            i.putExtra("BLUETOOTH", address)
            startActivity(i)
            this.finish()
        }
    }

    private fun setOnClicks(){
        cancelarButton.setOnClickListener{
            val intentMain = Intent(this, MainActivity::class.java)
            startActivity(intentMain)
            this.finish()
        }
    }
}