package mx.edu.ittepic.tpdm_u1_miniproyecto;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Cuenta extends AppCompatActivity {
    public static ConexionDB conexion;
    ListView lista;
    Button pedir;
    String orden="";
    String ordenes[];
    String [] comida;
    String [] tipo;
    Integer [] imgid;
    Integer id=R.drawable.food_fork_drink;
    String[] nombre;
    String[] descripcion;
    String[] precio;
    LenguajeListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuenta);
        conexion=Principal.conexion;
        lista=(ListView) findViewById(R.id.listaorden);
        pedir=(Button)findViewById(R.id.button5);

        nombre= Queries.getOrdenes(conexion).get(0);
        Log.e("Tamaño:", nombre.length+"");
        descripcion=Queries.getOrdenes(conexion).get(1);
        precio=Queries.getOrdenes(conexion).get(2);
        imgid= new Integer[nombre.length];

        for(int i=0;i<nombre.length;i++){
            imgid[i]=id;
        }
        LenguajeListAdapter adapter= new LenguajeListAdapter(this,nombre,descripcion,imgid);
        lista.setAdapter(adapter);
        //initList();
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showMessage("¿Desea eliminarlo?",position);
            }
        });
        pedir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(setMessage() == null){
                    return;
                }
                enviarSms("5522593632", setMessage());
                finish();
                limpiarlista();

            }
        });
    }
    private void initList(){
        leerFicheroMemoriaInterna();
        ordenes=orden.split("&");
        if(ordenes[0].toString().equals("")){
            lista.setAdapter(null);
        }
        else {
            inintArray(ordenes);
            adapter = new LenguajeListAdapter(this, comida, tipo, imgid);
            lista.setAdapter(adapter);
        }
    }

    public String setMessage(){
        String orden="Deseo ordenar:\n";
        if(ordenes[0].toString().equals("")){
            Toast.makeText(Cuenta.this, "No hay nada para ordenar", Toast.LENGTH_LONG).show();
            return null;
        }
        for(int i=0;i<comida.length;i++){
            orden+=tipo[i]+" ";
            orden+=comida[i]+".\n";
        }
        return orden;
    }

    private void inintArray(String[] array){
        String aux[];
        comida=new String[array.length];
        tipo=new String[array.length];
        imgid= new Integer[array.length];
        for(int i=0;i<array.length;i++){
            aux=array[i].split(",");
            comida[i] = aux[0];
            tipo[i] = aux[1];
            imgid[i] = id;
        }
    }

    private void limpiarlista (){
        escribirFicheroMemoriaInterna("");
        lista.setAdapter(null);
    }

    private void updateData(int pos){
        String[]prueba= delete(ordenes,pos);
        escribirFicheroMemoriaInterna(showArray(prueba));
        lista.setAdapter(null);
        orden="";
        initList();
        ordenes=orden.split("&");
    }
    private void fillArrayList(ArrayList<String> ar,String[] a){
        for(int i=0;i<a.length;i++){
            ar.add(a[i]);
        }
    }

    private String[] delete(String [] array, int pos){
        ArrayList<String> l= new ArrayList<String>();
        fillArrayList(l,array);
        l.remove(pos);
        Object[] aux=l.toArray();
        String []s=objectToArray(aux);
        return s;
    }
    public String[] objectToArray(Object[] objArray) {
        String[] strArray = new String[objArray.length];
        for(int i=0;i<strArray.length;i++){
            strArray[i]= objArray[i].toString();
        }
        return strArray;
    }
    private void escribirFicheroMemoriaInterna(String texto){
        OutputStreamWriter escritor=null;
        try
        {
            escritor=new OutputStreamWriter(openFileOutput("orden.txt", Context.MODE_PRIVATE));
            escritor.write(texto);
            //Toast.makeText(Cuenta.this,"Se agregó correctamente",Toast.LENGTH_LONG).show();
        }
        catch (Exception ex)
        {
            Log.e("Conde", "Error al agregar en la orden");
        }
        finally
        {
            try {
                if(escritor!=null)
                    escritor.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void leerFicheroMemoriaInterna(){
        InputStreamReader flujo=null;
        BufferedReader lector=null;
        try
        {
            flujo= new InputStreamReader(openFileInput("orden.txt"));
            lector= new BufferedReader(flujo);
            String texto = lector.readLine();
            //Toast.makeText(ArchivosMenu.this,texto,Toast.LENGTH_LONG).show();
            //texto=lector.readLine();
            //Toast.makeText(ArchivosMenu.this,"Jelou "+texto,Toast.LENGTH_LONG).show();

            while(texto!=null)
            {
                orden+=texto;
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
    }
    public void prueba(String mensaje){
        AlertDialog.Builder m= new AlertDialog.Builder(this);
        m.setMessage(mensaje).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
    }
    public void showMessage(String mensaje,final int positio){
        AlertDialog.Builder m= new AlertDialog.Builder(this);
        m.setMessage(mensaje).setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //updateData(positio);
                dialog.dismiss();
            }
        }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }).show();
    }

    private String showArray(String [] todo){
        String cad="";
        for(int i=0;i<todo.length;i++){
            cad+=todo[i]+"&";
        }
        return cad;
    }

    public void enviarSms(String num, String msj) {
        SmsManager smsManager = SmsManager.getDefault();

        String SMS_SENT = "SMS_SENT";
        String SMS_DELIVERED = "SMS_DELIVERED";

        PendingIntent sentPendingIntent = PendingIntent.getBroadcast(this, 0, new Intent(SMS_SENT), 0);
        PendingIntent deliveredPendingIntent = PendingIntent.getBroadcast(this, 0, new Intent(SMS_DELIVERED), 0);

        ArrayList<String> smsBodyParts = smsManager.divideMessage(msj);
        ArrayList<PendingIntent> sentPendingIntents = new ArrayList<PendingIntent>();
        ArrayList<PendingIntent> deliveredPendingIntents = new ArrayList<PendingIntent>();

        for (int i = 0; i < smsBodyParts.size(); i++) {
            sentPendingIntents.add(sentPendingIntent);
            deliveredPendingIntents.add(deliveredPendingIntent);
        }

        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        Toast.makeText(context, "Mensaje enviado", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Toast.makeText(context, "Hubo una falla", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Toast.makeText(context, "Servicio no disponible", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Toast.makeText(context, "No se proporcionó el pdu", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Toast.makeText(context, "La radio está apagada", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter(SMS_SENT));

        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "Mensaje entregado", Toast.LENGTH_SHORT).show();
                        break;
                    case Activity.RESULT_CANCELED:
                        Toast.makeText(getBaseContext(), "Mensaje no entregado", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter(SMS_DELIVERED));

        smsManager.sendMultipartTextMessage(num, null, smsBodyParts, sentPendingIntents, deliveredPendingIntents);
    }
}
