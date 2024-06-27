package com.example.clubdeportivo_grupo10

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2

class MainActivity : AppCompatActivity() {

    lateinit var clubDeportivo: sqlHelper
    private lateinit var sqlHelper: sqlHelper
    private lateinit var emailEditText:EditText
    private lateinit var claveEditText: EditText
    private lateinit var btnIngresar: Button


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

         btnIngresar.setOnClickListener {

             val email= emailEditText.text.toString()
             val clave = claveEditText.text.toString()

             val chequearEmail = clubDeportivo.existeEmail(email)

             if(chequearEmail != true){
                 Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show();
             }else{
                 val claveDB = clubDeportivo.obtenerClavePorEmail(email)

                 if(clave.equals(claveDB)){

                     val intent = Intent(this, PrincipalActivity::class.java)
                     startActivity(intent)
                 } else{
                     Toast.makeText(this, "Invalid login", Toast.LENGTH_SHORT).show();
                 }

             }
         }
    }
}