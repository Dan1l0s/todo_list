package ru.dan1l0s.project;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import ru.dan1l0s.project.task.Task;

public class DBClient extends SQLiteOpenHelper {

    private static final String Name = "DataBase";
    private static final String Desc = "Description";
    private static final String Table = "Todo";
    private static final String ID = "id";
    private static final String Task = "task";
    private static final String Status = "status";
    //private static final  String CreateTable = "CREATE TABLE " + Table + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + Task + "TEXT, " + Status + " INTEGER)";

    private SQLiteDatabase database;

    public DBClient(Context context) {
        super(context, Name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL("CREATE TABLE " + Table + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + Task + "TEXT, " + Status + " INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        database.execSQL("DROP TABLE IF EXISTS " + Table);
        onCreate(database);
    }

    public void openDataBase() {
        database = this.getWritableDatabase();
    }

    public void insertTask(Task task) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Task, task.getName());
        contentValues.put(Status, task.getStatus());
        database.insert(Table, null, contentValues);
    }



    @SuppressLint("Range")
    public List<Task> getList() {
        List<Task> list = new ArrayList<>();
        Cursor cur = null;
        database.beginTransaction();
        try {
            database.query(Table, null, null, null, null, null, null, null); // TODO: database order by status and name
            if (cur != null) {
                if (cur.moveToFirst()) {
                    do {
                        Task task = new Task();
                        task.setId(cur.getInt(cur.getColumnIndex(ID))); //FIXME: doesn't work without @SupressLint = "RANGE" annotation
                        task.setName(cur.getString(cur.getColumnIndex(Task))); //FIXME: doesn't work without @SupressLint = "RANGE" annotation
                        task.setStatus(cur.getInt(cur.getColumnIndex(Status))); //FIXME: doesn't work without @SupressLint = "RANGE" annotation
                        list.add(task);
                    } while (cur.moveToNext());
                }
            }
        } finally {
            database.endTransaction();
            cur.close();
        }
        return list;
    }

    public void updateStatus(int id, int status)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Status, status);
        database.update(Table, contentValues, ID + "=?", new String[] {String.valueOf(id)});
    }

    public void updateTask(int id, String task)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Task, task);
        database.update(Table, contentValues, ID + "=?", new String[] {String.valueOf(id)});
    }
    public void deleteTask(int id)
    {
        database.delete(Table, ID + "=?", new String[] {String.valueOf(id)});
    }
}
