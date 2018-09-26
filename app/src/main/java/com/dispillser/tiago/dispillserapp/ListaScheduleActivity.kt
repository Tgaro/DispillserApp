package com.dispillser.tiago.dispillserapp

import android.content.Intent
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity
import android.widget.*
import com.dispillser.tiago.dispillserapp.DAO.AgendamentoDAO
import com.dispillser.tiago.dispillserapp.Model.Agendamento
import com.dispillser.tiago.dispillserapp.Model.CustomScheduleAdapter
import com.dispillser.tiago.dispillserapp.Model.Paciente
@RequiresApi(28)
class ListaScheduleActivity : AppCompatActivity(){


    lateinit var pacienteNome : TextView
    lateinit var editPaciente : ImageButton
    lateinit var voltarButton: ImageButton
    lateinit var adicionaAgendamento: ImageButton
    lateinit var listaMedicamento : ListView
    var agendamentoDAO : AgendamentoDAO = AgendamentoDAO(this)
    var paciente : Paciente? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_agendamentos)
        pacienteNome = findViewById(R.id.pacienteTextNome)
        listaMedicamento = findViewById(R.id.list_medicamentos)
        editPaciente = findViewById(R.id.pacienteEditar)
        adicionaAgendamento = findViewById(R.id.adicionaAgendamento)
        voltarButton = findViewById(R.id.agendamentoVoltar)

        paciente = intent.getSerializableExtra("PACIENTE") as Paciente?
        pacienteNome.text = "${paciente?.nome}"

        listaMedicamento.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val agendamento = listaMedicamento.getItemAtPosition(position) as Agendamento
            val intentSchedule = Intent(this, ScheduleActivity::class.java)
            intentSchedule.putExtra("AGENDAMENTO", agendamento)
            intentSchedule.putExtra("PACIENTE", paciente)
            startActivity(intentSchedule)
        }

        setOnClicks()
        carregaLista()
    }

    override fun onResume() {
        super.onResume()
        carregaLista()
    }

    private fun setOnClicks(){
        editPaciente.setOnClickListener {
            val intentEdita = Intent(this, FormularioPacienteActivity::class.java)
            intentEdita.putExtra("PACIENTE", paciente)
            startActivity(intentEdita)

        }
        voltarButton.setOnClickListener {
            val intentVolta = Intent(this, ListaPacienteActivity::class.java)
            startActivity(intentVolta)
            this.finish()
        }
        adicionaAgendamento.setOnClickListener{
            val intentAdiciona = Intent(this, ScheduleActivity::class.java)
            intentAdiciona.putExtra("PACIENTE", paciente)
            startActivity(intentAdiciona)

        }
    }

    private fun carregaLista() {
        val agendamentos = agendamentoDAO.buscaAgendamentos(paciente?.id)
        agendamentoDAO.close()
        val adapter = CustomScheduleAdapter(this, agendamentos)
        listaMedicamento.adapter = adapter
    }
}