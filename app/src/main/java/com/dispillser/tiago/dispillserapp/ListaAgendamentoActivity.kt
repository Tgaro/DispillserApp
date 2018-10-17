package com.dispillser.tiago.dispillserapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity
import android.widget.*
import com.dispillser.tiago.dispillserapp.DAO.AgendamentoDAO
import com.dispillser.tiago.dispillserapp.Model.CustomAgendamentoAdapter
import com.dispillser.tiago.dispillserapp.Model.Paciente
@RequiresApi(28)
class ListaAgendamentoActivity : AppCompatActivity(){


    lateinit var pacienteNome : TextView
    lateinit var editPaciente : ImageButton
    lateinit var voltarButton: ImageButton
    lateinit var adicionaAgendamento: ImageButton
    lateinit var listaMedicamento : ListView
    var paciente : Paciente? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_agendamentos)
        pacienteNome = findViewById(R.id.pacienteTextNome)
        //editPaciente = findViewById(R.id.pacienteEditar)
        adicionaAgendamento = findViewById(R.id.adicionaAgendamento)
        voltarButton = findViewById(R.id.agendamentoVoltar)

        paciente = intent.getSerializableExtra("PACIENTE") as Paciente?
        pacienteNome.text = "${paciente?.nome}"

        setOnClicks()
        carregaLista(paciente?.id, this)
    }

    override fun onResume() {
        super.onResume()
        carregaLista(paciente?.id, this)
    }

    private fun setOnClicks(){
        voltarButton.setOnClickListener {
            val intentVolta = Intent(this, ListaPacienteActivity::class.java)
            startActivity(intentVolta)
            this.finish()
        }
        adicionaAgendamento.setOnClickListener{
            val intentAdiciona = Intent(this, FormularioAgendamentoActivity::class.java)
            intentAdiciona.putExtra("PACIENTE", paciente)
            startActivity(intentAdiciona)
        }
    }

    fun carregaLista(id : Long?, context: Activity) {
        val dao = AgendamentoDAO(context)
        val agendamentos = dao.buscaAgendamentos(id)
        dao.close()
        val adapter = CustomAgendamentoAdapter(this@ListaAgendamentoActivity, agendamentos, paciente)
        listaMedicamento = findViewById(R.id.list_medicamentos)
        listaMedicamento.adapter = adapter
    }
}