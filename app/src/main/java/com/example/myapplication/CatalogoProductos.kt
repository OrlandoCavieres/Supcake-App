package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.util.*

/**
 * Clase que representa un catalogo de productos, que archiva en el él todos los productos en inventario
 * presentes en la base de datos de la aplicación.
 */
class CatalogoProductos : AppCompatActivity() {

    // Variable base del catalogo que contiene todos los productos disponibles.
    private var listaProductos: MutableList<Producto> = mutableListOf()

    // Declaración de los elementos de layout para catalogo a ser usados.
    private lateinit var listViewProductos: ListView
    private lateinit var volverAlMenu: Button
    private lateinit var addProducto: Button
    private lateinit var cuadroBusqueda: EditText
    private lateinit var botonBuscar: ImageButton

    private lateinit var catalogoVacio: TextView

    private lateinit var adaptadorCatalogo: AdaptadorProducto

    // Credenciales del usuario.
    private var tipoIngreso: Int = 0
    private var nombreUsuario: String = ""
    private var idUsuario = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.mainCatalogo()
    }

    /**
     * Método principal de la sección catalogo, que permite mostrar la lista de productos y manipularla,
     * ya sea editando un producto en lista, eliminandolo o añadiendo un nuevo producto.
     * Implementa las funciones necesarias que se esperan de esta sección.
     */
    private fun mainCatalogo() {
        setContentView(R.layout.activity_catalogo)

        // Actualización con base de datos de los productos y aplicación de orden alfabético.
        this.obtenerListaProductoBaseDatos()
        this.listaProductos.sortBy { it.nombre }

        // Obtener credenciales del usuario desde las otras activities.
        nombreUsuario = intent.getStringExtra("nombreUsuarioLogin").toString()
        tipoIngreso = intent.getIntExtra("tipoUsuarioLogin", 0)
        idUsuario = intent.getIntExtra("idLogin", 0)

        // Configuración botón volver al menu.
        volverAlMenu = findViewById(R.id.boton_catalogo_volverAlMenu)
        volverAlMenu.setOnClickListener { finish() }

        /* Configuracion botón añadir producto. Lleva a un nuevo activity para poder crear un nuevo
           producto para la base de datos. */
        addProducto = findViewById(R.id.boton_catalogo_addProducto)
        addProducto.setOnClickListener {
            val accion = Intent(this, NuevoProducto::class.java)
            accion.putExtra("tipoUsuarioLogin", this.tipoIngreso)
            accion.putExtra("nombreUsuarioLogin", this.nombreUsuario)
            startActivity(accion)
        }

        /* Configuración del edit Text para buscar un producto en el catalogo.
           Al hacer click en el se limpia al generar de nuevo el activity para buscar de cero nuevamente. */
        cuadroBusqueda = findViewById(R.id.editText_catalogo_textoBuscar)
        cuadroBusqueda.setOnClickListener {
            if (cuadroBusqueda.text.toString().isNotBlank()) {
                mainCatalogo()
            }
        }

       /* Configuración botón buscar en catalogo. Permite filtrar una lista y entregarsela al adaptador,
          despues de vaciar a este último, filtrando así el list View asociado y mostrando los
          productos filtrados. */
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

        // Inicializador de mensaje en caso que catalogo se encuentre vacio.
        catalogoVacio = findViewById(R.id.textView_mensajeCatalogoVacio)

        // Inicializador del adaptador encargado del List View en Catalogo para productos.
        listViewProductos = findViewById(R.id.listView_productosCatalogo)
        adaptadorCatalogo = AdaptadorProducto(this, listaProductos)
        listViewProductos.adapter = adaptadorCatalogo

        /* Configuración de click en cada item de la lista de productos, para poder entrar a sus detalles
           y edicion. Usuario vendedor no puede hacer click. */
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

        // Cuando catalogo esté vació se despliega solo un mensaje especificando que no hay productos.
        if (this.listaProductos.isEmpty()) {
            listViewProductos.visibility = ListView.GONE
            catalogoVacio.visibility = TextView.VISIBLE
        }

        // Cuando el usuario es vendedor se ocultan las opciones y elementos layout del administrador.
        if (this.tipoIngreso == 0) {
            addProducto.visibility = Button.GONE
            val iconAdmin = findViewById<ImageView>(R.id.icon_properties_menu)
            iconAdmin.visibility = ImageView.GONE
            val textAdmin = findViewById<TextView>(R.id.txt_modAdministrador)
            textAdmin.visibility = TextView.GONE
        }
    }

    /* Se sobreescribe el método onResume para que actualize constantemente el layout después de realizar
       un cambio. */
    override fun onResume() {
        super.onResume()
        this.adaptadorCatalogo.notifyDataSetChanged()
        this.mainCatalogo()
    }

    /**
     * Método que permite obtener la lista de productos disponible en la aplicación al consultar
     * a la base de datos de la aplicación.
     */
    private fun obtenerListaProductoBaseDatos() {
        this.listaProductos = ClasesBD.bD_Productos(this)
    }
}
