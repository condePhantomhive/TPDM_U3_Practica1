package mx.edu.ittepic.tpdm_u1_miniproyecto;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {
    Button registrarse,entrar;
    public static String numero="";
    EditText telefono;
    ConexionDB conexion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        registrarse=(Button)findViewById(R.id.registro);
        conexion=Principal.conexion;
        telefono=(EditText)findViewById(R.id.telefono);
        entrar=(Button)findViewById(R.id.entrar);
        registrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirRegistro();
                finish();
            }
        });
        entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(telefono.getText().toString().isEmpty()){
                    Toast.makeText(Login.this, "Llene todos los campos", Toast.LENGTH_LONG).show();
                    return;
                }
                if (!Queries.verificarCliente(telefono.getText().toString(), conexion)){
                    Toast.makeText(Login.this, "Usuario no registrado, verifica el teléfono", Toast.LENGTH_LONG).show();
                    return;
                }
                numero=telefono.getText().toString();
                abrirMenu();
                finish();
            }
        });

    }
    public void abrirRegistro(){
        Intent ventana= new Intent(this,SignUp.class);
        startActivity(ventana);
    }
    public void abrirMenu(){
        Intent ventana= new Intent(this,Menu.class);
        ventana.putExtra("telefono",numero);
        startActivity(ventana);
    }
}
