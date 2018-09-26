package com.dispillser.tiago.dispillserapp.Model

import android.app.Activity
import android.app.TimePickerDialog
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.*
import com.dispillser.tiago.dispillserapp.MaskEditUtil
import com.dispillser.tiago.dispillserapp.R

class FormAgendamento(activity: Activity) : AppCompatActivity() {

    lateinit var paciente : Paciente
    var horario : EditText = activity.findViewById(R.id.calendarioDate)
    var pacienteNome: TextView = activity.findViewById(R.id.pacienteCalendarioNome)
    var medicamento_combo : Spinner = activity.findViewById(R.id.medicamento)
    var dosagem_combo : Spinner = activity.findViewById(R.id.dosagem)
    var medicamentoText : TextView = activity.findViewById(R.id.medicamentoText)
    var dosagemText : TextView = activity.findViewById(R.id.dosagemText)
    var confirmaButton : ImageButton = activity.findViewById(R.id.calendarioSalvar)
    var cancelaButton: ImageButton = activity.findViewById(R.id.calendarioCancelar)

    init {
    }

    fun setAgendamento(agendamento: Agendamento?){
        horario.setText(agendamento?.horario)
        medicamento_combo.setSelection(agendamento?.medicamento_id!!.toInt())
        dosagem_combo.setSelection(agendamento?.dose!!.toInt())

    }

    fun setErrors(){

        if(horario.text.toString().isEmpty() ){
            horario.error = "Entre uma data válida."
        }else
            horario.error = null

        if(dosagem_combo.selectedItem == 0){
            dosagemText.error = "Selecione uma dosagem."
        }else
            dosagemText.error = null

        if(medicamento_combo.selectedItem == ""){
            medicamentoText.error = "Selecione um medicamento."
        }else
            medicamentoText.error = null
    }

    fun checkErrors() : Boolean{
        return ! (horario.error.isNullOrBlank() &&
                dosagemText.error.isNullOrBlank() &&
                medicamentoText.error.isNullOrBlank())
    }

}

