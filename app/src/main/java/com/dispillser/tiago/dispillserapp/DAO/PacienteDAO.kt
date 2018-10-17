package com.dispillser.tiago.dispillserapp.DAO

import android.database.sqlite.SQLiteOpenHelper
import android.content.ContentValues
import android.content.Context
import android.support.annotation.NonNull
import android.database.sqlite.SQLiteDatabase
import android.support.annotation.RequiresApi
import com.dispillser.tiago.dispillserapp.Model.Paciente
import java.lang.reflect.Array.getDouble



@RequiresApi(28)
class PacienteDAO(context: Context) : SQLiteOpenHelper(context, "Paciente", null, 3){

    override fun onCreate(db: SQLiteDatabase) {
        val sql = "CREATE TABLE Paciente (paciente_id INTEGER PRIMARY KEY AUTOINCREMENT, nome TEXT NOT NULL, nascimento TEXT, nomeResp TEXT, telefoneResp TEXT, emailResp TEXT)"
        db.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        val sql = "DROP TABLE IF EXISTS Paciente"
        db.execSQL(sql)
        onCreate(db)
    }

    fun insere(paciente: Paciente?): Long {
        val db = writableDatabase

        val dados = pegaDadosPaciente(paciente)

        db.insert("Paciente", null, dados)

        val sql = "SELECT paciente_id FROM Paciente ORDER BY paciente_id DESC LIMIT 1;"

        val c = db.rawQuery(sql, null)
        return if(c.moveToLast())
            c.getLong(0)
        else
            0
    }

    fun atualiza(paciente: Paciente?) {
        val db = writableDatabase

        val dados = pegaDadosPaciente(paciente)

        val params = arrayOf<String>(paciente?.id.toString())
        db.update("Paciente", dados, "paciente_id = ?", params)
    }

    private fun pegaDadosPaciente(paciente: Paciente?): ContentValues {
        val dados = ContentValues()
        dados.put("nome", paciente?.nome)
        dados.put("nascimento", paciente?.nascimento.toString())
        dados.put("nomeResp", paciente?.nomeResp)
        dados.put("telefoneResp", paciente?.telefoneResp)
        dados.put("emailResp", paciente?.emailResp)
        return dados
    }

    fun buscaPaciente(): List<Paciente> {
        val sql = "SELECT * from Paciente;"
        val db = readableDatabase
        val c = db.rawQuery(sql, null)

        val pacientes = ArrayList<Paciente>()
        while (c.moveToNext()) {
            val paciente = Paciente()
            paciente.id = (c.getLong(c.getColumnIndex("paciente_id")))
            paciente.nome = (c.getString(c.getColumnIndex("nome")))
            paciente.nascimento = (c.getString(c.getColumnIndex("nascimento")))
            paciente.nomeResp = (c.getString(c.getColumnIndex("nomeResp")))
            paciente.telefoneResp = (c.getString(c.getColumnIndex("telefoneResp")))
            paciente.emailResp = (c.getString(c.getColumnIndex("emailResp")))
            pacientes.add(paciente)
        }
        c.close()
        return pacientes
    }

    fun deleta(paciente: Paciente) {
        val db = writableDatabase
        val params = arrayOf(paciente.id.toString())
        db.delete("Paciente", "paciente_id = ?", params)
    }

    fun enviaDispositivo(): List<String>{
        val sql = "SELECT * from Paciente;"
        val db = readableDatabase
        val c = db.rawQuery(sql, null)

        val lista = ArrayList<String>()

        while (c.moveToNext()) {
            val sb = StringBuilder()
            sb.append(c.getLong(c.getColumnIndex("paciente_id")).toString())
            sb.append("|")
            sb.append(c.getString(c.getColumnIndex("nome")))
            sb.append("|")
            sb.append(c.getString(c.getColumnIndex("nascimento")))
            sb.append("|")
            sb.append(c.getString(c.getColumnIndex("nomeResp")))
            sb.append("|")
            sb.append(c.getString(c.getColumnIndex("telefoneResp")))
            sb.append("|")
            sb.append(c.getString(c.getColumnIndex("emailResp")))

            lista.add(sb.toString())
        }
        c.close()

        return lista
    }
}
