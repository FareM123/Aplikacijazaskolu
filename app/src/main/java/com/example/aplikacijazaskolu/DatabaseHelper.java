package com.example.aplikacijazaskolu;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



public class DatabaseHelper extends SQLiteOpenHelper{
    private static final String DATABASE_NAME = "uceniciDatabase.db";
    private static final int DATABASE_VERSION = 5;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE ucenici (name TEXT, smjer Text, nacSmjer Text, vjeronauk Text, dsd Text)";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 5) {
        // Add the new column using ALTER TABLE if upgrading from version 1 to 2
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


}
