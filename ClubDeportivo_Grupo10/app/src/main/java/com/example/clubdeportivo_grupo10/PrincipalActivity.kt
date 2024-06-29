package com.example.clubdeportivo_grupo10

import android.app.Dialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigation.NavigationView
import androidx.drawerlayout.widget.DrawerLayout
import com.example.clubdeportivo_grupo10.model.Usuario
import java.util.Calendar
import java.util.Locale
import java.text.SimpleDateFormat

class PrincipalActivity : AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView

    private var cardViewAdded: View? = null

    private var usuario:Usuario? = null

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_principal)

        usuario = intent.getSerializableExtra("userData") as? Usuario

        val showCard = intent.getBooleanExtra("showCard", false)
        val actionType = intent.getStringExtra("actionType")


        if (showCard && actionType != null) {
            addCardView(actionType)
        }

        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.navigation_view)
        val headerView = navigationView.getHeaderView(0)

        headerView.findViewById<TextView>(R.id.textViewUsername).text = usuario?.nombre
        headerView.findViewById<TextView>(R.id.textViewEmail).text = usuario?.email

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
                R.id.action_payments -> {
                    // TODO Logica para "Pagos Realizados"

                    //val intent = Intent(this, PagosRealizadosActivity::class.java)
                    //startActivity(intent)
                    true
                }
                R.id.action_pay -> {
                    // TODO Logica para "Pagar"
                    true
                }
                R.id.action_hire -> {
                    // Lógica para "Contratar"
                    val intent = Intent(this, ContratarActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.action_credential -> {
                    // Lógica para "Credencial"

                   // val intent = Intent(this, CredencialActivity::class.java)
                   // startActivity(intent)
                    true
                }
                R.id.action_contact -> {
                    // Lógica para "Contacto"
                    true
                }
                R.id.action_terms_conditions -> {
                    // Lógica para "Términos y Condiciones"
                    true
                }
                R.id.action_logout -> {
                    val intent = Intent(this, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    true
                }
                else -> false
            }.also {
                drawerLayout.closeDrawer(GravityCompat.START)
                it
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun enableEdgeToEdge() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
    }

    private fun addCardView(actionType: String) {

        val cardView = layoutInflater.inflate(R.layout.card_pendiente_pago, null)


        when (actionType) {
            "asociar" -> {
                cardView.findViewById<TextView>(R.id.textTitulo).text = "Bienvenido al club deportivo"
                cardView.findViewById<TextView>(R.id.textDescripcion).text = "¡Ya estás registrado! Completa el pago para poder utilizar todas las actividades del club."
            }
            "contratar" -> {
                cardView.findViewById<TextView>(R.id.textTitulo).text = "Ya estás ahorrando"
                cardView.findViewById<TextView>(R.id.textDescripcion).text = "Ya estas registrado ! Completa el pago para comenzar a entrenar."
            }
            else -> {

                cardView.findViewById<TextView>(R.id.textTitulo).text = "Información"
                cardView.findViewById<TextView>(R.id.textDescripcion).text = "Completa el pago para continuar."
            }
        }

        val pagarButton = cardView.findViewById<Button>(R.id.button3)
        pagarButton.setOnClickListener {
            mostrarDialogoPagar(actionType)
        }

        val cardContainer = findViewById<FrameLayout>(R.id.cardContainer)
        cardContainer.addView(cardView)
        cardViewAdded = cardView
    }


    private fun mostrarDialogoPagar(actionType: String) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_pagar)

        val actividad = intent.getStringExtra("actividad")

        val textImporte = dialog.findViewById<TextView>(R.id.textImporte)
        val importe = if (actionType == "asociar") {
            "19.999"
        } else {
            "5.499"
        }
        textImporte.text = "$ $importe"

        val textVencimiento = dialog.findViewById<TextView>(R.id.textVencimiento)
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.MONTH, 1)
        calendar.set(Calendar.DAY_OF_MONTH, 10)
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val fechaVencimiento = dateFormat.format(calendar.time)
        textVencimiento.text = fechaVencimiento

        val textConcepto = dialog.findViewById<TextView>(R.id.textConcepto)
        textConcepto.text = if (actionType == "asociar") {
            "Membresía"
        } else {
            actividad
        }


        val buttonPagar = dialog.findViewById<Button>(R.id.button)
        buttonPagar.setOnClickListener {

            val usuarioId = usuario!!.id
            val importeDouble = importe.toDouble()
            val fechaVencimientoString = fechaVencimiento


            val db = sqlHelper(this)
            db.insertarPago(usuarioId, importeDouble, fechaVencimientoString)

            val cardContainer = findViewById<FrameLayout>(R.id.cardContainer)
            cardContainer.removeView(cardViewAdded)

            dialog.dismiss()

        }


        val buttonCancelar = dialog.findViewById<Button>(R.id.button2)
        buttonCancelar.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

}