package com.example.miprimeraapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText edtNumero;
    private Button btnNumero;
    private EditText edtMajuscules;
    private Button btnMajucules;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtNumero = (EditText) findViewById(R.id.edtNumero);
        btnNumero = (Button) findViewById(R.id.btnNumero);
        edtMajuscules = (EditText) findViewById(R.id.edtMajuscules);
        btnMajucules = (Button) findViewById(R.id.btnMajucules);

        btnNumero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incrementar();
            }
        });

        btnMajucules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Majucules();
            }
        });
    }

    private void Majucules() {
        String Majucules;
        try{
            Majucules = edtMajuscules.getText().toString();
            edtMajuscules.setText(String.valueOf(Majucules.toUpperCase()));
        }
        catch(Exception e){
            Toast.makeText(this,
                    "T'he demanat un nom",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void incrementar(){
       int valor;
       try {
           valor = Integer.valueOf(edtNumero.getText().toString());
           valor++;
           edtNumero.setText(String.valueOf(valor));
       }
       catch (Exception e){
           Toast.makeText(this,
                    "T'he demanat un numero",
                    Toast.LENGTH_SHORT).show();
       }
    }
}
