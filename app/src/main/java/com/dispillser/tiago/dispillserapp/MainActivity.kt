package com.dispillser.tiago.dispillserapp

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.dispillser.tiago.dispillserapp.Model.Header
import android.content.Intent
import android.bluetooth.BluetoothSocket
import android.os.Handler
import android.widget.*
import com.dispillser.tiago.dispillserapp.R.color.colorPrimary
import com.dispillser.tiago.dispillserapp.R.color.colorPrimaryLight
import java.io.IOException
import java.util.*
import android.widget.Toast
import java.io.InputStream
import java.io.OutputStream
import android.bluetooth.BluetoothDevice
import kotlinx.android.synthetic.main.activity_main.*


@SuppressLint("SetTextI18n")
class MainActivity : AppCompatActivity()  {

    private lateinit var headerText : TextView
    private lateinit var bodyText : TextView
    private lateinit var headerHelper : Header
    private lateinit var btnBluetooth : Button
    private lateinit var btnImportar : Button
    private lateinit var homeBack : RelativeLayout
    private lateinit var btnHome : ImageButton
    var bluetoothIn: Handler? = null

    val handlerState = 0                        //used to identify handler message
    private var btAdapter: BluetoothAdapter? = null
    private var btSocket: BluetoothSocket? = null
    private val recDataString = StringBuilder()
    private var isConnected: Boolean = false
    private var mConnectedThread: ConnectedThread? = null
    private val BTMODULEUUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb")
    private var address: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        headerText = findViewById(R.id.layoutName)
        btnBluetooth = findViewById(R.id.btnBluetooth)
        btnImportar = findViewById(R.id.btnImportar)
        bodyText = findViewById(R.id.inicioBody)
        homeBack = findViewById(R.id.home)
        btnHome = findViewById(R.id.btInicio)
        headerHelper = Header(this)
        headerText.text = "Início"
        homeBack.setBackgroundColor(resources.getColor(colorPrimaryLight))
        btnHome.setBackgroundColor(resources.getColor(colorPrimaryLight))

        setOnClicks()
        address = intent.getStringExtra("BLUETOOTH")

        if(address != null){
            connectBt(address)
            if(isConnected){
                Toast.makeText(applicationContext, "Bluetooth conectado: $address", Toast.LENGTH_SHORT).show()
                bodyText.text = "Conectado"
                bodyText.setTextColor(resources.getColor(colorPrimary))
            }
        }
    }
    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }

    public override fun onDestroy() {
        super.onDestroy()
        try {
            btSocket?.close()
        } catch (e2: Exception) {
            Toast.makeText(baseContext, "Fechar conexao falhou: ${e2.message}", Toast.LENGTH_LONG).show()
        }
    }

    private inner class ConnectedThread(socket: BluetoothSocket?) : Thread() {
        private val mmInStream: InputStream?
        private val mmOutStream: OutputStream?
        init {
            var tmpIn: InputStream? = null
            var tmpOut: OutputStream? = null
            try {
                tmpIn = socket?.inputStream
                tmpOut = socket?.outputStream
            } catch (e: Exception) {
                Toast.makeText(applicationContext, "Stream error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
            mmInStream = tmpIn
            mmOutStream = tmpOut
        }
        override fun run() {
            val buffer = ByteArray(256)
            var bytes: Int

            // Keep looping to listen for received messages
            while (true) {
                try {
                    bytes = mmInStream!!.read(buffer)            //read bytes from input buffer
                    val readMessage = String(buffer, 0, bytes)
                } catch (e: IOException) {
                    break
                }

            }
        }

        fun write(input: String) {
            val msgBuffer = input.toByteArray()           //converts entered String into bytes
            try {
                mmOutStream!!.write(msgBuffer)                //write bytes over BT connection via outstream
            } catch (e: IOException) {
                //if you cannot write, close the application
                Toast.makeText(baseContext, "Connection Failure", Toast.LENGTH_LONG).show()
                finish()
            }
        }
    }

    private fun setOnClicks(){
        btnBluetooth.setOnClickListener{
            val btAdapter = BluetoothAdapter.getDefaultAdapter()

            if (!btAdapter.isEnabled) {
                val requestBluetooth = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                startActivityForResult(requestBluetooth, 0)
            }else{
                val intentBluetooth = Intent(this, BluetoothActivity::class.java)
                startActivity(intentBluetooth)
                this.finish()
            }
        }

        btnImportar.setOnClickListener{
            Toast.makeText(baseContext, "Send x", Toast.LENGTH_SHORT).show()
            mConnectedThread?.write("x")
        }
    }

    @Throws(Exception::class)
    private fun createBluetoothSocket(device: BluetoothDevice?): BluetoothSocket {
        return device!!.createRfcommSocketToServiceRecord(BTMODULEUUID)
    }

    @Throws(IOException::class)
    private fun connectBt(address:String?) {
        btAdapter = BluetoothAdapter.getDefaultAdapter()

        val device = btAdapter?.getRemoteDevice(address)
        try {
            btSocket = createBluetoothSocket(device)
        } catch (e: Exception) {
            Toast.makeText(baseContext, "Socket creation failed ${e.message}", Toast.LENGTH_LONG).show()
        }
        try {
            btSocket?.connect()
            isConnected = true
        } catch (e: Exception) {
            Toast.makeText(baseContext, "Conexao falha: ${e.message}", Toast.LENGTH_LONG).show()
            try {
                btSocket?.close()
            } catch (e2: Exception) {

            }
        }
        mConnectedThread = ConnectedThread(btSocket)
        mConnectedThread?.start()
    }
}

