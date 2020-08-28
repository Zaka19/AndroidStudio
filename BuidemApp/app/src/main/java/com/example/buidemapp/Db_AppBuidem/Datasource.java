package com.example.buidemapp.Db_AppBuidem;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;

import com.example.buidemapp.ui.Maquinas.Maquina;
import com.example.buidemapp.ui.Maquinas.MaquinaViewModel;
import com.example.buidemapp.ui.TipoMaquinas.TipoDeMaquina;
import com.example.buidemapp.ui.Zonas.Zona;

import java.util.ArrayList;
import java.util.List;

public class Datasource {
    public static final String TODASTABLAS_ID = "_id";

    public static final String ZONAS = "zonas";
    public static final String ZONAS_NOMBRE = "nombreZona";

    public static final String TIPOMAQUINAS = "tipo_maquinas";
    public static final String TIPOMAQUINAS_NOMBRE = "nombreMaquina";
    public static final String TIPOMAQUINAS_COLOR = "colorTiposMaquina";

    public static final String MAQUINAS = "maquinas";
    public static final String MAQUINAS_NOMBRECLIENTE = "nombreCliente";
    public static final String MAQUINAS_DIRECCION = "direccion";
    public static final String MAQUINAS_CODIGOPOSTAL = "codiPostal";
    public static final String MAQUINAS_POBLACION = "nombrePoblacion";
    public static final String MAQUINAS_TELEFONO = "telefono";
    public static final String MAQUINAS_EMAIL = "email";
    public static final String MAQUINAS_NUMEROSERIE = "numSerieMaquina";
    public static final String MAQUINAS_FECHA = "fecha";
    public static final String MAQUINAS_TIPOMAQUINA = "tipus_maquinas";
    public static final String MAQUINAS_ZONA = "zonasMaquina";

    private Helper _helper;
    private SQLiteDatabase _reader, _writter;

    public Datasource(Context _context) {
        _helper = new Helper(_context);
        open();
    }

    public void open() {
        _reader = _helper.getReadableDatabase();
        _writter = _helper.getWritableDatabase();
    }

    public void close() {
        _reader.close();
        _writter.close();
    }

    //GETTERS

    public Cursor GetZonas() {
        return _reader.query(ZONAS, new String[]{TODASTABLAS_ID, ZONAS_NOMBRE},
                null, null,
                null, null, TODASTABLAS_ID);
    }

    public ArrayList<Zona> GetZonasList() {
        ArrayList<Zona> _zonas = new ArrayList<>();

        Cursor _cursor = _reader.query(ZONAS, new String[]{TODASTABLAS_ID, ZONAS_NOMBRE},
                null, null,
                null, null, TODASTABLAS_ID);
        _cursor.moveToFirst();

        while (!_cursor.isAfterLast()) {
            _zonas.add(new Zona(_cursor.getInt(_cursor.getColumnIndex(TODASTABLAS_ID)), _cursor.getString(_cursor.getColumnIndex(ZONAS_NOMBRE))));
            _cursor.moveToNext();
        }
        _cursor.close();
        return _zonas;
    }

    public Cursor GetTipoMaquinas() {
        return _reader.query(TIPOMAQUINAS, new String[]{TODASTABLAS_ID, TIPOMAQUINAS_NOMBRE, TIPOMAQUINAS_COLOR},
                null, null,
                null, null, TODASTABLAS_ID);
    }

    public ArrayList<TipoDeMaquina> GetTipoMaquinasList() {
        ArrayList<TipoDeMaquina> _tipoMaquinas = new ArrayList<>();
        Cursor _cursor = _reader.query(TIPOMAQUINAS, new String[]{TODASTABLAS_ID, TIPOMAQUINAS_NOMBRE, TIPOMAQUINAS_COLOR},
                null, null,
                null, null, TODASTABLAS_ID);
        _cursor.moveToFirst();

        while (!_cursor.isAfterLast()) {
            _tipoMaquinas.add(new TipoDeMaquina(_cursor.getInt(_cursor.getColumnIndex(TODASTABLAS_ID)), _cursor.getString(_cursor.getColumnIndex(TIPOMAQUINAS_NOMBRE)), _cursor.getString(_cursor.getColumnIndex(TIPOMAQUINAS_COLOR))));
            _cursor.moveToNext();
        }

        _cursor.close();
        return _tipoMaquinas;
    }

