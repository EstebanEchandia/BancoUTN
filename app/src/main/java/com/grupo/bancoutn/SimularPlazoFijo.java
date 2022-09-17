package com.grupo.bancoutn;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.grupo.bancoutn.databinding.ActivityConstituirPlazoFijoBinding;
import com.grupo.bancoutn.databinding.ActivitySimularPlazoFijoBinding;

public class SimularPlazoFijo extends AppCompatActivity {

    private ActivitySimularPlazoFijoBinding binding;

    private void acutalizarCampos (){
        SeekBar barraDias = binding.seekBar;
        TextView plazo = binding.plazo;
        TextView capital = binding.capital;
        TextView interesesGanados = binding.interesesGanados;
        TextView montoTotal = binding.montoTotal;
        TextView montoTotalAnual = binding.montoTotalAnual;
        EditText montoCapital = binding.montoCapital;

        plazo.setText("Plazo: " + barraDias.getProgress() + " días");
        if(!montoCapital.getText().toString().equals(""))
            capital.setText("Capital: " + montoCapital.getText().toString());
        if(!montoCapital.getText().toString().equals(""))
            interesesGanados.setText("Intereses ganados:"+ montoCapital.getText().toString());
        if(!montoCapital.getText().toString().equals(""))
            montoTotal.setText("Monto total: $ idem anterior" + montoCapital.getText().toString());
        if(!montoCapital.getText().toString().equals(""))
            montoTotalAnual.setText("Monto total Anual: $ idem anterior" + montoCapital.getText().toString());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivitySimularPlazoFijoBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        TextView textoDias = binding.textoDias;
        SeekBar barraDias = binding.seekBar;
        barraDias.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                textoDias.setText("Días: " + i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                acutalizarCampos();
            }
        });
        Button calcular = binding.calcular;


        calcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                acutalizarCampos();
            }
        });



    }
}