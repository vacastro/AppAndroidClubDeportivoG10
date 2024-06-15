package com.example.clubdeportivo_grupo10

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigation.NavigationView

class SuscripcionesActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var toolbar: MaterialToolbar
    private lateinit var navigationView: NavigationView
    private lateinit var drawerToggle: ActionBarDrawerToggle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_suscripciones)

        drawerLayout = findViewById(R.id.drawer_layout)
        toolbar = findViewById(R.id.menuHamburger)
        navigationView = findViewById(R.id.navigation_view)

        setSupportActionBar(toolbar)

        drawerToggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()

        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_profile -> {
                    // Manejar "Mi perfil"
                    true
                }
                R.id.action_change_password -> {
                    // Manejar "Cambiar clave"
                    true
                }
                R.id.action_contact -> {
                    // Manejar "Contacto"
                    true
                }
                R.id.action_pay -> {
                    // Manejar "Pagar"
                    true
                }
                R.id.action_hire -> {
                    // Manejar "Contratar"
                    true
                }
                R.id.action_terms_conditions -> {
                    // Manejar "Términos y condiciones"
                    true
                }
                R.id.action_logout -> {
                    // Manejar "Cerrar sesión"
                    true
                }
                else -> false
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}