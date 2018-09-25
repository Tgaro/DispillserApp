package com.dispillser.tiago.dispillserapp.Model

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.dispillser.tiago.dispillserapp.R

class CustomScheduleAdapter(private var activity: Activity, private var agendamentos: List<Agendamento>) : BaseAdapter() {

    private class ViewHolder(row: View?) {
        var txtName: TextView? = null
        var txtComment: TextView? = null

        init {
            this.txtName = row?.findViewById(R.id.txtName)
            this.txtComment = row?.findViewById(R.id.txtComment)
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