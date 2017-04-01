package mx.edu.ittepic.tpdm_u1_miniproyecto;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStreamWriter;

public class Pizzas extends AppCompatActivity {
    ListView lista;
    String add="¿Desea agregar ";
    String add2=" a su pedido?";
    String[] pizzas={"Campirana",
                    "Pollo Con Rajas Poblanas",
                    "Mexicana",
                    "Hawaiiana",
                    "TLC",
                    "Palomitas De Camarón",
                    "Vegetariana",
                    "Ranchera",
                    "Combinada",
                    "Texana Extrema",
                    "Carnívora",
                    "Super Extravaganza"};
    Integer [] imgid={
            R.drawable.heart,
            R.drawable.heart_outline,
            R.drawable.heart,
            R.drawable.heart,
            R.drawable.heart_outline,
            R.drawable.heart_outline,
            R.drawable.heart_outline,
            R.drawable.heart,
            R.drawable.heart_outline,
            R.drawable.heart,
            R.drawable.heart_outline,
            R.drawable.heart
    };
    String[] info={ "Champiñones, jugosa Piña y Elote. Pastor&Exquisita carne al pastor, trocitos de piña, cebolla, cilantro.",
                    "Delicioso pollo jugosito acompañado con rajas poblanas, elotitos, crema y doble queso.",
                    "Chile Jalapeño, Tocino, Carne Molida, Pepperoni, Cebolla y tiras de Pimiento Verde.",
                    "Jugosa Piña y Jamón.",
                    "Pepperoni, Tocino, Chiles Jalapeños y Queso.",
                    "Trocitos de camarón empanizado y aderezo picosito.",
                    "Champiñones, Pimiento Verde, Aceitunas, Cebolla, Elote y delicioso queso.",
                    "Carne Molida, Pepperoni, Cebolla, Chile Jalapeño y ricos trocitos de Jitomate.",
                    "Pepperoni, Pimiento Verde, Jamón, Champiñones, Cebolla y Aceituna.",
                    "Pollo BBQ, Tocino, Cebolla, Pimiento Verde y aderezo BBQ.",
                    "Salami, Carne Molida, Tocino, Jamón y Chorizo.",
                    "Camarón, Atún, Pimiento Verde, Aceitunas, Cebolla, Champiñon, Jamón, Salami y doble queso."};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pizzas);
        lista=(ListView)findViewById(R.id.listapizzas);
        LenguajeListAdapter adapter= new LenguajeListAdapter(this,pizzas,info,imgid);
        lista.setAdapter(adapter);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        messageDialog(add+pizzas[0]+add2,pizzas[0]+",Pizza"+"&");
                        break;
                    case 1:
                        messageDialog(add+pizzas[1]+add2,pizzas[1]+",Pizza"+"&");
                        break;
                    case 2:
                        messageDialog(add+pizzas[2]+add2,pizzas[2]+",Pizza"+"&");
                        break;
                    case 3:
                        messageDialog(add+pizzas[3]+add2,pizzas[3]+",Pizza"+"&");
                        break;
                    case 4:
                        messageDialog(add+pizzas[4]+add2,pizzas[4]+",Pizza"+"&");
                        break;
                    case 5:
                        messageDialog(add+pizzas[5]+add2,pizzas[5]+",Pizza"+"&");
                        break;
                    case 6:
                        messageDialog(add+pizzas[6]+add2,pizzas[6]+",Pizza"+"&");
                        break;
                    case 7:
                        messageDialog(add+pizzas[7]+add2,pizzas[7]+",Pizza"+"&");
                        break;
                    case 8:
                        messageDialog(add+pizzas[8]+add2,pizzas[8]+",Pizza"+"&");
                        break;
                    case 9:
                        messageDialog(add+pizzas[9]+add2,pizzas[9]+",Pizza"+"&");
                        break;
                    case 10:
                        messageDialog(add+pizzas[10]+add2,pizzas[10]+",Pizza"+"&");
                        break;
                    case 11:
                        messageDialog(add+pizzas[11]+add2,pizzas[11]+",Pizza"+"&");
                        break;
                }
            }
        });
    }
    public void messageDialog(String mensaje, final String cad_agregar){
        AlertDialog.Builder m= new AlertDialog.Builder(this);
        m.setMessage(mensaje).setPositiveButton("Agregar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                escribirFicheroMemoriaInterna(cad_agregar);
                dialog.dismiss();
            }
        }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }).show();
    }
    private void escribirFicheroMemoriaInterna(String texto){
        OutputStreamWriter escritor=null;
        try
        {
            escritor=new OutputStreamWriter(openFileOutput("orden.txt", Context.MODE_APPEND));
            escritor.write(texto);
            Toast.makeText(Pizzas.this,"Se agregó correctamente",Toast.LENGTH_LONG).show();
        }
        catch (Exception ex)
        {
            Log.e("Conde", "Error al escribir fichero a memoria interna");
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
}
