package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

/**
 * Clase que representa a un formulario apra un nuevo cliente.
 */
class NuevoCliente : AppCompatActivity() {

    // Declaración de elementos del layout para un nuevo cliente.
    private lateinit var volverAlMenu: Button
    private lateinit var cancelar: Button
    private lateinit var guardar: Button

    private lateinit var nombreNuevoCliente: EditText
    private lateinit var direccionNuevoCliente: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nuevo_cliente)

        // Configuración del botón volver al menu de forma directa.
        volverAlMenu = findViewById(R.id.boton_clientes_volverAlMenu2)
        volverAlMenu.setOnClickListener {
            val accion = Intent(this, MenuPrincipal::class.java)
                .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(accion)
        }

        // Configuracion de botón cancelar y guardar cambios.
        cancelar = findViewById(R.id.boton_cancelarNuevoCliente)
        cancelar.setOnClickListener { finish() }

        guardar = findViewById(R.id.boton_guardarNuevoCliente)
        guardar.setOnClickListener { verificarCamposFormulario() }

        // Inicialización de los campos de llenado del formulario.
        nombreNuevoCliente = findViewById(R.id.editText_nombreNuevoCliente)
        direccionNuevoCliente = findViewById(R.id.editText_direccionNuevoCliente)
    }

    /**
     * Método que permite verificar si los campos de llenado del formulario están correctos.
     */
    private fun verificarCamposFormulario() {
        val nombre = nombreNuevoCliente.text.toString()
        val direccion = direccionNuevoCliente.text.toString()

        if (nombre.isBlank()) {
            Toast.makeText(this, "Debe ingresar un nombre de cliente", Toast.LENGTH_SHORT).show()
        }
        if (direccion.isBlank()) {
            Toast.makeText(this, "Debe ingresar una direccion", Toast.LENGTH_SHORT).show()
        }
        if (nombre.isNotBlank() and nombre.isNotBlank()) {
            crearCliente(nombre, direccion)
            finish()
        }
    }

    /**
     * Método para crear un uevo cliente y añadirlo a la base de datos de la aplicación.
     * @param nombre Nombre del cliente.
     * @param direccion Dirección del cliente.
     */
    private fun crearCliente(nombre: String, direccion: String) {
        ClasesBD.bD_NuevoCliente(nombre, direccion, this)
    }
}