package com.example.clubdeportivo_grupo10

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.clubdeportivo_grupo10.model.Pagos

class ListadoCuotasActivity : AppCompatActivity() {
    lateinit var clubDeportivo: sqlHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_listado_cuotas)

        clubDeportivo= sqlHelper(this)

        // Supongamos que obtenemos una lista de Pagos (Cuotas Pagadas)
        val listaPagos = clubDeportivo.obtenerPagosPorUsuario(1)

        val linearLayoutCuotas = findViewById<LinearLayout>(R.id.linearLayoutCuotas)

        // Iteramos sobre la lista de Pagos y creamos vistas dinámicamente
        for (pago in listaPagos) {
            val itemView = layoutInflater.inflate(R.layout.item_cuota_pagada, null)

            val textViewFechaPago = itemView.findViewById<TextView>(R.id.textViewFechaPago)
            val textViewFechaVencimiento = itemView.findViewById<TextView>(R.id.textViewFechaVencimiento)
            val textViewImporte = itemView.findViewById<TextView>(R.id.textViewImporte)

            // Asignamos los datos del pago a las vistas correspondientes
            textViewFechaPago.text = pago.fechaPago
            textViewFechaVencimiento.text = pago.fechaVencimiento
            textViewImporte.text = pago.importe.toString()

            // Agregamos la vista del pago al LinearLayout
            linearLayoutCuotas.addView(itemView)
        }
    }



    // Función de ejemplo para obtener una lista estática de Pagos
    private fun obtenerPagos(): List<Pagos> {
        return listOf(
            Pagos("2024-06-01", "2024-06-30", 199.99),
            Pagos("2024-07-01", "2024-07-30", 199.99),
            Pagos("2024-08-01", "2024-08-30", 199.99)
            // Agrega más pagos según sea necesario
        )
    }
}