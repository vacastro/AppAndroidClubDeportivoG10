package com.example.clubdeportivo_grupo10

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.clubdeportivo_grupo10.model.Contrato
import com.example.clubdeportivo_grupo10.model.Pagos
import com.example.clubdeportivo_grupo10.model.Usuario
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class sqlHelper (context:Context): SQLiteOpenHelper (context, DATABASE_NAME, null, DATABASE_VERSION ){

    companion object {
        private const val DATABASE_NAME = "clubDeportivo.db"
        private const val DATABASE_VERSION = 2
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE_USUARIOS = """
            CREATE TABLE Usuarios (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre TEXT NOT NULL,
                email TEXT NOT NULL,
                telefono TEXT NOT NULL,
                clave TEXT NOT NULL
            );
        """

        val CREATE_TABLE_CONTRATOS = """
            CREATE TABLE Contratos (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                usuario INTEGER NOT NULL,
                actividad TEXT NOT NULL,
                fecha_alta TEXT NOT NULL,
                FOREIGN KEY (usuario) REFERENCES Usuarios(id)
            );
        """

        val CREATE_TABLE_PAGOS = """
            CREATE TABLE Pagos (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                usuario INTEGER NOT NULL,
                fecha_vencimiento TEXT NOT NULL,
                fecha_pago TEXT,
                importe REAL NOT NULL,
                FOREIGN KEY (usuario) REFERENCES Usuarios(id)
            );
        """

        db?.execSQL(CREATE_TABLE_USUARIOS)
        db?.execSQL(CREATE_TABLE_CONTRATOS)
        db?.execSQL(CREATE_TABLE_PAGOS)

        db?.execSQL("INSERT INTO Usuarios (nombre, email, telefono, clave) VALUES ('Agustin Guarmes', 'agustin@gmail.com', '1234567890', 'pass123')")
        db?.execSQL("INSERT INTO Usuarios (nombre, email, telefono, clave) VALUES ('Martin Castanio', 'martin@gmail.com', '1234567890', 'pass123')")
        db?.execSQL("INSERT INTO Usuarios (nombre, email, telefono, clave) VALUES ('Selene Rodriguez', 'selene@gmail.com', '1234567890', 'pass123')")
        db?.execSQL("INSERT INTO Usuarios (nombre, email, telefono, clave) VALUES ('Vero Castro', 'vero@gmail.com', '1234567890', 'pass123')")

        db?.execSQL("INSERT INTO Contratos (usuario, actividad, fecha_alta) VALUES (1, 'Socio', '2024-02-01')")
        db?.execSQL("INSERT INTO Contratos (usuario, actividad, fecha_alta) VALUES (2, 'Futbol', '2024-03-01')")
        db?.execSQL("INSERT INTO Contratos (usuario, actividad, fecha_alta) VALUES (3, 'Socio', '2024-05-01')")
        db?.execSQL("INSERT INTO Contratos (usuario, actividad, fecha_alta) VALUES (4, 'Yoga', '2024-05-01')")

        db?.execSQL("INSERT INTO Pagos (usuario, fecha_vencimiento, fecha_pago, importe) VALUES (1, '2024-02-01', '2024-02-05', 19999.0)")
        db?.execSQL("INSERT INTO Pagos (usuario, fecha_vencimiento, fecha_pago, importe) VALUES (1, '2024-03-01', '2024-03-05', 19999.0)")
        db?.execSQL("INSERT INTO Pagos (usuario, fecha_vencimiento, fecha_pago, importe) VALUES (1, '2024-04-01', '2024-04-05', 19999.0)")
        db?.execSQL("INSERT INTO Pagos (usuario, fecha_vencimiento, fecha_pago, importe) VALUES (1, '2024-05-01', '2024-05-05', 19999.0)")
        db?.execSQL("INSERT INTO Pagos (usuario, fecha_vencimiento, fecha_pago, importe) VALUES (1, '2024-06-01', '2024-06-05', 19999.0)")
        db?.execSQL("INSERT INTO Pagos (usuario, fecha_vencimiento, importe) VALUES (1, '2024-0-01', 19999.0)")
        db?.execSQL("INSERT INTO Pagos (usuario, fecha_vencimiento, fecha_pago, importe) VALUES (4, '2024-05-01', '2024-05-05', 5499.0)")
        db?.execSQL("INSERT INTO Pagos (usuario, fecha_vencimiento, importe) VALUES (4, '2024-05-01', 5499.0)")

    }



    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL("DROP TABLE IF EXISTS Usuarios")
        db?.execSQL("DROP TABLE IF EXISTS Contratos")
        db?.execSQL("DROP TABLE IF EXISTS Pagos")
        onCreate(db)
    }

    //Metodo para hacer un registro de usuarios
    fun insertarUsuario(nombre:String, email:String, telefono:String, clave:String){
        val datos =ContentValues().apply {
            put("nombre", nombre);
            put("email", email);
            put("telefono", telefono);
            put("clave", clave);
        };

        val db= this.writableDatabase;
        db.insert("Usuarios",null, datos);
        db.close();
    }

    // Metodo para obtener la clave dado un email
    fun obtenerUsuarioPorEmail(email: String): Usuario? {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT id, nombre, email, telefono, clave FROM Usuarios WHERE email = ?", arrayOf(email))
        var usuario: Usuario? = null

        if (cursor.moveToFirst()) {
            val idColumnIndex = cursor.getColumnIndex("id")
            val nombreColumnIndex = cursor.getColumnIndex("nombre")
            val telefonoColumnIndex = cursor.getColumnIndex("telefono")
            val claveColumnIndex = cursor.getColumnIndex("clave")

            if (idColumnIndex != -1 && nombreColumnIndex != -1 && telefonoColumnIndex != -1 && claveColumnIndex != -1) {
                val id = cursor.getInt(idColumnIndex)
                val nombre = cursor.getString(nombreColumnIndex)
                val telefono = cursor.getString(telefonoColumnIndex)
                val clave = cursor.getString(claveColumnIndex)

                usuario = Usuario(id, nombre, email, telefono, clave)
            }
        }
        cursor.close()
        db.close()

        return usuario
    }

    fun insertarContrato(usuarioId: Int, actividad: String): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put("usuario", usuarioId)
            put("actividad", actividad)
            put("fecha_alta", SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()))
        }
        val result = db.insert("Contratos", null, values)
        db.close()

        return result
    }

    fun insertarPago(usuarioId: Int, importe: Double, fechaVencimiento: String): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("usuario", usuarioId)
            put("fecha_vencimiento", fechaVencimiento)
            put("fecha_pago", SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()))
            put("importe", importe)
        }
        val result = db.insert("Pagos", null, values)
        db.close()
        return result
    }


    fun obtenerContratoPorUsuario(usuarioId: Int): Contrato? {
        val db = this.readableDatabase
        var contrato: Contrato? = null
        val cursor = db.rawQuery("SELECT * FROM Contratos WHERE usuario = ?", arrayOf(usuarioId.toString()))

        if (cursor.moveToFirst()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
            val usuario = cursor.getInt(cursor.getColumnIndexOrThrow("usuario"))
            val actividad = cursor.getString(cursor.getColumnIndexOrThrow("actividad"))
            val fechaAlta = cursor.getString(cursor.getColumnIndexOrThrow("fecha_alta"))

            contrato = Contrato(id, usuario, actividad, fechaAlta)
        }
        cursor.close()
        db.close()
        return contrato
    }

    fun obtenerPagosPorUsuario(usuarioId: Int): List<Pagos> {
        val db = this.readableDatabase
        val pagos = mutableListOf<Pagos>()

        val cursor = db.rawQuery("SELECT * FROM Pagos WHERE usuario = ? AND fecha_pago IS NOT NULL AND fecha_pago != ''", arrayOf(usuarioId.toString()))

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
                val usuario = cursor.getInt(cursor.getColumnIndexOrThrow("usuario"))
                val fechaVencimiento = cursor.getString(cursor.getColumnIndexOrThrow("fecha_vencimiento"))
                val fechaPago = cursor.getString(cursor.getColumnIndexOrThrow("fecha_pago"))
                val importe = cursor.getDouble(cursor.getColumnIndexOrThrow("importe"))

                val pago = Pagos(id, usuario, fechaVencimiento, fechaPago, importe)
                pagos.add(pago)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

        return pagos
    }


    fun obtenerPagosPendientes(usuarioId: Int): List<Pagos> {
        val db = this.readableDatabase
        val pagosPendientes = mutableListOf<Pagos>()

        val cursor = db.rawQuery("SELECT * FROM Pagos WHERE usuario = ? AND (fecha_pago IS NULL OR fecha_pago = '')", arrayOf(usuarioId.toString()))

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
                val usuario = cursor.getInt(cursor.getColumnIndexOrThrow("usuario"))
                val fechaVencimiento = cursor.getString(cursor.getColumnIndexOrThrow("fecha_vencimiento"))
                val fechaPago = cursor.getString(cursor.getColumnIndexOrThrow("fecha_pago"))
                val importe = cursor.getDouble(cursor.getColumnIndexOrThrow("importe"))

                val pago = Pagos(id, usuario, fechaVencimiento, fechaPago, importe)
                pagosPendientes.add(pago)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

        return pagosPendientes
    }


    fun actualizarPagos(pagos: List<Pagos>) {
        val db = writableDatabase
        val fechaPagoActual = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

        db.beginTransaction()
        try {
            pagos.forEach { pago ->
                val values = ContentValues().apply {
                    put("fecha_pago", fechaPagoActual)
                }
                db.update("Pagos", values, "id = ?", arrayOf(pago.id.toString()))
            }
            db.setTransactionSuccessful()
        } finally {
            db.endTransaction()
        }

        db.close()
    }


}