    public Cursor GetMaquinas() {
        return _reader.query(MAQUINAS, new String[]{TODASTABLAS_ID, MAQUINAS_NOMBRECLIENTE, MAQUINAS_DIRECCION, MAQUINAS_CODIGOPOSTAL, MAQUINAS_POBLACION, MAQUINAS_TELEFONO, MAQUINAS_EMAIL, MAQUINAS_NUMEROSERIE, MAQUINAS_FECHA, MAQUINAS_TIPOMAQUINA, MAQUINAS_ZONA},
                null, null,
                null, null, TODASTABLAS_ID);
    }

    //POSTS

    public long PostZonas(String _name) {
        ContentValues values = new ContentValues();
        values.put(ZONAS_NOMBRE, _name);

        return _writter.insert(ZONAS, null, values);
    }

    public long PostTiposMaquinas(String _name, String _color) {
        ContentValues values = new ContentValues();
        values.put(TIPOMAQUINAS_NOMBRE, _name);
        values.put(TIPOMAQUINAS_COLOR, _color);

        return _writter.insert(TIPOMAQUINAS, null, values);
    }

    public long PostMaquinas(Maquina _maquina) {
        Log.d("Direccio1", _maquina.get_direccion());
        ContentValues values = new ContentValues();
        values.put(MAQUINAS_NOMBRECLIENTE, _maquina.get_nombreCliente());
        values.put(MAQUINAS_DIRECCION, _maquina.get_direccion());
        values.put(MAQUINAS_CODIGOPOSTAL, _maquina.get_codigoPostal());
        values.put(MAQUINAS_POBLACION, _maquina.get_poblacion());
        values.put(MAQUINAS_TELEFONO, _maquina.get_telefono());
        values.put(MAQUINAS_EMAIL, _maquina.get_email());
        values.put(MAQUINAS_NUMEROSERIE, _maquina.get_numeroSerie());
        values.put(MAQUINAS_FECHA, _maquina.get_fecha());
        values.put(MAQUINAS_TIPOMAQUINA, _maquina.get_tipoMaquina());
        values.put(MAQUINAS_ZONA, _maquina.get_zonaMaquina());

        return _writter.insert(MAQUINAS, null, values);
    }

    //PUTS

    public void PutZonas(long _id, String _name) {
        ContentValues values = new ContentValues();
        values.put(ZONAS_NOMBRE, _name);

        _writter.update(ZONAS, values, TODASTABLAS_ID + " = ?", new String[]{String.valueOf(_id)});
    }

    public void PutTipoMaquinas(long _id, String _name, String _color) {
        ContentValues values = new ContentValues();
        values.put(TIPOMAQUINAS_NOMBRE, _name);
        values.put(TIPOMAQUINAS_COLOR, _color);

        _writter.update(TIPOMAQUINAS, values, TODASTABLAS_ID + " =?", new String[]{String.valueOf(_id)});
    }

