package com.example.crudsqllite_zaka;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class GSA_Helper extends SQLiteOpenHelper {

    //Infomation for database
    private static final int VERSION_DB = 2;
    private static final String NAME_DB = "gsaDB_new";
    private String CREATE_TABLE = "CREATE TABLE gsa (_id INTEGER PRIMARY KEY AUTOINCREMENT," + "code TEXT NOT NULL," + "description TEXT NOT NULL," + "pvp REAL NOT NULL," + "stock INTEGER NOT NULL DEFAULT 0)";
    private String CREATE_TABLE_1 = "CREATE TABLE moviment (_id INTEGER PRIMARY KEY AUTOINCREMENT," + "codigoarticulo TEXT NOT NULL," + "dia TEXT NOT NULL," + "cantidad INTEGER NOT NULL," + "type TEXT NOT NULL," + "gsaID INTEGER NOT NULL," + "FOREIGN KEY(gsaID) REFERENCES gsa(_id) ON DELETE CASCADE)";

    public GSA_Helper(Context context) {
        super(context, NAME_DB, null, VERSION_DB);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Fiquem la linia de codi SQL que creara la taula de la BD.
        db.execSQL(CREATE_TABLE);
        db.execSQL(CREATE_TABLE_1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("oldVersio",String.valueOf(oldVersion));
        Log.d("newVersion",String.valueOf(newVersion));
        if(oldVersion < 2){
            db.execSQL(CREATE_TABLE_1);
        }
    }
}
