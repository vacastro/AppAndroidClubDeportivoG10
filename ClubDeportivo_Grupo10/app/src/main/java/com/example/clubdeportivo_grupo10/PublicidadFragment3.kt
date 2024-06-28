package com.example.clubdeportivo_grupo10

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment

class PublicidadFragment3: Fragment() {

    @SuppressLint("SuspiciousIndentation")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Log.d("PublicidadFragment3", "onCreateView called")
        val rootView = inflater.inflate(R.layout.publicidad_pantalla3, container, false)

        val parentLayout = rootView.findViewById<ConstraintLayout>(R.id.parentLayout)
            parentLayout.setOnClickListener {
            val intent = Intent(requireContext(), RegistroActivity::class.java)
            startActivity(intent)
        }
        return rootView
    }
}