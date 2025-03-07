/*
 * Copyright 2015 Department of Computer Science and Engineering, University of Moratuwa.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *                  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model;

import java.util.Date;

/**
 * This POJO holds the information regarding a single transaction.
 */
public class Transaction {
    public static final String TABLE_NAME = "TRANSACTION0";
    public static final String COLUMN_ACCOUNTNO = "accountNo";
    public static final String COLUMN_EXPENSETYPE = "expenseType";
    public static final String COLUMN_AMOUNT = "amount";
    public static final String COLUMN_DATE = "date";




    private Date date;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public ExpenseType getExpenseType() {
        return expenseType;
    }

    public void setExpenseType(ExpenseType expenseType) {
        this.expenseType = expenseType;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    private String accountNo;
    private ExpenseType expenseType;
    private double amount;




    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("
                    + COLUMN_DATE + " VARCHAR PRIMARY KEY ,"
                    + COLUMN_ACCOUNTNO + " VARCHAR,"
                    + COLUMN_EXPENSETYPE + " VARCHAR,"
                    + COLUMN_AMOUNT + " NUMERIC"
                    + ")";


    public Transaction(Date date, String accountNo,
                       ExpenseType expenseType, double amount) {
        this.date = date;
        this.accountNo = accountNo;
        this.expenseType = expenseType;
        this.amount = amount;
    }
}
