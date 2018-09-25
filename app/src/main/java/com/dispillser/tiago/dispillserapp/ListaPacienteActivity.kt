package com.dispillser.tiago.dispillserapp

import android.annotation.SuppressLint
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


class ListaPacienteActivity : AppCompatActivity() {

    private lateinit var headerHelper : Header
    private lateinit var headerText : TextView
    private lateinit var pacienteAddBt : ImageButton
    private lateinit var listaPaciente : ListView

    @RequiresApi(28)
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_paciente)

        headerText = findViewById(R.id.layoutName)
        headerText.text = "Paciente"
        headerHelper = Header(this)
        listaPaciente = findViewById(R.id.list_pacientes)

        listaPaciente.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val paciente = listaPaciente.getItemAtPosition(position) as Paciente
            val intentTelaPaciente = Intent(this, ListaScheduleActivity::class.java)
            intentTelaPaciente.putExtra("PACIENTE", paciente)
            startActivity(intentTelaPaciente)
        }
        carregaLista()
        setOnClicks()

        registerForContextMenu(listaPaciente)

    }

    @RequiresApi(28)
    override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenu.ContextMenuInfo) {
        val deletar = menu.add("Excluir")
        deletar.setOnMenuItemClickListener {
            val info = menuInfo as AdapterView.AdapterContextMenuInfo
            val paciente = listaPaciente.getItemAtPosition(info.position) as Paciente
            Toast.makeText(this@ListaPacienteActivity, "Paciente " + paciente.nome + " deletado.", Toast.LENGTH_SHORT).show()

            val dao = PacienteDAO(this@ListaPacienteActivity)
            dao.deleta(paciente)
            dao.close()

            carregaLista()
            false
        }
    }

    @RequiresApi(28)
    override fun onResume() {
        super.onResume()
        carregaLista()
    }

    fun setOnClicks(){
        pacienteAddBt = findViewById<View>(R.id.pacienteAdd) as ImageButton

        pacienteAddBt.setOnClickListener{
            var intentNovoPaciente = Intent(this, FormularioPacienteActivity::class.java)
            startActivity(intentNovoPaciente)
            this.finish()
        }
    }

    @RequiresApi(28)
    fun carregaLista() {
        val dao = PacienteDAO(this)
        val pacientes = dao.buscaPaciente()
        dao.close()
        val adapter = CustomPacienteAdapter(this, pacientes)
        listaPaciente.adapter = adapter
    }

}
