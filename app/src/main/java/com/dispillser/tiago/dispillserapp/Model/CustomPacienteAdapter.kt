package com.dispillser.tiago.dispillserapp.Model

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.dispillser.tiago.dispillserapp.R

class CustomPacienteAdapter(private var activity: Activity, private var pacientes: List<Paciente>) : BaseAdapter() {

    //private var _pacientes = pacientes

    private class ViewHolder(row: View?) {
        var txtName: TextView? = null
        var txtComment: TextView? = null

        init {
            this.txtName = row?.findViewById(R.id.txtName)
            this.txtComment = row?.findViewById(R.id.txtComment)
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