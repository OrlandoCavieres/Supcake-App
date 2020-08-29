package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import kotlinx.android.synthetic.main.activity_clientes.*
import kotlinx.android.synthetic.main.activity_confirmar_eliminar_cliente.*

class ConfirmarEliminarCliente : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirmar_eliminar_cliente)

        val cliente = intent.getSerializableExtra("cliente") as Cliente

        listView_planillaClientes.visibility = ListView.GONE
        boton_clientes_addCliente.visibility = Button.GONE

        boton_cancelarEliminarCliente.setOnClickListener { finish() }
        boton_confirmarEliminarCliente.setOnClickListener {
            eliminarClienteBD(cliente)
            finish()
        }
    }

    private fun eliminarClienteBD(cliente: Cliente) {
        ClasesBD.bD_EliminarCliente(cliente.obtenerID(), this)
    }
}