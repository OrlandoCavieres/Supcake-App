package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import kotlinx.android.synthetic.main.activity_clientes.*
import kotlinx.android.synthetic.main.activity_confirmar_eliminar_cliente.*

/**
 * Clase centrada en solicitar confirmación de la eliminacion de un cliente al usuario.
 */
class ConfirmarEliminarCliente : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirmar_eliminar_cliente)

        // Obtener el cliente seleccionado.
        val cliente = intent.getSerializableExtra("cliente") as Cliente

        // Ocultar botones y elementos no necesarios del layout para dar foco al mensaje.
        listView_planillaClientes.visibility = ListView.GONE
        boton_clientes_addCliente.visibility = Button.GONE

        // Configuracion de los botones en el layout correspondiente a confirmación y cancelar.
        boton_cancelarEliminarCliente.setOnClickListener { finish() }
        boton_confirmarEliminarCliente.setOnClickListener {
            eliminarClienteBD(cliente)
            finish()
        }
    }

    /**
     * Metodo que elimina al cliente seleccionado de la base de datos de la aplicacion.
     * @param cliente Instancia de clase cliente a eliminar.
     */
    private fun eliminarClienteBD(cliente: Cliente) {
        ClasesBD.bD_EliminarCliente(cliente.obtenerID(), this)
    }
}