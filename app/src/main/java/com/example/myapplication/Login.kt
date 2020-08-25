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

    private var tipoUsuario = 0
    private var nombreUsuario = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        usuarioIngresado = findViewById(R.id.tedit_usuario)
        passwordIngresado = findViewById(R.id.password)
        login = findViewById(R.id.login)
        cargando = findViewById(R.id.anim_cargando)

        login.setOnClickListener { verificarDatosLogin() }
    }

    private fun verificarDatosLogin() {
        val usuario = usuarioIngresado.text.toString()
        val password = passwordIngresado.text.toString()
        if (usuario.isBlank()) {
            Toast.makeText(this, "Debe ingresar el nombre de usuario", Toast.LENGTH_SHORT).show()
        }
        if (password.isBlank()) {
            Toast.makeText(this, "Debe ingresar una contraseña", Toast.LENGTH_SHORT).show()
        }
        if (usuario.isNotBlank() && password.isNotBlank()) {
            cargando.visibility = ProgressBar.VISIBLE
            this.verificarUsuarioBaseDatos(usuario, password)
        }
    }

    private fun verificarUsuarioBaseDatos(usuario: String, password: String) {
        /* TODO Implementar verificación con base de datos del usuario.
        *   Cambiar tipoUsuario segun corresponda. 1 = Admin, 0 = Vendedor*/
        this.tipoUsuario = 1
        this.nombreUsuario = usuario
        inicioSesionCorrecto()
    }

    private fun inicioSesionCorrecto() {
        val inicio = Intent(this, MenuPrincipal::class.java)
        inicio.putExtra("tipoUsuarioLogin", this.tipoUsuario)
        inicio.putExtra("nombreUsuarioLogin", this.nombreUsuario)
        Toast.makeText(this, "¡Bienvenido, ${this.nombreUsuario}!", Toast.LENGTH_SHORT).show()
        startActivity(inicio)
    }
}