    public void PutMaquinas(long _id, Maquina _maquina) {
        ContentValues values = new ContentValues();
        values.put(MAQUINAS_NOMBRECLIENTE, _maquina.get_nombreCliente());
        values.put(MAQUINAS_DIRECCION, _maquina.get_direccion());
        values.put(MAQUINAS_CODIGOPOSTAL, _maquina.get_codigoPostal());
        values.put(MAQUINAS_POBLACION, _maquina.get_poblacion());
        values.put(MAQUINAS_TELEFONO, _maquina.get_telefono());
        values.put(MAQUINAS_EMAIL, _maquina.get_email());
        values.put(MAQUINAS_NUMEROSERIE, _maquina.get_numeroSerie());
        values.put(MAQUINAS_FECHA, _maquina.get_fecha());
        values.put(MAQUINAS_TIPOMAQUINA, _maquina.get_tipoMaquina());
        values.put(MAQUINAS_ZONA, _maquina.get_zonaMaquina());

        _writter.update(MAQUINAS, values, TODASTABLAS_ID + " = ?", new String[]{String.valueOf(_id)});
    }

    //DELETES

    public void DeleteZonas(long _id) {
        _writter.delete(ZONAS, TODASTABLAS_ID + " = ? ", new String[]{String.valueOf(_id)});
    }

    public void DeleteTipoMaquinas(long _id) {
        _writter.delete(TIPOMAQUINAS, TODASTABLAS_ID + " = ? ", new String[]{String.valueOf(_id)});
    }

    public void DeleteMaquinas(long _id) {
        _writter.delete(MAQUINAS, TODASTABLAS_ID + " = ? ", new String[]{String.valueOf(_id)});
    }

    //QUERY'S

    public String SearchColorByIdOfTypeMachine(int id) {
        String color = "#";

        Cursor _cursor = _reader.query(TIPOMAQUINAS, new String[]{TODASTABLAS_ID, TIPOMAQUINAS_NOMBRE, TIPOMAQUINAS_COLOR},
                TODASTABLAS_ID + "=?", new String[]{String.valueOf(id)},
                null, null, TODASTABLAS_ID);
        _cursor.moveToFirst();

        color += _cursor.getString(_cursor.getColumnIndexOrThrow(Datasource.TIPOMAQUINAS_COLOR)).substring(3);
        _cursor.close();

        return color;
    }

    public boolean SearchNameEqualsZona(String name) {
        boolean x = false;

        Cursor _cursor = _reader.query(ZONAS, new String[]{TODASTABLAS_ID, ZONAS_NOMBRE},
                ZONAS_NOMBRE + "=?", new String[]{name},
                null, null, TODASTABLAS_ID);
        _cursor.moveToFirst();

        x = _cursor.getCount() <= 0 ? false : true;

        _cursor.close();

        return x;
    }

    public boolean SearchNameEqualsTipoMaquinas(String name) {
        boolean x = false;

        Cursor _cursor = _reader.query(TIPOMAQUINAS, new String[]{TODASTABLAS_ID, TIPOMAQUINAS_NOMBRE, TIPOMAQUINAS_COLOR},
                TIPOMAQUINAS_NOMBRE + "=?", new String[]{name},
                null, null, TODASTABLAS_ID);
        _cursor.moveToFirst();

        x = _cursor.getCount() <= 0 ? false : true;

        _cursor.close();

        return x;
    }

    public boolean SearchNameEqualsMaquina(String name) {
        boolean x = false;

        Cursor _cursorMachine = _reader.query(MAQUINAS, new String[]{TODASTABLAS_ID, MAQUINAS_NOMBRECLIENTE, MAQUINAS_DIRECCION,
                        MAQUINAS_CODIGOPOSTAL, MAQUINAS_POBLACION, MAQUINAS_TELEFONO, MAQUINAS_EMAIL, MAQUINAS_NUMEROSERIE,
                        MAQUINAS_FECHA, MAQUINAS_TIPOMAQUINA, MAQUINAS_ZONA},
                MAQUINAS_NUMEROSERIE + "=?", new String[]{name},
                null, null, TODASTABLAS_ID);
        _cursorMachine.moveToFirst();

        x = _cursorMachine.getCount() <= 0 ? false : true;

        _cursorMachine.close();
        return x;
    }

