package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageButton
import kotlinx.android.synthetic.main.elemento_lista_clientes.view.*

/**
 * Clase que se encarga de crear el layout de elementos para listView en la seccionClientes, a partir
 * de una lista de clientes y un arrayadapter hederado.
 * @property datos Lista de clientes utilizada para crear el adaptador elemento cliente.
 * @property contexto Instancia de activity en la que se inyectará el adaptador.
 * @property tipoIngreso Corresponde a un entero que simboliza los permisos del usuario.
 */
class AdaptadorCliente(private val contexto: Context,
                       private val datos: List<Cliente>,
                       private val tipoIngreso: Int): ArrayAdapter<Cliente>(contexto, 0, datos) {

    /**
     * Se se realiza override a la función getView para adaptarse al layout del elemento lista para clientes.
     */
    @SuppressLint("ViewHolder", "SetTextI18n")
    override fun getView(posicion: Int, viewConvertido: View?, parent: ViewGroup): View {
        val layout = LayoutInflater.from(contexto).inflate(R.layout.elemento_lista_clientes, parent,false)
        val cliente = datos[posicion]

        layout.textView_IDCliente.text = "# ${cliente.obtenerID()}"
        layout.textView_nombreCliente.text = cliente.obtenerNombre()

        // Si se hace click en boton eliminar cliente se pasa a la actividad correspondiente para confitmar la eliminacion
        layout.boton_eliminarCliente.setOnClickListener {
            val accion = Intent(contexto, ConfirmarEliminarCliente::class.java)
            accion.putExtra("tipoUsuarioLogin", tipoIngreso)
            accion.putExtra("cliente", cliente)
            contexto.startActivity(accion)
        }

        if (tipoIngreso == 0) {
            layout.boton_accederHistorialCliente.visibility = ImageButton.GONE
            layout.boton_eliminarCliente.visibility = ImageButton.GONE
        }

        return layout
    }
}