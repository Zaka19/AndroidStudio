package com.example.tablaquimica;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.MenuItemCompat;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ConstraintLayout layout;
    private MediaPlayer song;
    private TextView tvintro;
    private AlphaAnimation fadeIn;
    private AlphaAnimation fadeOut;

    /*
    private cElement[] Elements = new cElement[]{
            new cElement("H", "Hidrogen", 1, 1.00794, ESTATS.GAS, NOMBREATOMIC.NO_METALLS, 1,1,"s","1s1",1,"https://ca.wikipedia.org/wiki/Hidrogen"),
            new cElement("He", "Heli", 2, 4.002602, ESTATS.GAS, NOMBREATOMIC.GASOS_NOBLES, 18,1,"s","1s2",2,"https://ca.wikipedia.org/wiki/Heli"),
            new cElement("Li", "Liti", 3, 6.941, ESTATS.SOLID, NOMBREATOMIC.METALLS_ALCALINS, 1,2,"s","1s2 2s1",2,"https://ca.wikipedia.org/wiki/Heli"),
    };
    */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        tvintro = (TextView) findViewById(R.id.tvInfo);
        tvintro.setBackgroundColor(Color.parseColor("#DF01D7"));
        layout = (ConstraintLayout) findViewById(R.id.layout);

        Animacion();

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),Main2Activity.class);
                startActivity(intent);
            }
        });
    }

    public void Animacion(){
        fadeIn = new AlphaAnimation(0.0f,1.0f);
        fadeIn.setDuration(500);
        fadeIn.setStartOffset(1);
        fadeIn.setFillAfter(true);

        fadeOut = new AlphaAnimation(1.0f,0.0f);
        fadeOut.setDuration(500);
        fadeOut.setStartOffset(1);
        fadeOut.setFillAfter(true);

        fadeIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                tvintro.setText(tvintro.getText());
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                tvintro.startAnimation(fadeOut);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                tvintro.startAnimation(fadeIn);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        tvintro.startAnimation(fadeIn);
    }
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Game:

                Intent intent = new Intent(this, Game.class);
                intent.putExtra("element",Elements);
                startActivity(intent);
                return true;
            case R.id.Tots:
                MostrarLlista(Elements);
                return true;
            case R.id.Liquids:
                MostrarLlista(newList(Elements, ESTATS.LIQUID));
                return true;
            case R.id.Gasos:
                MostrarLlista(newList(Elements, ESTATS.GAS));
                return true;
            case R.id.Solids:
                MostrarLlista(newList(Elements, ESTATS.SOLID));
                return true;
            case R.id.Sintetics:
                MostrarLlista(newList(Elements, ESTATS.SINTETIC));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /* public void Add() {
        Elements.add(new cElement("H", "Hidrogen", 1, 1.00794, ESTATS.GAS, NOMBREATOMIC.NO_METALLS, 1, 1, "s", "1s1", 1, "https://ca.wikipedia.org/wiki/Hidrogen"));
        Elements.add(new cElement("He", "Heli", 2, 4.002602, ESTATS.GAS, NOMBREATOMIC.GASOS_NOBLES, 18, 1, "s", "1s2", 2, "https://ca.wikipedia.org/wiki/Heli"));
        Elements.add(new cElement("Li", "Liti", 3, 6.941, ESTATS.SOLID, NOMBREATOMIC.METALLS_ALCALINS, 1, 2, "s", "1s2 2s1", 2, "https://ca.wikipedia.org/wiki/Liti"));
        Elements.add(new cElement("Be", "Beril·li", 4, 9.012182, ESTATS.SOLID, NOMBREATOMIC.ALCALINOTERRIS, 2, 2, "s", "1s2 2s2", 2, "https://ca.wikipedia.org/wiki/Beril·li"));
        Elements.add(new cElement("B", "Bor", 5, 10.811, ESTATS.SOLID, NOMBREATOMIC.METALOIDES, 13, 2, "p", "2s2 2p1", 3, "https://ca.wikipedia.org/wiki/Bor"));
        Elements.add(new cElement("C", "Carboni", 6, 12.0107, ESTATS.SOLID, NOMBREATOMIC.NO_METALLS, 14, 2, "p", "1s2 2s2 2p2", 4, "https://ca.wikipedia.org/wiki/Carboni"));
        Elements.add(new cElement("N", "Nitrogen", 7, 14.0067, ESTATS.GAS, NOMBREATOMIC.NO_METALLS, 15, 2, "p", "1s2 2s2 2p3", 4, "https://ca.wikipedia.org/wiki/Nitrogen"));
        Elements.add(new cElement("O", "Oxigen", 8, 15.9994, ESTATS.GAS, NOMBREATOMIC.NO_METALLS, 16, 2, "p", "1s2 2s2 2p4", 6, "https://ca.wikipedia.org/wiki/Oxigen"));
        Elements.add(new cElement("F", "Fluor", 9, 18.9984032, ESTATS.GAS, NOMBREATOMIC.HALOGENS, 17, 2, "p", "1s2 2s2 2p5", 7, "https://ca.wikipedia.org/wiki/Fluor"));
        Elements.add(new cElement("Ne", "Neó", 10, 20.1797, ESTATS.GAS, NOMBREATOMIC.METALLS_ALCALINS, 18, 2, "p", "1s2 2s2 2p6", 8, "https://ca.wikipedia.org/wiki/Neó"));
    } */