    public boolean SearchNameEqualsMaquina(String name, long id) {
        boolean x = false;

        Cursor _cursorMachine = _reader.query(MAQUINAS, new String[]{TODASTABLAS_ID, MAQUINAS_NOMBRECLIENTE, MAQUINAS_DIRECCION,
                        MAQUINAS_CODIGOPOSTAL, MAQUINAS_POBLACION, MAQUINAS_TELEFONO, MAQUINAS_EMAIL, MAQUINAS_NUMEROSERIE,
                        MAQUINAS_FECHA, MAQUINAS_TIPOMAQUINA, MAQUINAS_ZONA},
                MAQUINAS_NUMEROSERIE + "=?", new String[]{name},
                null, null, TODASTABLAS_ID);
        _cursorMachine.moveToFirst();

        if (_cursorMachine.getCount() > 0) {
            x = id == _cursorMachine.getInt(_cursorMachine.getColumnIndexOrThrow(TODASTABLAS_ID)) ? false : true;
        } else {
            x = false;
        }

        _cursorMachine.close();
        return x;
    }

    public Maquina SearchByIdMachine(long id) {
        Cursor _cursorMachine = _reader.query(MAQUINAS, new String[]{TODASTABLAS_ID, MAQUINAS_NOMBRECLIENTE, MAQUINAS_DIRECCION,
                        MAQUINAS_CODIGOPOSTAL, MAQUINAS_POBLACION, MAQUINAS_TELEFONO, MAQUINAS_EMAIL, MAQUINAS_NUMEROSERIE,
                        MAQUINAS_FECHA, MAQUINAS_TIPOMAQUINA, MAQUINAS_ZONA},
                TODASTABLAS_ID + "=?", new String[]{String.valueOf(id)},
                null, null, TODASTABLAS_ID);
        _cursorMachine.moveToFirst();

        Maquina _machine = new Maquina(String.valueOf(_cursorMachine.getInt(_cursorMachine.getColumnIndexOrThrow(TODASTABLAS_ID))), _cursorMachine.getString(_cursorMachine.getColumnIndexOrThrow(MAQUINAS_NOMBRECLIENTE)),
                _cursorMachine.getString(_cursorMachine.getColumnIndexOrThrow(MAQUINAS_DIRECCION)), _cursorMachine.getString(_cursorMachine.getColumnIndexOrThrow(MAQUINAS_CODIGOPOSTAL)),
                _cursorMachine.getString(_cursorMachine.getColumnIndexOrThrow(MAQUINAS_POBLACION)), _cursorMachine.getString(_cursorMachine.getColumnIndexOrThrow(MAQUINAS_TELEFONO)),
                _cursorMachine.getString(_cursorMachine.getColumnIndexOrThrow(MAQUINAS_EMAIL)), _cursorMachine.getString(_cursorMachine.getColumnIndexOrThrow(MAQUINAS_NUMEROSERIE)),
                _cursorMachine.getString(_cursorMachine.getColumnIndexOrThrow(MAQUINAS_FECHA)), _cursorMachine.getInt(_cursorMachine.getColumnIndexOrThrow(MAQUINAS_TIPOMAQUINA)),
                _cursorMachine.getInt(_cursorMachine.getColumnIndexOrThrow(MAQUINAS_ZONA)));

        _cursorMachine.close();
        return _machine;
    }

