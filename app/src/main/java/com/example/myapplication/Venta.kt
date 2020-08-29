package com.example.myapplication

import java.io.Serializable

/**
 * Clase que representa una venta realizada de un producto con su cantidad comprada, su total y su
 * descuento asociado.
 * Implementa Serializable que permite obtener los atributos y métodos del objeto en forma secuencial
 * para su uso en intents y en adaptadores.
 * @property producto Producto del que se esta realizando la venta.
 * @property cantidad Cantidad como numero entero que se esta vendiendo del producto.
 * @property descuento Cantidad asociada a descuento del precio normal del producto.
 */
class Venta : Serializable {
    private var producto: Producto = Producto(0,"",0,0,0)
    var cantidad: Int = 0
    var descuento: Int = 0

    /**
     * Método que permite calcular y obtener el total de la venta de un producto conociendo su precio,
     * el descuento aplicado y la cantidad a vender.
     * @return Entero que representa un total de venta.
     */
    fun obtenerTotal(): Int {
        return cantidad * producto.precio - (descuento * producto.precio / 100) * cantidad
    }

    /**
     * Método que permite obtener el producto asociado a la venta como instancia de clase Producto,
     * facilitando el acceso a sus atributos y métodos asociados.
     * @return Instancia de clase Producto.
     */
    fun obtenerProducto(): Producto {
        return this.producto
    }

    /**
     * Método que permite establecer cual es el producto que se está vendiendo.
     * @param nuevoProducto Producto de la venta.
     */
    fun establecerProducto(nuevoProducto: Producto) {
        producto = nuevoProducto
    }
}
