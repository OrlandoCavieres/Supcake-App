package com.example.myapplication

import java.io.Serializable

class Historial(private val fecha: String,
                private val total: Int): Serializable {

    fun obtenerFecha(): String {
        return this.fecha
    }

    fun obtenerTotal(): Int {
        return this.total
    }
}
