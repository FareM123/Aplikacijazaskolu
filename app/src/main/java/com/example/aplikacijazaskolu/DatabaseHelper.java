package com.example.aplikacijazaskolu;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "uceniciDatabase.db";
    private static final int DATABASE_VERSION = 5;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE ucenici (name TEXT, smjer TEXT, nacSmjer TEXT, vjeronauk TEXT, dsd TEXT)";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 5) {
            // Add the new columns using ALTER TABLE if upgrading from version 1 to 2
            String alterTableQuery = "ALTER TABLE ucenici ADD COLUMN smjer TEXT";
            db.execSQL(alterTableQuery);
            alterTableQuery = "ALTER TABLE ucenici ADD COLUMN nacSmjer TEXT";
            db.execSQL(alterTableQuery);
            alterTableQuery = "ALTER TABLE ucenici ADD COLUMN vjeronauk TEXT";
            db.execSQL(alterTableQuery);
            alterTableQuery = "ALTER TABLE ucenici ADD COLUMN dsd TEXT";
            db.execSQL(alterTableQuery);
        }
        String dropTableQuery = "DROP TABLE IF EXISTS ucenici";
        db.execSQL(dropTableQuery);
        onCreate(db);
    }

    public void insertUcenik(String name, String smjer, String nacSmjer, String vjeronauk, String dsd) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("smjer", smjer);
        values.put("nacSmjer", nacSmjer);
        values.put("vjeronauk", vjeronauk);
        values.put("dsd", dsd);

        db.insert("ucenici", null, values);
        db.close();
    }

    public Cursor getAllUcenici() {
        SQLiteDatabase db = getReadableDatabase();

        return db.query("ucenici", null, null, null, null, null, null);
    }

    public void updateUcenik(int id, String name, String smjer, String nacSmjer, String vjeronauk, String dsd) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("smjer", smjer);
        values.put("nacSmjer", nacSmjer);
        values.put("vjeronauk", vjeronauk);
        values.put("dsd", dsd);

        String whereClause = "_id=?";
        String[] whereArgs = {String.valueOf(id)};

        db.update("ucenici", values, whereClause, whereArgs);
        db.close();
    }

    public void deleteUcenik(int id) {
        SQLiteDatabase db = getWritableDatabase();

        String whereClause = "_id=?";
        String[] whereArgs = {String.valueOf(id)};

        db.delete("ucenici", whereClause, whereArgs);
        db.close();
    }

}