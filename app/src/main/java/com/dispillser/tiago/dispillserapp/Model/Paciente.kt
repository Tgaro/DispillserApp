package com.dispillser.tiago.dispillserapp.Model

import java.io.Serializable
import java.util.*

class Paciente() : Serializable {

    init{

    }
    var id: Long? = null
    var nome: String? = null
    var nascimento: String? = null
    var nomeResp: String? = null
    var telefoneResp: String? = null
    var emailResp: String? = null

    override fun toString(): String {
        return id.toString() + " - " + nome
    }
}