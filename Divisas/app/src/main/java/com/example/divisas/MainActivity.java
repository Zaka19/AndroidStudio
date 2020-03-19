package com.example.divisas;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    private TextView tv1;
    private TextView tv2;

    private Button btnNumber;

    private Button btnMonedaLL;
    private Button btnMonedaDo;
    private Button btnMonedaYe;
    private Button btnMonedaYu;

    private int seleccion = 0;
    double[] Monedas = {0,0,0,0};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv1 = (TextView) findViewById(R.id.tvFirst);
        tv2 = (TextView) findViewById(R.id.tvSecond);

        btnNumber = (Button) findViewById(R.id.btnNumber0);
        btnNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Mostrar("0");
            }
        });

        btnNumber = (Button) findViewById(R.id.btnNumber1);
        btnNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Mostrar("1");
            }
        });

        btnNumber = (Button) findViewById(R.id.btnNumber2);
        btnNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Mostrar("2");
            }
        });

        btnNumber = (Button) findViewById(R.id.btnNumber3);
        btnNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Mostrar("3");
            }
        });

        btnNumber = (Button) findViewById(R.id.btnNumber4);
        btnNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Mostrar("4");
            }
        });

        btnNumber = (Button) findViewById(R.id.btnNumber5);
        btnNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Mostrar("5");
            }
        });

        btnNumber = (Button) findViewById(R.id.btnNumber6);
        btnNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Mostrar("6");
            }
        });

        btnNumber = (Button) findViewById(R.id.btnNumber7);
        btnNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Mostrar("7");
            }
        });

        btnNumber = (Button) findViewById(R.id.btnNumber8);
        btnNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Mostrar("8");
            }
        });

        btnNumber = (Button) findViewById(R.id.btnNumber9);
        btnNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Mostrar("9");
            }
        });

        btnNumber = (Button) findViewById(R.id.btnBorrar);
        btnNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Borrar();
            }
        });

        btnNumber = (Button) findViewById(R.id.btnClear);
        btnNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MostrarC();
            }
        });

        btnNumber = (Button) findViewById(R.id.btnComa);
        btnNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MostrarComa(",");
            }
        });

        btnNumber = (Button) findViewById(R.id.btnResult);
        btnNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Final(seleccion);
            }
        });

        btnMonedaLL = (Button) findViewById(R.id.btnMonedaLL);
        btnMonedaLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(seleccion == 0) {
                    MonedasDecimales(1,btnMonedaLL);
                }
                else{
                    Reset();
                    MonedasDecimales(1,btnMonedaLL);
                }
            }
        });

        btnMonedaDo = (Button) findViewById(R.id.btnMonedaDo);
        btnMonedaDo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(seleccion == 0) {
                    MonedasDecimales(2,btnMonedaDo);
                }
                else{
                    Reset();
                    MonedasDecimales(2,btnMonedaDo);
                }
            }
        });

        btnMonedaYe = (Button) findViewById(R.id.btnMonedaYe);
        btnMonedaYe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(seleccion == 0) {
                    MonedasDecimales(3,btnMonedaYe);
                }
                else{
                    Reset();
                    MonedasDecimales(3,btnMonedaYe);
                }
            }
        });

        btnMonedaYu = (Button) findViewById(R.id.btnMonedaYu);
        btnMonedaYu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(seleccion == 0) {
                    MonedasDecimales(4,btnMonedaYu);
                }
                else{
                    Reset();
                    MonedasDecimales(4,btnMonedaYu);
                }

            }
        });
    }

    private void Reset(){
        btnMonedaLL.setBackgroundColor(Color.parseColor("#0D3CB4"));
        btnMonedaDo.setBackgroundColor(Color.parseColor("#0D3CB4"));
        btnMonedaYe.setBackgroundColor(Color.parseColor("#0D3CB4"));
        btnMonedaYu.setBackgroundColor(Color.parseColor("#0D3CB4"));

        seleccion = 0;
    }

    private void MostrarComa(String punt){
        String a = tv1.getText().toString();
        boolean b = false;
        int x = 0;

        while(x < a.length() && b == false){
            if(a.charAt(x) == ','){
                b = true;
            }
            x++;
        }

        if(b == false){
            Mostrar(punt);
        }
    }

    private void Mostrar(String num){
        if(tv1.getText().toString().indexOf(",") >= 0) {
            if(tv1.getText().toString().substring(tv1.getText().toString().indexOf(",")).length() <= 2){
                String s = tv1.getText() + "" + num;
                tv1.setText(s);
            }
        }
        else{
            if(num.equals(",")){
                String s = tv1.getText() + "" + num;
                tv1.setText(s);
            }
            else if(tv1.getText().equals("0")){
                String s = "" + num;
                tv1.setText(s);
            }
            else{
                String s = tv1.getText() + "" + num;
                tv1.setText(s);
            }
        }
    }

    private void Borrar(){
        String cadena = tv1.getText().toString();

        cadena = cadena.substring(0, cadena.length() - 1);

        if(cadena.isEmpty()){
            tv1.setText("0");
        }
        else {
            tv1.setText(cadena);
        }
    }

    private void MostrarC() {
        tv1.setText("0");
        tv2.setText("0");
    }

    private void Final(int seleccion){
        if(seleccion != 0){
            String a = CalculoFinal(Monedas[seleccion - 1]);
            if(Double.valueOf((a.replaceAll(",","."))) >= 1) {
                tv2.setText(a);
            }
            else if(a.equals("0")){
                tv2.setText(a);
            }
            else{
                String b = 0 + a;
                tv2.setText(b);
            }
        }
        else{
            Toast.makeText(getApplicationContext(), "Tienes que seleccionar una moneda.", Toast.LENGTH_LONG).show();
        }
    }

    private void MonedasDecimales(final int moneda,final Button btnMoneda) {
        if(Monedas[moneda-1] == 0) {
            AlertDialog ad;
            ad = new AlertDialog.Builder(this).create();
            ad.setTitle("Moneda");
            ad.setMessage("Valor de la moneda");

            final EditText edtValor = new EditText(this);
            ad.setView(edtValor);
            ad.setButton(AlertDialog.BUTTON_POSITIVE, "Aceptar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    String UserNumber = edtValor.getText().toString().replaceAll(",",".");
                    if(ComprovacionAlert(UserNumber,moneda-1)){
                        btnMoneda.setBackgroundColor(0xFF00FF00);
                        seleccion = moneda;
                    }
                }
            });
            ad.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancelar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {

                }
            });
            ad.show();

        }
        else{
            seleccion = moneda;
            btnMoneda.setBackgroundColor(0xFF00FF00);
        }
    }

