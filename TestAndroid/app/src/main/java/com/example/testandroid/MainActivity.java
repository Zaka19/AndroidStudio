package com.example.testandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText edtMostar;
    private Button[] arrayButton = new Button[10];
    private Button btn1Numero;
    private Button btn2Numero;
    private Button btn3Numero;
    private Button btn4Numero;
    private Button btn5Numero;
    private Button btn6Numero;
    private Button btn7Numero;
    private Button btn8Numero;
    private Button btn9Numero;
    private Button btnCNumero;
    private Button btnBNumero;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtMostar = (EditText) findViewById(R.id.etMostar);
        btn1Numero = (Button) findViewById(R.id.btn1Numero);
        btn2Numero = (Button) findViewById(R.id.btn2Numero);
        btn3Numero = (Button) findViewById(R.id.btn3Numero);
        btn4Numero = (Button) findViewById(R.id.btn4Numero);
        btn5Numero = (Button) findViewById(R.id.btn5Numero);
        btn6Numero = (Button) findViewById(R.id.btn6Numero);
        btn7Numero = (Button) findViewById(R.id.btn7Numero);
        btn8Numero = (Button) findViewById(R.id.btn8Numero);
        btn9Numero = (Button) findViewById(R.id.btn9Numero);
        btnBNumero = (Button) findViewById(R.id.btnBNumero);
        btnCNumero = (Button) findViewById(R.id.btnCNumero);
        btn1Numero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Mostrar(btn1Numero.getText());
            }
        });

        btn2Numero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Mostrar(btn2Numero.getText());
            }
        });

        btn3Numero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Mostrar(btn3Numero.getText());
            }
        });

        btn4Numero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Mostrar(btn4Numero.getText());
            }
        });

        btn5Numero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Mostrar(btn5Numero.getText());
            }
        });

        btn6Numero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Mostrar(btn6Numero.getText());
            }
        });

        btn7Numero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Mostrar(btn7Numero.getText());
            }
        });

        btn8Numero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Mostrar(btn8Numero.getText());
            }
        });

        btn9Numero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Mostrar(btn9Numero.getText());
            }
        });

       btnCNumero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MostrarC();
            }
        });

        btnBNumero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Borrar();
            }
        });
    }

    private void Borrar() {
        try {
            String cadena = edtMostar.getText().toString();

            cadena = cadena.substring(0, cadena.length() - 1);

            edtMostar.setText(cadena);
        }
        catch (Exception e){
            Toast.makeText(this,
                    "Introdueix un numero.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void MostrarC() {
        try {
            edtMostar.setText("");
        }
        catch (Exception e){
            Toast.makeText(this,
                    "Introdueix un numero.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void Mostrar(CharSequence text) {
        try {
            CharSequence Total = edtMostar.getText() + "" + text;
            edtMostar.setText(Total);
        }
        catch (Exception e){
            Toast.makeText(this,
                    "Introdueix un numero.",
                    Toast.LENGTH_SHORT).show();
        }
    }


}
