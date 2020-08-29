package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.Layout
import android.text.TextWatcher
import android.view.View
import android.widget.*
import org.w3c.dom.Text
import java.util.*

/**
 * Clase que representa la sección Nueva Venta y sus atributos como generar carro de compra y resultados
 * como añadir al registro de ventas de la bas de datos.
 */
class SeccionNuevaVenta : AppCompatActivity() {

    // Listas de datos necesarias para realziar carro de compras.
    private var carroCompras: MutableList<Venta> = mutableListOf()
    private var listaProductos: MutableList<Producto> = mutableListOf()

    // Variables que guardan la fecha de venta y el id del cliente si existe.
    private var fechaActual = Calendar.getInstance().time.toString()
    private var idCliente: Int = -1

    // Declaración de los elementos del layout de la sección.
    private lateinit var volverAlMenu: Button
    private lateinit var continuarVenta: Button
    private lateinit var nuevoProductoCarroCompra: ImageButton
    private lateinit var listaCarrito: ListView
    private lateinit var adaptadorCarrito: AdaptadorVenta

    private lateinit var elegirProducto: Spinner
    private lateinit var elegirCantidad: EditText
    private lateinit var elegirDescuento: EditText
    private lateinit var mostrarPrecioProducto: TextView
    private lateinit var mostrarTotalVentaProducto: TextView
    private lateinit var mostrarTotalVentaGlobal: TextView

    // Credenciales del usuario
    private var tipoIngreso: Int = 0
    private var nombreUsuario: String = ""
    private var idUsuario = 0

