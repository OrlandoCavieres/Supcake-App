package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class CatalogoProductos : AppCompatActivity() {

    private var listaProductos: MutableList<Producto> = mutableListOf()

    private lateinit var listViewProductos: ListView
    private lateinit var volverAlMenu: Button
    private lateinit var addProducto: Button
    private lateinit var cuadroBusqueda: EditText
    private lateinit var botonBuscar: ImageButton

    private lateinit var catalogoVacio: TextView

    private lateinit var adaptadorCatalogo: AdaptadorProducto

    private var tipoIngreso: Int = 0
    private var nombreUsuario: String = ""
    private var idUsuario = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.mainCatalogo()
    }

    private fun mainCatalogo() {
        setContentView(R.layout.activity_catalogo)

        this.obtenerListaProductoBaseDatos()
        this.listaProductos.sortBy { it.nombre }

        nombreUsuario = intent.getStringExtra("nombreUsuarioLogin").toString()
        tipoIngreso = intent.getIntExtra("tipoUsuarioLogin", 0)
        idUsuario = intent.getIntExtra("idLogin", 0)

        volverAlMenu = findViewById(R.id.boton_catalogo_volverAlMenu)
        volverAlMenu.setOnClickListener { finish() }

        addProducto = findViewById(R.id.boton_catalogo_addProducto)
        addProducto.setOnClickListener {
            val accion = Intent(this, NuevoProducto::class.java)
            accion.putExtra("tipoUsuarioLogin", this.tipoIngreso)
            accion.putExtra("nombreUsuarioLogin", this.nombreUsuario)
            startActivity(accion)
        }

        cuadroBusqueda = findViewById(R.id.editText_catalogo_textoBuscar)
        cuadroBusqueda.setOnClickListener {
            if (cuadroBusqueda.text.toString().isNotBlank()) {
                mainCatalogo()
            }
        }

        botonBuscar = findViewById(R.id.boton_catalogo_buscar)
        botonBuscar.setOnClickListener {
            val texto = cuadroBusqueda.text.toString()
            if (texto.isNotBlank()) {
                val listaFiltrada = this.listaProductos.filter {
                    it.nombre.toLowerCase(Locale.ROOT).contains(texto.toLowerCase(Locale.ROOT))
                }
                adaptadorCatalogo.clear()
                adaptadorCatalogo.addAll(listaFiltrada)
            }
        }

        catalogoVacio = findViewById(R.id.textView_mensajeCatalogoVacio)

        listViewProductos = findViewById(R.id.listView_productosCatalogo)
        adaptadorCatalogo = AdaptadorProducto(this, listaProductos)
        listViewProductos.adapter = adaptadorCatalogo

        listViewProductos.setOnItemClickListener { _, _, posicion, _ ->
            if (this.tipoIngreso == 1) {
                val accion = Intent(this, FichaProductoCatalogo::class.java)
                accion.putExtra("producto", listaProductos[posicion])
                accion.putExtra("tipoUsuarioLogin", this.tipoIngreso)
                accion.putExtra("nombreUsuarioLogin", this.nombreUsuario)
                startActivity(accion)
            } else {
                listViewProductos.isClickable = false
            }
        }

        if (this.listaProductos.isEmpty()) {
            listViewProductos.visibility = ListView.GONE
            catalogoVacio.visibility = TextView.VISIBLE
        }

        if (this.tipoIngreso == 0) {
            addProducto.visibility = Button.GONE
            val iconAdmin = findViewById<ImageView>(R.id.icon_properties_menu)
            iconAdmin.visibility = ImageView.GONE
            val textAdmin = findViewById<TextView>(R.id.txt_modAdministrador)
            textAdmin.visibility = TextView.GONE
        }
    }

    override fun onResume() {
        super.onResume()
        this.adaptadorCatalogo.notifyDataSetChanged()
        this.mainCatalogo()
    }

    private fun obtenerListaProductoBaseDatos() {
        this.listaProductos = ClasesBD.bD_Productos(this)
    }
}
