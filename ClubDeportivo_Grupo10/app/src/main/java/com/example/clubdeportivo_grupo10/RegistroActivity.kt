package com.example.clubdeportivo_grupo10

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class RegistroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registro)


        val cancelarButton = findViewById<TextView>(R.id.textView4)
        cancelarButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Opcional: finalizar RegistroActivity si no quieres que esté en el back stack
        }

        val registrarseButton = findViewById<Button>(R.id.btnRegistrarse)
        registrarseButton.setOnClickListener {
            val intent = Intent(this, ContratarActivity::class.java)
            startActivity(intent)
            finish() // Opcional: finalizar RegistroActivity si no quieres que esté en el back stack
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

    }
}