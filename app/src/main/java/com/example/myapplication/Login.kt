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
    private var idUsuario = 0

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

        val intento: Usuario? = ClasesBD.bD_Sesion(usuario, password, this)

        if(intento != null){
            this.tipoUsuario = intento.obtenertipoUser()
            this.nombreUsuario = intento.obtenerNombre()
            this.idUsuario = intento.obtenerID()
            inicioSesionCorrecto()

        }else{
            Toast.makeText(this, "Usuario o contraseña invalida", Toast.LENGTH_SHORT).show()
        }
    }

    private fun inicioSesionCorrecto() {
        val inicio = Intent(this, MenuPrincipal::class.java)
        inicio.putExtra("tipoUsuarioLogin", this.tipoUsuario)
        inicio.putExtra("nombreUsuarioLogin", this.nombreUsuario)
        inicio.putExtra("idLogin", this.idUsuario)
        Toast.makeText(this, "¡Bienvenido, ${this.nombreUsuario}!", Toast.LENGTH_SHORT).show()
        startActivity(inicio)
    }
}