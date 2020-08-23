package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class Clientes : AppCompatActivity() {

    private lateinit var volverAlMenu: Button
    private var tipoIngreso: Int = 0
    private var nombreUsuario: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clientes)

        nombreUsuario = intent.getStringExtra("nombreUsuario").toString()
        tipoIngreso = intent.getIntExtra("tipoUsuarioLogin", 0)

        volverAlMenu = findViewById(R.id.boton_clientes_volverAlMenu)
        volverAlMenu.setOnClickListener { irAlMenuDesdeClientes() }

        when (tipoIngreso) {
            0 -> { val iconAdmin = findViewById<ImageView> (R.id.icon_properties_menu)
                iconAdmin.visibility = ImageView.GONE
                val textAdmin = findViewById<TextView> (R.id.txt_modAdministrador)
                textAdmin.visibility = TextView.GONE }
        }
    }

    private fun irAlMenuDesdeClientes() {
        val irAlMenu = Intent(this, MenuPrincipal::class.java)
        irAlMenu.putExtra("tipoUsuarioLogin", this.tipoIngreso)
        irAlMenu.putExtra("nombreUsuario", this.nombreUsuario)
        startActivity(irAlMenu)
    }
}