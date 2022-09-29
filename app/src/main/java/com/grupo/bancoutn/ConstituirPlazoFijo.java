package com.grupo.bancoutn;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.grupo.bancoutn.databinding.ActivityConstituirPlazoFijoBinding;

import java.util.Locale;


public class ConstituirPlazoFijo extends AppCompatActivity {

    public static final int REQUEST_CODE = 1;

    private ActivityConstituirPlazoFijoBinding binding;
    private String nombre;
    private String apellido;
    private String moneda;
    private String capitalInicial;
    private String plazoDias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //declaramos los binder
        binding = ActivityConstituirPlazoFijoBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        Button botonSimular = binding.botonSimular;
        Button botonConstituir = binding.botonConstituir;
        Spinner spinnerMoneda = binding.spinner;
        EditText editTextNombre = binding.textoNombre;
        EditText editTextApellido = binding.textoApellido;

        botonConstituir.setEnabled(false);


        //Opciones del spinner
        final String[] opciones = {"PESOS","DOLARES","EUROS"};
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,opciones);
        spinnerMoneda.setAdapter(adapter);

        botonSimular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Guardamos los datos cargados por el usuario antes de pasar a la actividad
                nombre = editTextNombre.getText().toString();
                apellido = editTextApellido.getText().toString();
                moneda = spinnerMoneda.getSelectedItem().toString();

                //Debug
                Log.e("nombre, apellido y moneda", nombre+' '+apellido+' '+moneda);


                //Intent a actividad de simular plazo fijo, el startActivityForResult queda esperando una respuesta
                Intent intent = new Intent(view.getContext(), SimularPlazoFijo.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        botonConstituir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //popUp de dialogo constituido
                new AlertDialog.Builder(view.getContext())
                        .setTitle("Felicitaciones "+nombre+" "+apellido+"!")
                        .setMessage("Tu plazo fijo de "+capitalInicial+" "+moneda.toLowerCase()+" ha sido constituido!")
                        .setPositiveButton("Joya!", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                //no hacemos nada
                            }
                        })
                        .show();
            }
        });

    }

    //Este es el codigo que se ejecuta cuando vuelve de la actividad de SimularPlazoFijo
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == REQUEST_CODE  && resultCode  == RESULT_OK) {
                capitalInicial = data.getStringExtra("CapitalInicial");
                plazoDias = data.getStringExtra("PlazoDias");
                //Debug
                Log.e("capital y plazo", capitalInicial+' '+plazoDias);
                binding.botonConstituir.setEnabled(true);
            }
        } catch (Exception ex) {
            //Toast.makeText(Activity.this, ex.toString(),
            //        Toast.LENGTH_SHORT).show();
        }

    }

}
