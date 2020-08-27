package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import java.util.*

class ConfirmarVenta : AppCompatActivity() {

    private var tipoIngreso = 0
    private var nombreUsuario = ""

    private lateinit var carroCompras: MutableList<Venta>

    private var fechaActual = Calendar.getInstance().time.toString()
    private var totalVenta: Int = 0
    private var NombreCLiente: String = ""
    private var idCliente: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirmar_venta)
    }
}