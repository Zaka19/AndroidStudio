package com.example.appbuidem.Db_AppBuidem;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Datasource {
    public static final String TODASTABLAS_ID = "_id";

    public static final String ZONAS = "zonas";
    public static final String ZONAS_NOMBRE = "nombreZona";

    public static final String TIPOMAQUINAS = "tipo_maquinas";
    public static final String TIPOMAQUINAS_NOMBRE = "nombreMaquina";

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

    public void open(){
        _reader = _helper.getReadableDatabase();
        _writter = _helper.getWritableDatabase();
    }

    public void close(){
        _reader.close();
        _writter.close();
    }

    //GETTERS

    public Cursor GetZonas(){
        return _reader.query(ZONAS, new String[]{TODASTABLAS_ID, ZONAS_NOMBRE},
                null, null,
                null, null, TODASTABLAS_ID);
    }

    public Cursor GetTipoMaquinas(){
        return _reader.query(TIPOMAQUINAS, new String[]{TODASTABLAS_ID, TIPOMAQUINAS_NOMBRE},
                null, null,
                null, null, TODASTABLAS_ID);
    }

    public Cursor GetMaquinas(){
        return _reader.query(MAQUINAS, new String[]{TODASTABLAS_ID, MAQUINAS_NOMBRECLIENTE, MAQUINAS_DIRECCION, MAQUINAS_CODIGOPOSTAL, MAQUINAS_POBLACION, MAQUINAS_TELEFONO, MAQUINAS_EMAIL, MAQUINAS_NUMEROSERIE, MAQUINAS_FECHA, MAQUINAS_TIPOMAQUINA, MAQUINAS_ZONA},
                null, null,
                null, null, TODASTABLAS_ID);
    }

    //POSTS

    public long PostZonas(String _name) {
        ContentValues values = new ContentValues();
        values.put(ZONAS_NOMBRE, _name);

        return _writter.insert(ZONAS,null,values);
    }

    public long PostTiposMaquinas(String _name) {
        ContentValues values = new ContentValues();
        values.put(TIPOMAQUINAS_NOMBRE, _name);

        return _writter.insert(TIPOMAQUINAS,null,values);
    }

    public long PostMaquinas(Maquina _maquina) {
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

        return _writter.insert(MAQUINAS,null,values);
    }

    //PUTS

    public void PutZonas(int _id, String _name) {
        ContentValues values = new ContentValues();
        values.put(ZONAS_NOMBRE, _name);

        _writter.update(ZONAS,values, TODASTABLAS_ID + " =?", new String[] { String.valueOf(_id) });
    }

    public void PutTipoMaquinas(int _id, String _name) {
        ContentValues values = new ContentValues();
        values.put(TIPOMAQUINAS_NOMBRE, _name);

        _writter.update(TIPOMAQUINAS_NOMBRE,values, TODASTABLAS_ID + " =?", new String[] { String.valueOf(_id) });
    }

    public void PutMaquinas(int _id, Maquina _maquina) {
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

        _writter.update(MAQUINAS,values, TODASTABLAS_ID + " =?", new String[] { String.valueOf(_id) });
    }

    //DELETES

    public void DeleteZonas(int _id){
        _writter.delete(ZONAS,TODASTABLAS_ID + " = ? ", new String[]{ String.valueOf(_id) });
    }

    public void DeleteTipoMaquinas(int _id){
        _writter.delete(TIPOMAQUINAS,TODASTABLAS_ID + " = ? ", new String[]{ String.valueOf(_id) });
    }

    public void DeleteMaquinas(int _id){
        _writter.delete(MAQUINAS,TODASTABLAS_ID + " = ? ", new String[]{ String.valueOf(_id) });
    }
}
