package mx.edu.ittepic.tpdm_u1_miniproyecto;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.provider.Contacts;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.util.Calendar;

public class Reservaciones extends AppCompatActivity {
    ConexionDB conexion;
    EditText fecha,hora,id;
    Button btn;
    DateFormat formatoFecha = DateFormat.getDateInstance();
    DateFormat formatoHora = DateFormat.getTimeInstance();
    Calendar c = Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        conexion=Principal.conexion;
        Log.e("Telefono",Menu.numero);
        setContentView(R.layout.activity_reservaciones);
        fecha=(EditText)findViewById(R.id.fecha);
        hora=(EditText)findViewById(R.id.hora);
        id=(EditText)findViewById(R.id.id);
        id.setText(Menu.numero);
        id.setKeyListener(null);

        btn=(Button)findViewById(R.id.button4);
        hora.setKeyListener(null);
        fecha.setKeyListener(null);

        fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDate();
            }
        });
        hora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateTime();
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fecha.getText().toString().isEmpty() ||hora.getText().toString().isEmpty() || id.getText().toString().isEmpty()){
                    messageDialog("Verifique que todos los campos esten llenos.");
                    return;
                }
                messageDialog("¿Desea reservar con el No. de teléfono "+Menu.numero+"?",getDatos(fecha,hora,id));
                cleanEditText();

            }
        });
    }
    DatePickerDialog.OnDateSetListener datePickerDialog = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            c.set(Calendar.YEAR, year);
            c.set(Calendar.MONTH, month);
            c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            fecha.setText(formatoFecha.format(c.getTime()));
        }
    };
    TimePickerDialog.OnTimeSetListener timePickerDialog = new TimePickerDialog.OnTimeSetListener(){

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            c.set(Calendar.HOUR_OF_DAY, hourOfDay);
            c.set(Calendar.MINUTE, minute);

            hora.setText(formatoHora.format(c.getTime()));
        }
    };

    private void updateDate(){
        new DatePickerDialog(this, datePickerDialog,
                c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
    }
    private void updateTime(){
        new TimePickerDialog(this, timePickerDialog,
                c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), true).show();
    }
    private String[] getDatos(EditText f, EditText h, EditText t){
        String []cad=new String[3];
        cad[0]=f.getText().toString();
        cad[1]=h.getText().toString();
        cad[2]=Menu.numero;
        return cad;
    }
    private void cleanEditText(){
        fecha.setText("");
        hora.setText("");
        id.setText("");
    }
    public void messageDialog(String mensaje, final String[] cad_agregar){
        AlertDialog.Builder m= new AlertDialog.Builder(this);
        //Log.e("Elementos","Fecha"+cad_agregar[0]+"\nHora "+cad_agregar[1]+"\nTelefono "+cad_agregar[2]);
        m.setMessage(mensaje).setPositiveButton("Reservar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (!Queries.insertarReservacion(cad_agregar, conexion)){
                    Toast.makeText(Reservaciones.this, "Ocurrió un error al crear la reservación", Toast.LENGTH_LONG).show();
                    return;
                }
                Toast.makeText(Reservaciones.this, "Reservacion realizada exitosamente", Toast.LENGTH_LONG).show();
                dialog.dismiss();
                finish();
            }
        }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }).show();
    }

    public void messageDialog(String mensaje){
        AlertDialog.Builder m= new AlertDialog.Builder(this);
        m.setMessage(mensaje).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
    }
}
