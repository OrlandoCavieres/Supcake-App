package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

/**
 * Clase que representa la sección de clientes en la aplicación.
 */
class SeccionClientes : AppCompatActivity() {

    // Lista de clientes asociada a la sección
    private var listaClientes: MutableList<Cliente> = mutableListOf()

    // Declaración de elementos y adaptadores de la sección Clintes.
    private lateinit var adaptadorPlanilla: AdaptadorCliente
    private lateinit var volverAlMenu: Button

    private lateinit var addCliente: Button
    private lateinit var planillaClientes: ListView
    private lateinit var mensajePlanillaVacia: TextView

    private lateinit var cuadroBusqueda: EditText
    private lateinit var botonBuscar: ImageButton

    // Credenciales de inicio del usuario
    private var tipoIngreso: Int = 0
    private var nombreUsuario: String = ""
    private var idUsuario = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.mainSeccionClientes()
    }

    // Función principal que corre el catalogo.
    private fun mainSeccionClientes() {
        setContentView(R.layout.activity_clientes)

        // Actualización inicial de la lista de clientes con la base de datos y posterior orden alfabético.
        this.obtenerListaClientesBaseDatos()
        this.listaClientes.sortBy { it.obtenerNombre() }

        // Obtener credenciales del usuario
        nombreUsuario = intent.getStringExtra("nombreUsuarioLogin").toString()
        tipoIngreso = intent.getIntExtra("tipoUsuarioLogin", 0)
        idUsuario = intent.getIntExtra("idLogin", 0)

        // Configuración del botón volver al menu.
        volverAlMenu = findViewById(R.id.boton_clientes_volverAlMenu)
        volverAlMenu.setOnClickListener { finish() }

        /* Configuración del botón agregar cliente. Inicia una nueva activity para formulario de
        nuevo cliente. */
        addCliente = findViewById(R.id.boton_clientes_addCliente)
        addCliente.setOnClickListener {
            val accion = Intent(this, NuevoCliente::class.java)
            accion.putExtra("tipoUsuarioLogin", this.tipoIngreso)
            accion.putExtra("nombreUsuarioLogin", this.nombreUsuario)
            startActivity(accion)
        }

        // Inicializar mensaje para planilla Vacia.
        mensajePlanillaVacia = findViewById(R.id.textView_mensajePlanillaVacia)

        // Si lista clientes está vacía lanzar mensaje informativo.
        if (this.listaClientes.isEmpty()) {
            planillaClientes.visibility = ListView.GONE
            mensajePlanillaVacia.visibility = TextView.VISIBLE
        }

        // Si usuario es vendedor entonces ocultar opciones de usuario administrador.
        if (this.tipoIngreso == 0) {
            val iconAdmin = findViewById<ImageView> (R.id.icon_properties_menu)
            iconAdmin.visibility = ImageView.GONE
            val textAdmin = findViewById<TextView> (R.id.txt_modAdministrador)
            textAdmin.visibility = TextView.GONE
            addCliente.visibility = Button.GONE
        }

        // Inicializar adaptador para elementos de lista tipo cliente.
        planillaClientes = findViewById(R.id.listView_planillaClientes)
        adaptadorPlanilla = AdaptadorCliente(this, listaClientes, tipoIngreso)
        planillaClientes.adapter = adaptadorPlanilla
    }

    // Modificación de onResume para actualizar datos.
    override fun onResume() {
        super.onResume()
        this.adaptadorPlanilla.notifyDataSetChanged()
        this.mainSeccionClientes()
    }

    /**
     * Método que permite obtener la lista de clientes almacenada en la base de datos, para su uso en
     * esta sección.
     */
    private fun obtenerListaClientesBaseDatos() {
        listaClientes = ClasesBD.bD_Cliente(this)
    }
}