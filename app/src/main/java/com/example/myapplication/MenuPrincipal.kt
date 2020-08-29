package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MenuPrincipal : AppCompatActivity() {

    private lateinit var botonCatalogo: Button
    private lateinit var botonNuevaVenta: Button
    private lateinit var botonClientes: Button
    private lateinit var botonEstadisticas: Button

    private var tipoIngreso: Int = 0
    private var nombreUsuario: String = ""
    private var idUsuario = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_principal)

        botonCatalogo = findViewById(R.id.boton_catalogo)
        botonCatalogo.setOnClickListener { abrirSeccion("Catalogo") }

        botonNuevaVenta = findViewById(R.id.boton_nuevaVenta)
        botonNuevaVenta.setOnClickListener { abrirSeccion("NuevaVenta") }

        botonClientes = findViewById(R.id.boton_clientes)
        botonClientes.setOnClickListener { abrirSeccion("Clientes") }

        botonEstadisticas = findViewById(R.id.boton_estadisticas)
        botonEstadisticas.setOnClickListener { abrirSeccion("Estadisticas") }

        nombreUsuario = intent.getStringExtra("nombreUsuarioLogin").toString()
        tipoIngreso = intent.getIntExtra("tipoUsuarioLogin", 0)
        idUsuario = intent.getIntExtra("idLogin", 0)

        when (tipoIngreso) {
            0 -> {
                botonEstadisticas.visibility = Button.GONE
                val textAdmin = findViewById<TextView>(R.id.txt_modAdministrador)
                textAdmin.visibility = TextView.GONE
                val iconAdmin = findViewById<ImageView>(R.id.icon_properties_menu)
                iconAdmin.visibility = ImageView.GONE
            }
        }
    }

    private fun abrirSeccion(seccion: String) {
        val nuevaPagina = when (seccion) {
            "Catalogo" -> Intent(this, CatalogoProductos::class.java)
            "NuevaVenta" -> Intent(this, SeccionNuevaVenta::class.java)
            "Clientes" -> Intent(this, SeccionClientes::class.java)
            "Estadisticas" -> Intent(this, Estadisticas::class.java)
            else -> null
        }
        nuevaPagina!!.putExtra("tipoUsuarioLogin", this.tipoIngreso)
        nuevaPagina.putExtra("nombreUsuarioLogin", this.nombreUsuario)
        startActivity(nuevaPagina)
    }
}