package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast

class Login : AppCompatActivity() {

    private lateinit var usuarioIngresado: EditText
    private lateinit var passwordIngresado: EditText
    private lateinit var login: Button
    private lateinit var cargando: ProgressBar

    private var tipoUsuario = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        usuarioIngresado = findViewById(R.id.tedit_usuario)
        passwordIngresado = findViewById(R.id.password)
        login = findViewById(R.id.login)
        cargando = findViewById(R.id.anim_cargando)

        login.setOnClickListener { inicioSesionCorrecto() }
    }

    /* TODO Necesarios métodos de verificación con base de datos de los datos ingresados.
    *   Manejo explicito del boton entrar para determinar si el login tiene error o no.
    *   Si usuario es administrador cambiar tipoUsuario a 1.
    *   En cambio si es vendedor, no hacer nada.
    */


    private fun inicioSesionCorrecto() {
        val inicio = Intent(this, MenuPrincipal::class.java)
        inicio.putExtra("tipoUsuarioLogin", this.tipoUsuario)
        inicio.putExtra("nombreUsuario", usuarioIngresado.text.toString())
        Toast.makeText(this, "¡Bienvenido, ${this.usuarioIngresado.text}!", Toast.LENGTH_SHORT).show()
        startActivity(inicio)
    }
}