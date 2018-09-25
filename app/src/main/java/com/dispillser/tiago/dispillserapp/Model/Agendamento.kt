package com.dispillser.tiago.dispillserapp.Model

import java.io.Serializable
import java.util.*

class Agendamento() : Serializable {

    init{

    }
    var id: Long? = null
    var pessoa_id: Long? = null
    var medicamento_id: Long? = null
    var nome_medicamento: String? = null
    var dose: Int? = null
    var horario: String? = null


    override fun toString(): String {
        return id.toString() + " - " + horario
    }
}