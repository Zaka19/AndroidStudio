package com.example.buidemapp.Db_AppBuidem;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Helper extends SQLiteOpenHelper {
    private static final int Version = 1;
    private static final String databaseName = "BDD_BUIDEM";

    private String CreateTableZonas = "CREATE TABLE zonas " +
            "(_id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "nombreZona TEXT NOT NULL)";

    private String CreateTableTiposMaquinas = "CREATE TABLE tipo_maquinas " +
            "(_id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "nombreMaquina TEXT NOT NULL)";

    private String CreateTableMaquinas = "CREATE TABLE maquinas " +
            "(_id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "nombreCliente TEXT NOT NULL," +
            "direccion TEXT NOT NULL," +
            "codiPostal TEXT NOT NULL," +
            "nombrePoblacion TEXT NOT NULL," +
            "telefono TEXT," +
            "email TEXT," +
            "numSerieMaquina TEXT NOT NULL," +
            "fecha TEXT," +
            "tipus_maquinas TEXT NOT NULL," +
            "zonasMaquina TEXT NOT NULL," +
            "FOREIGN KEY(tipus_maquinas) REFERENCES tipo_maquinas(_id),"+
            "FOREIGN KEY(zonasMaquina) REFERENCES zonas(_id)"+ ")";

    public Helper(Context context) {
        super(context, databaseName, null, Version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CreateTableZonas);
        db.execSQL(CreateTableTiposMaquinas);
        db.execSQL(CreateTableMaquinas);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Nothing to now
    }
}
