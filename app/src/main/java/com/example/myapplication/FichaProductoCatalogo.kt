package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

/**
 * Clase que representa la ficha de detalles de un producto en el catalogo de la aplicación.
 */
class FichaProductoCatalogo : AppCompatActivity() {

    // Declaración de los elementos en el layout de la ficha de producto.
    private lateinit var volverAlMenu: Button
    private lateinit var cancelar: Button
    private lateinit var guardar: Button
    private lateinit var eliminarProducto: Button
    private lateinit var examinarImagen: Button

    private lateinit var nombreProducto: EditText
    private lateinit var precioProducto: EditText
    private lateinit var stockActualProducto: TextView
    private lateinit var modificadorStockProducto: EditText
    private lateinit var imagenProducto: ImageView

    // Switch para saber si la imagen fue cambiada.
    private var imagenCambiada = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ficha_producto_catalogo)

        // Inicializar los elementos del layout para datos del producto en la ficha.
        nombreProducto = findViewById(R.id.editText_cambioNombreProducto)
        precioProducto = findViewById(R.id.editText_cambioPrecioProducto)
        stockActualProducto = findViewById(R.id.textView_stockProductoDisponible)
        modificadorStockProducto = findViewById(R.id.editText_modificarStockProducto)
        imagenProducto = findViewById(R.id.imgv_fotoProductoFicha)

        // Obtener producto del activity Catalogo, del que se debe mostrar la ficha.
        val producto = intent.getSerializableExtra("producto") as Producto

        // Mostrar información del producto en pantalla mediante los elementos correspondientes.
        stockActualProducto.text = producto.obtenerStock().toString()
        imagenProducto.setImageResource(producto.imagen)
        nombreProducto.hint = producto.nombre
        precioProducto.hint = "$ ${producto.precio}"

        // COnfiguración del botón volver al menu.
        volverAlMenu = findViewById(R.id.boton_catalogo_volverAlMenu3)
        volverAlMenu.setOnClickListener { irAlMenuPrincipal() }

        // Configuración del botón cancelar.
        cancelar = findViewById(R.id.boton_cancelarEditarProducto)
        cancelar.setOnClickListener { finish() }

        /* COnfiguración del botón guardar cambios, el que realiza verificación de cambios en
        los detalles del producto */
        guardar = findViewById(R.id.boton_guardarEditarProducto)
        guardar.setOnClickListener {
            verificarCambios(producto)
            finish()
        }

        /* Configuración del botón eliminar producto, gatilla el evento de eliminar de base de datos. */
        eliminarProducto = findViewById(R.id.boton_eliminarProducto)
        eliminarProducto.setOnClickListener {
            quitarProductoBD(producto)
            finish()
        }

        /* Examinar la imagen ha sido cambiada por gatillo del boton examinar imagen.*/
        examinarImagen = findViewById(R.id.boton_examinarImagen2)
        examinarImagen.setOnClickListener {
            cambiarImagen()
            imagenCambiada = true
        }
    }

    /**
     * Método que permite cambiar la imagen de la ficha de un producto.
     */
    private fun cambiarImagen() {
        /* Se necesita implementar una busqueda de imagen, reemplazarla en imageView
        imagenNuevoProducto para luego poder almacenarla en la base de datos o en drawable.*/
    }

    /**
     * Método que permite volver directamente al menu principal a pesar de estar en un activity
     * hijo de catalogo.
     */
    private fun irAlMenuPrincipal() {
        val accion = Intent(this, MenuPrincipal::class.java)
            .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(accion)
    }

    /**
     * Método que permite verificar si existieron cambios en la ficha del producto inrgesados por el
     * usuario.
     * @param producto Producto en el que se basa la ficha.
     */
    private fun verificarCambios(producto: Producto) {
        val nombreProducto = nombreProducto.text.toString()
        val precioProducto = precioProducto.text.toString()
        val modStockProducto = modificadorStockProducto.text.toString()

        if (nombreProducto != producto.nombre && nombreProducto.isNotEmpty()) {
            producto.nombre = nombreProducto
        }
        if (precioProducto != producto.precio.toString() && precioProducto.isNotEmpty()) {
            producto.precio = precioProducto.toInt()
        }
        if (modStockProducto.isNotEmpty() && modStockProducto.toInt() != 0) {
            producto.modificarCantidad(modStockProducto.toInt())
        }
        if (imagenCambiada) {
            producto.imagen = R.drawable.cake_photo
        }
        modificarProductoBD(producto)
        finish()
    }

    /**
     * Método que modifica los datos del producto en la base de datos de la aplicación.
     */
    private fun modificarProductoBD(producto: Producto) {
        ClasesBD.bD_ModificarProducto(producto,this)
    }

    /**
     * Método que quita la entrada del producto de la base de datos de la aplicación.
     */
    private fun quitarProductoBD(producto: Producto) {
        ClasesBD.bD_EliminarProducto(producto.obtenerID(), this)
    }
}