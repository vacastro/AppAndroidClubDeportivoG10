package com.example.clubdeportivo_grupo10.model

import java.io.Serializable

data class Contrato(

    val id: Int,
    val usuario: Int,
    val actividad: String,
    val fechaAlta: String

): Serializable
