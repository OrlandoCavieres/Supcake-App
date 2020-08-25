package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class NuevoProducto : AppCompatActivity() {

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

        volverAlMenu = findViewById(R.id.boton_catalogo_volverAlMenu2)
        volverAlMenu.setOnClickListener {
            val accion = Intent(this, MenuPrincipal::class.java)
                .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(accion)
        }

        cancelar = findViewById(R.id.boton_cancelarNuevoProducto)
        cancelar.setOnClickListener { finish() }

        guardar = findViewById(R.id.boton_guardarNuevoProducto)
        guardar.setOnClickListener { verificarCamposFormulario() }

        nombreNuevoProducto = findViewById(R.id.editText_NombreNuevoProducto)
        precioNuevoProducto = findViewById(R.id.editText_precioNuevoProducto)
        stockNuevoProducto = findViewById(R.id.editText_stockDisponibleNuevoProducto)
        imagenNuevoProducto = findViewById(R.id.imgv_fotoNuevoProducto)

        examinarImagen = findViewById(R.id.boton_examinarImagen)
        examinarImagen.setOnClickListener { cambiarImagen() }
    }

    private fun cambiarImagen() {
        /* TODO Se necesita implementar una busqueda de imagen, reemplazarla en imageView
         *  imagenNuevoProducto para luego poder almacenarla en la base de datos o en drawable. */
    }

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
            crearProducto()
            finish()
        }
    }

    private fun crearProducto() {
        /* TODO Se necesita que la base de datos otorgue el id y guarde el producto para ser
            usado en el catalogo*/
    }
}