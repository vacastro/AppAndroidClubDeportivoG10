package com.example.clubdeportivo_grupo10

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.clubdeportivo_grupo10.model.Usuario
import com.google.android.material.textfield.TextInputEditText
import java.io.Serializable

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
            val name = findViewById<TextInputEditText>(R.id.textInputName).text.toString()
            val email = findViewById<TextInputEditText>(R.id.textInputEmail).text.toString()
            val phone = findViewById<TextInputEditText>(R.id.textInputPhone).text.toString()
            val pass = findViewById<TextInputEditText>(R.id.textInputPass).text.toString()
            val repeatedPass = findViewById<TextInputEditText>(R.id.textInputRepetPass).text.toString()

            if(passIsValid(pass, repeatedPass)) {
                val db = sqlHelper(this)
                db.insertarUsuario(name, email, phone, pass)
                var usuario : Usuario?= db.obtenerUsuarioPorEmail(email)
                val intent = Intent(this, ContratarActivity::class.java)
                intent.putExtra("userData", usuario as Serializable)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

    }
    private fun passIsValid(pass: String, repeatedPass: String): Boolean {
        return pass == repeatedPass
    }
}