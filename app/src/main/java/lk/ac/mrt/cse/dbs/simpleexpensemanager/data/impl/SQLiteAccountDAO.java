package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.exception.InvalidAccountException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;

public class SQLiteAccountDAO implements AccountDAO {

    private SQLiteHelper sqliteHelper;


    public SQLiteAccountDAO(Context context) {
        sqliteHelper = new SQLiteHelper(context);
    }


    @Override
    public List<String> getAccountNumbersList() {

        SQLiteDatabase db = sqliteHelper.getWritableDatabase();

        List<String> AccountNumbers = new ArrayList<>();

        String selectQuery = String.format("SELECT %s FROM ACCOUNT",Account.COLUMN_ACOOUNTNO) ;

        Cursor cursor = db.rawQuery(selectQuery,null);

        if(cursor.moveToFirst()){
            do{
                String AccountNumber =(cursor.getString(0));
                AccountNumbers.add(AccountNumber);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return AccountNumbers;
    }

    @Override
    public List<Account> getAccountsList() {

        SQLiteDatabase db = sqliteHelper.getWritableDatabase();

        List<Account> AccountList = new ArrayList<>();

        String selectQuery = String.format("SELECT %s %s %s %s FROM ACCOUNT ",Account.COLUMN_ACOOUNTNO,Account.COLUMN_BANKNAME,Account.COLUMN_HOLDERNAME,Account.COLUMN_BALANCE);

        Cursor cursor = db.rawQuery(selectQuery,null);

        if(cursor.moveToFirst()){
            do{
                AccountList.add(new Account(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getDouble(3)));
            }while(cursor.moveToNext());
        }
        cursor.close();
        return AccountList;
    }

    @Override
    public Account getAccount(String accountNo) throws InvalidAccountException {

        SQLiteDatabase db = sqliteHelper.getReadableDatabase();

        String selectQuery = String.format("SELECT %s %s %s %s FROM ACCOUNT WHERE %s =" +accountNo,
                Account.COLUMN_ACOOUNTNO,Account.COLUMN_BANKNAME,Account.COLUMN_HOLDERNAME,Account.COLUMN_BALANCE,Account.COLUMN_ACOOUNTNO);

        Cursor cursor = db.rawQuery(selectQuery,null);

        if(!cursor.moveToFirst()){
            throw new InvalidAccountException("Account " + accountNo + " is invalid");
        }

        Account account = new Account(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getDouble(3));
        cursor.close();

        return account;

    }

    @Override
    public void addAccount(Account account) {
        SQLiteDatabase db = sqliteHelper.getWritableDatabase();

        String query = String.format("INSERT OR IGNORE INTO ACCOUNT (%s %s %s %s) VALUES ? ? ? ?",
                Account.COLUMN_ACOOUNTNO,
                Account.COLUMN_BANKNAME,
                Account.COLUMN_HOLDERNAME,
                Account.COLUMN_BALANCE);

        db.execSQL(query, new Object[]{
                account.getAccountNo(),
                account.getBankName(),
                account.getAccountHolderName(),
                account.getBalance()});


    }

    @Override
    public void removeAccount(String accountNo) throws InvalidAccountException {
        getAccount(accountNo);
        SQLiteDatabase db = sqliteHelper.getWritableDatabase();

        String query = String.format("DELETE FROM ACCOUNT WHERE %S = ?",
                Account.COLUMN_ACOOUNTNO);

        db.execSQL(query, new Object[]{accountNo});
    }

    @Override
    public void updateBalance(String accountNo, ExpenseType expenseType, double amount) throws InvalidAccountException {

        getAccount(accountNo);
        SQLiteDatabase db = sqliteHelper.getWritableDatabase();

        String query = null;
        switch (expenseType){
            case EXPENSE:
                query = "UPDATE ACCOUNT SET "+Account.COLUMN_BALANCE+" = "+Account.COLUMN_BALANCE+" - ? WHERE "+Account.COLUMN_ACOOUNTNO+" = ?";
            case INCOME:
                query = "UPDATE ACCOUNT SET "+Account.COLUMN_BALANCE+" = "+Account.COLUMN_BALANCE+" + ? WHERE "+Account.COLUMN_ACOOUNTNO+" = ?";
        }

//        query = String.format(query,
//                Account.COLUMN_BALANCE,
//                Account.COLUMN_BALANCE,
//                Account.COLUMN_ACOOUNTNO);
        db.execSQL(query, new Object[]{amount,accountNo});
    }
}