    public ArrayList<Maquina> SearchMachinesByIdZone (int idZone){
        ArrayList<Maquina> _machines = new ArrayList<>();

        Cursor _cursor = _reader.query(MAQUINAS, new String[]{TODASTABLAS_ID, MAQUINAS_NOMBRECLIENTE, MAQUINAS_DIRECCION, MAQUINAS_CODIGOPOSTAL, MAQUINAS_POBLACION, MAQUINAS_TELEFONO, MAQUINAS_EMAIL, MAQUINAS_NUMEROSERIE, MAQUINAS_FECHA, MAQUINAS_TIPOMAQUINA, MAQUINAS_ZONA},
                MAQUINAS_ZONA + "=?", new String[]{String.valueOf(idZone)},
                null, null, TODASTABLAS_ID);

        _cursor.moveToFirst();

        while (!_cursor.isAfterLast()) {
            _machines.add(new Maquina(String.valueOf(_cursor.getInt(_cursor.getColumnIndexOrThrow(Datasource.TODASTABLAS_ID))),_cursor.getString(_cursor.getColumnIndexOrThrow(Datasource.MAQUINAS_NOMBRECLIENTE)),
                    _cursor.getString(_cursor.getColumnIndexOrThrow(Datasource.MAQUINAS_DIRECCION)),  _cursor.getString(_cursor.getColumnIndexOrThrow(Datasource.MAQUINAS_CODIGOPOSTAL)),
                    _cursor.getString(_cursor.getColumnIndexOrThrow(Datasource.MAQUINAS_POBLACION)),  _cursor.getString(_cursor.getColumnIndexOrThrow(Datasource.MAQUINAS_TELEFONO)),
                    _cursor.getString(_cursor.getColumnIndexOrThrow(Datasource.MAQUINAS_EMAIL)), _cursor.getString(_cursor.getColumnIndexOrThrow(Datasource.MAQUINAS_NUMEROSERIE)),
                    _cursor.getString(_cursor.getColumnIndexOrThrow(Datasource.MAQUINAS_FECHA)), _cursor.getInt(_cursor.getColumnIndexOrThrow(Datasource.MAQUINAS_TIPOMAQUINA)),
                    _cursor.getInt(_cursor.getColumnIndexOrThrow(Datasource.MAQUINAS_ZONA))));
            _cursor.moveToNext();
        }
        _cursor.close();

        return _machines;
    }

    public Cursor SearchByIdTypeMachine(long id) {
        return _reader.query(TIPOMAQUINAS, new String[]{TODASTABLAS_ID, TIPOMAQUINAS_NOMBRE, TIPOMAQUINAS_COLOR},
                TODASTABLAS_ID + "=?", new String[]{String.valueOf(id)},
                null, null, TODASTABLAS_ID);
    }

    public Cursor SearchByIdZone(long id) {
        return _reader.query(ZONAS, new String[]{TODASTABLAS_ID, ZONAS_NOMBRE},
                TODASTABLAS_ID + "=?", new String[]{String.valueOf(id)},
                null, null, TODASTABLAS_ID);
    }

    public Cursor SearchTypeMachineInMachine(long id) {
        return _reader.query(MAQUINAS, new String[]{TODASTABLAS_ID, MAQUINAS_NOMBRECLIENTE, MAQUINAS_DIRECCION,
                        MAQUINAS_CODIGOPOSTAL, MAQUINAS_POBLACION, MAQUINAS_TELEFONO, MAQUINAS_EMAIL, MAQUINAS_NUMEROSERIE,
                        MAQUINAS_FECHA, MAQUINAS_TIPOMAQUINA, MAQUINAS_ZONA},
                MAQUINAS_TIPOMAQUINA + "=?", new String[]{String.valueOf(id)},
                null, null, TODASTABLAS_ID);
    }

    public Cursor SearchZoneInMachine(long id) {
        return _reader.query(MAQUINAS, new String[]{TODASTABLAS_ID, MAQUINAS_NOMBRECLIENTE, MAQUINAS_DIRECCION,
                        MAQUINAS_CODIGOPOSTAL, MAQUINAS_POBLACION, MAQUINAS_TELEFONO, MAQUINAS_EMAIL, MAQUINAS_NUMEROSERIE,
                        MAQUINAS_FECHA, MAQUINAS_TIPOMAQUINA, MAQUINAS_ZONA},
                MAQUINAS_ZONA + "=?", new String[]{String.valueOf(id)},
                null, null, TODASTABLAS_ID);
    }

