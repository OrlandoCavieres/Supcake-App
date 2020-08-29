package com.example.myapplication

import java.io.Serializable

class Usuario (private val id: Int,
               private val nombre: String,
               private val tipoUser: Int): Serializable {

    fun obtenerID(): Int {
        return this.id
    }

    fun obtenerNombre(): String {
        return this.nombre
    }

    fun obtenertipoUser(): Int {
        return this.tipoUser
    }
}