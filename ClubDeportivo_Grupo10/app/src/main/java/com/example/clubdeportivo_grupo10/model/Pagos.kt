package com.example.clubdeportivo_grupo10.model

import java.io.Serializable

data class Pagos(
    val id: Int,
    val usuario: Int,
    val fechaVencimiento: String,
    val fechaPago: String?,
    val importe: Double
): Serializable
