package com.dispillser.tiago.dispillserapp
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.view.View
import android.widget.*
import com.dispillser.tiago.dispillserapp.DAO.PacienteDAO
import com.dispillser.tiago.dispillserapp.Model.Paciente
import com.dispillser.tiago.dispillserapp.Model.FormPaciente


class FormularioPacienteActivity : AppCompatActivity(){
    private lateinit var camposForm : FormPaciente
    private lateinit var headerText : TextView
    private lateinit var pacienteSalvarBt : ImageButton
    private lateinit var pacienteCancelarBt : ImageButton
    private lateinit var campoNascimento : EditText
    private lateinit var campoTelefone : EditText
    private var paciente : Paciente? = null

    @RequiresApi(28)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulario_paciente)
        camposForm = FormPaciente(this)
        headerText = findViewById<View>(R.id.layoutName) as TextView
        headerText.text = "Adicionar Paciente"

        paciente  = intent.getSerializableExtra("PACIENTE") as Paciente?

        if(paciente != null){
            camposForm.setPaciente(paciente)
        }
        setOnClicks()
        setFieldMasks()
    }

    private fun setFieldMasks(){
        campoNascimento = findViewById<View>(R.id.pacienteNasc) as EditText
        campoTelefone = findViewById<View>(R.id.pacienteTelResp) as EditText

        campoNascimento.addTextChangedListener(MaskEditUtil.mask(campoNascimento, MaskEditUtil.FORMAT_DATE))
        campoTelefone.addTextChangedListener(MaskEditUtil.mask(campoTelefone, MaskEditUtil.FORMAT_FONE))
    }

    @RequiresApi(28)
    fun setOnClicks(){
        pacienteSalvarBt = findViewById(R.id.pacienteSalvar)
        pacienteCancelarBt = findViewById(R.id.pacienteCancelar)
        //Salvar
        pacienteSalvarBt.setOnClickListener{
            camposForm.setErrors()
            if(!camposForm.checkErrors()){

                val pacienteId : Long? = paciente?.id

                paciente = camposForm.getPaciente(pacienteId)
                val dao = PacienteDAO(this)

                if (paciente?.id != null) {
                    dao.atualiza(paciente)
                    Toast.makeText(this, "Paciente " + paciente?.nome + " atualizado!", Toast.LENGTH_SHORT).show()
                } else {
                    paciente?.id = dao.insere(paciente)
                    Toast.makeText(this, "Paciente " + paciente?.nome + " salvo!", Toast.LENGTH_SHORT).show()
                }
                dao.close()

                var intentCadastrarHorario = Intent(this, AgendamentoActivity::class.java)
                intentCadastrarHorario.putExtra("PACIENTE", paciente)
                startActivity(intentCadastrarHorario)

                this.finish()
            }
        }
        //Voltar/Cancelar
        pacienteCancelarBt.setOnClickListener{
            var interntCancelarPaciente = Intent(this, ListaPacienteActivity::class.java)
            startActivity(interntCancelarPaciente)
            this.finish()
        }
    }
}
