package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Estadisticas : AppCompatActivity() {

    private lateinit var volverAlMenu: Button

    private var tipoIngreso: Int = 0
    private var nombreUsuario: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_estadisticas)

        nombreUsuario = intent.getStringExtra("nombreUsuario").toString()
        tipoIngreso = intent.getIntExtra("tipoUsuarioLogin", 0)

        volverAlMenu = findViewById(R.id.boton_estadisticas_volverAlMenu)
        volverAlMenu.setOnClickListener { irAlMenuDesdeEstadisticas() }
    }

    private fun irAlMenuDesdeEstadisticas() {
        val irAlMenu = Intent(this, MenuPrincipal::class.java)
        irAlMenu.putExtra("tipoUsuarioLogin", this.tipoIngreso)
        irAlMenu.putExtra("nombreUsuario", this.nombreUsuario)
        startActivity(irAlMenu)
    }
}