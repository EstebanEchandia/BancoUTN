package com.grupo.bancoutn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.grupo.bancoutn.databinding.ActivitySimularPlazoFijoBinding;

public class SimularPlazoFijo extends AppCompatActivity {

    private ActivitySimularPlazoFijoBinding binding;

    private double TNA = 0;
    private double TEA = 0;
    private double capitalFinalSinRenovacion;
    private double capitalFinalConRenovacion;
    private double capitalInicial;
    private double interesesGanados;
    private int cantDiasMes = 30;
    private int diasPlazo;
    private int meses = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivitySimularPlazoFijoBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        TextView textoDias = binding.textoDias;
        SeekBar barraDias = binding.seekBar;
        barraDias.setMax(12);
        binding.montoTNA.setText(Double.toString(TNA));
        binding.montoTEA.setText(Double.toString(TEA));
        binding.montoCapital.setText("0");

        //inicializar
        barraDias.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            //seteo de la cantidad de dias del seekbar, se actualizan los datos cada vez que se cambia el valor
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                meses = i;
                diasPlazo = i*30;
                textoDias.setText("Días: " + diasPlazo);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        //Actualizar campos cuando se cambia el texto
        binding.montoTNA.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            };
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            };
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        binding.montoTEA.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            };
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {};
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        binding.montoCapital.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {};
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {};
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        binding.confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // validar que el texto no este vacio, el capital no sea 0 o los meses sean 0
                    if(!binding.montoTNA.getText().toString().equals("") && !binding.montoTEA.getText().toString().equals("") && !binding.montoCapital.getText().toString().equals("")
                    && !binding.montoCapital.getText().toString().equals("0") && meses != 0
                    ){
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
        EditText editTextTNA = binding.montoTNA;

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

        if ( binding.montoCapital.getText().toString().equals("")){
            capitalInicial = 0;
        }
        else{
            capitalInicial = Float.parseFloat(binding.montoCapital.getText().toString());
        }
        if (false){
            interesesGanados = 0;
            capitalFinalSinRenovacion = capitalInicial;
            capitalFinalConRenovacion = capitalInicial;
        }
        else{
            interesesGanados = ((TNA / 100) / 12) * meses * capitalInicial;
            capitalFinalSinRenovacion = interesesGanados + capitalInicial;
            capitalFinalConRenovacion = capitalInicial * (TEA / 100) + capitalInicial;
        }
    }

    public double tasaNominalAnual(double tasaEfectivaAnual, int meses){
        double tasaNominalAnual, factor;
        tasaEfectivaAnual = tasaEfectivaAnual/100;
        factor = 12/meses;
        tasaNominalAnual = Math.pow((tasaEfectivaAnual+1), (1/factor)) * factor - factor;
        return tasaNominalAnual*100;
    }

    public double tasaEfectivaAnual(double tasaNominalAnual, int meses){
        if(meses==0) return 0;
        double tasaEfectivaAnual, factor;
        tasaNominalAnual=tasaNominalAnual/100;
        factor = 12/meses;
        tasaEfectivaAnual = Math.pow ((1 + (tasaNominalAnual/factor)), factor) -1;
        return tasaEfectivaAnual*100;
    }

}