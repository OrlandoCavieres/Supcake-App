package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

class FichaProductoCatalogo : AppCompatActivity() {

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

    private var imagenCambiada = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ficha_producto_catalogo)

        nombreProducto = findViewById(R.id.editText_cambioNombreProducto)
        precioProducto = findViewById(R.id.editText_cambioPrecioProducto)
        stockActualProducto = findViewById(R.id.textView_stockProductoDisponible)
        modificadorStockProducto = findViewById(R.id.editText_modificarStockProducto)
        imagenProducto = findViewById(R.id.imgv_fotoProductoFicha)

        val producto = intent.getSerializableExtra("producto") as Producto

        stockActualProducto.text = producto.obtenerStock().toString()
        imagenProducto.setImageResource(producto.imagen)
        nombreProducto.hint = producto.nombre
        precioProducto.hint = "$ ${producto.precio}"

        volverAlMenu = findViewById(R.id.boton_catalogo_volverAlMenu3)
        volverAlMenu.setOnClickListener { irAlMenuPrincipal() }

        cancelar = findViewById(R.id.boton_cancelarEditarProducto)
        cancelar.setOnClickListener { finish() }

        guardar = findViewById(R.id.boton_guardarEditarProducto)
        guardar.setOnClickListener { verificarCambios(producto) }

        eliminarProducto = findViewById(R.id.boton_eliminarProducto)
        eliminarProducto.setOnClickListener { quitarProductoBD(producto) }

        examinarImagen = findViewById(R.id.boton_examinarImagen2)
        examinarImagen.setOnClickListener { cambiarImagen()
                                            imagenCambiada = true }
    }

    private fun cambiarImagen() {
        /* TODO Se necesita implementar una busqueda de imagen, reemplazarla en imageView
        *   imagenNuevoProducto para luego poder almacenarla en la base de datos o en drawable.*/
    }

    private fun irAlMenuPrincipal() {
        val accion = Intent(this, MenuPrincipal::class.java)
            .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(accion)
    }

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

    private fun modificarProductoBD(producto: Producto) {

        ClasesBD.bD_ModificarProducto(producto,this)
    }

    private fun quitarProductoBD(producto: Producto) {

        ClasesBD.bD_EliminarProducto(producto.obtenerID(), this)
    }
}