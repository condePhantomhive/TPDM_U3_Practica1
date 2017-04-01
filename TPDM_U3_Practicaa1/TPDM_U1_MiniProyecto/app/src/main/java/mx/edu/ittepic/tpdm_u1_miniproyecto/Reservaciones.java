package mx.edu.ittepic.tpdm_u1_miniproyecto;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    EditText nombre,fecha,hora,telefono;
    Button btn;
    DateFormat formatoFecha = DateFormat.getDateInstance();
    DateFormat formatoHora = DateFormat.getTimeInstance();
    Calendar c = Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservaciones);
        nombre= (EditText)findViewById(R.id.editText);
        fecha=(EditText)findViewById(R.id.fecha);
        hora=(EditText)findViewById(R.id.hora);
        telefono=(EditText)findViewById(R.id.telefono);
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
                if(nombre.getText().toString().isEmpty()|| fecha.getText().toString().isEmpty() ||
                        hora.getText().toString().isEmpty() || telefono.getText().toString().isEmpty()){
                    messageDialog("Verifique que todos los campos esten llenos.");
                    return;
                }
                escribirFicheroMemoriaInterna(getDatos(nombre,fecha,hora,telefono));
                cleanEditText();
                finish();
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
    private String getDatos(EditText n, EditText f, EditText h, EditText t){
        String cad="";
        cad+=n.getText().toString()+",";
        cad+=f.getText().toString()+",";
        cad+=h.getText().toString()+",";
        cad+=t.getText().toString()+"\n";
        return cad;
    }
    private void cleanEditText(){
        nombre.setText("");
        fecha.setText("");
        hora.setText("");
        telefono.setText("");
    }
    private void escribirFicheroMemoriaInterna(String texto){
        OutputStreamWriter escritor=null;
        try
        {
            escritor=new OutputStreamWriter(openFileOutput("reservacion.csv", Context.MODE_APPEND));
            escritor.write(texto);
            Toast.makeText(Reservaciones.this,"Se realizó la reservación correctamente",Toast.LENGTH_LONG).show();
        }
        catch (Exception ex)
        {
            Log.e("Conde", "Error al realizar la reservación");
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
