package com.example.crudsqllite_zaka;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class addForm extends AppCompatActivity {

    private long idTask = 0;
    Cursor _cursor;
    String _code;
    private GSA_Datasource _data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_form);

        androidx.appcompat.widget.Toolbar _toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(_toolbar);

        _data = new GSA_Datasource(this);

        idTask = this.getIntent().getExtras().getLong("code");

        if(idTask != -1) {
            _cursor = _data.SearchId(idTask);
            _cursor.moveToFirst();
            _code = _cursor.getString(_cursor.getColumnIndexOrThrow(_data.TABLE_COL1));
            _toolbar.setTitle(_code);
            update();
        }
    }

    public boolean onCreateOptionsMenu(Menu menu){
      getMenuInflater().inflate(R.menu.menu_elements,menu);
      if(idTask == -1){
          menu.getItem(1).setVisible(false);
      }
      return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.imgDelete:
                delete();
                break;
            case R.id.imgAccept:
                accept();
               break;
            case R.id.imgHome:
                cancel();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void accept(){
        TextView tv;

        String code;
        String description;
        Double pvp;
        int stock;

        Intent mIntent = new Intent();

        tv = (TextView) findViewById(R.id.edtDes);

        if(tv.getText().toString().isEmpty()){
            Snackbar.make(findViewById(R.id.form),"Has de poner descripcion.",Snackbar.LENGTH_SHORT).show();
            return;
        }
        else {
            description = tv.getText().toString();
        }

        tv = (TextView) findViewById(R.id.edtPVP);
        if(TryCatchDouble(tv.getText().toString().replaceAll(",","."))) {
            pvp = Double.valueOf(tv.getText().toString().replaceAll(",","."));
        }
        else{
            Snackbar.make(findViewById(R.id.form),"PVP: Has de poner un numero.",Snackbar.LENGTH_SHORT).show();
            return;
        }

        tv = (TextView) findViewById(R.id.edtStock);
        if(TryCatchInt(tv.getText().toString().replaceAll(",","."))) {
            stock = Integer.valueOf(tv.getText().toString().replaceAll(",","."));
        }
        else{
            Snackbar.make(findViewById(R.id.form),"Stock: Has de poner un numero.",Snackbar.LENGTH_SHORT).show();
            return;
        }

        if(pvp < 0){
            Log.d("numero",String.valueOf(stock));
            Snackbar.make(findViewById(R.id.form),"PVP: Has de poner un numero superior o igual a 0.",Snackbar.LENGTH_SHORT).show();
            return;
        }

        tv = (TextView) findViewById(R.id.edtCode);

        if(idTask == -1) {

            if(tv.getText().toString().isEmpty()) {
                Snackbar.make(findViewById(R.id.form),"El codigo esta vacio.",Snackbar.LENGTH_SHORT).show();
                return;
            }
            else  if (_data.SearhCodeAdd(tv.getText().toString())) {
                    Snackbar.make(findViewById(R.id.form),"El codigo ya esta en la base de datos.",Snackbar.LENGTH_SHORT).show();
                    return;
                }
            else if(stock < 0){
                Snackbar.make(findViewById(R.id.form),"Stock: Has de poner un numero superior o igual a 0.",Snackbar.LENGTH_SHORT).show();
                return;
            }
            else{
                code = tv.getText().toString();
            }

            idTask = _data.Add(code,description,pvp,stock);
            Snackbar.make(findViewById(R.id.form),"Se ha añadido correctamente",Snackbar.LENGTH_SHORT).show();

        }
        else{
            _data.Update(_code, description, pvp, stock);
            Snackbar.make(findViewById(R.id.form),"Se ha actualizado correctamente",Snackbar.LENGTH_SHORT).show();
            _cursor.close();
        }

        setResult(RESULT_OK, mIntent);
        mIntent.putExtra("id", idTask);
        finish();
    }

    public void update(){
        TextView tv;

        tv = (TextView) findViewById(R.id.textView);
        tv.setVisibility(View.GONE);
        tv = (TextView) findViewById(R.id.edtCode);
        //tv.setText(_cursor.getString(_cursor.getColumnIndexOrThrow(_data.TABLE_COL1)));

        tv.setVisibility(View.GONE);

        tv = (TextView) findViewById(R.id.edtDes);
        tv.setText(_cursor.getString(_cursor.getColumnIndexOrThrow(_data.TABLE_COL2)));

        tv = (TextView) findViewById(R.id.edtPVP);
        tv.setText(_cursor.getString(_cursor.getColumnIndexOrThrow(_data.TABLE_COL3)).replace(".",","));

        tv = (TextView) findViewById(R.id.edtStock);
        tv.setText(_cursor.getString(_cursor.getColumnIndexOrThrow(_data.TABLE_COL4)).replace(".",","));
        tv.setEnabled(false);
    }

    public void cancel(){
        Intent mIntent = new Intent();
        mIntent.putExtra("id", idTask);
        setResult(RESULT_CANCELED, mIntent);
        if(idTask != -1) {
            _cursor.close();
        }
        finish();
    }

    public void delete(){
        android.app.AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Estas seguro que deseas eliminar el registro?");
        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                _data.Delete(_code);

                Intent mIntent = new Intent();
                mIntent.putExtra("id", -1);
                setResult(RESULT_OK, mIntent);
                _cursor.close();
                finish();
            }
        });
        builder.setNegativeButton("No", null);
        builder.show();
    }

    public boolean TryCatchDouble(String _string){
        boolean a = true;

        try{
            Double.valueOf(_string);
        }
        catch (Exception e){
            a = false;
        }

        return a;
    }

    public boolean TryCatchInt(String _string){
        boolean a = true;

        try{
            Integer.valueOf(_string);
        }
        catch (Exception e){
            a = false;
        }

        return a;
    }
}
