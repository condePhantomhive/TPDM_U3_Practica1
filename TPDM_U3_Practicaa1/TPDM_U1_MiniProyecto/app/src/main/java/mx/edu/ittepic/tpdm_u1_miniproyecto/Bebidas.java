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

import org.w3c.dom.ls.LSException;

import java.io.IOException;
import java.io.OutputStreamWriter;

public class Bebidas extends AppCompatActivity {
    ListView lista;
    String add="¿Desea agregar ";
    String add2=" a su pedido?";
    String [] bebidas={ "XA Cabernet Sauvignon",
                        "Gran sangre de toro",
                        "Marqués de Caceres",
                        "Casillero del diablo",
                        "Concha y Toro",
                        "L.A Cetto Blanc de Blancs",
                        "Marqués de Caceres",
                        "Openhermer",
                        "Lambrusco",
                        "Heineken",
                        "Miller",
                        "Corona",
                        "Blue Light"};
    Integer [] imgid={
            R.drawable.glass_flute,
            R.drawable.glass_flute,
            R.drawable.glass_flute,
            R.drawable.glass_flute,
            R.drawable.glass_flute,
            R.drawable.glass_tulip,
            R.drawable.glass_tulip,
            R.drawable.glass_tulip,
            R.drawable.glass_tulip,
            R.drawable.glass_mug,
            R.drawable.glass_mug,
            R.drawable.glass_mug,
            R.drawable.glass_mug
    };
    String [] tipos={   "Vino tinto",
                        "Vino tinto",
                        "Vino tinto",
                        "Vino tinto",
                        "Vino tinto",
                        "Vino Blanco",
                        "Vino Blanco",
                        "Vino Blanco",
                        "Vino Espumoso",
                        "Cerveza",
                        "Cerveza",
                        "Cerveza",
                        "Cerveza"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bebidas);
        lista=(ListView)findViewById(R.id.listabebida);
        LenguajeListAdapter adapter= new LenguajeListAdapter(this,bebidas,tipos,imgid);
        lista.setAdapter(adapter);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        messageDialog(add+bebidas[0]+add2,bebidas[0]+",Bebida"+"&");
                        break;
                    case 1:
                        messageDialog(add+bebidas[1]+add2,bebidas[1]+",Bebida"+"&");
                        break;
                    case 2:
                        messageDialog(add+bebidas[2]+add2,bebidas[2]+",Bebida"+"&");
                        break;
                    case 3:
                        messageDialog(add+bebidas[3]+add2,bebidas[3]+",Bebida"+"&");
                        break;
                    case 4:
                        messageDialog(add+bebidas[4]+add2,bebidas[4]+",Bebida"+"&");
                        break;
                    case 5:
                        messageDialog(add+bebidas[5]+add2,bebidas[5]+",Bebida"+"&");
                        break;
                    case 6:
                        messageDialog(add+bebidas[6]+add2,bebidas[6]+",Bebida"+"&");
                        break;
                    case 7:
                        messageDialog(add+bebidas[7]+add2,bebidas[7]+",Bebida"+"&");
                        break;
                    case 8:
                        messageDialog(add+bebidas[8]+add2,bebidas[8]+",Bebida"+"&");
                        break;
                    case 9:
                        messageDialog(add+bebidas[9]+add2,bebidas[9]+",Bebida"+"&");
                        break;
                    case 10:
                        messageDialog(add+bebidas[10]+add2,bebidas[10]+",Bebida"+"&");
                        break;
                    case 11:
                        messageDialog(add+bebidas[11]+add2,bebidas[11]+",Bebida"+"&");
                        break;
                    case 12:
                        messageDialog(add+bebidas[12]+add2,bebidas[12]+",Bebida"+"&");
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
            Toast.makeText(Bebidas.this,"Se agregó correctamente",Toast.LENGTH_LONG).show();
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
