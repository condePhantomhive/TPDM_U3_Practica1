package mx.edu.ittepic.tpdm_u1_miniproyecto;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUp extends AppCompatActivity {
    ConexionDB conexion;
    EditText nombre,apellido,telefono;
    Button boton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        conexion=Principal.conexion;
        nombre=(EditText)findViewById(R.id.nombre_su);
        apellido=(EditText)findViewById(R.id.apellido_su);
        telefono=(EditText)findViewById(R.id.telefono_su);
        boton=(Button)findViewById(R.id.registrarse);
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nombre.getText().toString().isEmpty() || apellido.getText().toString().isEmpty() || telefono.getText().toString().isEmpty()){
                    Toast.makeText(SignUp.this,"LLene todos los campos",Toast.LENGTH_LONG).show();
                    return;
                }
                messageDialog("¿ "+nombre.getText().toString()+" confirme que desea registrarse con el siguiente teléfono "+telefono.getText().toString()
                        +" ?",getDatos(nombre,apellido,telefono));
            }
        });

    }
    public void abrirRegistro(){
        Intent ventana= new Intent(this,Login.class);
        startActivity(ventana);
    }
    private String[] getDatos(EditText n, EditText a, EditText t){
        String []cad=new String[3];
        cad[0]=n.getText().toString();
        cad[1]=a.getText().toString();
        cad[2]=t.getText().toString();
        return cad;
    }
    public void messageDialog(String mensaje, final String[] cad_agregar){
        AlertDialog.Builder m= new AlertDialog.Builder(this);
        m.setMessage(mensaje).setPositiveButton("Agregar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (!Queries.insertarcliente(cad_agregar, conexion)){
                    Toast.makeText(SignUp.this, "Ocurrió un error al registrarse", Toast.LENGTH_LONG).show();
                    return;
                }
                Toast.makeText(SignUp.this, "Registro exitoso", Toast.LENGTH_LONG).show();
                dialog.dismiss();
                abrirRegistro();
            }
        }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }).show();
    }
}
