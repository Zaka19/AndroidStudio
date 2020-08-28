package com.example.buidemapp.ui.TipoMaquinas;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.buidemapp.ColorPicker;
import com.example.buidemapp.Db_AppBuidem.Datasource;
import com.example.buidemapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class CRUDTipoMaquinas extends AppCompatActivity {

    private long id;

    private Datasource _data;
    private ColorPicker _color;

    private FloatingActionButton _addOrUpdate;
    private FloatingActionButton _delete;

    private EditText _edtName;
    private EditText _edtColor;
    private TextView _tvColorSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud_typemachine);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        _data = new Datasource(this);
        _color = new ColorPicker();

        _addOrUpdate = (FloatingActionButton) findViewById(R.id.faAddTM);
        _delete = (FloatingActionButton) findViewById(R.id.faDeleteTM);
        _edtName = (EditText) findViewById(R.id.edtNameTM);
        _edtColor = (EditText) findViewById(R.id.edtColorTM);
        _tvColorSelected = (TextView) findViewById(R.id.tvShowColorSelected);

        id = this.getIntent().getExtras().getLong("CRUD");

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

        _edtColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _color.ColorDialog(v.getContext(), _edtColor, _tvColorSelected);
            }
        });

        if(id > 0){
            InputDataResult();
        }
        else{
            setTitle("Añadir nuevo tipo de maquina");
            _delete.hide();
        }
    }

    private void addOrUpdate() {
        String _name = "";
        String _color = "";

        Intent mIntent = new Intent();

        if(!_edtName.getText().toString().isEmpty()){
            _name = _edtName.getText().toString();
        }
        else{
            return;
        }

        if(!_edtColor.getText().toString().isEmpty()){
            _color = _edtColor.getText().toString();
        }
        else{
            return;
        }

        if(id < 0){
            if(_data.SearchNameEqualsTipoMaquinas(_edtName.getText().toString())){
                Snackbar snackBar = Snackbar.make(this.findViewById(android.R.id.content),
                        "Tienes que poner un nombre que no se haya introducido", Snackbar.LENGTH_SHORT);
                snackBar.show();
                return;
            }

            id = _data.PostTiposMaquinas(_name, _color);
        }
        else{
            _data.PutTipoMaquinas(id, _name, _color);
        }

        setResult(RESULT_OK, mIntent);
        mIntent.putExtra("id", id);
        finish();
    }

    private void InputDataResult(){
        Cursor _cursorToUpdate = _data.SearchByIdTypeMachine(id);
        _cursorToUpdate.moveToFirst();

        setTitle(_cursorToUpdate.getString(_cursorToUpdate.getColumnIndexOrThrow(Datasource.TIPOMAQUINAS_NOMBRE)));

        _edtName.setText(_cursorToUpdate.getString(_cursorToUpdate.getColumnIndexOrThrow(Datasource.TIPOMAQUINAS_NOMBRE)));
        _edtColor.setText(_cursorToUpdate.getString(_cursorToUpdate.getColumnIndexOrThrow(Datasource.TIPOMAQUINAS_COLOR)));
        _edtColor.setTextColor(Color.parseColor(_cursorToUpdate.getString(_cursorToUpdate.getColumnIndexOrThrow(Datasource.TIPOMAQUINAS_COLOR))));
        _tvColorSelected.setBackgroundColor(Color.parseColor(_cursorToUpdate.getString(_cursorToUpdate.getColumnIndexOrThrow(Datasource.TIPOMAQUINAS_COLOR))));

        _cursorToUpdate.close();
    }

    private void Delete(final long _id){
        Cursor _cursorToDelete = _data.SearchTypeMachineInMachine(id);
        _cursorToDelete.moveToFirst();
        android.app.AlertDialog.Builder builder = new AlertDialog.Builder(this);

        if(_cursorToDelete.getCount() <= 0){
            builder.setMessage("Estas seguro que deseas eliminar el tipo de maquina?");
            builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    _data.DeleteTipoMaquinas(_id);

                    Intent mIntent = new Intent();
                    mIntent.putExtra("id", -1);
                    setResult(RESULT_OK, mIntent);
                    finish();
                }
            });
            builder.setNegativeButton("No", null);
        }
        else{
            builder.setMessage("No puedes eliminar este tipo de maquina porque esta en uso: \n " +
                    "Numero de serie de la maquina: " + _cursorToDelete.getString(_cursorToDelete.getColumnIndexOrThrow(_data.MAQUINAS_NUMEROSERIE)));
            builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                   return;
                }
            });
        }

        builder.show();
        _cursorToDelete.close();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
