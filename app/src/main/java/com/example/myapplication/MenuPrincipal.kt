package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

/**
 * Clase que representa el menu principal de la aplicacion.
 * Permite selecionar la seccion que se requiere usar y es eje principal de la aplicación.
 */
class MenuPrincipal : AppCompatActivity() {

    // Declaración de botones del layout menu principal.
    private lateinit var botonCatalogo: Button
    private lateinit var botonNuevaVenta: Button
    private lateinit var botonClientes: Button
    private lateinit var botonEstadisticas: Button

    // Credenciales de acceso del usuario
    private var tipoIngreso: Int = 0
    private var nombreUsuario: String = ""
    private var idUsuario = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_principal)

        // Configuración del botón ir a Catalogo.
        botonCatalogo = findViewById(R.id.boton_catalogo)
        botonCatalogo.setOnClickListener { abrirSeccion("Catalogo") }

        // Configuración del botón ir a Sección Nueva Venta
        botonNuevaVenta = findViewById(R.id.boton_nuevaVenta)
        botonNuevaVenta.setOnClickListener { abrirSeccion("NuevaVenta") }

        // Configuración del botón ir a Sección Clientes.
        botonClientes = findViewById(R.id.boton_clientes)
        botonClientes.setOnClickListener { abrirSeccion("Clientes") }

        // Configuración del botón ir a Estadísticas.
        botonEstadisticas = findViewById(R.id.boton_estadisticas)
        botonEstadisticas.setOnClickListener { abrirSeccion("Estadisticas") }

        // Obtener las credenciales desde el login.
        nombreUsuario = intent.getStringExtra("nombreUsuarioLogin").toString()
        tipoIngreso = intent.getIntExtra("tipoUsuarioLogin", 0)
        idUsuario = intent.getIntExtra("idLogin", 0)

        // Ocultar características del administrador a usuario vendedor.
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

    /**
     * Método que permite discriminar a la sección que se desea ir en base a un string de entrada con un nombre
     * clave de la sección.
     * @param seccion Sección de la aplicación en string a la que se desea ir.
     */
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
        nuevaPagina.putExtra("idLogin", this.idUsuario)
        startActivity(nuevaPagina)
    }
}