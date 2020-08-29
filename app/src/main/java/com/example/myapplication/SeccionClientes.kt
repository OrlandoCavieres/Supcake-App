package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import kotlinx.android.synthetic.main.activity_clientes.*

class SeccionClientes : AppCompatActivity() {

    private var listaClientes: MutableList<Cliente> = mutableListOf()

    private lateinit var adaptadorPlanilla: AdaptadorCliente
    private lateinit var volverAlMenu: Button

    private lateinit var addCliente: Button
    private lateinit var planillaClientes: ListView
    private lateinit var mensajePlanillaVacia: TextView

    private lateinit var cuadroBusqueda: EditText
    private lateinit var botonBuscar: ImageButton

    private var tipoIngreso: Int = 0
    private var nombreUsuario: String = ""
    private var idUsuario = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.mainSeccionClientes()
    }

    private fun mainSeccionClientes() {
        setContentView(R.layout.activity_clientes)

        this.obtenerListaClientesBaseDatos()
        this.listaClientes.sortBy { it.obtenerNombre() }

        nombreUsuario = intent.getStringExtra("nombreUsuarioLogin").toString()
        tipoIngreso = intent.getIntExtra("tipoUsuarioLogin", 0)
        idUsuario = intent.getIntExtra("idLogin", 0)

        volverAlMenu = findViewById(R.id.boton_clientes_volverAlMenu)
        volverAlMenu.setOnClickListener { finish() }

        addCliente = findViewById(R.id.boton_clientes_addCliente)
        addCliente.setOnClickListener {
            val accion = Intent(this, NuevoCliente::class.java)
            accion.putExtra("tipoUsuarioLogin", this.tipoIngreso)
            accion.putExtra("nombreUsuarioLogin", this.nombreUsuario)
            startActivity(accion)
        }

        mensajePlanillaVacia = findViewById(R.id.textView_mensajePlanillaVacia)

        if (this.listaClientes.isEmpty()) {
            planillaClientes.visibility = ListView.GONE
            mensajePlanillaVacia.visibility = TextView.VISIBLE
        }

        if (tipoIngreso == 0) {
            val iconAdmin = findViewById<ImageView> (R.id.icon_properties_menu)
            iconAdmin.visibility = ImageView.GONE
            val textAdmin = findViewById<TextView> (R.id.txt_modAdministrador)
            textAdmin.visibility = TextView.GONE
            addCliente.visibility = Button.GONE
        }

        planillaClientes = findViewById(R.id.listView_planillaClientes)
        adaptadorPlanilla = AdaptadorCliente(this, listaClientes, tipoIngreso)
        planillaClientes.adapter = adaptadorPlanilla
    }

    override fun onResume() {
        super.onResume()
        this.adaptadorPlanilla.notifyDataSetChanged()
        this.mainSeccionClientes()
    }

    private fun buscarEnCatalogo() {

    }

    private fun obtenerListaClientesBaseDatos() {
        listaClientes = ClasesBD.bD_Cliente(this)
    }
}