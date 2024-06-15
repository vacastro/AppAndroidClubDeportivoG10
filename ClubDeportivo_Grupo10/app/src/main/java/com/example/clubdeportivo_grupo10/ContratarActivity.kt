package com.example.clubdeportivo_grupo10

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity

class ContratarActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contratar)

        // Configuración de borde a borde si es Android 11 (API 30) o superior
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(false)
            val controller = window.insetsController
            if (controller != null) {
                controller.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
                controller.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        } else {
            // Configuración de pantalla completa para versiones anteriores a Android 11
            @Suppress("DEPRECATION")
            window.decorView.systemUiVisibility = (
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            or View.SYSTEM_UI_FLAG_FULLSCREEN
                            or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    )
        }

        val txtCancelar: TextView = findViewById(R.id.txtCancelar)
        txtCancelar.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()
        }

        val btnAsociarme = findViewById<Button>(R.id.btnAsociarme)
        btnAsociarme.setOnClickListener {
            val dialog = AsociarmeDialog("asociarse")
            dialog.show(supportFragmentManager, "AsociarmeDialog")
        }

        val btnAsociarme2 = findViewById<Button>(R.id.btnAsociarme2)
        btnAsociarme2.setOnClickListener {
            val dialog = AsociarmeDialog("contratar")
            dialog.show(supportFragmentManager, "contratar_dialog")
        }
    }
}

