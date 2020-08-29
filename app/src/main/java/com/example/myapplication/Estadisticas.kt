package com.example.myapplication

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.*

/**
 * Clase central de la sección estadísticas de la aplicación.
 */
class Estadisticas : AppCompatActivity() {

    // Declaración de los elementos del layout Estadísticas.
    private lateinit var volverAlMenu: Button
    private lateinit var obtenerEstadisticas: Button

    private lateinit var nombreProducto: EditText
    private lateinit var nombreVendedor: EditText
    private lateinit var fechaInicial: TextView
    private lateinit var fechaFinal: TextView

    private lateinit var calendarioFechaInicial: ImageButton
    private lateinit var calendarioFechaFinal: ImageButton

    // Credenciales de inicio de sesión
    private var tipoIngreso: Int = 0
    private var nombreUsuario: String = ""
    private var idUsuario: Int = 0

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_estadisticas)

        // Obtener de intent las credenciales del usuario.
        nombreUsuario = intent.getStringExtra("nombreUsuarioLogin").toString()
        tipoIngreso = intent.getIntExtra("tipoUsuarioLogin", 0)
        idUsuario = intent.getIntExtra("idLogin", 0)

        // Inicialización de elementos del layout de sección Estadísticas
        nombreProducto = findViewById(R.id.editText_estadisticas_filtroProducto)
        nombreVendedor = findViewById(R.id.editText_estadisticas_filtroVendedor)
        fechaInicial = findViewById(R.id.textView_estadisticas_filtroFechaInicial)
        fechaFinal = findViewById(R.id.textView_estadisticas_filtroFechaFinal)

        calendarioFechaInicial = findViewById(R.id.boton_verCalendarioFechaInicial)
        calendarioFechaFinal = findViewById(R.id.boton_verCalendarioFechaFinal)

        // Establecer configuración del botón volver al menu principal.
        volverAlMenu = findViewById(R.id.boton_estadisticas_volverAlMenu)
        volverAlMenu.setOnClickListener { finish() }

        // Establecer configuración del botón obtener Estadísticas.
        obtenerEstadisticas = findViewById(R.id.boton_estadisticas_obtenerEstadisticas)
        obtenerEstadisticas.setOnClickListener {  }

        // Creación del selector de fecha inicial de búsqueda en sección Estadísticas.
        calendarioFechaInicial.setOnClickListener {
            val now = Calendar.getInstance()
            val datePicker = DatePickerDialog(this, { _, year, mes, dia ->
                fechaInicial.text = "${dia}/${mes + 1}/${year}"
            }, now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH))
            datePicker.show()
        }

        // Creación del selector de fecha final de búsqueda en sección Estadísticas.
        calendarioFechaFinal.setOnClickListener {
            val now = Calendar.getInstance()
            val datePicker = DatePickerDialog(this, { _, year, mes, dia ->
                fechaFinal.text = "${dia}/${mes + 1}/${year}"
            }, now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH))
            datePicker.show()
        }
    }
}












