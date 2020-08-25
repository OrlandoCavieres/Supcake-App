package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class SeccionNuevaVenta : AppCompatActivity() {

    private lateinit var volverAlMenu: Button

    private var tipoIngreso: Int = 0
    private var nombreUsuario: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nueva_venta)

        nombreUsuario = intent.getStringExtra("nombreUsuario").toString()
        tipoIngreso = intent.getIntExtra("tipoUsuarioLogin", 0)

        volverAlMenu = findViewById(R.id.boton_nuevaVenta_volverAlMenu)
        volverAlMenu.setOnClickListener { finish() }
    }
}