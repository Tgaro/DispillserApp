package com.dispillser.tiago.dispillserapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.dispillser.tiago.dispillserapp.Model.Paciente
import android.app.TimePickerDialog
import android.content.Intent
import android.support.annotation.RequiresApi
import android.view.View
import android.widget.*
import com.dispillser.tiago.dispillserapp.DAO.AgendamentoDAO
import com.dispillser.tiago.dispillserapp.Model.Agendamento
import com.dispillser.tiago.dispillserapp.Model.FormAgendamento
import com.dispillser.tiago.dispillserapp.Model.Medicamento
import java.util.*
import java.text.SimpleDateFormat
@RequiresApi(28)
class FormularioAgendamentoActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener{

    var myCalendar = Calendar.getInstance()
    var agendamento : Agendamento? = null
    var paciente : Paciente? = null
    lateinit var horario : EditText
    lateinit var pacienteNome: TextView
    lateinit var timePicker: TimePickerDialog
    lateinit var confirmaButton : ImageButton
    lateinit var cancelaButton: ImageButton
    lateinit var medicamento_combo : Spinner
    lateinit var dosagem_combo : Spinner
    lateinit var medicamentoText : TextView
    lateinit var dosagemText : TextView
    lateinit var formHelper : FormAgendamento
    var dosagemSel : Int? = 0
    var agendamentoDAO : AgendamentoDAO = AgendamentoDAO(this)
    var medicamento : Medicamento = Medicamento()
    var lista_Medicamentos = arrayOf("", "Rivotril", "Coristina", "Buscopan", "Aspirina")
    var lista_Dosagem = arrayOf(0,1,2,3)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agendamento)
        formHelper = FormAgendamento(this)
        //Recupera paciente
        paciente = intent.getSerializableExtra("PACIENTE") as Paciente?
        agendamento = intent.getSerializableExtra("AGENDAMENTO") as Agendamento?
        //Define cliques
        pacienteNome = findViewById(R.id.pacienteCalendarioNome)
        horario = findViewById(R.id.calendarioDate)
        confirmaButton = findViewById(R.id.calendarioSalvar)
        cancelaButton = findViewById(R.id.calendarioCancelar)
        medicamentoText = findViewById(R.id.medicamentoText)
        dosagemText = findViewById(R.id.dosagemText)

        //Seta nome do paciente
        pacienteNome.text = "${pacienteNome.text}${paciente?.nome}"
        //Define horário
        val timer: TimePickerDialog.OnTimeSetListener = TimePickerDialog.OnTimeSetListener{ _, hour, minute ->
            myCalendar.set(Calendar.HOUR_OF_DAY, hour)
            myCalendar.set(Calendar.MINUTE, minute)
            updateHorario()
        }
        timePicker = TimePickerDialog(this, timer, myCalendar.get(Calendar.HOUR_OF_DAY)
                                                  , myCalendar.get(Calendar.MINUTE), true)
        //Define clique do edittext de horário
        horario.setOnClickListener {
            timePicker.show()
        }

        medicamento_combo = findViewById(R.id.medicamento)
        dosagem_combo = findViewById(R.id.dosagem)

        medicamento_combo.onItemSelectedListener = this
        val adapterMed = ArrayAdapter(this, android.R.layout.simple_spinner_item, lista_Medicamentos)
        adapterMed.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        medicamento_combo.adapter = adapterMed

        dosagem_combo.onItemSelectedListener = this
        val adapterDos = ArrayAdapter(this, android.R.layout.simple_spinner_item, lista_Dosagem)
        adapterDos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        dosagem_combo.adapter = adapterDos

        setOnClicks()
        if(agendamento != null){
            formHelper.setAgendamento(agendamento)
        }
        
    }

    override fun onItemSelected(arg0: AdapterView<*>, arg1: View, position: Int, id: Long) {
        when(arg0.id){
            R.id.medicamento -> {
                medicamento.id = position.toLong()
                medicamento.nome  = lista_Medicamentos[position]
            }
            R.id.dosagem -> dosagemSel  = lista_Dosagem[position]
        }
    }

    override fun onNothingSelected(arg0: AdapterView<*>) {

    }

    private fun updateHorario() {
        val myFormat = "HH:mm" //In which you need put here
        val sdf = SimpleDateFormat(myFormat, Locale("pt", "BR"))
        horario.setText(sdf.format(myCalendar.time))
    }

    private fun setOnClicks(){
        cancelaButton.setOnClickListener{
            val intentCancela = Intent(this, ListaAgendamentoActivity::class.java)
            intentCancela.putExtra("PACIENTE", paciente)
            startActivity(intentCancela)
            this.finish()
        }
        confirmaButton.setOnClickListener{

            formHelper.setErrors()
            if(!formHelper.checkErrors()){
                if(agendamento == null){
                    agendamentoDAO.insere(paciente, medicamento, horario.text.toString(), dosagemSel)
                    Toast.makeText(this, "Agendamento salvo!", Toast.LENGTH_SHORT).show()
                }else{
                    agendamentoDAO.atualiza(paciente, medicamento, horario.text.toString(), dosagemSel, agendamento)
                    Toast.makeText(this, "Agendamento atualizado!", Toast.LENGTH_SHORT).show()
                }
                val intentConfirma = Intent(this, ListaAgendamentoActivity::class.java)
                intentConfirma.putExtra("PACIENTE", paciente)
                startActivity(intentConfirma)
                this.finish()
            }
        }
    }
}