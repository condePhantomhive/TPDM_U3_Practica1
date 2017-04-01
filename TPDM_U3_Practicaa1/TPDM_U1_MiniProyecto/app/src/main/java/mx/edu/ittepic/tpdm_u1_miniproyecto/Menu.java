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
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 1);
        setContentView(R.layout.activity_menu);
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
        AlertDialog.Builder m= new AlertDialog.Builder(this);
        m.setTitle("Reservaciones");
        m.setMessage(getReservaciones()).setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
    }

    public String getReservaciones() {
        String reservaciones="";
        InputStreamReader flujo=null;
        BufferedReader lector=null;
        try
        {
            flujo= new InputStreamReader(openFileInput("reservacion.csv"));
            lector= new BufferedReader(flujo);
            String texto = lector.readLine();
            //Toast.makeText(ArchivosMenu.this,texto,Toast.LENGTH_LONG).show();
            //texto=lector.readLine();
            //Toast.makeText(ArchivosMenu.this,"Jelou "+texto,Toast.LENGTH_LONG).show();

            while(texto!=null)
            {
                reservaciones+=texto+"\n";
                texto=lector.readLine();
            }
        }
        catch (Exception ex)
        {
            Log.e("Conde", "Error al leer fichero desde memoria interna");
        }
        finally
        {
            try {
                if(flujo!=null)
                    flujo.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return reservaciones;
    }
}
