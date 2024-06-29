package com.example.clubdeportivo_grupo10

import android.app.Activity
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
import android.widget.Toast
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
import com.example.clubdeportivo_grupo10.model.Contrato
import com.example.clubdeportivo_grupo10.model.Usuario
import java.text.DecimalFormat
import java.io.Serializable
import java.util.Calendar
import java.util.Locale
import java.text.SimpleDateFormat
import java.util.Date

class PrincipalActivity : AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView

    lateinit var clubDeportivo: sqlHelper
    private lateinit var sqlHelper: sqlHelper

    private var cardViewPendientePago: View? = null
    private var cardViewContrato: View? = null

    private var usuario:Usuario? = null
    private var contrato: Contrato? = null

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_principal)


        clubDeportivo= sqlHelper(this)

        usuario = intent.getSerializableExtra("userData") as? Usuario

        usuario = intent.getSerializableExtra("userData", Usuario::class.java)

        contrato = clubDeportivo.obtenerContratoPorUsuario(usuario!!.id)


        val showCard = intent.getBooleanExtra("showCard", false)
        val actionType = intent.getStringExtra("actionType")



        if (showCard && actionType != null) {
            addCardPendientePago(actionType)
        }

        if(!showCard){
            addCardContrato(contrato!!)
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
                    val intent = Intent(this, ListadoCuotasActivity::class.java)
                    intent.putExtra("userData", usuario as Serializable)
                    startActivity(intent)
                    true
                }
                R.id.action_pay -> {
                    val usuarioId = usuario!!.id
                    val (totalImporte, fechaVencimiento) = obtenerTotalPagosPendientes(usuarioId)
                    if (totalImporte > 0) {
                        mostrarDialogoPagarPendiente(totalImporte, usuarioId, fechaVencimiento)
                    } else {
                        // Mostrar un mensaje diciendo que no hay pagos pendientes
                        Toast.makeText(this, "No hay pagos pendientes", Toast.LENGTH_SHORT).show()
                    }
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

    private fun addCardPendientePago(actionType: String) {

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
        cardViewPendientePago = cardView


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
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
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



            clubDeportivo.insertarPago(usuarioId, importeDouble, fechaVencimientoString)

            val cardContainer = findViewById<FrameLayout>(R.id.cardContainer)
            cardContainer.removeView(cardViewPendientePago)

            addCardContrato(contrato!!)

            dialog.dismiss()

        }


        val buttonCancelar = dialog.findViewById<Button>(R.id.button2)
        buttonCancelar.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }



    private fun addCardContrato(contrato: Contrato) {
        val cardView = layoutInflater.inflate(R.layout.card_contrato, null)

        val textTitulo = cardView.findViewById<TextView>(R.id.textTitulo)
        val textDescripcion = cardView.findViewById<TextView>(R.id.textDescripcion)

        val buttonCancelar = cardView.findViewById<Button>(R.id.button3)

        if(contrato.actividad.equals("Socio")){
            textTitulo.text = "Membresía"
            textDescripcion.text = "$ 19.999"
        }else{
            textTitulo.text = contrato.actividad
            textDescripcion.text = "$ 5.499"
        }



        buttonCancelar.setOnClickListener {

        }

        val cardContainer = findViewById<FrameLayout>(R.id.cardContainer)
        cardContainer.addView(cardView)
    }


    fun obtenerTotalPagosPendientes(usuarioId: Int): Pair<Double, String?> {
        val pagosPendientes = clubDeportivo.obtenerPagosPendientes(usuarioId)
        val totalImporte = pagosPendientes.sumOf { it.importe }
        val fechaVencimiento = pagosPendientes.firstOrNull()?.fechaVencimiento
        return Pair(totalImporte, fechaVencimiento)
    }

    private fun mostrarDialogoPagarPendiente(totalImporte: Double, usuarioId: Int, fechaVencimiento: String?) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_pagar)

        val textImporte = dialog.findViewById<TextView>(R.id.textImporte)
        textImporte.text = "$ $totalImporte"

        val textVencimiento = dialog.findViewById<TextView>(R.id.textVencimiento)
        textVencimiento.text = fechaVencimiento ?: "N/A"

        val textConcepto = dialog.findViewById<TextView>(R.id.textConcepto)
        textConcepto.text = contrato!!.actividad

        val buttonPagar = dialog.findViewById<Button>(R.id.button)
        buttonPagar.setOnClickListener {

            val pagosPendientes = clubDeportivo.obtenerPagosPendientes(usuarioId)
            pagosPendientes.forEach {
                clubDeportivo.actualizarPagos(pagosPendientes)
            }

            dialog.dismiss()
        }

        val buttonCancelar = dialog.findViewById<Button>(R.id.button2)
        buttonCancelar.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }


}