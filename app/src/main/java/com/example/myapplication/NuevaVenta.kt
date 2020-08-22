package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class NuevaVenta : AppCompatActivity() {

    private lateinit var volverAlMenu: Button
    private var tipoIngreso: Int = 0
    private var nombreUsuario: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nueva_venta)

        nombreUsuario = intent.getStringExtra("nombreUsuario").toString()
        tipoIngreso = intent.getIntExtra("tipoUsuarioLogin", 0)

        volverAlMenu = findViewById(R.id.boton_nuevaVenta_volverAlMenu)
        volverAlMenu.setOnClickListener { irAlMenuDesdeSeccionVenta() }
    }

    private fun irAlMenuDesdeSeccionVenta() {
        val irAlMenu = Intent(this, MenuPrincipal::class.java)
        irAlMenu.putExtra("tipoUsuarioLogin", this.tipoIngreso)
        irAlMenu.putExtra("nombreUsuario", this.nombreUsuario)
        startActivity(irAlMenu)
    }
}