package mx.edu.ittepic.tpdm_u1_miniproyecto;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class Pastas extends AppCompatActivity {
    ListView lista;
    String add="¿Desea agregar ";
    String add2=" a su pedido?";
    String pastas[]={"Margarita","Putanesca","Cordon Blue","Salmón","4 Quesos","Linguine al pesto","Pappardelle alla bolognesa",
            "Fettuccine Alfredo","Lasagna bolognesa","Lasagna sorentina","Lasagna vegetales"};
    Integer [] imgid={
            R.drawable.star,
            R.drawable.star_outline,
            R.drawable.star,
            R.drawable.star_outline,
            R.drawable.star,
            R.drawable.star_outline,
            R.drawable.star_outline,
            R.drawable.star,
            R.drawable.star_outline,
            R.drawable.star_outline,
            R.drawable.star
    };
    String [] info={"Queso bocconcini, tomate y albahaca. Servida con salsa pomodoro.",
                    "Cebolla, alcaparras, anchoas, albahaca, aceitunas, y chile picante a su gusto. Servida con salsa pomodoro.",
                    "Tiras de jamón, blue cheese y pollo. Acompañadas de salsa blanca.",
                    "Salmón ahumado y eneido fresco. Acompañado de salsa blanca.",
                    "Mozzarela, blue cheese, emmnetal y parmesano. Acompañados de salsa blanca.",
                    "Salsa cremosa de pesto de almbahaca y nuez.",
                    "Salsa de tomates frescos con albóndicas.",
                    "Pasta al huevo con salsa cremosa con jamón.",
                    "Carne molida, mozzarela y queso parmesano. Servida con salsa pomodoro.",
                    "Salsa blanca, pollo, hongos, jamón y queso gratinado.",
                    "Cebolla, chile, berenjena, hongos y zuquini. Elija a su gusto salsa pomodoro o blanca."};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pastas);
        lista=(ListView)findViewById(R.id.listapasta);
        LenguajeListAdapter adapter= new LenguajeListAdapter(this,pastas,info,imgid);
        lista.setAdapter(adapter);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        messageDialog(add+pastas[0]+add2,pastas[0]+",Pasta"+"&");
                        break;
                    case 1:
                        messageDialog(add+pastas[1]+add2,pastas[1]+",Pasta"+"&");
                        break;
                    case 2:
                        messageDialog(add+pastas[2]+ add2,pastas[2]+",Pasta"+"&");
                        break;
                    case 3:
                        messageDialog(add+pastas[3]+ add2,pastas[3]+",Pasta"+"&");
                        break;
                    case 4:
                        messageDialog(add+pastas[4]+ add2,pastas[4]+",Pasta"+"&");
                        break;
                    case 5:
                        messageDialog(add+pastas[5]+ add2,pastas[5]+",Pasta"+"&");
                        break;
                    case 6:
                        messageDialog(add+pastas[6]+ add2,pastas[6]+",Pasta"+"&");
                        break;
                    case 7:
                        messageDialog(add+pastas[7]+ add2,pastas[7]+",Pasta"+"&");
                        break;
                    case 8:
                        messageDialog(add+pastas[8]+ add2,pastas[8]+",Pasta"+"&");
                        break;
                    case 9:
                        messageDialog(add+pastas[9]+ add2,pastas[9]+",Pasta"+"&");
                        break;
                    case 10:
                        messageDialog(add+pastas[10]+ add2, pastas[10]+",Pasta"+"&");
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
            Toast.makeText(Pastas.this,"Se agregó correctamente",Toast.LENGTH_LONG).show();
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
}
