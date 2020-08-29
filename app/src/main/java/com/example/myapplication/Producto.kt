package com.example.myapplication

import java.io.Serializable

/**
 * Clase que representa a un producto dentro de la aplicación, considerando los atributos básicos
 * que este debe tener en una aplicación de venta.
 * Implementa Serializable que permite obtener los atributos del objeto de forma secuencial para su
 * aplicación en intents y en adaptadores.
 * @property id Identificador en la base de datos del producto.
 * @property nombre Nombre del producto.
 * @property precio Precio asociado al producto.
 * @property cantidad Cantidad en stock disponible del producto.
 * @property imagen Identificador de imagen del producto.
 */
class Producto(private val id: Int,
               var nombre: String,
               var precio: Int,
               private var cantidad: Int,
               var imagen: Int): Serializable {

    /**
     * Método que permite obtener el identificador asociado del producto.
     * @return Entero que representa al identificador del producto.
     */
    fun obtenerID(): Int {
        return this.id
    }

    /**
     * Método que permite obtener la cantidad actual disponible en stock del producto.
     * @return Entero que representa cantidad producto.
     */
    fun obtenerStock(): Int {
        return this.cantidad
    }

    /**
     * Método que permite de forma sutil modificar la cantidad de un producto y sin mayor intervención
     * del administrador para mantener seguro el stock actual del producto.
     * @param modificador Valor en que se modifica la cantidad stock del producto.
     */
    fun modificarCantidad(modificador: Int) {
        this.cantidad += modificador
    }
}