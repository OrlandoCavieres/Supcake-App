package com.example.myapplication

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import androidx.appcompat.app.AppCompatActivity

class ClasesBD: AppCompatActivity() {

    /**Clase estatica para agregar, modificar e eliminar
     * datos de la local BD puesta en el disposito */

    companion object {

        /** Compara Nombre y Contraseña ingresada por el
         * usuario y así poder iniciar sesión en la app*/
        @SuppressLint("Recycle")
        fun bD_Sesion(Nombre: String, Pass: String, Contexto: Context): Usuario? {

            val admin = AdminSQLiteOpenHelper(Contexto, "BD", null, 1)
            val bd = admin.writableDatabase
            val fila = bd.rawQuery("SELECT * FROM usuarios WHERE (nombre_Usuario='$Nombre' AND password='$Pass')LIMIT 1", null)

            var user: Usuario? = null

            if (fila.moveToFirst()) {

                user = Usuario(fila.getInt(0), fila.getString(2), fila.getInt(4))
            }

            bd.close()
            return user
        }

        ///////////////////////////////////////
        ///////////// Producto ////////////////
        ///////////////////////////////////////

        /** Captura y retorna una lista de todos los
         * productos disponibles de la BD */
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

        /** Con los datos Nombre, Precio y Stock se agrega
         * el nuevo producto a la BD */
        fun bD_NuevoProducto(Nombre: String, Precio: Int, Stock: Int, Contexto: Context){

            val admin = AdminSQLiteOpenHelper(Contexto, "BD", null, 1)
            val bd = admin.writableDatabase

            val nuevoRegistro = ContentValues()
            nuevoRegistro.put("nombre_Producto", Nombre)
            nuevoRegistro.put("cantidad_Producto", Precio)
            nuevoRegistro.put("Precio_Producto", Stock)

            bd.insert("producto", null, nuevoRegistro)
        }

        /** Con el ID del producto se busca e elimina
         * el producto de la BD */
        fun bD_EliminarProducto(idProducto: Int, Contexto: Context) {

            val admin = AdminSQLiteOpenHelper(Contexto, "BD", null, 1)
            val bd = admin.writableDatabase
            bd.delete("producto", "idProducto=$idProducto", null)
            bd.close()
            return
        }

        /** Con el ID del producto se busca y modifica los
         * datos actuales por los nuevos que definió el Usuario*/
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

        /** Captura y retorna una lista de todos los
         * clientes disponibles de la BD */
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

        /** Con los datos Nombre y Dirección se agrega
         * el nuevo Cliente a la BD */
        fun bD_NuevoCliente(Nombre: String, Direccion: String, Contexto: Context){

            val admin = AdminSQLiteOpenHelper(Contexto, "BD", null, 1)
            val bd = admin.writableDatabase

            val nuevoRegistro = ContentValues()
            nuevoRegistro.put("nombre_Cliente", Nombre)
            nuevoRegistro.put("direccion_Cliente", Direccion)

            bd.insert("clientes", null, nuevoRegistro)
        }

        /** Con el ID del Cliente se busca e elimina
         * al Cliente de la BD */
        fun bD_EliminarCliente(idCliente: Int, Contexto: Context) {

            val admin = AdminSQLiteOpenHelper(Contexto, "BD", null, 1)
            val bd = admin.writableDatabase
            bd.delete("clientes", "idClientes=$idCliente", null)
            bd.close()
            return
        }

        /** Se verifica si el actual cliente a consultar
         * sigue vigente en los registros */
        fun bD_VerificarCliente(idCliente: Int, Contexto: Context): Boolean {

            val admin = AdminSQLiteOpenHelper(Contexto, "BD", null, 1)
            val bd = admin.writableDatabase
            val fila = bd.rawQuery("SELECT idClientes FROM clientes WHERE idClientes = $idCliente", null)

            var Bol = false;

            if (fila.moveToFirst()) { Bol = true }

            bd.close()
            return Bol
        }

        ///////////////////////////////////////
        /////////// Compras Productos /////////
        ///////////////////////////////////////

        /** Se agrega la compra con todos los productos
         * agregados por el usuario, los datos del cliente
         * y a la vez los datos del mismo usuario */
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