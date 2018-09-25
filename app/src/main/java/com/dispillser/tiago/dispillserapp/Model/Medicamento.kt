package com.dispillser.tiago.dispillserapp.Model

import java.io.Serializable
import java.util.*

class Medicamento() : Serializable {

    init{

    }
    var id: Long? = null
    var nome: String? = null


    override fun toString(): String {
        return id.toString() + " - " + nome
    }
}