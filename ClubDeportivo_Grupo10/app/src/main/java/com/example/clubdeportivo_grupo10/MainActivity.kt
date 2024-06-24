package com.example.clubdeportivo_grupo10

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2

class MainActivity : AppCompatActivity() {

     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnRegister = findViewById<Button>(R.id.btnRegistrarse)
        btnRegister.setOnClickListener {
            // Aquí se define la acción al hacer clic en el botón registrarse
            val intent = Intent(this, PublicidadActivity::class.java)
            startActivity(intent)
        }

         val btnIngresar = findViewById<Button>(R.id.btnIngresar)
         btnIngresar.setOnClickListener {
            val intent = Intent(this, PrincipalActivity::class.java)
         startActivity(intent)}

    }
}