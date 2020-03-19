package com.example.intentsimplicits;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URI;

public class MainActivity extends AppCompatActivity {
    private Button btnActivity;
    private TextView tvnum;
    private Button btnIncrement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvnum = findViewById(R.id.tvNum);
        btnIncrement = findViewById(R.id.buttonIncrement);
        btnIncrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AutoSum();
            }
        });

        btnActivity = findViewById(R.id.btnWeb);
        btnActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.animeflv.net"));
                startActivity(intent);
            }
        });

        btnActivity = findViewById(R.id.btnTelefono);
        btnActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:631140866"));
                startActivity(intent);
            }
        });

        btnActivity = findViewById(R.id.btnMaps);
        btnActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:41.386780, 2.174125"));
                startActivity(intent);
            }
        });

        btnActivity = findViewById(R.id.btnFoto);
        btnActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivity(intent);
            }
        });

        btnActivity = findViewById(R.id.btnCorreo);
        btnActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT, "asunto");
                intent.putExtra(Intent.EXTRA_TEXT, "texto del correo");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[] {"zzbakh444@gmail.com"});
                startActivity(intent);
            }
        });
    }

    private void AutoSum(){
        int num = Integer.valueOf(tvnum.getText().toString());
        tvnum.setText(String.valueOf(num + 1));
    }

    @Override protected void onStart(){
        super.onStart();
        Toast.makeText(this,"onStart",Toast.LENGTH_SHORT).show();
    }

    @Override protected void onResume(){
        super.onResume();
        Toast.makeText(this,"onResume",Toast.LENGTH_SHORT).show();
    }

    @Override protected void onPause(){
        super.onPause();
        Toast.makeText(this,"onPause",Toast.LENGTH_SHORT).show();
    }

    @Override protected void onStop(){
        super.onStop();
        Toast.makeText(this,"onStop",Toast.LENGTH_SHORT).show();
    }

    @Override protected void onRestart(){
        super.onRestart();
        Toast.makeText(this,"OnRestart",Toast.LENGTH_SHORT).show();
    }

    @Override protected void onDestroy(){
        super.onDestroy();
        Toast.makeText(this,"onDestroy",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("resultat",tvnum.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle inState) {
        super.onRestoreInstanceState(inState);
        tvnum.setText(inState.getString("resultat"));
    }
}
