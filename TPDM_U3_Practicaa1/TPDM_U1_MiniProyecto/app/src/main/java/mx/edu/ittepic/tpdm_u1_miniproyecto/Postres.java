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

public class Postres extends AppCompatActivity {
    ListView lista;
    String add="¿Desea agregar ";
    String add2=" a su pedido?";
    String [] postres={ "Sopa fría de ciruelas",
                        "Frutillas",
                        "El clásico vigilante de la boca",
                        "Mousse de chocolate y naranja",
                        "Panqueque de manzana",
                        "Panqueque al hierro",
                        "Tartaleta de manzana",
                        "Tartaleta de fresa",
                        "Mix Tropical",
                        "Bonnie's la bamba",
                        "Chocolate fondue"};
    Integer [] imgid={
            R.drawable.star,
            R.drawable.star_outline,
            R.drawable.star_outline,
            R.drawable.star_outline,
            R.drawable.star,
            R.drawable.star,
            R.drawable.star_outline,
            R.drawable.star_outline,
            R.drawable.star,
            R.drawable.star_outline,
            R.drawable.star,
    };
    String [] info={    "Sorbet casero de manzanas verdes.",
                        "Crema y merengue.",
                        "Dulce de guayaba y queso crema.",
                        "Salsa de chocolate, chocolate blanco rallado.",
                        "Manzana Granny Smith y helado de vainilla.",
                        "Duraznos, dulce de leche y helado de vainilla.",
                        "Manzana, nueves y pasas",
                        "Pasta dulce, crema pastelera y fresas",
                        "Frías bolas de helado con trozos de frutas tropicales de temporada.",
                        "Fabulosa mezcla de café, tequila, kahlua y crema dulce.",
                        "Exquisita y deliciosa salsa de chocolate para degustar con frutas, galletas y otras variedades"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postres);
        lista=(ListView)findViewById(R.id.listapostre);
        LenguajeListAdapter adapter= new LenguajeListAdapter(this,postres,info,imgid);
        lista.setAdapter(adapter);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        messageDialog(add+postres[0]+add2,postres[0]+",Postre"+"&");
                        break;
                    case 1:
                        messageDialog(add+postres[1]+add2,postres[1]+",Postre"+"&");
                        break;
                    case 2:
                        messageDialog(add+postres[2]+add2,postres[2]+",Postre"+"&");
                        break;
                    case 3:
                        messageDialog(add+postres[3]+add2,postres[3]+",Postre"+"&");
                        break;
                    case 4:
                        messageDialog(add+postres[4]+add2,postres[4]+",Postre"+"&");
                        break;
                    case 5:
                        messageDialog(add+postres[5]+add2,postres[5]+",Postre"+"&");
                        break;
                    case 6:
                        messageDialog(add+postres[6]+add2,postres[6]+",Postre"+"&");
                        break;
                    case 7:
                        messageDialog(add+postres[7]+add2,postres[7]+",Postre"+"&");
                        break;
                    case 8:
                        messageDialog(add+postres[8]+add2,postres[8]+",Postre"+"&");
                        break;
                    case 9:
                        messageDialog(add+postres[9]+add2,postres[9]+",Postre"+"&");
                        break;
                    case 10:
                        messageDialog(add+postres[10]+add2,postres[10]+",Postre"+"&");
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
            Toast.makeText(Postres.this,"Se agregó correctamente",Toast.LENGTH_LONG).show();
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
