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
    public static ConexionDB conexion;
    String nombre[];
    String descripcion[];
    String precio[];

    ListView lista;

    String add="¿Desea agregar ";
    String add2=" a su pedido?";


    Integer [] imgid={
            R.drawable.glass_flute,
            R.drawable.glass_flute,
            R.drawable.glass_tulip,
            R.drawable.glass_tulip,
            R.drawable.glass_mug,
            R.drawable.glass_mug,
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bebidas);
        conexion=Principal.conexion;
        lista=(ListView)findViewById(R.id.listabebida);
        nombre = Queries.mostrarProducto("Bebidas", conexion).get(0);
        descripcion = Queries.mostrarProducto("Bebidas", conexion).get(1);
        precio = Queries.mostrarProducto("Bebidas", conexion).get(2);
        LenguajeListAdapter adapter= new LenguajeListAdapter(this,nombre,descripcion,imgid);
        lista.setAdapter(adapter);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        messageDialog(add+nombre[0]+add2,nombre[0]);
                        break;
                    case 1:
                        messageDialog(add+nombre[1]+add2,nombre[1]);
                        break;
                    case 2:
                        messageDialog(add+nombre[2]+add2,nombre[2]);
                        break;
                    case 3:
                        messageDialog(add+nombre[3]+add2,nombre[3]);
                        break;
                    case 4:
                        messageDialog(add+nombre[4]+add2,nombre[4]);
                        break;
                    case 5:
                        messageDialog(add+nombre[5]+add2,nombre[5]);
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
                if (!Queries.insertarOrden(cad_agregar, conexion)){
                    Toast.makeText(Bebidas.this, "Ocurrió un error al añadir al carrito", Toast.LENGTH_LONG).show();
                    return;
                }
                Toast.makeText(Bebidas.this, "Se añadió al carrito", Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }).show();
    }
}
