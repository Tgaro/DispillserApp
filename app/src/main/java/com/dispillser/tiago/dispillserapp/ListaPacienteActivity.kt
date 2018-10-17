package com.dispillser.tiago.dispillserapp

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.*
import com.dispillser.tiago.dispillserapp.Model.Header
import com.dispillser.tiago.dispillserapp.DAO.PacienteDAO
import com.dispillser.tiago.dispillserapp.Model.Paciente
import android.widget.AdapterView
import android.widget.Toast
import android.view.ContextMenu
import com.dispillser.tiago.dispillserapp.Model.CustomPacienteAdapter


@RequiresApi(28)
class ListaPacienteActivity : AppCompatActivity() {

    private lateinit var headerHelper : Header
    private lateinit var headerText : TextView
    private lateinit var pacienteAddBt : ImageButton
    private lateinit var listaPaciente : ListView
    private lateinit var pacienteBack : RelativeLayout
    private lateinit var btnPaciente : ImageButton

    @RequiresApi(28)
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_paciente)
        pacienteBack = findViewById(R.id.pacientes)
        btnPaciente = findViewById(R.id.btPacientes)
        headerText = findViewById(R.id.layoutName)
        headerText.text = "Paciente"
        headerHelper = Header(this)
        pacienteBack.setBackgroundColor(resources.getColor(R.color.colorPrimaryLight))
        btnPaciente.setBackgroundColor(resources.getColor(R.color.colorPrimaryLight))
        carregaLista(this)
        setOnClicks()
    }
    override fun onResume() {
        super.onResume()
        carregaLista(this)
    }

    fun setOnClicks(){
        pacienteAddBt = findViewById<View>(R.id.pacienteAdd) as ImageButton

        pacienteAddBt.setOnClickListener{
            var intentNovoPaciente = Intent(this, FormularioPacienteActivity::class.java)
            startActivity(intentNovoPaciente)
            this.finish()
        }
    }
    fun carregaLista(context: Activity) {
        val dao = PacienteDAO(context)
        val pacientes = dao.buscaPaciente()
        dao.close()
        val adapter = CustomPacienteAdapter(this@ListaPacienteActivity, pacientes)
        listaPaciente = findViewById(R.id.list_pacientes)
        listaPaciente.adapter = adapter
    }
}
