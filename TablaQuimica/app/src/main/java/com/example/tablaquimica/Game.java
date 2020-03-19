package com.example.tablaquimica;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.google.android.material.snackbar.Snackbar;

import java.util.Collections;
import java.util.ArrayList;
import java.util.List;

public class Game extends AppCompatActivity {

    private ArrayList<cElement> Elements = new ArrayList<>();
    private TextView tv;
    private EditText ed;
    private Button btn;
    private int aRecord = 0;
    private int Punts = 0;
    private SharedPreferences myPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        setTitle("Game");

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        RecuperarRecord();

        Elements = (ArrayList<cElement>) getIntent().getSerializableExtra("elements");

        btn = (Button) findViewById(R.id.btnNext);
        ed = (EditText) findViewById(R.id.edtName);

        Random();
        Quest();
        GameGame();
    }

    public void Random(){
        Collections.shuffle(Elements);
    }

    public void GameGame(){
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ed.getText().toString().replaceAll(" ","").toUpperCase().equals(Elements.get(Punts).getNom().toUpperCase())) {
                    Punts++;

                    if (Punts > aRecord) {
                        aRecord = Punts;
                    }

                    if(Punts == Elements.size()){
                        saveRecord();
                        Alert(v.getContext(),"Congratulations you finish the game!!!",R.drawable.nice);
                    }
                    else {
                        Good(v);
                        Quest();
                    }
                }
                else{
                    saveRecord();
                    Alert(v.getContext(),"You lose...",R.drawable.losee);
                }
            }
        });
    }

    public void Sleep(){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 2000);
    }

    public void Good(View v){
        Snackbar sn = Snackbar.make(v, "Good job!!!", Snackbar.LENGTH_SHORT);
        View view = sn.getView();
        view.setBackgroundColor(Color.parseColor("#1ac21c"));
        sn.show();
    }

    public void Quest(){
        tv = (TextView) findViewById(R.id.tvPoints);
        tv.setText("Points: " + String.valueOf(Punts));

        tv = (TextView) findViewById(R.id.tvRound);
        tv.setText("Round: " + String.valueOf(Punts+1));

        tv = (TextView) findViewById(R.id.tvRecord);
        tv.setText("Record: " + String.valueOf(aRecord));

        tv = (TextView) findViewById(R.id.tvM);
        tv.setText(Elements.get(Punts).getSimbol());

        ed.setText("");
    }

    public void Alert(Context v, String Result, int image){
        AlertDialog ad;
        ad = new AlertDialog.Builder(v).create();
        ad.setTitle(Result);
        ad.setIcon(image);
        ad.setMessage("Points: " + Punts + "\n" + "Record: " + aRecord);
        ad.show();
        Sleep();
    }

    public void saveRecord(){
        SharedPreferences.Editor myEditor = myPreferences.edit();
        myEditor.putInt("Record", aRecord);
        myEditor.commit();
    }

    public void RecuperarRecord(){
        myPreferences = getSharedPreferences("MyPrefence",Context.MODE_PRIVATE);
        aRecord = myPreferences.getInt("Record", 0);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
