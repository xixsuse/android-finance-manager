package entities;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import data.FinancialManager;
import data.FinancialManagerDbHelper;
import interfaces.DatabaseHelperFunctions;

public class Constraint implements DatabaseHelperFunctions {

    private int constraintId;
    private Double moneyLimit;
    private String dateOfBegin;
    private String dateOfEnd;
    private Double warningMoneyBorder;
    private String isFinished;

    private Account parentAccount;

    public Constraint() {}

    public Constraint(Double moneyLimit, String dateOfBegin, String dateOfEnd,
                      Double warningMoneyBorder, String isFinished, Account parentAccount) {
        this.moneyLimit = moneyLimit;
        this.dateOfBegin = dateOfBegin;
        this.dateOfEnd = dateOfEnd;
        this.warningMoneyBorder = warningMoneyBorder;
        this.isFinished = isFinished;
        this.parentAccount = parentAccount;
    }

    public Constraint(int constraintId, Double moneyLimit, String dateOfBegin,
                      String dateOfEnd, Double warningMoneyBorder, String isFinished, Account parentAccount) {
        this.constraintId = constraintId;
        this.moneyLimit = moneyLimit;
        this.dateOfBegin = dateOfBegin;
        this.dateOfEnd = dateOfEnd;
        this.warningMoneyBorder = warningMoneyBorder;
        this.isFinished = isFinished;
        this.parentAccount = parentAccount;
    }

    @Override
    public void writeToDatabase(FinancialManagerDbHelper dbHelper) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(FinancialManager.Constraint.COLUMN_MONEY_LIMIT, this.moneyLimit);
        values.put(FinancialManager.Constraint.COLUMN_DATE_OF_BEGIN, this.dateOfBegin);
        values.put(FinancialManager.Constraint.COLUMN_DATE_OF_END, this.dateOfEnd);
        values.put(FinancialManager.Constraint.COLUMN_WARNING_MONEY_BORDER, this.warningMoneyBorder);
        values.put(FinancialManager.Constraint.COLUMN_IS_FINISHED, this.isFinished);
        values.put(FinancialManager.Constraint.COLUMN_ACCOUNT_ID, this.parentAccount.getAccountId());

        this.constraintId = (int) db.insert(FinancialManager.Constraint.TABLE_NAME, null, values);

    }

    @Override
    public void updateToDatabase(FinancialManagerDbHelper dbHelper, int rowId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(FinancialManager.Constraint.COLUMN_MONEY_LIMIT, this.moneyLimit);
        values.put(FinancialManager.Constraint.COLUMN_DATE_OF_BEGIN, this.dateOfBegin);
        values.put(FinancialManager.Constraint.COLUMN_DATE_OF_END, this.dateOfEnd);
        values.put(FinancialManager.Constraint.COLUMN_WARNING_MONEY_BORDER, this.warningMoneyBorder);
        values.put(FinancialManager.Constraint.COLUMN_IS_FINISHED, this.isFinished);
        values.put(FinancialManager.Constraint.COLUMN_ACCOUNT_ID, this.parentAccount.getAccountId());

        long amountOfUpdated = db.update(FinancialManager.Constraint.TABLE_NAME, values, "constraint_id=?",
                new String[] {Integer.toString(rowId)});
    }

    @Override
    public void updateFromDatabase(FinancialManagerDbHelper dbHelper) {
        readFromDatabase(dbHelper, this.constraintId);
    }

    public static ArrayList<Constraint> readAllFromDatabase(FinancialManagerDbHelper dbHelper, int accId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT constraint_id AS _id, * FROM constraints WHERE account_id=?",
                new String[] {Integer.toString(accId)});
        int idIndex = cursor.getColumnIndex(FinancialManager.Constraint._ID);

        ArrayList<Constraint> result = new ArrayList<>();

        while (cursor.moveToNext()) {
            int billId = cursor.getInt(idIndex);
            Constraint constraintToRead = new Constraint();
            constraintToRead.readFromDatabase(dbHelper, billId);
            result.add(constraintToRead);
        }

        return result;
    }

    @Override
    public void readFromDatabase(FinancialManagerDbHelper dbHelper, int entryId) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT constraint_id AS _id, * FROM constraints " +
                "WHERE constraint_id=?", new String[]{String.valueOf(entryId)});

        int moneyLimitIndex = cursor.getColumnIndex(FinancialManager.Constraint.COLUMN_MONEY_LIMIT);
        int dateOfBeginIndex = cursor.getColumnIndex(FinancialManager.Constraint.COLUMN_DATE_OF_BEGIN);
        int dateOfEndIndex = cursor.getColumnIndex(FinancialManager.Constraint.COLUMN_DATE_OF_END);
        int warningBorderIndex = cursor.getColumnIndex(FinancialManager.Constraint.COLUMN_WARNING_MONEY_BORDER);
        int isFinishedIndex = cursor.getColumnIndex(FinancialManager.Constraint.COLUMN_IS_FINISHED);
        int accountIdIndex = cursor.getColumnIndex(FinancialManager.Constraint.COLUMN_ACCOUNT_ID);
        int constraintIdIndex = cursor.getColumnIndex(FinancialManager.Constraint._ID);

        cursor.moveToNext();
        this.moneyLimit = cursor.getDouble(moneyLimitIndex);
        this.dateOfBegin = cursor.getString(dateOfBeginIndex);
        this.dateOfEnd = cursor.getString(dateOfEndIndex);
        this.warningMoneyBorder = cursor.getDouble(warningBorderIndex);
        this.isFinished = cursor.getString(isFinishedIndex);

        this.parentAccount = new Account();
        this.parentAccount.readFromDatabase(dbHelper, cursor.getInt(accountIdIndex));

        this.constraintId = cursor.getInt(constraintIdIndex);

        cursor.close();
    }

    @Override
    public void deleteFromDatabase(FinancialManagerDbHelper dbHelper) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String whereClause = "constraint_id=?";
        String[] whereArgs = new String[] { String.valueOf(this.constraintId) };
        db.delete(FinancialManager.Constraint.TABLE_NAME, whereClause, whereArgs);
    }

    public int getConstraintId() {
        return constraintId;
    }

    public void setConstraintId(int constraintId) {
        this.constraintId = constraintId;
    }

    public Double getMoneyLimit() {
        return moneyLimit;
    }

    public void setMoneyLimit(Double moneyLimit) {
        this.moneyLimit = moneyLimit;
    }

    public String getDateOfBegin() {
        return dateOfBegin;
    }

    public void setDateOfBegin(String dateOfBegin) {
        this.dateOfBegin = dateOfBegin;
    }

    public String getDateOfEnd() {
        return dateOfEnd;
    }

    public void setDateOfEnd(String dateOfEnd) {
        this.dateOfEnd = dateOfEnd;
    }

    public Double getWarningMoneyBorder() {
        return warningMoneyBorder;
    }

    public void setWarningMoneyBorder(Double warningMoneyBorder) {
        this.warningMoneyBorder = warningMoneyBorder;
    }

    public String getIsFinished() {
        return isFinished;
    }

    public void setIsFinished(String isFinished) {
        this.isFinished = isFinished;
    }

    public Account getParentAccount() {
        return parentAccount;
    }

    public void setParentAccount(Account parentAccount) {
        this.parentAccount = parentAccount;
    }
}