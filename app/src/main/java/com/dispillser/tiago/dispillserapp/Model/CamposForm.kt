package com.dispillser.tiago.dispillserapp.Model

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import com.dispillser.tiago.dispillserapp.MaskEditUtil
import com.dispillser.tiago.dispillserapp.R

class CamposForm(activity: Activity) : AppCompatActivity() {

    private var campoNome: EditText = activity.findViewById<View>(R.id.pacienteNome) as EditText
    private var campoNascimento: EditText = activity.findViewById<View>(R.id.pacienteNasc) as EditText
    private var campoNomeResp: EditText = activity.findViewById<View>(R.id.pacienteNomeResp) as EditText
    private var campoTelResp: EditText = activity.findViewById<View>(R.id.pacienteTelResp) as EditText
    private var campoEmailResp: EditText = activity.findViewById<View>(R.id.pacienteEmailResp) as EditText
    private lateinit var paciente : Paciente

    init {
    }

    fun getPaciente(id: Long?) : Paciente {
        paciente = Paciente()
        paciente.id = id
        paciente.nome = campoNome.text.toString()
        paciente.nascimento = campoNascimento.text.toString()
        paciente.nomeResp = campoNomeResp.text.toString()
        paciente.telefoneResp = MaskEditUtil.unmask(campoTelResp.text.toString())
        paciente.emailResp = campoEmailResp.text.toString()

        return paciente
    }

    fun setPaciente(paciente: Paciente?){
        campoNome.setText(paciente?.nome)
        campoNascimento.setText(paciente?.nascimento)
        campoNomeResp.setText(paciente?.nomeResp)
        campoTelResp.setText(paciente?.telefoneResp)
        campoEmailResp.setText(paciente?.emailResp)

    }

    fun setErrors(){

        if(campoNascimento.text.toString().isEmpty() || !validateDate(campoNascimento.text.toString())){
            campoNascimento.error = "Entre uma data válida."
        }else
            campoNascimento.error = null

        if(campoNome.text.toString().isEmpty()){
            campoNome.error = "Entre um nome."
        }else
            campoNome.error = null
    }

    fun checkErrors() : Boolean{
        return ! (campoNascimento.error.isNullOrBlank() &&
                  campoNome.error.isNullOrBlank())
    }

    private fun validateDate(date: String):Boolean{

        if(date.length == 10){
            val day : String = date.split("/")[0]
            val month : String = date.split("/")[1]
            val year : Int = date.split("/")[2].toInt()

            return if (day == "31" && (month == "11" || month == "04" || month == "06" || month == "09")){
                false // only 1,3,5,7,8,10,12 has 31 days
            }
            else if (month == "02") {
                if(year % 4==0){
                    !(day == "30" || day == "31")
                }else{
                    !(day == "29" || day == "30" || day == "31")
                }
            }else{
                true
            }
        }else
            return false
    }
}

