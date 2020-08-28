package com.example.buidemapp.ui.Maquinas;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.buidemapp.Db_AppBuidem.Datasource;
import com.example.buidemapp.DialogCalendar;
import com.example.buidemapp.R;
import com.example.buidemapp.SpinnerSearchPos;
import com.example.buidemapp.ui.TipoMaquinas.TipoDeMaquina;
import com.example.buidemapp.ui.TipoMaquinas.TipoMaquinasFragment;
import com.example.buidemapp.ui.Zonas.Zona;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;

public class CRUDMaquinas extends AppCompatActivity {

    private long id;

    private Datasource _data;

    private Maquina _machine;

    private EditText edtNombreCliente;
    private EditText edtNumeroSerie;
    private EditText edtTelefono;
    private EditText edtEmail;
    private EditText edtFecha;
    private EditText edtCP;
    private EditText edtPoblacio;
    private EditText edtDireccion;

    private Spinner edtZonas;
    private Spinner edtTipoMaquinas;

    private FloatingActionButton Add;
    private FloatingActionButton Delete;

    private ImageView date;

    private ArrayList<Zona> _zonas;
    private ArrayList<TipoDeMaquina> _tipoMaquinas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crudmaquinas);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        _data = new Datasource(this);

        _zonas = _data.GetZonasList();
        _tipoMaquinas = _data.GetTipoMaquinasList();

        Add = (FloatingActionButton) findViewById(R.id.faAddM);
        Delete = (FloatingActionButton) findViewById(R.id.faDeleteM);
        edtZonas = (Spinner) findViewById(R.id.edtZonaM);
        edtTipoMaquinas = (Spinner) findViewById(R.id.edtTipoMaquinaM);
        edtNombreCliente = (EditText) findViewById(R.id.edtNameClientM);
        edtNumeroSerie = (EditText) findViewById(R.id.edtNumSerieM);
        edtTelefono = (EditText) findViewById(R.id.edtTelM);
        edtEmail = (EditText) findViewById(R.id.edtEmailM);
        edtFecha = (EditText) findViewById(R.id.edtDateFromM);
        edtCP = (EditText) findViewById(R.id.edtCPM);
        edtPoblacio = (EditText) findViewById(R.id.edtPoblacioM2);
        edtDireccion = (EditText) findViewById(R.id.edtDireccionM3);
        date = (ImageView) findViewById(R.id.imgDateM);

        id = this.getIntent().getExtras().getLong("CRUD");

        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    addOrUpdate();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Delete(id);
            }
        });

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogCalendar.Dialog(v.getContext(), edtFecha);
            }
        });

        edtZonas.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, CreateArrayForNameZonas()));
        edtTipoMaquinas.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, CreateArrayForNameTipoMaquinas()));

        if(id > 0){
            try {
                InputDataResult();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        else{
            setTitle("Añadir nueva maquina");
            Delete.hide();
        }
    }

    private void addOrUpdate() throws ParseException {
        Maquina _machine = new Maquina();
        Intent mIntent = new Intent();

        _machine.set_telefono(edtTelefono.getText().toString());
        _machine.set_email(edtEmail.getText().toString());

        if(edtNombreCliente.getText().toString().isEmpty()){
            Snackbar snackBar = Snackbar.make(this.findViewById(android.R.id.content),
                    "Tienes que poner el nombre del cliente.", Snackbar.LENGTH_SHORT);
            snackBar.show();
            return;
        }
        else{
            _machine.set_nombreCliente(edtNombreCliente.getText().toString().substring(0, 1).toUpperCase() + edtNombreCliente.getText().toString().substring(1));
        }

        if(edtNumeroSerie.getText().toString().isEmpty()) {
            Snackbar snackBar = Snackbar.make(this.findViewById(android.R.id.content),
                    "Tienes que poner un numero de serie.", Snackbar.LENGTH_SHORT);
            snackBar.show();
            return;
        }
        else{
            _machine.set_numeroSerie(edtNumeroSerie.getText().toString());
        }

        if(!edtFecha.getText().toString().isEmpty()){
            _machine.set_fecha(DialogCalendar.ChangeFormatDate(edtFecha.getText().toString(), "dd/MM/yyyy", "yyyy/MM/dd"));
        }

        if(edtCP.getText().toString().isEmpty()){
            Snackbar snackBar = Snackbar.make(this.findViewById(android.R.id.content),
                    "Tienes que poner un Codigo postal.", Snackbar.LENGTH_SHORT);
            snackBar.show();
            return;
        }
        else{
            _machine.set_codigoPostal(edtCP.getText().toString());
        }

        if(edtPoblacio.getText().toString().isEmpty()){
            Snackbar snackBar = Snackbar.make(this.findViewById(android.R.id.content),
                    "Tienes que poner una poblacion.", Snackbar.LENGTH_SHORT);
            snackBar.show();
            return;
        }
        else{
            _machine.set_poblacion(edtPoblacio.getText().toString());
        }

        if(edtDireccion.getText().toString().isEmpty()){
            Snackbar snackBar = Snackbar.make(this.findViewById(android.R.id.content),
                    "Tienes que poner una direccion.", Snackbar.LENGTH_SHORT);
            snackBar.show();
            return;
        }
        else{
            _machine.set_direccion(edtDireccion.getText().toString().substring(0, 1).toUpperCase() + edtDireccion.getText().toString().substring(1));
        }

        if(edtZonas.getSelectedItem().toString().isEmpty()){
            Snackbar snackBar = Snackbar.make(this.findViewById(android.R.id.content),
                    "Tienes que poner una zona.", Snackbar.LENGTH_SHORT);
            snackBar.show();
            return;
        }
        else{
            _machine.set_zonaMaquina(SearchIdByNameZona(edtZonas.getSelectedItem().toString()));
        }

        if(edtTipoMaquinas.getSelectedItem().toString().isEmpty()){
            Snackbar snackBar = Snackbar.make(this.findViewById(android.R.id.content),
                    "Tienes que poner un tipo de maquina.", Snackbar.LENGTH_SHORT);
            snackBar.show();
            return;
        }
        else{
            _machine.set_tipoMaquina(SearchIdByNameTipoMaquinas(edtTipoMaquinas.getSelectedItem().toString()));
        }

        if(id < 0){
            if(_data.SearchNameEqualsMaquina(edtNumeroSerie.getText().toString())){
                Snackbar snackBar = Snackbar.make(this.findViewById(android.R.id.content),
                        "Tienes que poner un numero de serie que no se haya puesto.", Snackbar.LENGTH_SHORT);
                snackBar.show();
                return;
            }

            Log.d("Direccio", _machine.get_direccion());

            id = _data.PostMaquinas(_machine);
        }
        else{
            if(_data.SearchNameEqualsMaquina(edtNumeroSerie.getText().toString(), id)){
                Snackbar snackBar = Snackbar.make(this.findViewById(android.R.id.content),
                        "Tienes que poner un numero de serie que no se haya puesto.", Snackbar.LENGTH_SHORT);
                snackBar.show();
                return;
            }
            _data.PutMaquinas(id, _machine);
        }

        setResult(RESULT_OK, mIntent);
        mIntent.putExtra("id", id);
        finish();
    }

    private void InputDataResult() throws ParseException {
        /*EditText _edt;
        Spinner spn;*/

        _machine = _data.SearchByIdMachine(id);

        Log.d("Direccio3", _machine.get_direccion());

        setTitle(_machine.get_numeroSerie());

        //_edt = findViewById(R.id.edtNameClientM);
        edtNombreCliente.setText(_machine.get_nombreCliente());

        //_edt = findViewById(R.id.edtNumSerieM);
        edtNumeroSerie.setText(_machine.get_numeroSerie());

        //_edt = findViewById(R.id.edtTelM);
        edtTelefono.setText(_machine.get_telefono());

        //_edt = findViewById(R.id.edtEmailM);
        edtEmail.setText(_machine.get_email());

        //_edt = findViewById(R.id.edtDateFromM);
        if(_machine.get_fecha() != null) {
            edtFecha.setText(DialogCalendar.ChangeFormatDate(_machine.get_fecha(), "yyyy/MM/dd", "dd/MM/yyyy"));
        }

        //_edt = findViewById(R.id.edtCPM);
        edtCP.setText(_machine.get_codigoPostal());

        //_edt = findViewById(R.id.edtPoblacioM2);
        edtPoblacio.setText(_machine.get_poblacion());

        //_edt = findViewById(R.id.edtDireccionM3);
        edtDireccion.setText(_machine.get_direccion());

        //spn = findViewById(R.id.edtZonaM);
        edtZonas.setSelection(SearchForEditZona(Integer.valueOf(_machine.get_zonaMaquina())));

        //spn = findViewById(R.id.edtTipoMaquinaM);
        edtTipoMaquinas.setSelection(SearchForEditTipoMaquina(Integer.valueOf(_machine.get_tipoMaquina())));
    }

    private void Delete(final long _id){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("¿Estas seguro quieres borrar esta maquina?");
        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                _data.DeleteMaquinas(_id);

                Intent mIntent = new Intent();
                mIntent.putExtra("id", -1);
                setResult(RESULT_OK, mIntent);
                finish();
            }
        });
        builder.setNegativeButton("No", null);

        builder.show();
    }

    private ArrayList<String> CreateArrayForNameZonas(){
        ArrayList<String> _names = new ArrayList<>();

        for (Zona _zona:
             _zonas) {
            _names.add(_zona.getName());
        }

        return _names;
    }

    private ArrayList<String> CreateArrayForNameTipoMaquinas(){
        ArrayList<String> _names = new ArrayList<>();

        for (TipoDeMaquina _tm:
                _tipoMaquinas) {
            _names.add(_tm.getName());
        }

        return _names;
    }

    private int SearchIdByNameZona(String _name){
        int id = 0;
        for (Zona _zona:
                _zonas) {
            if(_zona.getName().equals(_name)){
                id = _zona.getId();
            }
        }

        return id;
    }

    private int SearchIdByNameTipoMaquinas(String _name){
        int id = 0;
        for (TipoDeMaquina _tm:
                _tipoMaquinas) {
            if(_tm.getName().equals(_name)){
                id = _tm.getId();
            }
        }

        return id;
    }

    private int SearchForEditZona(int id){
        String name = "";

        for (Zona _zona:
             _zonas) {
            if(_zona.getId() == id){
              name = _zona.getName();
            }
        }

        return SpinnerSearchPos.SearchPos(CreateArrayForNameZonas(), name);
    }

    private int SearchForEditTipoMaquina(int id){
        String name = "";

        for (TipoDeMaquina _zona:
                _tipoMaquinas) {
            if(_zona.getId() == id){
                name = _zona.getName();
            }
        }

        return SpinnerSearchPos.SearchPos(CreateArrayForNameTipoMaquinas(), name);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
