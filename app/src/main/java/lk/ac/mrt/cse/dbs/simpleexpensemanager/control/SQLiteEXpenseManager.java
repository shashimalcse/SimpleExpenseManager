package lk.ac.mrt.cse.dbs.simpleexpensemanager.control;

import android.content.Context;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.SQLiteAccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.SQLiteTransactionDAO;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;

public class SQLiteEXpenseManager extends  ExpenseManager {

    Context context;
    public SQLiteEXpenseManager(Context context) {

        this.context=context;
        setup();
    }

    @Override
    public void setup() {
        /*** Begin generating dummy data for In-Memory implementation ***/
//
        TransactionDAO SQLiteTransactionDAO = new SQLiteTransactionDAO(context);
        setTransactionsDAO(SQLiteTransactionDAO);

        AccountDAO SQLiteAccountDAO = new SQLiteAccountDAO(context);
        setAccountsDAO(SQLiteAccountDAO);

        Account dummyAcct1 = new Account("12345A", "Yoda Bank", "Anakin Skywalker", 10000.0);
        Account dummyAcct2 = new Account("78945Z", "Clone BC", "Obi-Wan Kenobi", 80000.0);
        getAccountsDAO().addAccount(dummyAcct1);
        getAccountsDAO().addAccount(dummyAcct2);


        /*** End ***/
    }
}