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
import android.widget.Spinner;

import com.grupo.bancoutn.databinding.ActivityConstituirPlazoFijoBinding;


public class ConstituirPlazoFijo extends AppCompatActivity {

    public static final int REQUEST_CODE = 1;

    private ActivityConstituirPlazoFijoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityConstituirPlazoFijoBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        Button botonSimular = binding.botonSimular;
        Spinner moneda = binding.spinner;
        final String[] opciones = {"PESOS","DOLARES","EUROS"};
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,opciones);
        moneda.setAdapter(adapter);
        //Button botonSimular = (Button)findViewById(R.id.botonSimular);

        botonSimular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), SimularPlazoFijo.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == REQUEST_CODE  && resultCode  == RESULT_OK) {
                String capitalInicial = data.getStringExtra("CapitalInicial");
                String plazoDias = data.getStringExtra("PlazoDias");
                Log.e("capital", capitalInicial);
                Log.e("plazo", plazoDias);

            }
        } catch (Exception ex) {
            //Toast.makeText(Activity.this, ex.toString(),
            //        Toast.LENGTH_SHORT).show();
        }

    }

}
