package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.elemento_lista_productos.view.*

/**
 * Clase que se encarga de crear el layout de elementos para listView en la seccion CatalogoProductos,
 * a partir de una lista de productos y un ArrayAdapter hederado.
 * @property datos Lista de productos que se utilizara para crear el adaptador elemento producto.
 * @property contexto Instancia de activity en la que se inyectará el adaptador.
 */
class AdaptadorProducto(private val contexto: Context,
                        private val datos: List<Producto>): ArrayAdapter<Producto>(contexto, 0, datos) {

    // Se realiza override al metodo getView para adaptarse al layout del elemento lista producto.
    @SuppressLint("ViewHolder", "SetTextI18n")
    override fun getView(posicion: Int, viewConvertido: View?, parent: ViewGroup): View {
        val layout = LayoutInflater.from(contexto).inflate(R.layout.elemento_lista_productos, parent,false)
        val producto = datos[posicion]

        layout.textView_nombreProductoResultado.text = producto.nombre
        layout.textView_precioProductoResultado.text = "$ ${producto.precio}"
        layout.imgv_fotoProductoCatalogo.setImageResource(producto.imagen)
        return layout
    }
}