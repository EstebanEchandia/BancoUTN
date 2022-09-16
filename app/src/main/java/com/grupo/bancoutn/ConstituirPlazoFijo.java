package com.grupo.bancoutn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;

import com.grupo.bancoutn.databinding.ActivityConstituirPlazoFijoBinding;


public class ConstituirPlazoFijo extends AppCompatActivity {

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
                view.getContext().startActivity(intent);}
        });
    }



}