    /*public int SearchIdByNameZones(String _name){
        int id;

        Cursor _cursor = _reader.query(ZONAS, new String[]{TODASTABLAS_ID, ZONAS_NOMBRE},
                ZONAS_NOMBRE + "=?", new String[]{ String.valueOf(_name) },
                null, null, TODASTABLAS_ID);
        _cursor.moveToFirst();

        id = _cursor.getInt(_cursor.getColumnIndexOrThrow(TODASTABLAS_ID));

        _cursor.close();

        return id;
    }

    public int SearchIdByNameMachineTypes(String _name){
        int id;

        Cursor _cursor = _reader.query(TIPOMAQUINAS, new String[]{TODASTABLAS_ID, TIPOMAQUINAS_NOMBRE, TIPOMAQUINAS_COLOR},
                TIPOMAQUINAS_NOMBRE + "=?", new String[]{ String.valueOf(_name) },
                null, null, TODASTABLAS_ID);
        _cursor.moveToFirst();

        id = _cursor.getInt(_cursor.getColumnIndexOrThrow(TODASTABLAS_ID));

        _cursor.close();

        return id;
    }*/

    public Cursor FilterMachinesToSerieCode(String _filter) {
        /*return _reader.query(MAQUINAS, new String[]{TODASTABLAS_ID, MAQUINAS_NOMBRECLIENTE, MAQUINAS_DIRECCION,
                        MAQUINAS_CODIGOPOSTAL, MAQUINAS_POBLACION, MAQUINAS_TELEFONO, MAQUINAS_EMAIL, MAQUINAS_NUMEROSERIE,
                        MAQUINAS_FECHA, MAQUINAS_TIPOMAQUINA, MAQUINAS_ZONA},
                MAQUINAS_NUMEROSERIE + " LIKE ? ", new String[]{ "%" + String.valueOf(_filter) + "%" },
                null, null, TODASTABLAS_ID);*/
        Log.d("filtro", _filter);
        return _reader.rawQuery("select * from " + MAQUINAS + " where " + MAQUINAS_NUMEROSERIE + " LIKE '%" + _filter + "%' order by " + TODASTABLAS_ID, null);
    }

    public Cursor OrderByClientName() {
        return _reader.query(MAQUINAS, new String[]{TODASTABLAS_ID, MAQUINAS_NOMBRECLIENTE, MAQUINAS_DIRECCION, MAQUINAS_CODIGOPOSTAL, MAQUINAS_POBLACION, MAQUINAS_TELEFONO, MAQUINAS_EMAIL, MAQUINAS_NUMEROSERIE, MAQUINAS_FECHA, MAQUINAS_TIPOMAQUINA, MAQUINAS_ZONA},
                null, null,
                null, null, MAQUINAS_NOMBRECLIENTE);
    }

    public Cursor OrderByDateLastRevision() {
        return _reader.query(MAQUINAS, new String[]{TODASTABLAS_ID, MAQUINAS_NOMBRECLIENTE, MAQUINAS_DIRECCION, MAQUINAS_CODIGOPOSTAL, MAQUINAS_POBLACION, MAQUINAS_TELEFONO, MAQUINAS_EMAIL, MAQUINAS_NUMEROSERIE, MAQUINAS_FECHA, MAQUINAS_TIPOMAQUINA, MAQUINAS_ZONA},
                null, null,
                null, null, MAQUINAS_FECHA);
    }

    public Cursor OrderByAddress() {
        return _reader.query(MAQUINAS, new String[]{TODASTABLAS_ID, MAQUINAS_NOMBRECLIENTE, MAQUINAS_DIRECCION, MAQUINAS_CODIGOPOSTAL, MAQUINAS_POBLACION, MAQUINAS_TELEFONO, MAQUINAS_EMAIL, MAQUINAS_NUMEROSERIE, MAQUINAS_FECHA, MAQUINAS_TIPOMAQUINA, MAQUINAS_ZONA},
                null, null,
                null, null, MAQUINAS_DIRECCION);
    }
}
