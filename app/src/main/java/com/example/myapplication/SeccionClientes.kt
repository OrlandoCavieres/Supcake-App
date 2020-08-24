package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView

class SeccionClientes : AppCompatActivity() {

    private lateinit var volverAlMenu: Button

    private lateinit var addCliente: Button
    private lateinit var planillaClientes: ListView

    private var tipoIngreso: Int = 0
    private var nombreUsuario: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clientes)

        nombreUsuario = intent.getStringExtra("nombreUsuario").toString()
        tipoIngreso = intent.getIntExtra("tipoUsuarioLogin", 0)

        volverAlMenu = findViewById(R.id.boton_clientes_volverAlMenu)
        volverAlMenu.setOnClickListener { finish() }

        addCliente = findViewById(R.id.boton_clientes_addCliente)
        addCliente.setOnClickListener {  }

        planillaClientes = findViewById(R.id.listView_planillaClientes)

        if (tipoIngreso == 0) {
            val iconAdmin = findViewById<ImageView> (R.id.icon_properties_menu)
            iconAdmin.visibility = ImageView.GONE
            val textAdmin = findViewById<TextView> (R.id.txt_modAdministrador)
            textAdmin.visibility = TextView.GONE
        }
    }
}