package com.grupo.bancoutn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
    private float capitalFinalSinRenovacion;
    private float capitalFinalConRenovacion;
    private float capitalInicial;
    private float interesesGanados;
    private int cantDiasMes = 30;
    private int diasPlazo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivitySimularPlazoFijoBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        EditText TNA = binding.montoTNA;
        TextView textoDias = binding.textoDias;
        SeekBar barraDias = binding.seekBar;
        barraDias.setMax(5);
        barraDias.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            //seteo de la cantidad de dias del seekbar, se actualizan los datos cada vez que se cambia el valor
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(i<=3){
                    diasPlazo = i*cantDiasMes;
                }
                else if(i==4){
                    diasPlazo = 180;
                }
                else{
                    diasPlazo = 360;
                }
                textoDias.setText("Días: " + diasPlazo);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                actualizarCampos();
                calcularSimulacion();
            }
        });

        //Actualizar campos cuando se cambia el texto
        binding.montoTNA.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {};
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {};
            @Override
            public void afterTextChanged(Editable s) {
                calcularSimulacion();
                actualizarCampos();
            }
        });
        binding.montoTEA.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {};
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {};
            @Override
            public void afterTextChanged(Editable s) {
                calcularSimulacion();
                actualizarCampos();
            }
        });
        binding.montoCapital.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {};
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {};
            @Override
            public void afterTextChanged(Editable s) {
                calcularSimulacion();
                actualizarCampos();
            }
        });

        Button botonConfirmar = binding.confirmar;

        botonConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    if(!binding.montoTNA.getText().toString().equals("") && !binding.montoTEA.getText().toString().equals("") && !binding.montoCapital.getText().toString().equals("")){
                    actualizarCampos();

                    Intent intent = getIntent();
                    intent.putExtra("CapitalInicial" , binding.montoCapital.getText().toString());
                    intent.putExtra("PlazoDias", String.valueOf(diasPlazo));
                    setResult(RESULT_OK, intent);
                    finish();
                }
                else{
                    Toast t = new Toast(getApplicationContext());
                    t.setText("Debe sumnistrar un valor valido para los campos");
                    t.show();
                }



                //funcion de calcular plazo fijo
                /*
                Intent intent = getIntent();
                intent.putExtra("CapitalInicial" , binding.montoCapital.getText().toString());
                intent.putExtra("PlazoDias", String.valueOf(diasPlazo));
                setResult(RESULT_OK, intent);
                finish();*/
            }
        });
    }

    private void actualizarCampos (){
        TextView textViewPlazo = binding.plazo;
        TextView textViewCapital = binding.capital;
        TextView textViewInteresesGanados = binding.interesesGanados;
        TextView textViewMontoTotal = binding.montoTotal;
        TextView textViewMontoTotalAnual = binding.montoTotalAnual;
        EditText editTextMontoCapital = binding.montoCapital;

        Log.d("A ver que tiene",binding.montoTNA.getText().toString());
        textViewPlazo.setText("Plazo: " + diasPlazo + " días");
        if(!editTextMontoCapital.getText().toString().equals(""))
            textViewCapital.setText("Capital: " + capitalInicial);
        if(!editTextMontoCapital.getText().toString().equals(""))
            textViewInteresesGanados.setText("Intereses ganados: "+ String.valueOf(interesesGanados));
        if(!editTextMontoCapital.getText().toString().equals(""))
            textViewMontoTotal.setText("Monto total: " + String.valueOf(capitalFinalSinRenovacion));
        if(!editTextMontoCapital.getText().toString().equals(""))
            textViewMontoTotalAnual.setText("Monto total Anual: " + String.valueOf(capitalFinalConRenovacion));
    }

    public void calcularSimulacion(){
        if(!binding.montoTNA.getText().toString().equals("") && !binding.montoTEA.getText().toString().equals("") && !binding.montoCapital.getText().toString().equals("")){
            Integer progresoBarra, meses;
            progresoBarra=(Integer) binding.seekBar.getProgress();
            meses = Integer.parseInt(progresoBarra.toString());
            if(meses==4) meses=6;
            if(meses==5) meses=12;
            TNA = Float.parseFloat(binding.montoTNA.getText().toString());
            TEA = Float.parseFloat(binding.montoTEA.getText().toString());
            capitalInicial = Float.parseFloat(binding.montoCapital.getText().toString());

            if (binding.checkBox.isChecked()){

            }
            else {
                interesesGanados = ((TNA/100)/12)*meses*capitalInicial;
                capitalFinalSinRenovacion = interesesGanados + capitalInicial;
                capitalFinalConRenovacion = capitalInicial * (TEA/100) + capitalInicial;
            }
        }

    }

    public float tasa_nominal_anual(double tasa_efectiva_anual, int meses){

        double tasa_nominal_anual, factor;

        tasa_efectiva_anual = tasa_efectiva_anual/100;

        factor = 12/meses;

        tasa_nominal_anual = Math.pow((tasa_efectiva_anual+1), (1/factor)) * factor - factor;

        return (float) tasa_nominal_anual*100;
    }

    public float tasa_efectiva_anual(double tasa_nominal_anual, int meses){

        double tasa_efectiva_anual, factor;

        tasa_nominal_anual=tasa_nominal_anual/100;

        factor = 12/meses;

        tasa_efectiva_anual = Math.pow ((1 + (tasa_nominal_anual/factor)), factor) -1;

        return (float) tasa_efectiva_anual*100;
    }


}