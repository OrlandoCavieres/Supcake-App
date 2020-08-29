package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import kotlinx.android.synthetic.main.elemento_lista_venta.view.*

/**
 * Clase que se encarga de crear el layout de elementos para listView en la seccion NuevaVenta, a partir
 * de una lista modificable de elementos en un carro compra y un ArrayAdapter hederado.
 * @property ventas Lista de ventas modificable segun elementos en carro compra que se utilizara para crear
 * el adaptador elemento.
 * @property contexto Instancia de activity en la que se inyectará el adaptador.
 */
class AdaptadorVenta(private val contexto: Context,
                     private val ventas: MutableList<Venta>) : ArrayAdapter<Venta>(contexto, 0, ventas) {

    private lateinit var cerrarVenta: ImageButton

    // Override al metodo getView para adaptarlo al layout de un elemento lsita venta para el carro de compras.
    @SuppressLint("ViewHolder", "SetTextI18n")
    override fun getView(posicion: Int, viewConvertido: View?, parent: ViewGroup): View {
        val layout = LayoutInflater.from(contexto).inflate(R.layout.elemento_lista_venta, parent,false)
        val venta = ventas[posicion]

        layout.textView_resumenVenta_mostrarNombreProducto.text = venta.obtenerProducto().nombre
        layout.textView_resumenVenta_mostrarPrecioProducto.text = "$${venta.obtenerProducto().precio}"
        layout.textView_resumenVenta_mostrarCant.text = venta.cantidad.toString()
        layout.textView_resumenVenta_mostrarDcto.text = "${venta.descuento}%"
        layout.textView_resumenVenta_mostrarTotal.text = "$${venta.obtenerTotal()}"

        // Configuración del ImageButton que cerrara y eliminara un elemento lista venta en el ListView
        cerrarVenta = layout.findViewById(R.id.boton_resumenVenta_cancelarVenta)
        cerrarVenta.setOnClickListener {
            ventas.removeAt(posicion)
            notifyDataSetChanged()
        }

        return layout
    }
}