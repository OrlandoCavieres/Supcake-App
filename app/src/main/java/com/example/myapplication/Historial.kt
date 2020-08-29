package com.example.myapplication

import java.io.Serializable

/**
 * Clase que representa el historial de ventas realizadas a un cliente
 * @property fecha Representa fecha de la venta.
 * @property total Valor total de la venta.
 */
class Historial(private val fecha: String,
                private val total: Int): Serializable {

    /**
     * Método que permite obtener la fecha de una venta en el historial.
     * @return Fecha como string
     */
    fun obtenerFecha(): String {
        return this.fecha
    }

    /**
     * Método que permite obtener el valor total de la venta.
     * @return Valor entero.
     */
    fun obtenerTotal(): Int {
        return this.total
    }
}
