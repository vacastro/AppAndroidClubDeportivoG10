package com.example.clubdeportivo_grupo10

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.clubdeportivo_grupo10.model.Pagos
import com.example.clubdeportivo_grupo10.model.Usuario

class sqlHelper (context:Context): SQLiteOpenHelper (context, "clubDeportivo.db",null,1 ){

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

    fun obtenerPagosPorUsuario(usuario: Int) :  List<Pagos>{
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT fecha_pago, fecha_vencimiento, importe FROM Pagos WHERE usuario = ?", arrayOf(usuario.toString()))
        val listaPagos = ArrayList<Pagos>()
        if (cursor.moveToFirst()){
            do{
                val fechaPago = cursor.getString(1)
                val fechaVencimiento = cursor.getString(0)
                val importe = cursor.getDouble(2)
                val pago = Pagos(fechaPago, fechaVencimiento, importe)
                listaPagos.add(pago)

            } while(cursor.moveToNext())

        }

        cursor.close()
        db.close()
       return listaPagos
    }

}