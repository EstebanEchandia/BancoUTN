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
    private float interesesGanados;


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

            //seteo de la cantidad de dias del seekbar, se actualizan los datos cada vez que se cambia el valor
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                textoDias.setText("Días: " + i);
                diasPlazo = i;
                calcularSimulacion();
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

    private void actualizarCampos (){
        SeekBar barraDias = binding.seekBar;
        TextView textViewPlazo = binding.plazo;
        TextView textViewCapital = binding.capital;
        TextView textViewInteresesGanados = binding.interesesGanados;
        TextView textViewMontoTotal = binding.montoTotal;
        TextView textViewMontoTotalAnual = binding.montoTotalAnual;
        EditText editTextMontoCapital = binding.montoCapital;

        textViewPlazo.setText("Plazo: " + barraDias.getProgress() + " días");
        if(!editTextMontoCapital.getText().toString().equals(""))
            textViewCapital.setText("Capital: " + capitalInicial);
        if(!editTextMontoCapital.getText().toString().equals(""))
            textViewInteresesGanados.setText("Intereses ganados: "+ String.valueOf(interesesGanados));
        if(!editTextMontoCapital.getText().toString().equals(""))
            textViewMontoTotal.setText("Monto total: " + String.valueOf(capitalFinalSinRenovacion));
        if(!editTextMontoCapital.getText().toString().equals(""))
            textViewMontoTotalAnual.setText("Monto total Anual: " + editTextMontoCapital.getText().toString());
    }

    public void calcularSimulacion(){
        TNA = Float.parseFloat(binding.montoTNA.getText().toString());
        TEA = Float.parseFloat(binding.montoTEA.getText().toString());
        capitalInicial = Float.parseFloat(binding.montoCapital.getText().toString());

        if (binding.checkBox.isChecked()){

        }
        else {
            // https://www.elmejortrato.com.ar/inversiones/como-calcular-la-ganancia-por-plazo-fijo
            capitalFinalSinRenovacion = capitalInicial * (TNA * (diasPlazo / 365));
            interesesGanados = capitalFinalSinRenovacion - capitalInicial;
        }
    };

}