/*
    public void MostrarLlista(ArrayList<cElement> elets) {
        element = new AdaptadorElement(this, elets);
        ListView lst = (ListView) findViewById(R.id.listBasic);
        lst.setAdapter(element);
    }

    public ArrayList<cElement> newList(ArrayList<cElement> elements, ESTATS estat) {
        ArrayList<cElement> newList = new ArrayList<cElement>();

        int x = 0;

        for(int a = 0;a < elements.size();a++){
           if(elements.get(a).get_estat() == estat){
               newList.add(elements.get(a));
           }
        }
        return newList;
    }
    */
}

/* class AdaptadorElement extends ArrayAdapter<cElement> {

    private Context context;

    public AdaptadorElement(Context context, ArrayList<cElement> elements) {
        super(context, R.layout.elements, elements);
        this.context = context;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View item = inflater.inflate(R.layout.elements, null);

        final cElement element = (cElement) getItem(position);

        TextView edt = (TextView) item.findViewById(R.id.edtNum);
        edt.setText(String.valueOf(element.getNumero()));

        CambiarEstat(element,edt);

         nom
        edt = (TextView) item.findViewById(R.id.edtNom);
        edt.setText(element.getNom());

        TextView simbol
        edt = (TextView) item.findViewById(R.id.edtSimbol);
        edt.setText(element.getSimbol());
        edt.setTextColor(Color.parseColor("#3a5bb5"));
        TextView massa
        edt = (TextView) item.findViewById(R.id.edtMassa);
        edt.setText(String.valueOf(element.getMassa_Atomica()));

        CambiarNAtomic(element,item);

        item.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Nova Activity
                Intent intent = new Intent(v.getContext(), InformacioElement.class);
                intent.putExtra("element",element);
                context.startActivity(intent);
            }
        });

        return(item);
    }

    public void CambiarEstat(cElement element,TextView edt){
        switch (element.get_estat()) {
            case SOLID:
                edt.setTextColor(Color.parseColor("BLACK"));
                break;
            case LIQUID:
                edt.setTextColor(Color.parseColor("#0B0B61"));
                break;
            case GAS:
                edt.setTextColor(Color.parseColor("#246b1b"));
                break;
            case SINTETIC:
                edt.setTextColor(Color.parseColor("#8A0808"));
                break;
        }
    }

    public void CambiarNAtomic(cElement element,View item){
        switch (element.get_nombreatomic()) {
            case METALLS_ALCALINS:
                item.setBackgroundColor(Color.parseColor("#FA5858"));
                break;
            case ALCALINOTERRIS:
                item.setBackgroundColor(Color.parseColor("#F7BE81"));
                break;
            case LANTADIS:
                item.setBackgroundColor(Color.parseColor("#F7819F"));
                break;
            case ACTINIDS:
                item.setBackgroundColor(Color.parseColor("#FA58AC"));
                break;
            case METALLS_DE_TRANSICIO:
                item.setBackgroundColor(Color.parseColor("#F5A9BC"));
                break;
            case METALLS_DEL_BLOC_P:
                item.setBackgroundColor(Color.parseColor("#A4A4A4"));
                break;
            case METALOIDES:
                item.setBackgroundColor(Color.parseColor("#222A0A"));
                break;
            case NO_METALLS:
                item.setBackgroundColor(Color.parseColor("#58FAAC"));
                break;
            case HALOGENS:
                item.setBackgroundColor(Color.parseColor("#F4FA58"));
                break;
            case GASOS_NOBLES:
                item.setBackgroundColor(Color.parseColor("#81F7F3"));
                break;
        }
    }
    }
    */





