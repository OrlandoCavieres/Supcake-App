package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class Catalogo : AppCompatActivity() {
    private val listaProductos: List<Producto> = listOf(
        Producto(0,"cake",1000,10, R.drawable.cake_photo),
        Producto(0,"cake",1000,10, R.drawable.cake_photo),
        Producto(0,"cake",1000,10, R.drawable.cake_photo),
        Producto(0,"cake",1000,10, R.drawable.cake_photo),
        Producto(0,"cake",1000,10, R.drawable.cake_photo),
        Producto(0,"cake",1000,10, R.drawable.cake_photo),
        Producto(0,"cake",1000,10, R.drawable.cake_photo),
        Producto(0,"cake",1000,10, R.drawable.cake_photo),
        Producto(0,"cake",1000,10, R.drawable.cake_photo)
    )

    private lateinit var listViewProductos: ListView
    private lateinit var volverAlMenu: Button
    private lateinit var addProducto: Button

    private var tipoIngreso: Int = 0
    private var nombreUsuario: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_catalogo)

        nombreUsuario = intent.getStringExtra("nombreUsuario").toString()
        tipoIngreso = intent.getIntExtra("tipoUsuarioLogin", 0)

        volverAlMenu = findViewById(R.id.boton_catalogo_volverAlMenu)
        volverAlMenu.setOnClickListener { irAlMenuDesdeCatalogo() }
        addProducto = findViewById(R.id.boton_catalogo_addProducto)
        addProducto.setOnClickListener { irAlMenuDesdeCatalogo() }

        listViewProductos = findViewById(R.id.listv_productos)
        val adaptadorCatalogo = AdaptadorProducto(this, listaProductos)
        listViewProductos.adapter = adaptadorCatalogo

        when (tipoIngreso) {
            0 -> { addProducto.visibility = Button.GONE
                   val iconAdmin = findViewById<ImageView> (R.id.icon_properties_menu)
                   iconAdmin.visibility = ImageView.GONE
                   val textAdmin = findViewById<TextView> (R.id.txt_modAdministrador)
                   textAdmin.visibility = TextView.GONE }
        }
    }

    private fun irAlMenuDesdeCatalogo() {
        val irAlMenu = Intent(this, MenuPrincipal::class.java)
        irAlMenu.putExtra("tipoUsuarioLogin", this.tipoIngreso)
        irAlMenu.putExtra("nombreUsuario", this.nombreUsuario)
        startActivity(irAlMenu)
    }

    private fun obtenerListaDeBaseDatos() {
        /* TODO Implementar método que pregunte a la base de datos la lista de productos y
        *   actualize la lista a representar en seccion catalogo.*/
    }

    fun obtenerListaProductos(): List<Producto> {
        return listaProductos
    }

    fun agregarProductoEnLista() {
        /* TODO Implementar método que cambie instancia a una ficha vacía y si usuario confirma
        *   cambios añadir a lista de productos. Guardar en base de datos luego.*/
    }

    private fun quitarProductoEnLista() {
        /* TODO Implementar método en ficha producto modo editar*/
    }

    fun editarProductoEnLista() {
        /* TODO Implementar método que cambie instancia a la ficha del producto seleccionado
        *   y permita guardar cambios en ese producto. Guardar en base de datos.*/
    }
}
