package com.example.myapplication

class Producto(private val id: Int,
               var nombre: String,
               var precio: Int,
               var cantidad: Int,
               var imagen: Int) {

    fun getID(): Int {
        return this.id
    }
}