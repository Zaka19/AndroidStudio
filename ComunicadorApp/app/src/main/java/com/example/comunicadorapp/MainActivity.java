package com.example.comunicadorapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private EditText edtName;
    private Button btnName;
    private TextView tvResultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtName = findViewById(R.id.edtName);
        btnName = findViewById(R.id.btnName);
        btnName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Main2Activity.class);
                intent.putExtra("name",edtName.getText().toString());
                startActivityForResult(intent,1234);
            }
        });

        tvResultado = findViewById(R.id.tvResultado);
    }

    @Override protected void onActivityResult (int requestCode,
        int resultCode, Intent data) {
        if (requestCode == 1234) {
            if( resultCode == RESULT_OK) {
                tvResultado.setText(" Acceptado");
            }
            else{
                tvResultado.setText(" Rechazado");
            }
        }
    }
}
