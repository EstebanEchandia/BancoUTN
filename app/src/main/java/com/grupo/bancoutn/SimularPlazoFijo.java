package com.grupo.bancoutn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

    private float TNA;
    private float TEA;
    private float diasPlazo;
    private float capitalFinalSinRenovacion;
    private float capitalFinalConRenovacion;
    private float capitalInicial;

    private void actualizarCampos (){
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

   public void calcularSimulacion(){
        TNA = Float.parseFloat(binding.montoTNA.getText().toString());
        TEA = Float.parseFloat(binding.montoTEA.getText().toString());
        capitalInicial = Float.parseFloat(binding.montoCapital.getText().toString());
        if (binding.checkBox.isChecked()){

        }
        else {
            capitalFinalSinRenovacion = capitalInicial * (TNA * diasPlazo / 365);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivitySimularPlazoFijoBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        TextView textoDias = binding.textoDias;
        SeekBar barraDias = binding.seekBar;
        barraDias.setMax(365);
        barraDias.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                textoDias.setText("Días: " + i);
                diasPlazo = i;
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                actualizarCampos();
            }
        });

        Button botonCalcular = binding.calcular;

        botonCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actualizarCampos();

                //funcion de calcular plazo fijo
                Intent intent = getIntent();
                intent.putExtra("CapitalInicial" , binding.montoCapital.getText().toString());
                intent.putExtra("PlazoDias", String.valueOf(diasPlazo));
                setResult(RESULT_OK, intent);
                finish();
            }
        });


    }

}