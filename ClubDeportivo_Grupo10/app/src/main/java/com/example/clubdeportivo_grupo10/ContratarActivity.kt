package com.example.clubdeportivo_grupo10

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Window
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ContratarActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contratar)


        val asociar: Button = findViewById(R.id.asociar)
        asociar.setOnClickListener {
            showDialog("asociar")
        }

        val contratar: Button = findViewById(R.id.contratar)
        contratar.setOnClickListener {
            showDialog("contratar")
        }
    }

    private fun showDialog(type: String) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)

        val dialogView = if (type == "asociar") {
            LayoutInflater.from(this).inflate(R.layout.dialog_asociar, null)
        } else {
            LayoutInflater.from(this).inflate(R.layout.dialog_contratar_actividad, null)


        }

        dialog.setContentView(dialogView)

        val aceptarButton: Button = dialogView.findViewById(R.id.button)
        val cancelarButton: Button = dialogView.findViewById(R.id.button2)

        if (type == "contratar") {
            val spinner: Spinner = dialogView.findViewById(R.id.spinner)
            val actividadesArray = resources.getStringArray(R.array.actividades_array_contratar)
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, actividadesArray)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        aceptarButton.setOnClickListener {
            val intent = Intent(this, PrincipalActivity::class.java)
            startActivity(intent)
            dialog.dismiss()
        }

        cancelarButton.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }
}