package com.example.myapplication.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.myapplication.Database.MyDatabase;
import com.example.myapplication.Models.Category;
import com.example.myapplication.Models.IncomeExpense;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.myapplication.Utilities.Constants.*;

public class IncomeExpenseDAO implements IIncomeExpense{
    MyDatabase db;
    public IncomeExpenseDAO(Context ctx){
        db = MyDatabase.getInstance(ctx);
    }


    @Override
    public List<IncomeExpense> get() {
        List<IncomeExpense> list = new ArrayList<>();
        String q = "SELECT "+COLUMN_INCOME_EXPENSE_ID+", " +
                ""+COLUMN_INCOME_EXPENSE_NAME+", " +
                ""+COLUMN_INCOME_EXPENSE_DESCRIPTION+", " +
                ""+COLUMN_INCOME_EXPENSE_CREATED_DATE+", " +
                ""+COLUMN_INCOME_EXPENSE_AMOUNT+", " +
                ""+COLUMN_INCOME_EXPENSE_CATEGORY_ID+", " +
                ""+COLUMN_FLAG+" " +
                "FROM  "+TABLE_INCOME_EXPENSE+" ";
        SQLiteDatabase database = db.getReadableDatabase();
        Cursor cursor = database.rawQuery(q, null);
        try {
            if (cursor.moveToFirst()){
                while (cursor.isAfterLast() == false){
                    Integer id = cursor.getInt(cursor.getColumnIndex(COLUMN_INCOME_EXPENSE_ID));
                    String name = cursor.getString(cursor.getColumnIndex(COLUMN_INCOME_EXPENSE_NAME));
                    String description = cursor.getString(cursor.getColumnIndex(COLUMN_INCOME_EXPENSE_DESCRIPTION));
                    Long dateInt = cursor.getLong(cursor.getColumnIndex(COLUMN_INCOME_EXPENSE_CREATED_DATE));
                    Date createdDate = new Date(dateInt);
//                    Date createdDate = new SimpleDateFormat("dd/MM/yyyy").parse());

                    Double amount = cursor.getDouble(cursor.getColumnIndex(COLUMN_INCOME_EXPENSE_AMOUNT));
                    Integer categoryId = cursor.getInt(cursor.getColumnIndex(COLUMN_INCOME_EXPENSE_CATEGORY_ID));
                    Integer flag = cursor.getInt(cursor.getColumnIndex(COLUMN_FLAG));

                    IncomeExpense incomeExpense = new IncomeExpense();
                    incomeExpense.setId(id);
                    incomeExpense.setAmount(amount);
                    incomeExpense.setCategoryId(categoryId);
                    incomeExpense.setCreatedDate(createdDate);
                    incomeExpense.setDescription(description);
                    incomeExpense.setFlag(flag);
                    incomeExpense.setName(name);

                    list.add(incomeExpense);
                    cursor.moveToNext();
                }
            }
        }catch(Exception e) {
            Log.e("Get Income expense error: ", e.getMessage());
        } finally {
            if (cursor!= null && !cursor.isClosed()){
                cursor.close();
            }
        }
        return list;
    }

