package com.example.tablaquimica;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class InformacioElement extends AppCompatActivity {
    private cElement c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacio_element);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        c = (cElement) getIntent().getSerializableExtra("element");
        actionBar.setTitle(c.getNom());

        TextView tv = findViewById(R.id.tvSimbol);
        tv.setText(c.getSimbol());

        tv = findViewById(R.id.tvNum);
        tv.setText(String.valueOf(c.getNumero()));

        tv = findViewById(R.id.tvNom);
        tv.setText(c.getNom());

        tv = findViewById(R.id.tvMassa);
        tv.setText(String.valueOf(c.getMassa_Atomica()));

        tv = findViewById(R.id.tvEstat);
        tv.setText(c.get_estat().name());
        CambiarEstat(c,tv);

        tv = findViewById(R.id.tvNumberAtomic);
        tv.setText(c.get_nombreatomic().name().replaceAll("_"," "));
        AdaptadorElement.CambiarNAtomic(c,tv);

        tv = findViewById(R.id.tvElectronicConfiguration);
        tv.setText(c.getConfiguracioElectronica());

        tv = findViewById(R.id.tvElectrocAfinity);
        tv.setText(c.getElectronicAffinity());

        tv = findViewById(R.id.tvElectronNegativy);
        tv.setText(c.getElectronicNegativy());

        tv = findViewById(R.id.tvDensitat);
        tv.setText(String.valueOf(c.get_densitat()));

        tv = findViewById(R.id.tvAny);
        if(c.getAny() == 0){
            tv.setText("Ancient");
        }
        else{
            tv.setText(String.valueOf(c.getAny()));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menuelement, menu);
        return true;
    }

    public void CambiarEstat(cElement element,TextView edt){
        switch (element.get_estat()) {
            case SOLID:
                edt.setBackgroundColor(Color.parseColor("BLACK"));
                break;
            case LIQUID:
                edt.setBackgroundColor(Color.parseColor("#0B0B61"));
                break;
            case GAS:
                edt.setBackgroundColor(Color.parseColor("#246b1b"));
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.imgShare:
                Intent compartir = new Intent(android.content.Intent.ACTION_SEND);
                compartir.setType("text/plain");
                String mensaje = "Nom: " + c.getNom() + "\n" + "Simbol: " + c.getSimbol() + "\n" + "Estat: " + c.get_estat().name() + "\n" + "Nombre atomic: " + c.get_nombreatomic().name().replaceAll("_"," ");
                compartir.putExtra(android.content.Intent.EXTRA_TEXT, mensaje);
                startActivity(Intent.createChooser(compartir, "Compartir via..."));
                return true;
            case R.id.imgInfo:
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(c.getUrl()));
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}