/*
    private void MonedasDecimalesLL() {
        if(Monedas[0] == 0) {
            AlertDialog ad;
            ad = new AlertDialog.Builder(this).create();
            ad.setTitle("Moneda");
            ad.setMessage("Valor de la moneda");

            final EditText edtValor = new EditText(this);
            ad.setView(edtValor);
            ad.setButton(AlertDialog.BUTTON_POSITIVE, "Aceptar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    String UserNumber = edtValor.getText().toString().replaceAll(",",".");
                    if(ComprovacionAlert(UserNumber,0)){
                        seleccion = 1;
                    }
                }
            });
            ad.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancelar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {

                }
            });
            ad.show();

        }
        else{
            seleccion = 1;
            btnMonedaLL.setBackgroundColor(0xFF00FF00);
        }
}

    private void MonedasDecimalesDo() {
        if(Monedas[1] == 0) {
            AlertDialog ad;
            ad = new AlertDialog.Builder(this).create();
            ad.setTitle("Moneda");
            ad.setMessage("Valor de la moneda");

            final EditText edtValor = new EditText(this);
            ad.setView(edtValor);
            ad.setButton(AlertDialog.BUTTON_POSITIVE, "Aceptar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    String UserNumber = edtValor.getText().toString().replaceAll(",",".");
                    if(ComprovacionAlert(UserNumber,1)){
                        seleccion = 2;
                    }
                }
            });
            ad.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancelar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {

                }
            });
            ad.show();
        }
        else{
            seleccion = 2;
            btnMonedaDo.setBackgroundColor(0xFF00FF00);
        }
    }

    private void MonedasDecimalesYe() {
        if(Monedas[2] == 0) {
            AlertDialog ad;
            ad = new AlertDialog.Builder(this).create();
            ad.setTitle("Moneda");
            ad.setMessage("Valor de la moneda");

            final EditText edtValor = new EditText(this);
            ad.setView(edtValor);
            ad.setButton(AlertDialog.BUTTON_POSITIVE, "Aceptar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    String UserNumber = edtValor.getText().toString().replaceAll(",",".");
                    if(ComprovacionAlert(UserNumber,2)){
                        seleccion = 3;
                    }
                }
            });
            ad.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancelar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {

                }
            });
            ad.show();
        }
        else{
            seleccion = 3;
            btnMonedaYe.setBackgroundColor(0xFF00FF00);
        }
    }

    private void MonedasDecimalesYu() {
        if(Monedas[3] == 0) {
            AlertDialog ad;
            ad = new AlertDialog.Builder(this).create();
            ad.setTitle("Moneda");
            ad.setMessage("Valor de la moneda");

            final EditText edtValor = new EditText(this);
            ad.setView(edtValor);
            ad.setButton(AlertDialog.BUTTON_POSITIVE, "Aceptar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    String UserNumber = edtValor.getText().toString().replaceAll(",",".");
                    if(ComprovacionAlert(UserNumber,3)){
                        seleccion = 4;
                    }
                }
            });
            ad.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancelar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {

                }
            });
            ad.show();
        }
        else{
            seleccion = 4;
            btnMonedaYu.setBackgroundColor(0xFF00FF00);
        }
    }

*/

    private String CalculoFinal(double conversor){
        DecimalFormat formato1 = new DecimalFormat("#.00");
        double value;
        int value1 = 0;
        String Final = formato1.format(Double.valueOf(tv1.getText().toString().replaceAll(",",".")) * conversor);
        if(Final.substring(Final.indexOf(",")).equals(",00")){
            value = Double.valueOf(Final.replaceAll(",","."));
            value1 = (int) value;
            Final = String.valueOf(value1);
            Log.d("final", String.valueOf(value1));
        }
        return Final;
    }

    private boolean ComprovacionAlert(String edtValor,int moneda){
        boolean a = false;
        try {
            Monedas[moneda] = Double.valueOf(edtValor);
            a = true;
            Toast.makeText(getApplicationContext(), "Se ha introducido correctamente el valor." + " " + edtValor, Toast.LENGTH_LONG).show();
        }
        catch (Exception e){
            Monedas[moneda] = 0;
            Toast.makeText(getApplicationContext(), "No se ha introducido correctamente el valor." + " " + edtValor, Toast.LENGTH_LONG).show();
        }
        return a;
    }

}
