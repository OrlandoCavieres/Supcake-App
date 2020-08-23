package com.example.myapplication

import java.io.Serializable

class Producto(private val id: Int,
               var nombre: String,
               var precio: Int,
               private var cantidad: Int,
               var imagen: Int): Serializable {

    fun getID(): Int {
        return this.id
    }

    fun obtenerStock(): Int {
        return this.cantidad
    }

    fun modificarCantidad(modificador: Int) {
        this.cantidad += modificador
    }
}