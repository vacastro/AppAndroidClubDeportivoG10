package com.example.clubdeportivo_grupo10

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigation.NavigationView
import androidx.drawerlayout.widget.DrawerLayout

class PrincipalActivity : AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_principal)

        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.navigation_view)

        // Configurar el Toolbar
        val toolbar: MaterialToolbar = findViewById(R.id.menuHamburger)
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        // Manejar la selección de elementos del NavigationView
        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_profile -> {
                    // Lógica para "Mi Perfil"
                    true
                }
                R.id.action_change_password -> {
                    // Lógica para "Cambiar Clave"
                    true
                }
                R.id.action_contact -> {
                    // Lógica para "Contacto"
                    true
                }
                R.id.action_pay -> {
                    // Lógica para "Pagar"
                    true
                }
                R.id.action_hire -> {
                    // Lógica para "Contratar"
                    val intent = Intent(this, ContratarActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.action_terms_conditions -> {
                    // Lógica para "Términos y Condiciones"
                    true
                }
                R.id.action_logout -> {
                    // Lógica para "Cerrar Sesión"
                    true
                }
                else -> false
            }.also {
                drawerLayout.closeDrawer(GravityCompat.START)
                it
            }
        }

        // Aplicar insets de la ventana
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun enableEdgeToEdge() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
    }
}