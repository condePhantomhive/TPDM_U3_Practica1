package mx.edu.ittepic.tpdm_u1_miniproyecto;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by jessica on 29/03/17.
 */

public class Queries {
    public static boolean verificarCliente(String telefono,ConexionDB conexion){
        boolean r=false;
        try {
            SQLiteDatabase db= conexion.getReadableDatabase();
            String sql="SELECT NOMBRE FROM CLIENTE WHERE TELEFONO='"+telefono+"'";
            Cursor result=db.rawQuery(sql,null);
            result.moveToFirst();
            r=result.getCount()>0;
        }catch (SQLException e){
            Log.e("ERROR",e.getMessage());
            return false;
        }
        return r;
    }
    public static boolean insertarcliente(String[] datos, ConexionDB conexion){
        try {
            SQLiteDatabase db=conexion.getWritableDatabase();
            String sql="INSERT INTO CLIENTE VALUES (NULL,'<NOMBRE>','<APELLIDOS>','<TELEFONO>')";
            sql=sql.replace("<NOMBRE>",datos[0]);
            sql=sql.replace("<APELLIDOS>",datos[1]);
            sql=sql.replace("<TELEFONO>",datos[2]);
            db.execSQL(sql);
            db.close();
        }catch (SQLException e){
            Log.e("ERROR",e.getMessage());
            return false;
        }
        return true;
    }
    public static String verReservacion(ConexionDB conexion){
        String cad="";
        try{
            SQLiteDatabase db=conexion.getReadableDatabase();
            String sql="SELECT CLIENTE_ID FROM CLIENTE WHERE TELEFONO = '"+Menu.numero+"'";
            Cursor result=db.rawQuery(sql,null);
            result.moveToFirst();
            int id=result.getInt(0);
            sql="SELECT * FROM RESERVACION WHERE CLIENTE_CLIENTE_ID="+id;
            result=db.rawQuery(sql,null);
            int c=1;
            while(result.moveToNext()){
                cad+="Reservación "+c+"\n";
                cad+="Fecha: "+result.getString(1)+"\n";
                cad+="Hora: " +result.getString(2)+"\n\n";
                c++;
            }
            db.close();
        }catch (SQLException e){
            Log.e("Error",e.getMessage());
        }
        return cad;
    }
    public static boolean insertarReservacion(String[] cad_agregar,ConexionDB conexion){
        try {
            SQLiteDatabase db=conexion.getWritableDatabase();
            String sql="SELECT CLIENTE_ID FROM CLIENTE WHERE TELEFONO='"+cad_agregar[2]+"'";
            Cursor result = db.rawQuery(sql, null);
            result.moveToFirst();
            Log.e("cursors",""+result.getCount());
            int id=result.getInt(0);
            Log.e("id",""+id);
            sql="SELECT COUNT(ID_RES) FROM RESERVACION";
            result=db.rawQuery(sql,null);
            result.moveToFirst();
            int ultimo=result.getInt(0)+1;

            sql="INSERT INTO RESERVACION VALUES(<ID_RES>,'<FECHA>','<HORA>',<CLIENTE_CLIENTE_ID>)";
            sql=sql.replace("<ID_RES>",""+ultimo);
            sql=sql.replace("<FECHA>",cad_agregar[0]);
            sql=sql.replace("<HORA>",cad_agregar[1]);
            sql=sql.replace("<CLIENTE_CLIENTE_ID>",id+"");
            db.execSQL(sql);
            db.close();
        }catch (SQLException e){
            Log.e("ERROR",e.getMessage());
            return false;
        }
        return true;
    }
    public static boolean insertarOrden(String cad_agregar, ConexionDB conexion) {
        try{
            SQLiteDatabase db = conexion.getWritableDatabase();
            String sql="SELECT CLIENTE_ID FROM CLIENTE WHERE TELEFONO='"+Menu.numero+"'";
            Cursor result = db.rawQuery(sql, null);
            result.moveToFirst();
            int id=result.getInt(0);

            sql = "SELECT * FROM Orden WHERE Cliente_cliente_id = "+id + " and status = 'P'";
            result = db.rawQuery(sql, null);

            if (result.getCount() == 0) {

                Calendar c = Calendar.getInstance();

                int year = c.get(Calendar.YEAR);
                int mes = c.get(Calendar.MONTH);
                int dia = c.get(Calendar.DAY_OF_MONTH);
                String fecha = year +"-"+ mes +"-"+ dia;

                int hora = c.get(Calendar.HOUR_OF_DAY);
                int min = c.get(Calendar.MINUTE);
                String time = hora+":"+min;

                sql = "INSERT INTO ORDEN VALUES (NULL, <cliente_id>, '<fecha>', '<hora>', '<status>')";

                sql = sql.replace("<cliente_id>", Menu.clienteId+"");
                sql = sql.replace("<fecha>", fecha);
                sql = sql.replace("<hora>", time);
                sql = sql.replace("<status>", "P");

                db.execSQL(sql);
            }

            sql = "SELECT * FROM ORDEN WHERE STATUS='P' ORDER BY FECHA DESC";

            int idOrden = 0;

            result = db.rawQuery(sql, null);
            if(result.moveToFirst()){
                idOrden = result.getInt(0);
            }

            int idProd = 0;
            sql = "SELECT * FROM PRODUCTO WHERE NOMBRE = '"+ cad_agregar+"'";
            result = db.rawQuery(sql, null);
            if(result.moveToFirst()){
                idProd = result.getInt(0);
            }

            sql = "INSERT INTO DETALLE_ORDEN VALUES (NULL,<prod_id>, <orden_id>)";
            sql = sql.replace("<prod_id>", ""+ idProd);
            sql = sql.replace("<orden_id>", ""+idOrden);

            db.execSQL(sql);

            db.close();

        }catch(SQLiteException e){
            Log.e("ERRORRRRRRRRRRRRR",e.getMessage());
            return false;

        }
        return true;
    }
    public static ArrayList<String[]> getOrdenes(ConexionDB conexion){
        ArrayList<String[]> array = new ArrayList<String[]>();
        String nombre[] = null, descripcion[] = null, precio[]= null,detalleid[]=null;
        try {
            SQLiteDatabase db= conexion.getReadableDatabase();
            String sql="SELECT CLIENTE_ID FROM CLIENTE WHERE TELEFONO='"+Menu.numero+"'";
            Cursor result = db.rawQuery(sql, null);
            result.moveToFirst();
            int id=result.getInt(0);

            sql="SELECT ORDEN_ID FROM ORDEN WHERE STATUS='P' AND CLIENTE_CLIENTE_ID ="+id;
            result=db.rawQuery(sql,null);
            result.moveToFirst();
            id= result.getInt(0);
            if (result.getCount() <= 0) {
                Log.e("Error","El cursor es 0");
                return null;
            }
            sql="SELECT * FROM DETALLE_ORDEN WHERE ORDEN_ORDEN_ID = "+id;
            result=db.rawQuery(sql,null);
            int tam= result.getCount();
            nombre= new String[tam];
            descripcion= new String[tam];
            precio= new String[tam];
            detalleid= new String[tam];
            int i=0;
            while (result.moveToNext()){
                nombre[i]=result.getString(1);
                descripcion[i]=result.getString(2);
                precio[i]=result.getString(3);
                detalleid[i]=result.getString(0);
                i++;
            }
            db.close();
        }catch (SQLException e){
            Log.e("Error",e.getMessage());
            return null;
        }
        array.add(0,nombre);
        array.add(1,descripcion);
        array.add(2,precio);
        array.add(3,detalleid);
        Log.e("Tamaño",array.get(0).length+"");
        return array;
    }
    public static ArrayList<String[]> mostrarProducto(String categoria, ConexionDB conexion) {
        ArrayList<String[]> array = new ArrayList<String[]>();
        String nombre[] = null, descripcion[] = null, precio[]= null;
        try {
            SQLiteDatabase db = conexion.getReadableDatabase();

            String sql = "SELECT * FROM  CATEGORIA WHERE nombre='"+categoria+"'";
            String cat = "";
            Cursor result = db.rawQuery(sql, null);

            if(result.moveToFirst()){
                cat = result.getString(0);
            }

            sql = "SELECT * FROM PRODUCTO  WHERE  Categoria_id_categoria = <categoria> ";

            sql = sql.replace("<categoria>", cat);
            Log.e("dato",sql);


            result = db.rawQuery(sql, null);
            if (result.moveToFirst()) {
                int rows = result.getCount();
                int n = 0;
                nombre = new String[rows];
                descripcion = new String[rows];
                precio = new String[rows];
                do {
                    nombre[n] = result.getString(1);
                    descripcion[n] = result.getString(2);
                    precio[n] = result.getString(3);
                    n++;
                } while (result.moveToNext());
            }
            array.add(0,nombre);
            array.add(1,descripcion);
            array.add(2,precio);
            db.close();
        } catch (SQLiteException e) {
            //Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
        return array;
    }
}