    // Crear elemento para rellenar y meter al carro.
    var nuevaVentaProducto = Venta()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nueva_venta)

        // Se recupera la lista de productos de la base de datos al inicio.
        this.recuperarListaProductosBD()

        // Obtener credenciales del usuario
        nombreUsuario = intent.getStringExtra("nombreUsuarioLogin").toString()
        tipoIngreso = intent.getIntExtra("tipoUsuarioLogin", 0)
        idUsuario = intent.getIntExtra("idLogin", 0)

        // COnfiguración de los botones volver al menu y continuar venta.
        volverAlMenu = findViewById(R.id.boton_nuevaVenta_volverAlMenu)
        volverAlMenu.setOnClickListener { finish() }

        continuarVenta = findViewById(R.id.boton_nuevaVenta_seguirVenta)
        continuarVenta.setOnClickListener { chequearContinuarVenta() }

        // Inicializar variables de guardado de datos para mostrar en formulario.
        mostrarTotalVentaGlobal = findViewById(R.id.textView_resumenVenta_totalVentaGlobal)

        listaCarrito = findViewById(R.id.listView_carroCompras)
        adaptadorCarrito = AdaptadorVenta(this, carroCompras)
        listaCarrito.adapter = adaptadorCarrito

        // Configuración del boton añadir nueva compra a carro compras.
        nuevoProductoCarroCompra = findViewById(R.id.boton_nuevaVenta_addCompra)
        nuevoProductoCarroCompra.setOnClickListener { verificarDatosNumericos(nuevaVentaProducto) }

        // Configuración mediante un Text Change Listener automatizado de los cambios del editText para cantidad
        // del producto.
        elegirCantidad = findViewById(R.id.editNumber_ventas_cantidad)
        elegirCantidad.addTextChangedListener (object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, inicio: Int, contador: Int, despues: Int) {}
            override fun onTextChanged(s: CharSequence?, inicio: Int, contador: Int, despues: Int) {}
            override fun afterTextChanged(s: Editable?) {
                if (s.toString().trim().isNotEmpty()) {
                    nuevaVentaProducto.cantidad = s.toString().toInt()
                    mostrarTotalVentaProducto.text = nuevaVentaProducto.obtenerTotal().toString()
                }
            }
        })

        // Configuración mediante un Text Change Listener automatizado de los cambios del editText para descuento
        // del producto.
        elegirDescuento = findViewById(R.id.editNumber_ventas_descuento)
        elegirDescuento.addTextChangedListener (object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, inicio: Int, contador: Int, despues: Int) {}
            override fun onTextChanged(s: CharSequence?, inicio: Int, contador: Int, despues: Int) {}
            override fun afterTextChanged(s: Editable?) {
                if (s.toString().trim().isNotEmpty()) {
                    nuevaVentaProducto.descuento = s.toString().toInt()
                    mostrarTotalVentaProducto.text = nuevaVentaProducto.obtenerTotal().toString()
                }
            }
        })

        // Inicialización de los elementos que muestran precio y total venta de producto.
        mostrarPrecioProducto = findViewById(R.id.textView_ventas_mostrarPrecioProducto)
        mostrarTotalVentaProducto = findViewById(R.id.textView_ventas_mostrarTotalProducto)

        // Inicialización de lo necesario para un spinner funcional de nombres de productos.
        val nombresProductos: MutableList<String> = mutableListOf()
        listaProductos.forEach { nombresProductos.add(it.nombre) }
        elegirProducto = findViewById(R.id.spinner_nombreProductos)
        val adaptadorSpinner: ArrayAdapter<String> = ArrayAdapter<String>(this, R.layout.spinner_custom, nombresProductos)
        elegirProducto.adapter = adaptadorSpinner

        // Configuración del spinner en tiempo real.
        elegirProducto.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                nuevaVentaProducto.establecerProducto(listaProductos[position])
                mostrarPrecioProducto.text = listaProductos[position].precio.toString()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    /**
     * Método que permite verificar si los datos ingresados en el formulario están permitidos y
     * acorde a los datos conocidos del producto.
     * @param venta Analiza los datos ingresados en la venta.
     */
    @SuppressLint("SetTextI18n")
    private fun verificarDatosNumericos(venta: Venta) {
        if (venta.descuento > 25) {
            Toast.makeText(this, "Maximo descuento permitido es 25%", Toast.LENGTH_SHORT).show()
        }
        if (venta.cantidad > venta.obtenerProducto().obtenerStock()) {
            Toast.makeText(this, "Cantidad ingresada excede el stock disponible del producto", Toast.LENGTH_SHORT).show()
        }
        if (venta.cantidad == 0) {
            Toast.makeText(this, "La minima cantidad a vender es una unidad", Toast.LENGTH_SHORT).show()
        }
        if ((venta.descuento <= 25) and (venta.descuento >= 0)
            and (venta.cantidad <= venta.obtenerProducto().obtenerStock())
            and (venta.cantidad > 0)) {
            this.carroCompras.add(nuevaVentaProducto)
            nuevaVentaProducto = Venta()
            mostrarTotalVentaGlobal.text = "Total: $${this.calcularTotalVenta()}"
            elegirCantidad.setText("")
            elegirDescuento.setText("")
            elegirProducto.setSelection(0)
            adaptadorCarrito.notifyDataSetChanged()
        }
    }

    /**
     * Método que permite calcular el total de la venta.
     */
    private fun calcularTotalVenta(): Int {
        var suma = 0
        carroCompras.forEach { suma += it.obtenerTotal() }
        return suma
    }

    /**
     * Método que permite discriminar si el carro esta vacio o no para continuar la venta.
     */
    private fun chequearContinuarVenta() {
        if (carroCompras.isEmpty()) {
            Toast.makeText(this, "El carro de compras está vacío", Toast.LENGTH_SHORT).show()
        }
        if (carroCompras.isNotEmpty()) {
            confirmarVenta()
        }
    }

    /**
     * Método que prosigue la venta cambiando el layout y mostrando el resumen de la venta previa a su
     * confirmación.
     * Verifica los datos de cliente ingresado si corresponde con la base de datos.
     * Actualiza finalmente el stock de cada producto en la venta y registra la venta en base de datos.
     */
    @SuppressLint("SetTextI18n")
    private fun confirmarVenta() {
        setContentView(R.layout.activity_confirmar_venta)

        val montoTotal: TextView = findViewById(R.id.textView_confirmarVenta_mostrarMontoTotal)
        montoTotal.text = "$ ${this.calcularTotalVenta()}"

        val fechaHora: TextView = findViewById(R.id.textView_confirmarVenta_mostrarFechaHora)
        fechaHora.text = fechaActual

        val clienteIDingresado: EditText =
            findViewById(R.id.editText_confirmarVenta_ingresoIDClienteVenta)

        val volverAlMenu2: Button = findViewById(R.id.boton_nuevaVenta_volverAlMenu2)
        volverAlMenu2.setOnClickListener {
            val accion = Intent(this, MenuPrincipal::class.java)
                .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(accion)
        }

        val confirmarVenta: Button = findViewById(R.id.boton_nuevaVenta_confirmarVenta)

        confirmarVenta.setOnClickListener {

            if (clienteIDingresado.text.isNotBlank()) {
                this.idCliente = clienteIDingresado.text.toString().toInt()

                if (this.verificarExistenciaCliente()) {
                    this.actualizarHistorialCliente()
                    this.actualizarRegistroVentas()

                    confirmarVenta.visibility = Button.GONE
                    val resumen = findViewById<View>(R.id.resumenVenta)
                    resumen.visibility = View.GONE
                    val mensajeExito = findViewById<TextView>(R.id.textView_mensajeVentaExitosa)
                    mensajeExito.visibility = TextView.VISIBLE

                    volverAlMenu2.setOnClickListener {
                        val accion = Intent(this, MenuPrincipal::class.java)
                            .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        startActivity(accion)
                    }
                }
            }
            if (clienteIDingresado.text.isBlank()) {
                this.actualizarRegistroVentas()

                confirmarVenta.visibility = Button.GONE
                val resumen = findViewById<View>(R.id.resumenVenta)
                resumen.visibility = View.GONE
                val mensajeExito = findViewById<TextView>(R.id.textView_mensajeVentaExitosa)
                mensajeExito.visibility = TextView.VISIBLE

                volverAlMenu2.setOnClickListener {
                    val accion = Intent(this, MenuPrincipal::class.java)
                        .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(accion)
                }
            }
        }
    }

    /**
     * Método que permite recuperar de la base de datos la lista de productos disponible.
     */
    private fun recuperarListaProductosBD(){
        this.listaProductos = ClasesBD.bD_Productos(this)
    }

    /**
     * Método booleano que permite discriminar si el cliente se encuentra o no en la base de datos.
     */
    private fun verificarExistenciaCliente(): Boolean {
        return ClasesBD.bD_VerificarCliente(this.idCliente, this)
    }

    /**
     * Método que permite actualizar el historial del cliente si se tiene un id válido, añadiendo
     * una venta con su monto y la fecha.
     */
    private fun actualizarHistorialCliente() {
        /* metodo que emplea totalVenta, el id del cliente y la fecha/hora para actualizar
        *   y añadir una entrada al historial del cliente.*/
    }

    /**
     * Método que permite actualizar el registro de ventas de la aplicación en la base de datos.
     */
    private fun actualizarRegistroVentas() {
        ClasesBD.bD_RealizarCompra(this.carroCompras, this.idCliente, this.idUsuario,
            this.fechaActual, this.calcularTotalVenta(), 0, this)
    }
}