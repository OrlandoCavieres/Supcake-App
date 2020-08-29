package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast

/**
 * Clase principal de autentificación de la aplicación
 */
class Login : AppCompatActivity() {

    // Declaración de elementos del layout para login
    private lateinit var usuarioIngresado: EditText
    private lateinit var passwordIngresado: EditText
    private lateinit var login: Button
    private lateinit var cargando: ProgressBar

    // Declaración inicial de guardado de credenciales.
    private var tipoUsuario = 0
    private var nombreUsuario = ""
    private var idUsuario = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Inicialización de los elementos del layout login.
        usuarioIngresado = findViewById(R.id.tedit_usuario)
        passwordIngresado = findViewById(R.id.password)
        login = findViewById(R.id.login)
        cargando = findViewById(R.id.anim_cargando)

        // Configuración del botón login.
        login.setOnClickListener { verificarDatosLogin() }
    }

    /**
     * Método que permite verificar los datos ingresados por el usuario en los editText del layout
     * login
     */
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

    /**
     * Método que verifica los datos ingresados por el usuario en la base de datos de la aplicación.
     * @param usuario Nombre del usuario.
     * @param password Contraseña del usuario.
     */
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

    /**
     * Método que gatilla el evento de un inicio de sesión satisfactorio hacia dentro de la aplicación.
     */
    private fun inicioSesionCorrecto() {
        val inicio = Intent(this, MenuPrincipal::class.java)
        inicio.putExtra("tipoUsuarioLogin", this.tipoUsuario)
        inicio.putExtra("nombreUsuarioLogin", this.nombreUsuario)
        inicio.putExtra("idLogin", this.idUsuario)
        Toast.makeText(this, "¡Bienvenido, ${this.nombreUsuario}!", Toast.LENGTH_SHORT).show()
        startActivity(inicio)
    }
}