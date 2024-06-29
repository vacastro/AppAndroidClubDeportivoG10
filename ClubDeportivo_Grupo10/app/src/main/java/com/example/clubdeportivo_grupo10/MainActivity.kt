package com.example.clubdeportivo_grupo10

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.clubdeportivo_grupo10.model.Usuario
import com.google.android.material.textfield.TextInputLayout
import java.io.Serializable

class MainActivity : AppCompatActivity() {

    lateinit var clubDeportivo: sqlHelper
    private lateinit var sqlHelper: sqlHelper
    private lateinit var emailEditText:EditText
    private lateinit var claveEditText: EditText
    private lateinit var btnIngresar: Button
    private var usuario:Usuario? = null

    private lateinit var emailError: TextView
    private lateinit var passError: TextView
    private lateinit var inputLayoutEmail: TextInputLayout
    private lateinit var inputLayoutPass: TextInputLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

         // Si elige la opcion Registrarse, va a la publicidad
        val btnRegister = findViewById<Button>(R.id.btnRegistrarse)
        btnRegister.setOnClickListener {

            val intent = Intent(this, PublicidadActivity::class.java)
            startActivity(intent)
        }

         // Si elige la opcion Ingresar, debe verificar los datos en la base
         //TODO falta hacer validaciones de datos en login, si esta vacio, etc y mostrar el error

         clubDeportivo= sqlHelper(this)
         emailEditText= findViewById(R.id.textInputEmail)
         claveEditText=findViewById(R.id.textInputPass)
         btnIngresar = findViewById<Button>(R.id.btnIngresar)
         emailError = findViewById(R.id.emailError)
         passError = findViewById(R.id.passError)
        inputLayoutEmail = findViewById(R.id.inputLayoutEmail)
        inputLayoutPass = findViewById(R.id.inputLayoutPass)

         btnIngresar.setOnClickListener {

             val email = emailEditText.text.toString()
             val clave = claveEditText.text.toString()

             var isValid = true


             if (email.isEmpty()) {
                 emailError.visibility = View.VISIBLE
                 inputLayoutEmail.error = " "
                 inputLayoutEmail.boxStrokeColor = Color.RED
                 emailEditText.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_person_error, 0, 0, 0)
                 isValid = false
             } else {
                 emailError.visibility = View.GONE
                 inputLayoutEmail.error = null
                 emailEditText.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_person, 0, 0, 0)
             }

             if (clave.isEmpty()) {
                 passError.visibility = View.VISIBLE
                 inputLayoutPass.error = " "
                 inputLayoutPass.boxStrokeColor = Color.RED
                 claveEditText.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_lock_error, 0, 0, 0)
                 isValid = false
             } else {
                 passError.visibility = View.GONE
                 inputLayoutPass.error = null
                 claveEditText.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_lock, 0, 0, 0)
             }

             if (isValid) {
                 usuario = clubDeportivo.obtenerUsuarioPorEmail(email)
                 if (usuario == null) {
                     Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show();
                 } else {

                     val claveDB = usuario!!.clave

                     if (clave.equals(claveDB)) {

                         val intent = Intent(this, PrincipalActivity::class.java)
                         intent.putExtra("userData", usuario as Serializable)
                         startActivity(intent)
                     } else {
                         Toast.makeText(this, "Invalid login", Toast.LENGTH_SHORT).show();
                     }

                 }
             }
         }
    }
}