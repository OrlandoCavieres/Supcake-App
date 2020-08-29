package com.example.myapplication

import java.io.Serializable

/**
 * Clase que representa a un cliente de la aplicación.
 * @property id Número identificador de cliente en base de datos.
 * @property nombre Nombre del cliente.
 * @property direccion Dirección del cliente.
 */
class Cliente(private val id: Int,
              private var nombre: String,
              private var direccion: String) : Serializable {

    // Atributos privados no necesarios para el constructor de clase. Variables internas.
    private var historial = mutableListOf<Historial>()
    private var numeroTotalCompras = 0

    /**
     * Método que permite obtener el identificador del cliente.
     * @return Int identificador
     */
    fun obtenerID(): Int {
        return this.id
    }

    /**
     * Método que permite obtener el nombre del cliente.
     * @return String nombre
     */
    fun obtenerNombre(): String {
        return this.nombre
    }

    /**
     * Método que permite obtener la dirección del cliente.
     * @return String dirección
     */
    fun obtenerDireccion(): String {
        return this.direccion
    }

    /**
     * Método que permite obtener el historial guardado de ventas realizadas al cliente.
     * @return Lista con las ventas realizadas
     */
    fun obtenerHistorial(): MutableList<Historial> {
        return this.historial
    }

    /**
     * Método que agrega una nueva compra al historial del cliente.
     * @param fecha Fecha de la venta a ingresar al historial.
     * @param total Valor total cancelado por la venta.
     */
    fun agregarNuevaCompra(fecha: String, total: Int) {
        this.numeroTotalCompras += 1
        this.historial.add(Historial(fecha, total))
    }
}