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

class SeccionNuevaVenta : AppCompatActivity() {

    private var carroCompras: MutableList<Venta> = mutableListOf()
    private var listaProductos: MutableList<Producto> = mutableListOf()

    private var fechaActual = Calendar.getInstance().time.toString()
    private var idCliente: Int = -1

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

    private var tipoIngreso: Int = 0
    private var nombreUsuario: String = ""
    private var idUsuario = 0

    var nuevaVentaProducto = Venta()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nueva_venta)

        recuperarListaProductosBD()

        nombreUsuario = intent.getStringExtra("nombreUsuarioLogin").toString()
        tipoIngreso = intent.getIntExtra("tipoUsuarioLogin", 0)
        idUsuario = intent.getIntExtra("idLogin", 0)

        volverAlMenu = findViewById(R.id.boton_nuevaVenta_volverAlMenu)
        volverAlMenu.setOnClickListener { finish() }

        continuarVenta = findViewById(R.id.boton_nuevaVenta_seguirVenta)
        continuarVenta.setOnClickListener { chequearContinuarVenta() }

        mostrarTotalVentaGlobal = findViewById(R.id.textView_resumenVenta_totalVentaGlobal)

        listaCarrito = findViewById(R.id.listView_carroCompras)
        adaptadorCarrito = AdaptadorVenta(this, carroCompras)
        listaCarrito.adapter = adaptadorCarrito

        nuevoProductoCarroCompra = findViewById(R.id.boton_nuevaVenta_addCompra)
        nuevoProductoCarroCompra.setOnClickListener { verificarDatosNumericos(nuevaVentaProducto) }

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

        mostrarPrecioProducto = findViewById(R.id.textView_ventas_mostrarPrecioProducto)
        mostrarTotalVentaProducto = findViewById(R.id.textView_ventas_mostrarTotalProducto)

        val nombresProductos: MutableList<String> = mutableListOf()
        listaProductos.forEach { nombresProductos.add(it.nombre) }
        elegirProducto = findViewById(R.id.spinner_nombreProductos)
        val adaptadorSpinner: ArrayAdapter<String> = ArrayAdapter<String>(this, R.layout.spinner_custom, nombresProductos)
        elegirProducto.adapter = adaptadorSpinner

        elegirProducto.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                nuevaVentaProducto.establecerProducto(listaProductos[position])
                mostrarPrecioProducto.text = listaProductos[position].precio.toString()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

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

    private fun calcularTotalVenta(): Int {
        var suma = 0
        carroCompras.forEach { suma += it.obtenerTotal() }
        return suma
    }

    private fun chequearContinuarVenta() {
        if (carroCompras.isEmpty()) {
            Toast.makeText(this, "El carro de compras está vacío", Toast.LENGTH_SHORT).show()
        }
        if (carroCompras.isNotEmpty()) {
            /*val accion = Intent(this, ConfirmarVenta::class.java)
            accion.putExtra("nombreUsuarioLogin", this.nombreUsuario)
            accion.putExtra("tipoIngresoLogin", this.tipoIngreso)
            accion.putExtra("totalVenta", this.calcularTotalVenta())
            startActivity(accion)*/
            confirmarVenta()
        }
    }

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

                if(this.verificarExistenciaCliente()){

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

    private fun recuperarListaProductosBD(){

        this.listaProductos = ClasesBD.bD_Productos(this)
    }

    private fun verificarExistenciaCliente(): Boolean {

            return ClasesBD.bD_VerificarCliente(this.idCliente, this)
    }

    private fun actualizarHistorialCliente() {
        /* TODO metodo que emplea totalVenta, el id del cliente y la fecha/hora para actualizar
        *   y añadir una entrada al historial del cliente.*/
    }

    private fun actualizarRegistroVentas() {

        ClasesBD.bD_RealizarCompra(this.carroCompras, this.idCliente, this.idUsuario, this.fechaActual, this.calcularTotalVenta(), 0, this)
    }
}