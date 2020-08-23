package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast

class NuevoProducto : AppCompatActivity() {

    private lateinit var volverAlMenu: Button
    private lateinit var cancelar: Button
    private lateinit var guardar: Button

    private lateinit var nombreNuevoProducto: EditText
    private lateinit var precioNuevoProducto: EditText
    private lateinit var stockNuevoProducto: EditText

    private lateinit var imagenNuevoProducto: ImageView
    private lateinit var examinarImagen: Button

    private var tipoIngreso: Int = 0
    private var nombreUsuario: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nuevo_producto)

        nombreUsuario = intent.getStringExtra("nombreUsuario").toString()
        tipoIngreso = intent.getIntExtra("tipoUsuarioLogin", 0)

        volverAlMenu = findViewById(R.id.boton_catalogo_volverAlMenu2)
        volverAlMenu.setOnClickListener { accionBotonCambioActivity("VolverAlMenu") }
        cancelar = findViewById(R.id.boton_cancelarNuevoProducto)
        cancelar.setOnClickListener { accionBotonCambioActivity("Cancelar") }
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
        TODO("Se necesita implementar una busqueda de imagen, reemplazarla en imageView " +
                "imagenNuevoProducto para luego poder almacenarla en la base de datos o en drawable.")
    }

    private fun verificarCamposFormulario() {
        val nombreProducto = nombreNuevoProducto.text.toString()
        val precioProducto = precioNuevoProducto.text.toString()
        val stockProducto = stockNuevoProducto.text.toString()
        if (nombreProducto.isEmpty()) {
            Toast.makeText(this, "Debe escribir un nombre al producto", Toast.LENGTH_SHORT).show()
        }
        if (precioProducto.isEmpty()) {
            Toast.makeText(this,"El producto debe tener un precio", Toast.LENGTH_SHORT).show()
        }
        if (stockProducto.isEmpty()) {
            Toast.makeText(this, "El producto debe tener un stock", Toast.LENGTH_SHORT).show()
        }
        if (nombreProducto.isNotEmpty() && precioProducto.isNotEmpty() && stockProducto.isNotEmpty()) {
            this.accionBotonCambioActivity("GuardarCambios")
        }
    }

    private fun accionBotonCambioActivity(nombreAccion: String) {
        val accion = when (nombreAccion) {
            "VolverAlMenu" -> Intent(this, MenuPrincipal::class.java)
            "Cancelar", "GuardarCambios" -> Intent(this, Catalogo::class.java)
            else -> null
        }
        accion!!.putExtra("tipoUsuarioLogin", this.tipoIngreso)
        accion.putExtra("nombreUsuario", this.nombreUsuario)
        when (nombreAccion) {
            "GuardarCambios" -> crearProducto()
        }
        startActivity(accion)
    }

    private fun crearProducto() {
        TODO("Se necesita que la base de datos otorgue el id y guarde el producto para ser " +
                "usado en el catalogo. */")
    }
}