package mx.edu.ittepic.tpdm_u1_miniproyecto;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by jessica on 29/03/17.
 */

public class ConexionDB extends SQLiteOpenHelper {
    public ConexionDB(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {



        //Categoria
        db.execSQL("CREATE TABLE CATEGORIA(" +
                "id_categoria INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombre VARCHAR (100) NOT NULL," +
                "descripcion TEXT)");

        //cliente
        db.execSQL("CREATE TABLE CLIENTE (" +
                "cliente_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombre VARCHAR(80) NOT NULL," +
                "apellidos VARCHAR(80) NOT NULL," +
                "telefono VARCHAR(10) NOT NULL UNIQUE)");

        //producto
        db.execSQL("CREATE TABLE Producto (" +
                "producto_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombre varchar(100)," +
                "descripcion text NOT NULL," +
                "precio double NOT NULL," +
                "Categoria_id_categoria integer," +
                "FOREIGN KEY (Categoria_id_categoria)" +
                "REFERENCES Categoria (id_categoria))");

        //orden
        db.execSQL("CREATE TABLE Orden (" +
                "orden_id integer PRIMARY KEY AUTOINCREMENT," +
                "Cliente_cliente_id integer," +
                "fecha TEXT NOT NULL," +
                "hora text NOT NULL," +
                "status character(1) NOT NULL," +
                "FOREIGN KEY (Cliente_cliente_id)" +
                "REFERENCES Cliente (cliente_id))");

        //detalle_orden
        db.execSQL("CREATE TABLE DETALLE_ORDEN(" +
                "id_detalle INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "Orden_orden_id INTEGER," +
                "Producto_Producto_id INTEGER," +
                "FOREIGN KEY (Orden_orden_id)" +
                "REFERENCES Orden (orden_id)," +
                "FOREIGN KEY (Producto_producto_id)" +
                "REFERENCES Producto (producto_id))");

        //reservacion
        db.execSQL("CREATE TABLE RESERVACION(" +
                "id_res INTEGER NOT NULL," +
                "fecha TEXT NOT NULL," +
                "hora TEXT NOT NULL," +
                "Cliente_cliente_id INTEGER," +
                "PRIMARY KEY (id_res, fecha, hora, Cliente_cliente_id)," +
                "FOREIGN KEY (Cliente_cliente_id) REFERENCES Cliente (cliente_id))");
        }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
