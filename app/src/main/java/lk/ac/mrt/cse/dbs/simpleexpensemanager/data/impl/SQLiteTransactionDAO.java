package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Transaction;

public class SQLiteTransactionDAO implements TransactionDAO {
    private SQLiteHelper sqliteHelper;


    public SQLiteTransactionDAO(Context context){
        sqliteHelper = new SQLiteHelper(context);
    }

    @Override
    public void logTransaction(Date date, String accountNo, ExpenseType expenseType, double amount) {
        SQLiteDatabase db = sqliteHelper.getWritableDatabase();


        String query = String.format("INSERT OR IGNORE INTO TRANSACTION (%s, %s, %s, %s) VALUES (?, ?, ?, ?)",
                Transaction.COLUMN_DATE,
                Transaction.COLUMN_ACCOUNTNO,
                Transaction.COLUMN_EXPENSETYPE,
                Transaction.COLUMN_AMOUNT);

        db.execSQL(query, new Object[]{
                date.getTime(),
                accountNo,
                expenseType,
                amount});
    }

    @Override
    public List<Transaction> getAllTransactionLogs() {
        SQLiteDatabase db = sqliteHelper.getWritableDatabase();

        String query = String.format("SELECT %s, %s, %s, %s FROM TRANSACTION)",
                Transaction.COLUMN_DATE,
                Transaction.COLUMN_ACCOUNTNO,
                Transaction.COLUMN_EXPENSETYPE,
                Transaction.COLUMN_AMOUNT);

        List<Transaction> TransactionList = new ArrayList<>();

        final Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                TransactionList.add(new Transaction(
                        new Date(cursor.getLong(0)),
                        cursor.getString(1),
                        Enum.valueOf(ExpenseType.class, cursor.getString(2)),
                        cursor.getDouble(3)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return TransactionList;
    }

    @Override
    public List<Transaction> getPaginatedTransactionLogs(int limit) {

        SQLiteDatabase db = sqliteHelper.getWritableDatabase();

        String query = String.format("SELECT %s, %s, %s, %s FROM TRANSACTION LIMIT %s)",
                Transaction.COLUMN_DATE,
                Transaction.COLUMN_ACCOUNTNO,
                Transaction.COLUMN_EXPENSETYPE,
                Transaction.COLUMN_AMOUNT,
                limit);

        List<Transaction> TransactionList = new ArrayList<>();

        final Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                TransactionList.add(new Transaction(
                        new Date(cursor.getLong(0)),
                        cursor.getString(1),
                        Enum.valueOf(ExpenseType.class, cursor.getString(2)),
                        cursor.getDouble(3)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return TransactionList;
    }
}
