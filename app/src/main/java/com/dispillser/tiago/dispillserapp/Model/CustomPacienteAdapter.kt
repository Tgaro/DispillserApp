package com.dispillser.tiago.dispillserapp.Model

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.media.Image
import android.os.Build
import android.support.annotation.RequiresApi
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import com.dispillser.tiago.dispillserapp.DAO.PacienteDAO
import com.dispillser.tiago.dispillserapp.FormularioPacienteActivity
import com.dispillser.tiago.dispillserapp.ListaAgendamentoActivity
import com.dispillser.tiago.dispillserapp.ListaPacienteActivity
import com.dispillser.tiago.dispillserapp.R
@RequiresApi(Build.VERSION_CODES.P)

class CustomPacienteAdapter(private var activity: Activity, private var pacientes: List<Paciente>) : BaseAdapter() {

    //private var _pacientes = pacientes

    private class ViewHolder(row: View?) {
        var txtName: TextView? = null
        var txtComment: TextView? = null
        var editPaciente: ImageButton? = null
        var deletePaciente: ImageButton? = null
        var listaAgendamentos: ImageButton? = null
        var pacienteActivity : ListaPacienteActivity = ListaPacienteActivity()

        init {
            this.txtName = row?.findViewById(R.id.txtName)
            this.txtComment = row?.findViewById(R.id.txtComment)
            this.editPaciente = row?.findViewById(R.id.pacienteEditar)
            this.deletePaciente = row?.findViewById(R.id.pacienteExcluir)
            this.listaAgendamentos = row?.findViewById(R.id.pacienteAgendamentos)
        }
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View?
        val viewHolder: ViewHolder
        if (convertView == null) {
            val inflater = activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.paciente_list_row, null)
            viewHolder = ViewHolder(view)
            view?.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        val paciente = pacientes[position]
        viewHolder.txtName?.text = paciente.nome
        viewHolder.txtComment?.text = paciente.nascimento

        viewHolder.editPaciente?.setOnClickListener {
            val intentEdita = Intent(activity, FormularioPacienteActivity::class.java)
            intentEdita.putExtra("PACIENTE", paciente)
            activity.startActivity(intentEdita)
        }

        viewHolder.deletePaciente?.setOnClickListener{
            Toast.makeText(activity, "Paciente " + paciente.nome + " deletado.", Toast.LENGTH_SHORT).show()

            val dao = PacienteDAO(activity)
            dao.deleta(paciente)
            dao.close()
            activity.recreate()

        }
        viewHolder.listaAgendamentos?.setOnClickListener {
            val intentTelaPaciente = Intent(activity, ListaAgendamentoActivity::class.java)
            intentTelaPaciente.putExtra("PACIENTE", paciente)
            activity.startActivity(intentTelaPaciente)
        }

        return view as View
    }

    override fun getItem(position: Int): Paciente {
        return pacientes[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return pacientes.size
    }
}