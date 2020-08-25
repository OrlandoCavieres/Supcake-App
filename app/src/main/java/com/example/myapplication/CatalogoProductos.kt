package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class CatalogoProductos : AppCompatActivity() {

    private var listaProductos: MutableList<Producto> = mutableListOf(
        Producto(0,"cake",1000,1, R.drawable.cake_photo),
        Producto(0,"cake",1000,2, R.drawable.cake_photo),
        Producto(0,"cake",1000,3, R.drawable.cake_photo),
        Producto(0,"cake",1000,4, R.drawable.cake_photo),
        Producto(0,"cake",1000,5, R.drawable.cake_photo),
        Producto(0,"pancake",1000,6, R.drawable.cake_photo),
        Producto(0,"cake",1000,7, R.drawable.cake_photo),
        Producto(0,"cake",1000,8, R.drawable.cake_photo),
        Producto(0,"atacake",1000,9, R.drawable.cake_photo)
    )

    private lateinit var listViewProductos: ListView
    private lateinit var volverAlMenu: Button
    private lateinit var addProducto: Button
    private lateinit var catalogoVacio: TextView

    private lateinit var adaptadorCatalogo: AdaptadorProducto

    private var tipoIngreso: Int = 0
    private var nombreUsuario: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_catalogo)

        this.obtenerListaProductoBaseDatos()
        this.listaProductos.sortBy { it.nombre }

        nombreUsuario = intent.getStringExtra("nombreUsuarioLogin").toString()
        tipoIngreso = intent.getIntExtra("tipoUsuarioLogin", 0)

        volverAlMenu = findViewById(R.id.boton_catalogo_volverAlMenu)
        volverAlMenu.setOnClickListener { finish() }

        addProducto = findViewById(R.id.boton_catalogo_addProducto)
        addProducto.setOnClickListener {
            val accion = Intent(this, NuevoProducto::class.java)
            accion.putExtra("tipoUsuarioLogin", this.tipoIngreso)
            accion.putExtra("nombreUsuarioLogin", this.nombreUsuario)
            startActivity(accion)
        }

        catalogoVacio = findViewById(R.id.textView_mensajeCatalogoVacio)

        listViewProductos = findViewById(R.id.listView_productosCatalogo)
        adaptadorCatalogo = AdaptadorProducto(this, listaProductos)
        listViewProductos.adapter = adaptadorCatalogo

        listViewProductos.setOnItemClickListener { _, _, posicion, _ ->
            val accion = Intent(this, FichaProductoCatalogo::class.java)
            accion.putExtra("producto", listaProductos[posicion])
            accion.putExtra("tipoUsuarioLogin", this.tipoIngreso)
            accion.putExtra("nombreUsuarioLogin", this.nombreUsuario)
            startActivity(accion)
        }

        if (listaProductos.isEmpty()) {
            listViewProductos.visibility = ListView.GONE
            catalogoVacio.visibility = TextView.VISIBLE
        }

        if (tipoIngreso == 0) {
            addProducto.visibility = Button.GONE
            val iconAdmin = findViewById<ImageView> (R.id.icon_properties_menu)
            iconAdmin.visibility = ImageView.GONE
            val textAdmin = findViewById<TextView> (R.id.txt_modAdministrador)
            textAdmin.visibility = TextView.GONE
        }
    }

    override fun onResume() {
        super.onResume()
        this.obtenerListaProductoBaseDatos()
        this.adaptadorCatalogo.notifyDataSetChanged()
    }

    private fun obtenerListaProductoBaseDatos() {
        /* TODO Implementar método que pregunte a la base de datos la lista de productos
            y actualize la lista a presentar en seccion catalogo*/
    }
}
