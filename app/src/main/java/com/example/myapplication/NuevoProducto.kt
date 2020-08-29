package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

/**
 * Clase que representa el formulario para un nuevo producto y controla las características y creación
 * del mismo.
 */
class NuevoProducto : AppCompatActivity() {

    // Declaración de los elementos del layput ficha Nuevo Producto.
    private lateinit var volverAlMenu: Button
    private lateinit var cancelar: Button
    private lateinit var guardar: Button

    private lateinit var nombreNuevoProducto: EditText
    private lateinit var precioNuevoProducto: EditText
    private lateinit var stockNuevoProducto: EditText

    private lateinit var imagenNuevoProducto: ImageView
    private lateinit var examinarImagen: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nuevo_producto)

        // Configuración del botón ir al menu principal de forma directa.
        volverAlMenu = findViewById(R.id.boton_catalogo_volverAlMenu2)
        volverAlMenu.setOnClickListener {
            val accion = Intent(this, MenuPrincipal::class.java)
                .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(accion)
        }

        // Configuración de los botones cancelar y guardar cambios.
        cancelar = findViewById(R.id.boton_cancelarNuevoProducto)
        cancelar.setOnClickListener { finish() }

        guardar = findViewById(R.id.boton_guardarNuevoProducto)
        guardar.setOnClickListener { verificarCamposFormulario() }

        // Inicialización de los campos de llenado del formulario.
        nombreNuevoProducto = findViewById(R.id.editText_NombreNuevoProducto)
        precioNuevoProducto = findViewById(R.id.editText_precioNuevoProducto)
        stockNuevoProducto = findViewById(R.id.editText_stockDisponibleNuevoProducto)
        imagenNuevoProducto = findViewById(R.id.imgv_fotoNuevoProducto)

        examinarImagen = findViewById(R.id.boton_examinarImagen)
        examinarImagen.setOnClickListener { cambiarImagen() }
    }

    /**
     * Método que permite cambiar la imagen en la ficha de un nuevo producto.
     */
    private fun cambiarImagen() {
        /* Se necesita implementar una busqueda de imagen, reemplazarla en imageView
         *  imagenNuevoProducto para luego poder almacenarla en la base de datos o en drawable. */
    }

    /**
     * Método que permite verificar si los campos del formulario están correctamente llenados y procede si es así
     * con la creación de un producto en la aplicación
     */
    private fun verificarCamposFormulario() {
        var imagenIngresada = false
        val nombreProducto = nombreNuevoProducto.text.toString()
        val precioProducto = precioNuevoProducto.text.toString()
        val stockProducto = stockNuevoProducto.text.toString()
        if (nombreProducto.isBlank()) {
            Toast.makeText(this, "Debe escribir un nombre al producto", Toast.LENGTH_SHORT).show()
        }
        if (precioProducto.isBlank()) {
            Toast.makeText(this, "El producto debe tener un precio", Toast.LENGTH_SHORT).show()
        }
        if (stockProducto.isBlank()) {
            Toast.makeText(this, "El producto debe tener un stock", Toast.LENGTH_SHORT).show()
        }
        if (!imagenIngresada) {
            /* TODO Usar funcion cambiar imagen aca, ya que no hay imagen.*/
            imagenIngresada = true
        }
        if (nombreProducto.isNotBlank() && precioProducto.isNotBlank() && stockProducto.isNotBlank() && imagenIngresada) {
            crearProducto(nombreProducto, precioProducto.toInt(), stockProducto.toInt())
            finish()
        }
    }

    /**
     * Método que permite crear y agregar un nuevo producto a la base de datos de la aplicación.
     * @param nombreProducto Nombre del producto a añadir.
     * @param precioProducto Precio asociado al producto a añadir.
     * @param stockProducto Cantidad asociada en inventario para el nuevo producto.
     */
    private fun crearProducto(nombreProducto: String, precioProducto: Int, stockProducto: Int) {
        ClasesBD.bD_NuevoProducto(nombreProducto, precioProducto, stockProducto, this)
    }
}