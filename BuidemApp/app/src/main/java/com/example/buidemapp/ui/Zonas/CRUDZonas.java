package com.example.buidemapp.ui.Zonas;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.buidemapp.Db_AppBuidem.Datasource;
import com.example.buidemapp.R;
import com.example.buidemapp.SpinnerSearchPos;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class CRUDZonas extends AppCompatActivity {

    private long id;

    private Datasource _data;

    private FloatingActionButton _addOrUpdate;
    private FloatingActionButton _delete;

    private Spinner _edtName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud_zonas);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        _data = new Datasource(this);

        _addOrUpdate = (FloatingActionButton) findViewById(R.id.faAddZ);
        _delete = (FloatingActionButton) findViewById(R.id.faDeleteZ);
        _edtName = (Spinner) findViewById(R.id.edtNameZ);

        id = this.getIntent().getExtras().getLong("CRUD");

        /*ArrayAdapter<String> adapter = null;
        try {
            adapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item, Search());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        _edtName.setAdapter(adapter);*/

        try {
            _edtName.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Search()));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        _addOrUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addOrUpdate();
            }
        });

        _delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Delete(id);
            }
        });

        if(id > 0){
            try {
                InputDataResult();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else{
            setTitle("Añadir nueva zona");
            _delete.hide();
        }
    }

    private void addOrUpdate() {
        String _name = "";

        Intent mIntent = new Intent();

        if(!_edtName.getSelectedItem().toString().isEmpty()){
            _name = _edtName.getSelectedItem().toString();
        }
        else{
            return;
        }

        if(id < 0){
            if(_data.SearchNameEqualsZona(_edtName.getSelectedItem().toString())){
                Snackbar snackBar = Snackbar.make(this.findViewById(android.R.id.content),
                        "Tienes que poner nombre que no se haya puesto.", Snackbar.LENGTH_SHORT);
                snackBar.show();
                return;
            }
            id = _data.PostZonas(_name);
        }
        else{
            _data.PutZonas(id, _name);
        }

        setResult(RESULT_OK, mIntent);
        mIntent.putExtra("id", id);
        finish();
    }

    private void InputDataResult() throws JSONException {
        String name = "";

        Cursor _cursorToUpdate = _data.SearchByIdZone(id);
        _cursorToUpdate.moveToFirst();

        name = _cursorToUpdate.getString(_cursorToUpdate.getColumnIndexOrThrow(Datasource.ZONAS_NOMBRE));

        setTitle(_cursorToUpdate.getString(_cursorToUpdate.getColumnIndexOrThrow(Datasource.ZONAS_NOMBRE)));

        ArrayList<String> _lista = Search();

        _edtName.setSelection(SpinnerSearchPos.SearchPos(_lista, name));

        _cursorToUpdate.close();
    }

    private void Delete(final long _id){
        Cursor _cursorToDelete = _data.SearchZoneInMachine(id);
        _cursorToDelete.moveToFirst();

        android.app.AlertDialog.Builder builder = new AlertDialog.Builder(this);

        if(_cursorToDelete.getCount() <= 0){
            builder.setMessage("Estas seguro que deseas eliminar la zona?");
            builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    _data.DeleteZonas(_id);

                    Intent mIntent = new Intent();
                    mIntent.putExtra("id", -1);
                    setResult(RESULT_OK, mIntent);
                    finish();
                }
            });
            builder.setNegativeButton("No", null);
        }
        else{
            builder.setMessage("No puedes eliminar esta zona porque esta en uso.");
            builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    return;
                }
            });
        }

        builder.show();
        _cursorToDelete.close();
    }

    public ArrayList<String> Search() throws JSONException {
        Resources res = getResources();
        InputStream is = res.openRawResource(R.raw.geo);
        Scanner sc = new Scanner(is);
        StringBuilder sb = new StringBuilder();

        while (sc.hasNextLine()) {
            sb.append(sc.nextLine());
        }

        JSONObject object = new JSONObject(sb.toString());
        JSONObject object1 = object.getJSONObject("feed");
        JSONArray JSONcities = object1.getJSONArray("entry");
        Log.d("Lista", JSONcities.toString());
        ArrayList<String> cities = new ArrayList<>();

        for (int i = 0; i < JSONcities.length(); i++) {
            JSONObject jsonObject = (JSONObject) JSONcities.get(i);
            cities.add(jsonObject.getString("title"));
        }
        Log.d("Lista1", cities.toString());
        return cities;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
