package com.dispillser.tiago.dispillserapp.DAO

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.support.annotation.RequiresApi
import android.util.Log
import com.dispillser.tiago.dispillserapp.Model.Agendamento
import com.dispillser.tiago.dispillserapp.Model.Medicamento
import com.dispillser.tiago.dispillserapp.Model.Paciente


@RequiresApi(28)
class AgendamentoDAO(context: Context) : SQLiteOpenHelper(context, "Agendamento", null, 5){

    override fun onCreate(db: SQLiteDatabase) {
        val sql = "CREATE TABLE Agendamento (agendamento_id INTEGER PRIMARY KEY AUTOINCREMENT, paciente_id INTEGER, nome_paciente TEXT, medicamento_id INTEGER, nome_medicamento TEXT, dose INTEGER, horario TEXT)"
        db.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        val sql = "DROP TABLE IF EXISTS Agendamento"
        db.execSQL(sql)
        onCreate(db)
    }

    fun insere(paciente: Paciente?, medicamento: Medicamento, horario: String, dose: Int?) {
        val db = writableDatabase

        val dados = dadosAgendamento(paciente, medicamento, horario, dose)

        db.insert("Agendamento", null, dados)
    }

    fun atualiza(paciente: Paciente?, medicamento: Medicamento, horario: String, dose: Int?, agendamento: Agendamento?) {
        val db = writableDatabase

        val dados = dadosAgendamento(paciente, medicamento, horario, dose)

        val params = arrayOf<String>(agendamento?.id.toString())
        db.update("Agendamento", dados, "agendamento_id = ?", params)
    }

    private fun dadosAgendamento(paciente: Paciente?, medicamento: Medicamento, horario: String?, dose: Int?): ContentValues {
        val dados = ContentValues()
        dados.put("paciente_id", paciente?.id)
        dados.put("nome_paciente", paciente?.nome)
        dados.put("medicamento_id", medicamento.id)
        dados.put("nome_medicamento", medicamento.nome)
        dados.put("dose", dose)
        dados.put("horario", horario)
        return dados
    }

    fun buscaAgendamentos(paciente_id: Long?): List<Agendamento> {
        val sql = "SELECT * FROM Agendamento WHERE paciente_id = ? ;"
        val db = readableDatabase
        val params = arrayOf(paciente_id?.toString())
        val c = db.rawQuery(sql, params)

        val agendamentos = ArrayList<Agendamento>()
        while (c.moveToNext()) {
            val agendamento = Agendamento()
            agendamento.id = (c.getLong(c.getColumnIndex("agendamento_id")))
            agendamento.pessoa_id = (c.getLong(c.getColumnIndex("paciente_id")))
            agendamento.nome_paciente = (c.getString(c.getColumnIndex("nome_paciente")))
            agendamento.medicamento_id = (c.getLong(c.getColumnIndex("medicamento_id")))
            agendamento.nome_medicamento = (c.getString(c.getColumnIndex("nome_medicamento")))
            agendamento.dose = (c.getInt(c.getColumnIndex("dose")))
            agendamento.horario = (c.getString(c.getColumnIndex("horario")))

            agendamentos.add(agendamento)
        }
        c.close()
        return agendamentos
    }

    fun deleta(agendamento: Agendamento) {
        val db = writableDatabase
        val params = arrayOf(agendamento.id.toString())
        db.delete("Agendamento", "agendamento_id = ?", params)
    }

    fun enviaDispositivo(): List<String>{

        val sql = "SELECT * FROM Agendamento;"
        val db = readableDatabase
        val c = db.rawQuery(sql, null)

        val lista = ArrayList<String>()

        while (c.moveToNext()) {
            val sb = StringBuilder()
            sb.append(c.getLong(c.getColumnIndex("agendamento_id")).toString())
            sb.append("|")
            sb.append(c.getLong(c.getColumnIndex("paciente_id")).toString())
            sb.append("|")
            sb.append(c.getLong(c.getColumnIndex("nome_paciente")).toString())
            sb.append("|")
            sb.append(c.getLong(c.getColumnIndex("medicamento_id")).toString())
            sb.append("|")
            sb.append(c.getString(c.getColumnIndex("nome_medicamento")))
            sb.append("|")
            sb.append(c.getInt(c.getColumnIndex("dose")).toString())
            sb.append("|")
            val minutes = toMinutes(c.getString(c.getColumnIndex("horario")))
            sb.append(minutes.toString())

            lista.add(sb.toString())
        }
        c.close()

        return lista
    }

    fun toMinutes(horario: String) : Int{
        val horas = horario.substring(0, horario.indexOf(":")).toInt()
        Log.d("Horas", horas.toString())
        val minutos = horario.substring(horario.indexOf(":") + 1).toInt()
        Log.d("Minutos", minutos.toString())
        Log.d("Total", ((horas*60) + minutos).toString())
        return (horas*60) + minutos
    }
}