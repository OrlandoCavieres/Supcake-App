package com.example.myapplication

import android.content.ContentValues
import android.content.Context
import androidx.appcompat.app.AppCompatActivity

class ClasesBD : AppCompatActivity() {

    companion object {

        fun bD_Sesion(Nombre: String, Pass: String, Contexto: Context): Usuario? {

            val admin = AdminSQLiteOpenHelper(Contexto, "BD", null, 1)
            val bd = admin.writableDatabase
            val fila = bd.rawQuery("SELECT * FROM usuarios WHERE (nombre_Usuario='$Nombre' AND password='$Pass')LIMIT 1", null)

            var User: Usuario? = null

            if (fila.moveToFirst()) {

                User = Usuario(fila.getInt(0), fila.getString(2), fila.getInt(4))
            }

            bd.close()
            return User
        }

        ///////////////////////////////////////
        ///////////// Producto ////////////////
        ///////////////////////////////////////

        fun bD_Productos(Contexto: Context): MutableList<Producto> {

            val admin = AdminSQLiteOpenHelper(Contexto, "BD", null, 1)
            val bd = admin.writableDatabase
            val fila = bd.rawQuery("SELECT * FROM producto", null)

            var listaProductos: MutableList<Producto> = mutableListOf()

            if (fila.moveToFirst()) {

                while(!fila.isAfterLast()) {

                    listaProductos.add(Producto(fila.getInt(0), fila.getString(1), fila.getInt(3), fila.getInt(2), R.drawable.cake_photo))
                    fila.moveToNext();
                }
            }

            bd.close()
            return listaProductos
        }

        fun bD_NuevoProducto(Nombre: String, Precio: Int, Stock: Int, Contexto: Context){

            val admin = AdminSQLiteOpenHelper(Contexto, "BD", null, 1)
            val bd = admin.writableDatabase

            val nuevoRegistro = ContentValues()
            nuevoRegistro.put("nombre_Producto", Nombre)
            nuevoRegistro.put("cantidad_Producto", Precio)
            nuevoRegistro.put("Precio_Producto", Stock)

            bd.insert("producto", null, nuevoRegistro)
        }

        fun bD_EliminarProducto(idProducto: Int, Contexto: Context) {

            val admin = AdminSQLiteOpenHelper(Contexto, "BD", null, 1)
            val bd = admin.writableDatabase
            bd.delete("producto", "idProducto=$idProducto", null)
            bd.close()
            return
        }

        fun bD_ModificarProducto(producto: Producto, Contexto: Context){

            val admin = AdminSQLiteOpenHelper(Contexto, "BD", null, 1)
            val bd = admin.writableDatabase

            val valores = ContentValues()
            valores.put("nombre_Producto", producto.nombre)
            valores.put("cantidad_Producto", producto.obtenerStock())
            valores.put("Precio_Producto", producto.precio)

            bd.update("producto", valores, "idProducto = ${producto.obtenerID()}", null)
        }

        ///////////////////////////////////////
        ///////////// Clientes ////////////////
        ///////////////////////////////////////
        fun bD_Cliente(Contexto: Context): MutableList<Cliente> {

            val admin = AdminSQLiteOpenHelper(Contexto, "BD", null, 1)
            val bd = admin.writableDatabase
            val fila = bd.rawQuery("SELECT * FROM clientes", null)

            var listaClientes: MutableList<Cliente> = mutableListOf()

            if (fila.moveToFirst()) {

                while(!fila.isAfterLast()) {

                    listaClientes.add(Cliente(fila.getInt(0), fila.getString(1), fila.getString(2)))
                    fila.moveToNext();
                }
            }

            bd.close()
            return listaClientes
        }

        fun bD_EliminarCliente(idCliente: Int, Contexto: Context) {

            val admin = AdminSQLiteOpenHelper(Contexto, "BD", null, 1)
            val bd = admin.writableDatabase
            bd.delete("clientes", "idClientes=$idCliente", null)
            bd.close()
            return
        }

        fun bD_VerificarCliente(idCliente: Int, Contexto: Context): Boolean {

            val admin = AdminSQLiteOpenHelper(Contexto, "BD", null, 1)
            val bd = admin.writableDatabase
            val fila = bd.rawQuery("SELECT idClientes FROM clientes WHERE idClientes = $idCliente", null)

            var Bol = false;

            if (fila.moveToFirst()) { Bol = true }

            bd.close()
            return Bol
        }

        fun bD_NuevoCliente(Nombre: String, Dirrecion: String, Contexto: Context){

            val admin = AdminSQLiteOpenHelper(Contexto, "BD", null, 1)
            val bd = admin.writableDatabase

            val nuevoRegistro = ContentValues()
            nuevoRegistro.put("nombre_Cliente", Nombre)
            nuevoRegistro.put("direccion_Cliente", Dirrecion)

            bd.insert("clientes", null, nuevoRegistro)
        }

        ///////////////////////////////////////
        /////////// Compras Productos /////////
        ///////////////////////////////////////

        fun bD_RealizarCompra(Lista: MutableList<Venta>, idCliente: Int, idUsuario: Int, Fecha: String, total: Int, Metodo: Int, Contexto: Context){

            val admin = AdminSQLiteOpenHelper(Contexto, "BD", null, 1)
            val bd = admin.writableDatabase

            val nuevoRegistro = ContentValues()
            nuevoRegistro.put("id_Usuario", idCliente)
            nuevoRegistro.put("fecha", idUsuario)
            nuevoRegistro.put("id_Cliente", Fecha)
            nuevoRegistro.put("tipo_Pago", Metodo)
            nuevoRegistro.put("total", total)

            val ID_Venta = bd.insert("ventas", null, nuevoRegistro)

            for(i in (0..Lista.size - 1)){

                val registro = ContentValues()
                registro.put("id_Venta", ID_Venta)
                registro.put("id_Producto", Lista[i].obtenerProducto().obtenerID())
                registro.put("cantidad_Comprada", Lista[i].cantidad)
                registro.put("total", Lista[i].obtenerTotal())

                bd.insert("productoVentas", null, registro)

                Lista[i].obtenerProducto().modificarCantidad(-Lista[i].cantidad)

                val producto = ContentValues()
                producto.put("cantidad_Producto", Lista[i].obtenerProducto().obtenerStock())

                bd.update("producto", producto, "idProducto = ${Lista[i].obtenerProducto().obtenerID()}", null)
            }

            bd.close()
            return
        }
    }
}