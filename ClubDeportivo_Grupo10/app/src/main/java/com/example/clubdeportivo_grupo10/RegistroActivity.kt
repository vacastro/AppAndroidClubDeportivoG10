package com.example.clubdeportivo_grupo10

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.clubdeportivo_grupo10.model.Usuario
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.io.Serializable

class RegistroActivity : AppCompatActivity() {

    private lateinit var nameEditText: TextInputEditText
    private lateinit var emailEditText: TextInputEditText
    private lateinit var phoneEditText: TextInputEditText
    private lateinit var passEditText: TextInputEditText
    private lateinit var repeatedPassEditText: TextInputEditText
    private lateinit var inputLayoutName: TextInputLayout
    private lateinit var inputLayoutEmail: TextInputLayout
    private lateinit var inputLayoutPhone: TextInputLayout
    private lateinit var inputLayoutPass: TextInputLayout
    private lateinit var inputLayoutRepetPass: TextInputLayout
    private lateinit var nameError: TextView
    private lateinit var emailError: TextView
    private lateinit var phoneError: TextView
    private lateinit var passError: TextView
    private lateinit var repetPassError: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registro)

        nameEditText = findViewById(R.id.textInputName)
        emailEditText = findViewById(R.id.textInputEmail)
        phoneEditText = findViewById(R.id.textInputPhone)
        passEditText = findViewById(R.id.textInputPass)
        repeatedPassEditText = findViewById(R.id.textInputRepetPass)
        inputLayoutName = findViewById(R.id.inputLayoutName)
        inputLayoutEmail = findViewById(R.id.inputLayoutEmail)
        inputLayoutPhone = findViewById(R.id.inputLayoutPhone)
        inputLayoutPass = findViewById(R.id.inputLayoutPass)
        inputLayoutRepetPass = findViewById(R.id.inputLayoutRepetPass)
        nameError = findViewById(R.id.nameError)
        emailError = findViewById(R.id.emailError)
        phoneError = findViewById(R.id.phoneError)
        passError = findViewById(R.id.passError)
        repetPassError = findViewById(R.id.repetPassError)

        val cancelarButton = findViewById<TextView>(R.id.textView4)
        cancelarButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Opcional: finalizar RegistroActivity si no quieres que esté en el back stack
        }

        val registrarseButton = findViewById<Button>(R.id.btnRegistrarse)
        registrarseButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val email = emailEditText.text.toString()
            val phone = phoneEditText.text.toString()
            val pass = passEditText.text.toString()
            val repeatedPass = repeatedPassEditText.text.toString()

            var isValid = true

            if (name.isEmpty()) {
                inputLayoutName.error = "Campo requerido"
                inputLayoutName.boxStrokeColor = Color.RED
                nameEditText.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_person_error, 0, 0, 0)
                isValid = false
            } else {
                inputLayoutName.error = null
                nameEditText.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_person, 0, 0, 0)
            }

            if (email.isEmpty()) {
                inputLayoutEmail.error = "Campo requerido"
                inputLayoutEmail.boxStrokeColor = Color.RED
                emailEditText.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_mail_error, 0, 0, 0)
                isValid = false
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                inputLayoutEmail.error = "Email inválido"
                inputLayoutEmail.boxStrokeColor = Color.RED
                emailEditText.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_mail_error, 0, 0, 0)
                isValid = false
            } else {
                inputLayoutEmail.error = null
                emailEditText.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_mail, 0, 0, 0)
            }

            if (phone.isEmpty()) {
                inputLayoutPhone.error = "Campo requerido"
                inputLayoutPhone.boxStrokeColor = Color.RED
                phoneEditText.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_phone_error, 0, 0, 0)
                isValid = false
            } else if (phone.length < 8) {
                inputLayoutPhone.error = "Número de teléfono inválido"
                inputLayoutPhone.boxStrokeColor = Color.RED
                phoneEditText.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_phone_error, 0, 0, 0)
                isValid = false
            } else {
                inputLayoutPhone.error = null
                phoneEditText.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_phone, 0, 0, 0)
            }

            if (pass.isEmpty()) {
                inputLayoutPass.error = "Campo requerido"
                inputLayoutPass.boxStrokeColor = Color.RED
                passEditText.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_lock_error, 0, 0, 0)
                isValid = false
            } else {
                inputLayoutPass.error = null
                passEditText.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_lock, 0, 0, 0)
            }

            if (repeatedPass.isEmpty()) {
                inputLayoutRepetPass.error = "Campo requerido"
                inputLayoutRepetPass.boxStrokeColor = Color.RED
                repeatedPassEditText.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_lock_error, 0, 0, 0)
                isValid = false
            } else if (pass != repeatedPass) {
                inputLayoutRepetPass.error = "Las contraseñas no coinciden"
                inputLayoutRepetPass.boxStrokeColor = Color.RED
                repeatedPassEditText.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_lock_error, 0, 0, 0)
                isValid = false
            } else {
                inputLayoutRepetPass.error = null
                repeatedPassEditText.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_lock, 0, 0, 0)
            }

            if (isValid) {
                val db = sqlHelper(this)
                db.insertarUsuario(name, email, phone, pass)
                val usuario: Usuario? = db.obtenerUsuarioPorEmail(email)
                val intent = Intent(this, ContratarActivity::class.java)
                intent.putExtra("userData", usuario as Serializable)
                startActivity(intent)
                finish()
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