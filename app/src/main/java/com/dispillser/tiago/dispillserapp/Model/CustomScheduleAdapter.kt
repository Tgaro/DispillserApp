package com.dispillser.tiago.dispillserapp.Model

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.annotation.RequiresApi
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import com.dispillser.tiago.dispillserapp.AgendamentoActivity
import com.dispillser.tiago.dispillserapp.DAO.AgendamentoDAO
import com.dispillser.tiago.dispillserapp.FormularioPacienteActivity
import com.dispillser.tiago.dispillserapp.ListaAgendamentoActivity
import com.dispillser.tiago.dispillserapp.R
@RequiresApi(Build.VERSION_CODES.P)
class CustomScheduleAdapter(private var activity: Activity, private var agendamentos: List<Agendamento>, private var paciente: Paciente?) : BaseAdapter() {

    private class ViewHolder(row: View?) {
        var txtName: TextView? = null
        var txtComment: TextView? = null
        var editAgendamento: ImageButton? = null
        var deleteAgendamento: ImageButton? = null
        var agendamentoActivity: ListaAgendamentoActivity = ListaAgendamentoActivity()

        init {
            this.txtName = row?.findViewById(R.id.txtName)
            this.txtComment = row?.findViewById(R.id.txtComment)
            this.editAgendamento = row?.findViewById(R.id.agendamentoEditar)
            this.deleteAgendamento = row?.findViewById(R.id.agendamentoExcluir)
        }
    }


    @SuppressLint("SetTextI18n")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View?
        val viewHolder: ViewHolder
        if (convertView == null) {
            val inflater = activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.schedule_list_row, null)
            viewHolder = ViewHolder(view)
            view?.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        val agendamento = agendamentos[position]
        viewHolder.txtName?.text = agendamento.nome_medicamento
        viewHolder.txtComment?.text = "Dosagem: " + agendamento.dose

        viewHolder.editAgendamento?.setOnClickListener {
            val intentSchedule = Intent(activity, AgendamentoActivity::class.java)
            intentSchedule.putExtra("AGENDAMENTO", agendamento)
            intentSchedule.putExtra("PACIENTE", paciente)
            activity.startActivity(intentSchedule)
        }
        viewHolder.deleteAgendamento?.setOnClickListener{
            val dao = AgendamentoDAO(activity)
            dao.deleta(agendamento)
            dao.close()
            Toast.makeText(activity, "Agendamento deletado.", Toast.LENGTH_SHORT).show()
            viewHolder.agendamentoActivity.carregaLista()
        }

        return view as View
    }

    override fun getItem(position: Int): Agendamento {
        return agendamentos[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return agendamentos.size
    }

}