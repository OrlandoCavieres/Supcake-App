package com.example.myapplication

import java.io.Serializable

/**
 * Clase que representa a un usuario en la aplicación y que guarda sus nombre, el tipo usuario y
 * el id de la base de datos para su autentificación.
 * Implementa serializable que permite obtener los atributos y métodos del objeto en forma secuencial
 * para su uso en intents y en adaptadores.
 * @property id Identificador del usuario en la base de datos.
 * @property nombre Nombre del usuario.
 * @property tipoUser Número entero asociado al tipo de usuario que ingresa. Es 1 si es administrador
 * y 0 si es vendedor.
 */
class Usuario (private val id: Int,
               private val nombre: String,
               private val tipoUser: Int): Serializable {

    /**
     * Método que permite obtener el identificador del usuario.
     * @return Numero que representa al identificador.
     */
    fun obtenerID(): Int {
        return this.id
    }

    /**
     * Método que permite obtener el nombre asociado al usuario.
     * @return Nombre del usuario como string.
     */
    fun obtenerNombre(): String {
        return this.nombre
    }

    /**
     * Método que permite obtener el tipo de usuario asociado al login del usuario.
     * @return 0 o 1.
     */
    fun obtenertipoUser(): Int {
        return this.tipoUser
    }
}