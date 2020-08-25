package com.example.myapplication

import java.io.Serializable

class Cliente(private val id: Int,
              private var nombre: String,
              private var direccion: String) : Serializable {

    private var historial = mutableListOf<Historial>()
    private var numeroTotalCompras = 0

    fun obtenerID(): Int {
        return this.id
    }

    fun obtenerNombre(): String {
        return this.nombre
    }

    fun obtenerDireccion(): String {
        return this.direccion
    }

    fun obtenerHistorial(): MutableList<Historial> {
        return this.historial
    }

    fun agregarNuevaCompra(fecha: String, total: Int) {
        this.numeroTotalCompras += 1
        this.historial.add(Historial(fecha, total))
    }
}