    @Override
    public List<IncomeExpense> get(int _flag) {
        List<IncomeExpense> list = new ArrayList<>();
        String q = "SELECT "+COLUMN_INCOME_EXPENSE_ID+", " +
                ""+COLUMN_INCOME_EXPENSE_NAME+", " +
                ""+COLUMN_INCOME_EXPENSE_DESCRIPTION+", " +
                ""+COLUMN_INCOME_EXPENSE_CREATED_DATE+", " +
                ""+COLUMN_INCOME_EXPENSE_AMOUNT+", " +
                ""+COLUMN_INCOME_EXPENSE_CATEGORY_ID+", " +
                ""+COLUMN_FLAG+" " +
                "FROM  "+TABLE_INCOME_EXPENSE+" WHERE  "+COLUMN_FLAG+" = ?  ";
        SQLiteDatabase database = db.getReadableDatabase();
        Cursor cursor = database.rawQuery(q, new String[]{String.valueOf(_flag)});
        try {
            if (cursor.moveToFirst()){
                while (cursor.isAfterLast() == false){
                    Integer id = cursor.getInt(cursor.getColumnIndex(COLUMN_INCOME_EXPENSE_ID));
                    String name = cursor.getString(cursor.getColumnIndex(COLUMN_INCOME_EXPENSE_NAME));
                    String description = cursor.getString(cursor.getColumnIndex(COLUMN_INCOME_EXPENSE_DESCRIPTION));
                    Long dateInt = cursor.getLong(cursor.getColumnIndex(COLUMN_INCOME_EXPENSE_CREATED_DATE));
                    Date createdDate = new Date(dateInt);

//                    Date createdDate = new SimpleDateFormat("dd/MM/yyyy").parse(cursor.getString(cursor.getColumnIndex(COLUMN_INCOME_EXPENSE_CREATED_DATE)));
                    Double amount = cursor.getDouble(cursor.getColumnIndex(COLUMN_INCOME_EXPENSE_AMOUNT));
                    Integer categoryId = cursor.getInt(cursor.getColumnIndex(COLUMN_INCOME_EXPENSE_CATEGORY_ID));
                    Integer flag = cursor.getInt(cursor.getColumnIndex(COLUMN_FLAG));

                    IncomeExpense incomeExpense = new IncomeExpense();
                    incomeExpense.setId(id);
                    incomeExpense.setAmount(amount);
                    incomeExpense.setCategoryId(categoryId);
                    incomeExpense.setCreatedDate(createdDate);
                    incomeExpense.setDescription(description);
                    incomeExpense.setFlag(flag);
                    incomeExpense.setName(name);

                    list.add(incomeExpense);
                    cursor.moveToNext();
                }
            }
        }catch(Exception e) {
            Log.e("Get Income expense error: ", e.getMessage());
        } finally {
            if (cursor!= null && !cursor.isClosed()){
                cursor.close();
            }
        }
        return list;
    }

    @Override
    public void insert(IncomeExpense model) {
        SQLiteDatabase database =  db.getWritableDatabase();
        database.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(COLUMN_INCOME_EXPENSE_NAME, model.getName());
            values.put(COLUMN_INCOME_EXPENSE_DESCRIPTION, model.getDescription());
            values.put(COLUMN_INCOME_EXPENSE_CREATED_DATE, model.getCreatedDate().getTime());
            values.put(COLUMN_INCOME_EXPENSE_AMOUNT, model.getAmount());
            values.put(COLUMN_INCOME_EXPENSE_CATEGORY_ID, model.getCategoryId());
            values.put(COLUMN_FLAG, model.getFlag());
            database.insertOrThrow(TABLE_INCOME_EXPENSE,null, values);
            database.setTransactionSuccessful();
        }catch (Exception e){
            Log.e("Insert IncomeExpense error: ", e.getMessage());
        } finally {
            database.endTransaction();
        }
    }

    @Override
    public void update(IncomeExpense model) {
        SQLiteDatabase database =  db.getWritableDatabase();
        database.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(COLUMN_INCOME_EXPENSE_NAME, model.getName());
            values.put(COLUMN_INCOME_EXPENSE_DESCRIPTION, model.getDescription());
            values.put(COLUMN_INCOME_EXPENSE_CREATED_DATE, model.getCreatedDate().getTime());
            values.put(COLUMN_INCOME_EXPENSE_AMOUNT, model.getAmount());
            values.put(COLUMN_INCOME_EXPENSE_CATEGORY_ID, model.getCategoryId());
            values.put(COLUMN_FLAG, model.getFlag());

            database.update(TABLE_INCOME_EXPENSE, values,
                    COLUMN_INCOME_EXPENSE_ID + " = ?",
                    new String[]{String.valueOf(model.getId())});
            database.setTransactionSuccessful();
        }catch (Exception e){
            Log.e("Update IncomeExpense error: ", e.getMessage());
        } finally {
            database.endTransaction();
        }
    }

    @Override
    public void delete(Integer id) {
        SQLiteDatabase database =  db.getWritableDatabase();
        database.beginTransaction();
        try {
            database.delete(TABLE_INCOME_EXPENSE, COLUMN_INCOME_EXPENSE_ID + " = ?",
                    new String[]{String.valueOf(id)});
            database.setTransactionSuccessful();
        }catch (Exception e){
            Log.e("Delete IncomeExpense error: ", e.getMessage());
        } finally {
            database.endTransaction();
        }
    }
}
