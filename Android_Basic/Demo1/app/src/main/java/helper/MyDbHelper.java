package helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import androidx.annotation.Nullable;
import model.Clazz;
import model.Student;

public class MyDbHelper extends SQLiteOpenHelper {

    private final String TABLE_CLASS = "TableClass";
    private final String COLUMN_CLASS_CODE = "ClassCode";
    private final String COLUMN_CLASS_NAME = "ClassName";


    private final String TABLE_STUDENT = "TableStudent";
    private final String COLUMN_STUDENT_ID = "StudentId";
    private final String COLUMN_STUDENT_NAME = "StudentName";
    private final String COLUMN_STUDENT_DOB = "StudentDoB";
    private final String COLUMN_STUDENT_CLASS_CODE = "StudentClassCode";

    String script = "CREATE TABLE " + TABLE_CLASS + "("
            + COLUMN_CLASS_CODE + " TEXT PRIMARY KEY," + COLUMN_CLASS_NAME + " TEXT )";

    private final String script1 = "CREATE TABLE " + TABLE_STUDENT + "("
            + COLUMN_STUDENT_ID + " INTEGER PRIMARY KEY   AUTOINCREMENT,"
            + COLUMN_STUDENT_NAME + " TEXT,"
            + COLUMN_STUDENT_CLASS_CODE + " TEXT,"
            + COLUMN_STUDENT_DOB + " INTEGER )";

    public MyDbHelper(@Nullable Context context, int version) {
        super(context, "MyDBName", null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(script);
        db.execSQL(script1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLASS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENT);
        onCreate(db);
    }

    public void addClazz(Clazz clazz) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_CLASS_CODE, clazz.getClassCode());
        contentValues.put(COLUMN_CLASS_NAME, clazz.getClassName());
        database.insert(TABLE_CLASS, null,contentValues);
        database.close();
    }

    public List<Clazz> getClazz(){
        List<Clazz> list = new ArrayList<>();
        String query = "Select * from " + TABLE_CLASS;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Clazz note = new Clazz();
                note.setClassCode(cursor.getString(0));
                note.setClassName(cursor.getString(1));
                list.add(note);
            } while (cursor.moveToNext());
        }
        return list;
    }

    public Clazz getClazz(String code){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CLASS, new String[] { COLUMN_CLASS_CODE,
                        COLUMN_CLASS_NAME }, COLUMN_CLASS_CODE + "=?",
                new String[] { String.valueOf(code) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Clazz clazz = new Clazz(cursor.getString(0), cursor.getString(1));
        return clazz;
    }

    public int updateClazz(Clazz clazz) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_CLASS_NAME, clazz.getClassName());
        return db.update(TABLE_CLASS, values, COLUMN_CLASS_CODE + " = ?",
                new String[]{String.valueOf(clazz.getClassCode())});
    }

    public void deleteClazz(Clazz clazz) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CLASS, COLUMN_CLASS_CODE + " = ?",
                new String[] { String.valueOf(clazz.getClassCode()) });
        db.close();
    }



    public void addStudent(Student student) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_STUDENT_NAME, student.getName());
        contentValues.put(COLUMN_STUDENT_CLASS_CODE, student.getClazzCode());
        contentValues.put(COLUMN_STUDENT_DOB, student.getDob().getTime());
        database.insert(TABLE_STUDENT, null,contentValues);
        database.close();
    }

    public List<Student> getStudent(){
        List<Student> list = new ArrayList<>();
        String query = "Select * from " + TABLE_STUDENT;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Student st = new Student();
                st.setStudentId(Integer.parseInt(cursor.getString(0)));
                st.setName(cursor.getString(1));
                st.setClazzCode(cursor.getString(2));
                st.setDob(new Date(cursor.getLong(3)));
                list.add(st);
            } while (cursor.moveToNext());
        }
        return list;
    }

}
