package com.example.gioenmark.myapplication.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by Giovski on 08-04-16.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    public static SQLiteDatabase mSQLDB;
    private static DatabaseHelper mInstance;			// SINGLETON TRUC
    public static final String dbName = "cijferlijst.db";	// Naam van je DB
    public static final int dbVersion = 1;				// Versie nr van je db.

    public DatabaseHelper(Context ctx) {				// De constructor doet niet veel meer dan ...
        super(ctx, dbName, null, dbVersion);			// … de super constructor aan te roepen.
    }

    public static synchronized DatabaseHelper getHelper (Context ctx){  // SYNCRONIZED TRUC
        if (mInstance == null){
            mInstance = new DatabaseHelper(ctx);
            mSQLDB = mInstance.getWritableDatabase();
        }
        return mInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + Databaseinfo.CourseTables.COURSE + " (" +
                BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Databaseinfo.CourseColumn.NAME + " TEXT," + Databaseinfo.CourseColumn.ECTS + " TEXT," +
                Databaseinfo.CourseColumn.PERIOD + " TEXT," + Databaseinfo.CourseColumn.GRADE + " TEXT," + Databaseinfo.CourseColumn.GEHAALD + " TEXT);"
        );
    }




    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ Databaseinfo.CourseTables.COURSE);	 // GOOI ALLES WEG
        onCreate(db);									 // EN CREER HET OPNIEUW
    }

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version ){
        super(context,name,factory, version);
    }

    public void insert(String table, String nullColumnHack, ContentValues values){
        mSQLDB.insert(table, nullColumnHack, values);
    }

    public Cursor query(String table, String[] columns, String selection, String[] selectArgs, String groupBy, String having, String orderBy){
        return mSQLDB.query(table, columns, selection, selectArgs, groupBy, having, orderBy);
    }


}


