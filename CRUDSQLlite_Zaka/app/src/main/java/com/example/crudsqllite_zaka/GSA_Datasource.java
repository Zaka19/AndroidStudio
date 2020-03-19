package com.example.crudsqllite_zaka;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class GSA_Datasource {

    //Dades de la taula creada a GSA_Helper
    public static final String TABLE_NAME = "gsa";
    public static final String TABLE_ID = "_id";
    public static final String TABLE_COL1 = "code";
    public static final String TABLE_COL2 = "description";
    public static final String TABLE_COL3 = "pvp";
    public static final String TABLE_COL4 = "stock";


    public static final String TABLE_NAME_2 = "moviment";
    public static final String TABLE_ID_2 = "_id";
    public static final String TABLE_COL1_2 = "codigoarticulo";
    public static final String TABLE_COL2_2 = "dia";
    public static final String TABLE_COL3_2 = "cantidad";
    public static final String TABLE_COL4_2 = "type";
    public static final String TABLE_COL5_2 = "gsaID";

    //Instanciem el helper + lo que ens permetra llegir i escriure a la BDD.
    private GSA_Helper _helper;
    private SQLiteDatabase _reader, _writter;

    public GSA_Datasource(Context _context){
        //Amb el helper obrim la comunicacio amb la base de dades per aixi començar a treballar amb ella.
        _helper = new GSA_Helper(_context);
        /* Amb els reader i writter creem dos bases de dades virtuals en les qual una llegirem i la'altre escriurem per despres
        aplicar els canvis a la real. */
        open();
    }

    public void open(){
        //Com podem veure ens permet llegir i escriure a la database.
        _reader = _helper.getReadableDatabase();
        _writter = _helper.getWritableDatabase();
    }

    public void close(){
        //Aqui tancarem al connexio amb la abse de dades tant a l'hora de llegir com a l'hora d'escriure
        _reader.close();
        _writter.close();
    }

    //Funcions reader

    public boolean SearhCodeAdd(String code) {
        boolean x = false;
        Cursor _c = _reader.query(TABLE_NAME, new String[]{TABLE_ID, TABLE_COL1,TABLE_COL2,TABLE_COL3,TABLE_COL4},
                TABLE_COL1 + "=?", new String[]{ code },
                null, null, TABLE_COL1);

        if(_c.moveToFirst()){
            x = true;
        }

        _c.close();
        return x;
    }

    public Cursor SearhCode(String code) {
        return _reader.query(TABLE_NAME, new String[]{TABLE_ID, TABLE_COL1,TABLE_COL2,TABLE_COL3,TABLE_COL4},
                TABLE_COL1 + "=?", new String[]{ code },
                null, null, TABLE_COL1);
    }

    public Cursor SearchId(long id){
        return _reader.query(TABLE_NAME, new String[]{TABLE_ID, TABLE_COL1,TABLE_COL2,TABLE_COL3,TABLE_COL4},
                TABLE_ID + "=?", new String[]{ String.valueOf(id) },
                null, null, TABLE_ID);
    }


    public Cursor ReadAll(){
        //Aqui retornem tot lo que hi ha a la BDD.
        return _reader.query(TABLE_NAME, new String[]{TABLE_ID, TABLE_COL1,TABLE_COL2,TABLE_COL3,TABLE_COL4},
                null, null,
                null, null, TABLE_COL1);
    }

    public Cursor ReadDescending(){
        return _reader.query(TABLE_NAME, new String[]{TABLE_ID, TABLE_COL1,TABLE_COL2,TABLE_COL3,TABLE_COL4},
                null, null,
                null, null, TABLE_COL1+" DESC");
    }

    public Cursor ReadNoHaveStock(){
        return _reader.query(TABLE_NAME, new String[]{TABLE_ID, TABLE_COL1,TABLE_COL2,TABLE_COL3,TABLE_COL4},
                TABLE_COL4 + "<=?", new String[]{ String.valueOf(0) },
                null, null, TABLE_COL1);
    }

    public Cursor ReadHaveStock(){
        return _reader.query(TABLE_NAME, new String[]{TABLE_ID, TABLE_COL1,TABLE_COL2,TABLE_COL3,TABLE_COL4},
                TABLE_COL4 + ">?", new String[]{ String.valueOf(0) },
                null, null, TABLE_COL1);
    }


    public Cursor ReadMovement(long id){
        return _reader.query(TABLE_NAME_2, new String[]{TABLE_ID_2, TABLE_COL1_2,TABLE_COL2_2,TABLE_COL3_2,TABLE_COL4_2,TABLE_COL5_2},
                TABLE_COL5_2 + "=?", new String[]{ String.valueOf(id) },
                null, null, TABLE_COL2_2 + " DESC");
    }

    public Cursor DateIDateF(String _dateI, String _dateF, long id){
       /* return _reader.query(TABLE_NAME_2, new String[]{TABLE_ID_2, TABLE_COL1_2,TABLE_COL2_2,TABLE_COL3_2,TABLE_COL4_2,TABLE_COL5_2},
                TABLE_COL2_2 + ">=?" + " AND "+ TABLE_COL2_2 + "<=?", new String[]{ _dateI, _dateF },
                null, null, null);*/
       return _reader.rawQuery("select * from " + TABLE_NAME_2 + " where dia BETWEEN '" + _dateI + "' AND '" + _dateF + "' AND gsaId = " + id + " ORDER BY dia DESC ", null);
    }

    public Cursor DateI(String _dateI, long id){
        /*return _reader.query(TABLE_NAME_2, new String[]{TABLE_ID_2, TABLE_COL1_2,TABLE_COL2_2,TABLE_COL3_2,TABLE_COL4_2,TABLE_COL5_2},
                TABLE_COL2_2 + ">=?", new String[]{_dateI},
                null, null, null);*/
        return _reader.rawQuery("SELECT * FROM " + TABLE_NAME_2 + " WHERE dia >= '"+_dateI+"'" + " AND gsaId = " + id + " ORDER BY dia DESC ", null);
    }

    public Cursor DateF(String _dateF, long id){
        /*return _reader.query(TABLE_NAME_2, new String[]{TABLE_ID_2, TABLE_COL1_2,TABLE_COL2_2,TABLE_COL3_2,TABLE_COL4_2,TABLE_COL5_2},
                TABLE_COL2_2 + "<=?", new String[]{_dateF},
                null, null, TABLE_COL2_2 + " DESC");*/
        return _reader.rawQuery("SELECT * FROM " + TABLE_NAME_2 + " WHERE dia <= '"+_dateF+"'" + " AND gsaId = " + id + " ORDER BY dia DESC ", null);
    }

    public Cursor DateMain(String date){
        return _reader.rawQuery("SELECT gsa._id,code,description,stock FROM " + TABLE_NAME
                + " INNER JOIN " + TABLE_NAME_2
                + " ON " + TABLE_NAME + "." + TABLE_ID + "=" + TABLE_NAME_2 + "." + TABLE_COL5_2
                + " WHERE " + TABLE_NAME_2 + "." + TABLE_COL2_2 + "=?"
                + " GROUP BY " + TABLE_NAME + "." + TABLE_COL1
                + " ORDER BY " + TABLE_NAME + "." + TABLE_COL1 + " ASC",new String[]{date});
    }

    public Cursor DateMovements(String date){
        return _reader.rawQuery("SELECT * FROM " + TABLE_NAME
                + " INNER JOIN " + TABLE_NAME_2
                + " ON " + TABLE_NAME + "." + TABLE_ID + "=" + TABLE_NAME_2 + "." + TABLE_COL5_2
                + " WHERE " + TABLE_NAME_2 + "." + TABLE_COL2_2 + "=?"
                + " ORDER BY " + TABLE_NAME + "." + TABLE_COL1 + " ASC",new String[]{date});
    }

    public Cursor DateMovementsAll(){
        return _reader.rawQuery("SELECT * FROM " + TABLE_NAME
                + " INNER JOIN " + TABLE_NAME_2
                + " ON " + TABLE_NAME + "." + TABLE_ID + "=" + TABLE_NAME_2 + "." + TABLE_COL5_2
                + " ORDER BY " + TABLE_NAME + "." + TABLE_COL1 + " ASC",null);
    }


    //Funcions writter

    public long Add(String code, String description, double pvp, double stock) {
        ContentValues values = new ContentValues();
        values.put(TABLE_COL1, code);
        values.put(TABLE_COL2, description);
        values.put(TABLE_COL3, pvp);
        values.put(TABLE_COL4, stock);

        return _writter.insert(TABLE_NAME,null,values);
    }

    public void Update(String code, String description, double pvp, double stock) {
        ContentValues values = new ContentValues();
        values.put(TABLE_COL1, code);
        values.put(TABLE_COL2, description);
        values.put(TABLE_COL3, pvp);
        values.put(TABLE_COL4, stock);

        _writter.update(TABLE_NAME,values, TABLE_COL1 + " =?", new String[] { String.valueOf(code) });
    }

    public void Delete(String code){
        _writter.delete(TABLE_NAME,TABLE_COL1 + " = ? ", new String[]{ code });
    }


    public long Add_2(String code, String date, long cantidad, String tipo, long _gsaId) {
        ContentValues values = new ContentValues();
        values.put(TABLE_COL1_2, code);
        values.put(TABLE_COL2_2, date);
        values.put(TABLE_COL3_2, cantidad);
        values.put(TABLE_COL4_2, tipo);
        values.put(TABLE_COL5_2, _gsaId);

        return _writter.insert(TABLE_NAME_2,null,values);
    }
}
