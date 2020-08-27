package com.example.myapplication

import java.io.Serializable

class Venta : Serializable {
    private var producto: Producto = Producto(0,"",0,0,0)
    var cantidad: Int = 0
    var descuento: Int = 0

    fun obtenerTotal(): Int {
        return cantidad * producto.precio - (descuento * producto.precio / 100) * cantidad
    }

    fun obtenerProducto(): Producto {
        return this.producto
    }

    fun establecerProducto(nuevoProducto: Producto) {
        producto = nuevoProducto
    }
}
