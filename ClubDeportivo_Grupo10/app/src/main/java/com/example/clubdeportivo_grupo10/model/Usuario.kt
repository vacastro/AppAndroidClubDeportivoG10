package com.example.clubdeportivo_grupo10.model

import android.os.Parcel
import android.os.Parcelable
import androidx.versionedparcelable.VersionedParcelize
import java.io.Serializable


data class Usuario(
    val id: Int,
    val nombre: String,
    val email: String,
    val telefono: String,
    val clave: String
):Serializable