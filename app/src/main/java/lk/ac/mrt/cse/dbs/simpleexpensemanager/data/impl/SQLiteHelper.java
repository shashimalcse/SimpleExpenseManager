package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Transaction;

public class SQLiteHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 2;

    // Database Name
    private static final String DATABASE_NAME = "170568";


    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(Account.CREATE_TABLE);
        db.execSQL(Transaction.CREATE_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String message = String.format("Upgrading db from %s to %s", Integer.toString(oldVersion), Integer.toString(newVersion));
        Log.w(this.getClass().getName(), message);
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + Account.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Transaction.TABLE_NAME);


        // Create tables again
        onCreate(db);
    }
}
