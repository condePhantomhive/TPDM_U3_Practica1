package mx.edu.ittepic.tpdm_u1_miniproyecto;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class Menu extends AppCompatActivity {
    ImageView reservacion, pasta, pizza, postre, bebida, cuenta;
    ConexionDB conexion;
    FloatingActionButton fab;
    public static String numero;
    public static int clienteId=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        numero=this.getIntent().getExtras().getString("telefono");
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 1);
        setContentView(R.layout.activity_menu);
        conexion= Principal.conexion;
        //insertClient();
        reservacion = (ImageView) findViewById(R.id.reservacion);
        pasta = (ImageView) findViewById(R.id.pasta);
        pizza = (ImageView) findViewById(R.id.pizza);
        postre = (ImageView) findViewById(R.id.postre);
        bebida = (ImageView) findViewById(R.id.bebida);
        cuenta = (ImageView) findViewById(R.id.cuenta);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        reservacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ventanaReservacion();
            }
        });
        pasta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ventanaPastas();
            }
        });
        pizza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ventanaPizza();
            }
        });
        postre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ventanaPostre();
            }
        });
        bebida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ventanaBebida();
            }
        });
        cuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ventanaCuenta();
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar snackbar = Snackbar
                        .make(v, "¿Desea salir de la aplicación?", Snackbar.LENGTH_LONG)
                        .setAction("OK", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                finish();
                            }
                        });
                snackbar.show();
            }
        });
        if(!checkInitData()){
            insertCategories();
            insertProducts();
        }
    }
    private void insertClient(){
        try {
            SQLiteDatabase db= conexion.getWritableDatabase();
            String sql="INSERT INTO CLIENTE VALUES(NULL,'Jorch','Rodriguez','3111234567')";
            db.execSQL(sql);
            db.close();
        }catch (SQLException e){

        }
    }

    private void ventanaReservacion() {
        Intent reservacion = new Intent(this, mx.edu.ittepic.tpdm_u1_miniproyecto.Reservaciones.class);
        startActivity(reservacion);
    }

    private void ventanaPastas() {
        Intent pastas = new Intent(this, mx.edu.ittepic.tpdm_u1_miniproyecto.Pastas.class);
        startActivity(pastas);
    }

    private void ventanaPizza() {
        Intent pizza = new Intent(this, mx.edu.ittepic.tpdm_u1_miniproyecto.Pizzas.class);
        startActivity(pizza);
    }

    private void ventanaPostre() {
        Intent postre = new Intent(this, mx.edu.ittepic.tpdm_u1_miniproyecto.Postres.class);
        startActivity(postre);
    }

    private void ventanaBebida() {
        Intent bebida = new Intent(this, mx.edu.ittepic.tpdm_u1_miniproyecto.Bebidas.class);
        startActivity(bebida);
    }

    private void ventanaCuenta() {
        Intent cuenta = new Intent(this, mx.edu.ittepic.tpdm_u1_miniproyecto.Cuenta.class);
        startActivity(cuenta);
    }

    private void ventanaAcercade() {
        Intent acerca = new Intent(this, mx.edu.ittepic.tpdm_u1_miniproyecto.Acercade.class);
        startActivity(acerca);
    }

    public boolean onCreateOptionsMenu(android.view.Menu m) {
        getMenuInflater().inflate(R.menu.main_menu, m);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem m) {
        switch (m.getItemId()) {
            case R.id.acerca:
                ventanaAcercade();
                break;
            case R.id.reservacion:
                reservacion();
                break;
            case R.id.salir:
                finish();
                break;
        }
        return true;
    }

    private void reservacion() {
        String mensaje="";
        mensaje=Queries.verReservacion(conexion);
        if(mensaje.isEmpty()){mensaje="NO EXISTEN RESERVACIONES!";}
        AlertDialog.Builder m= new AlertDialog.Builder(this);
        m.setTitle("Mis Reservaciones :)");
        m.setMessage(mensaje).setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
    }


    private boolean checkInitData(){
        int c=0;
        try{
            SQLiteDatabase db= conexion.getReadableDatabase();
            String SQL="SELECT * FROM CATEGORIA";
            Cursor resultado= db.rawQuery(SQL,null);
            c= resultado.getCount();
            Log.d("valorC",""+c);
            db.close();
        }catch (SQLiteException e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }
        return c>0;
    }
    private void insertCategories(){
        try{
            SQLiteDatabase db= conexion.getWritableDatabase();
            String SQL="INSERT INTO CATEGORIA VALUES (NULL,'<NOMBRE>','<DESCRIPCION>')";
            SQL=SQL.replace("<NOMBRE>","Pastas");
            SQL=SQL.replace("<DESCRIPCION>","Pastas de diferentes tipos.");
            db.execSQL(SQL);

            SQL="INSERT INTO CATEGORIA VALUES (NULL,'<NOMBRE>','<DESCRIPCION>')";
            SQL=SQL.replace("<NOMBRE>","Pizzas");
            SQL=SQL.replace("<DESCRIPCION>","Pizzas de diferentes tipos y especialidades.");
            db.execSQL(SQL);

            SQL="INSERT INTO CATEGORIA VALUES (NULL,'<NOMBRE>','<DESCRIPCION>')";
            SQL=SQL.replace("<NOMBRE>","Postres");
            SQL=SQL.replace("<DESCRIPCION>","Postres simples y elaborados de diferentes sabores y colores");
            db.execSQL(SQL);

            SQL="INSERT INTO CATEGORIA VALUES (NULL,'<NOMBRE>','<DESCRIPCION>')";
            SQL=SQL.replace("<NOMBRE>","Bebidas");
            SQL=SQL.replace("<DESCRIPCION>","Bebidas con alcohol y sin alcohol");
            db.execSQL(SQL);

            db.close();

        }catch (SQLException e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
    private void insertProducts(){
        try{
            SQLiteDatabase db= conexion.getWritableDatabase();
            //PASTAS
            String SQL="INSERT INTO PRODUCTO VALUES(NULL,'<NOMBRE>','<DESCRIPCION>','<PRECIO>','<CATEGORIA_ID_CATEGORIA>')";
            SQL=SQL.replace("<NOMBRE>","Margarita");
            SQL=SQL.replace("<DESCRIPCION>","Queso bocconcini, tomate y albahaca. Servida con salsa pomodoro.");
            SQL=SQL.replace("<PRECIO>","90");
            SQL=SQL.replace("<CATEGORIA_ID_CATEGORIA>","1");
            db.execSQL(SQL);

            SQL="INSERT INTO PRODUCTO VALUES(NULL,'<NOMBRE>','<DESCRIPCION>','<PRECIO>','<CATEGORIA_ID_CATEGORIA>')";
            SQL=SQL.replace("<NOMBRE>","Putanesca");
            SQL=SQL.replace("<DESCRIPCION>","Cebolla, alcaparras, anchoas, albahaca, aceitunas, y chile picante a su gusto. Servida con salsa pomodoro.");
            SQL=SQL.replace("<PRECIO>","100");
            SQL=SQL.replace("<CATEGORIA_ID_CATEGORIA>","1");
            db.execSQL(SQL);

            SQL="INSERT INTO PRODUCTO VALUES(NULL,'<NOMBRE>','<DESCRIPCION>','<PRECIO>','<CATEGORIA_ID_CATEGORIA>')";
            SQL=SQL.replace("<NOMBRE>","Cordon Blue");
            SQL=SQL.replace("<DESCRIPCION>","Tiras de jamón, blue cheese y pollo. Acompañadas de salsa blanca.");
            SQL=SQL.replace("<PRECIO>","85");
            SQL=SQL.replace("<CATEGORIA_ID_CATEGORIA>","1");
            db.execSQL(SQL);

            SQL="INSERT INTO PRODUCTO VALUES(NULL,'<NOMBRE>','<DESCRIPCION>','<PRECIO>','<CATEGORIA_ID_CATEGORIA>')";
            SQL=SQL.replace("<NOMBRE>","Salmón");
            SQL=SQL.replace("<DESCRIPCION>","Salmón ahumado y eneido fresco. Acompañado de salsa blanca.");
            SQL=SQL.replace("<PRECIO>","95");
            SQL=SQL.replace("<CATEGORIA_ID_CATEGORIA>","1");
            db.execSQL(SQL);

            SQL="INSERT INTO PRODUCTO VALUES(NULL,'<NOMBRE>','<DESCRIPCION>','<PRECIO>','<CATEGORIA_ID_CATEGORIA>')";
            SQL=SQL.replace("<NOMBRE>","4 Quesos");
            SQL=SQL.replace("<DESCRIPCION>","Mozzarela, blue cheese, emmnetal y parmesano. Acompañados de salsa blanca.");
            SQL=SQL.replace("<PRECIO>","80");
            SQL=SQL.replace("<CATEGORIA_ID_CATEGORIA>","1");
            db.execSQL(SQL);

            //PIZZAS
            SQL="INSERT INTO PRODUCTO VALUES(NULL,'<NOMBRE>','<DESCRIPCION>','<PRECIO>','<CATEGORIA_ID_CATEGORIA>')";
            SQL=SQL.replace("<NOMBRE>","Campirana");
            SQL=SQL.replace("<DESCRIPCION>","Champiñones, jugosa Piña y Elote. Pastor&Exquisita carne al pastor, trocitos de piña, cebolla, cilantro.");
            SQL=SQL.replace("<PRECIO>","130");
            SQL=SQL.replace("<CATEGORIA_ID_CATEGORIA>","2");
            db.execSQL(SQL);

            SQL="INSERT INTO PRODUCTO VALUES(NULL,'<NOMBRE>','<DESCRIPCION>','<PRECIO>','<CATEGORIA_ID_CATEGORIA>')";
            SQL=SQL.replace("<NOMBRE>","Pollo Con Rajas Poblanas");
            SQL=SQL.replace("<DESCRIPCION>","Delicioso pollo jugosito acompañado con rajas poblanas, elotitos, crema y doble queso.");
            SQL=SQL.replace("<PRECIO>","");
            SQL=SQL.replace("<CATEGORIA_ID_CATEGORIA>","2");
            db.execSQL(SQL);

            SQL="INSERT INTO PRODUCTO VALUES(NULL,'<NOMBRE>','<DESCRIPCION>','<PRECIO>','<CATEGORIA_ID_CATEGORIA>')";
            SQL=SQL.replace("<NOMBRE>","Mexicana");
            SQL=SQL.replace("<DESCRIPCION>","Chile Jalapeño, Tocino, Carne Molida, Pepperoni, Cebolla y tiras de Pimiento Verde.");
            SQL=SQL.replace("<PRECIO>","130");
            SQL=SQL.replace("<CATEGORIA_ID_CATEGORIA>","2");
            db.execSQL(SQL);

            SQL="INSERT INTO PRODUCTO VALUES(NULL,'<NOMBRE>','<DESCRIPCION>','<PRECIO>','<CATEGORIA_ID_CATEGORIA>')";
            SQL=SQL.replace("<NOMBRE>","Hawaiiana");
            SQL=SQL.replace("<DESCRIPCION>","Jugosa Piña y Jamón.");
            SQL=SQL.replace("<PRECIO>","130");
            SQL=SQL.replace("<CATEGORIA_ID_CATEGORIA>","2");
            db.execSQL(SQL);

            SQL="INSERT INTO PRODUCTO VALUES(NULL,'<NOMBRE>','<DESCRIPCION>','<PRECIO>','<CATEGORIA_ID_CATEGORIA>')";
            SQL=SQL.replace("<NOMBRE>","TLC");
            SQL=SQL.replace("<DESCRIPCION>","Pepperoni, Tocino, Chiles Jalapeños y Queso.");
            SQL=SQL.replace("<PRECIO>","130");
            SQL=SQL.replace("<CATEGORIA_ID_CATEGORIA>","2");
            db.execSQL(SQL);

            //POSTRES
            SQL="INSERT INTO PRODUCTO VALUES(NULL,'<NOMBRE>','<DESCRIPCION>','<PRECIO>','<CATEGORIA_ID_CATEGORIA>')";
            SQL=SQL.replace("<NOMBRE>","Sopa fría de ciruelas");
            SQL=SQL.replace("<DESCRIPCION>","Sorbet casero de manzanas verdes.");
            SQL=SQL.replace("<PRECIO>","50");
            SQL=SQL.replace("<CATEGORIA_ID_CATEGORIA>","3");
            db.execSQL(SQL);

            SQL="INSERT INTO PRODUCTO VALUES(NULL,'<NOMBRE>','<DESCRIPCION>','<PRECIO>','<CATEGORIA_ID_CATEGORIA>')";
            SQL=SQL.replace("<NOMBRE>","Frutillas");
            SQL=SQL.replace("<DESCRIPCION>","Crema y merengue.");
            SQL=SQL.replace("<PRECIO>","60");
            SQL=SQL.replace("<CATEGORIA_ID_CATEGORIA>","3");
            db.execSQL(SQL);

            SQL="INSERT INTO PRODUCTO VALUES(NULL,'<NOMBRE>','<DESCRIPCION>','<PRECIO>','<CATEGORIA_ID_CATEGORIA>')";
            SQL=SQL.replace("<NOMBRE>","El clásico vigilante de la boca");
            SQL=SQL.replace("<DESCRIPCION>","Dulce de guayaba y queso crema.");
            SQL=SQL.replace("<PRECIO>","75");
            SQL=SQL.replace("<CATEGORIA_ID_CATEGORIA>","3");
            db.execSQL(SQL);

            SQL="INSERT INTO PRODUCTO VALUES(NULL,'<NOMBRE>','<DESCRIPCION>','<PRECIO>','<CATEGORIA_ID_CATEGORIA>')";
            SQL=SQL.replace("<NOMBRE>","Mousse de chocolate y naranja");
            SQL=SQL.replace("<DESCRIPCION>","Salsa de chocolate, chocolate blanco rayado.");
            SQL=SQL.replace("<PRECIO>","60");
            SQL=SQL.replace("<CATEGORIA_ID_CATEGORIA>","3");
            db.execSQL(SQL);

            SQL="INSERT INTO PRODUCTO VALUES(NULL,'<NOMBRE>','<DESCRIPCION>','<PRECIO>','<CATEGORIA_ID_CATEGORIA>')";
            SQL=SQL.replace("<NOMBRE>","Panqueque de manzana");
            SQL=SQL.replace("<DESCRIPCION>","Manzana Granny Smith y helado de vainilla.");
            SQL=SQL.replace("<PRECIO>","30");
            SQL=SQL.replace("<CATEGORIA_ID_CATEGORIA>","3");
            db.execSQL(SQL);

            //BEBIDAS
            SQL="INSERT INTO PRODUCTO VALUES(NULL,'<NOMBRE>','<DESCRIPCION>','<PRECIO>','<CATEGORIA_ID_CATEGORIA>')";
            SQL=SQL.replace("<NOMBRE>","XA Cabernet Sauvignon");
            SQL=SQL.replace("<DESCRIPCION>","Vino tinto");
            SQL=SQL.replace("<PRECIO>","150");
            SQL=SQL.replace("<CATEGORIA_ID_CATEGORIA>","4");
            db.execSQL(SQL);

            SQL="INSERT INTO PRODUCTO VALUES(NULL,'<NOMBRE>','<DESCRIPCION>','<PRECIO>','<CATEGORIA_ID_CATEGORIA>')";
            SQL=SQL.replace("<NOMBRE>","Gran sangre de toro");
            SQL=SQL.replace("<DESCRIPCION>","200");
            SQL=SQL.replace("<PRECIO>","");
            SQL=SQL.replace("<CATEGORIA_ID_CATEGORIA>","4");
            db.execSQL(SQL);

            SQL="INSERT INTO PRODUCTO VALUES(NULL,'<NOMBRE>','<DESCRIPCION>','<PRECIO>','<CATEGORIA_ID_CATEGORIA>')";
            SQL=SQL.replace("<NOMBRE>","L.A Cetto Blanc de Blancs");
            SQL=SQL.replace("<DESCRIPCION>","Vino Blanco");
            SQL=SQL.replace("<PRECIO>","");
            SQL=SQL.replace("<CATEGORIA_ID_CATEGORIA>","4");
            db.execSQL(SQL);

            SQL="INSERT INTO PRODUCTO VALUES(NULL,'<NOMBRE>','<DESCRIPCION>','<PRECIO>','<CATEGORIA_ID_CATEGORIA>')";
            SQL=SQL.replace("<NOMBRE>","Marqués de Caceres");
            SQL=SQL.replace("<DESCRIPCION>","Vino Blanco");
            SQL=SQL.replace("<PRECIO>","170");
            SQL=SQL.replace("<CATEGORIA_ID_CATEGORIA>","4");
            db.execSQL(SQL);

            SQL="INSERT INTO PRODUCTO VALUES(NULL,'<NOMBRE>','<DESCRIPCION>','<PRECIO>','<CATEGORIA_ID_CATEGORIA>')";
            SQL=SQL.replace("<NOMBRE>","Heineken");
            SQL=SQL.replace("<DESCRIPCION>","Cerveza");
            SQL=SQL.replace("<PRECIO>","30");
            SQL=SQL.replace("<CATEGORIA_ID_CATEGORIA>","4");
            db.execSQL(SQL);

            SQL="INSERT INTO PRODUCTO VALUES(NULL,'<NOMBRE>','<DESCRIPCION>','<PRECIO>','<CATEGORIA_ID_CATEGORIA>')";
            SQL=SQL.replace("<NOMBRE>","Miller");
            SQL=SQL.replace("<DESCRIPCION>","Cerveza");
            SQL=SQL.replace("<PRECIO>","35");
            SQL=SQL.replace("<CATEGORIA_ID_CATEGORIA>","4");
            db.execSQL(SQL);

            db.close();

        }catch (SQLException e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
}
