package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import java.io.Serializable

class SeccionNuevaVenta : AppCompatActivity() {

    private var carroCompras: MutableList<Venta> = mutableListOf()
    private var listaProductos: MutableList<Producto> = recuperarListaProductosBD()
    private var montoTotalVentaCompletada: Int = calcularTotalVenta()

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

    var nuevaVentaProducto = Venta()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nueva_venta)

        nombreUsuario = intent.getStringExtra("nombreUsuarioLogin").toString()
        tipoIngreso = intent.getIntExtra("tipoUsuarioLogin", 0)

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
            mostrarTotalVentaGlobal.text = "Total: $${this.montoTotalVentaCompletada}"
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
            val accion = Intent(this, ConfirmarVenta::class.java)
            accion.putExtra("nombreUsuarioLogin", this.nombreUsuario)
            accion.putExtra("tipoIngresoLogin", this.tipoIngreso)
            accion.putExtra("carroCompras", this.carroCompras as Serializable)
            accion.putExtra("montoTotalVenta", this.montoTotalVentaCompletada)
            startActivity(accion)
        }
    }

    private fun recuperarListaProductosBD(): MutableList<Producto> {
        /* TODO metodo que recupera los datos de productos desde la base de datos y lo otorga a la lista
        *   que los guardara para el uso de spinner y para obtener sus datos para venta como stock y precio. */
        return mutableListOf(
            Producto(0,"A",1000,1, R.drawable.cake_photo),
            Producto(1,"B",2000,2, R.drawable.cake_photo),
            Producto(2,"C",1000,3, R.drawable.cake_photo),
            Producto(3,"D",2000,4, R.drawable.cake_photo),
            Producto(4,"E",3000,5, R.drawable.cake_photo),
            Producto(5,"F",1000,6, R.drawable.cake_photo),
            Producto(6,"G",2000,7, R.drawable.cake_photo),
            Producto(7,"H",1000,8, R.drawable.cake_photo),
            Producto(8,"I",3000,9, R.drawable.cake_photo)
        )
    }
}