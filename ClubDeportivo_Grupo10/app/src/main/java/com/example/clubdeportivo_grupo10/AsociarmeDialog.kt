package com.example.clubdeportivo_grupo10

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class AsociarmeDialog(private val modo: String) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())
        val inflater = requireActivity().layoutInflater
        val view = when (modo) {
            "asociarse" -> inflater.inflate(R.layout.dialog_asociarme, null)
            "contratar" -> inflater.inflate(R.layout.dialog_contratar_por_separado, null)
            else -> inflater.inflate(R.layout.dialog_asociarme, null)
        }

        builder.setView(view)
            .setPositiveButton("Aceptar") { dialog, which ->

                startActivity(Intent(requireContext(), BienvenidaActivity::class.java))

                dialog.dismiss()
            }
            .setNegativeButton("Cancelar") { dialog, which ->

                dialog.cancel()
            }

        return builder.create()
    }
}
