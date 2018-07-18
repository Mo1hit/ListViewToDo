package com.example.mohit.listviewtodo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ToDoOpenHelper extends SQLiteOpenHelper
{

    public static ToDoOpenHelper instance;

    public static final String DATABASE_NAME="todo_db";
    public static final int VERSION=1;

    public ToDoOpenHelper(Context context)
    {
        super(context, DATABASE_NAME,null, VERSION);
    }

    public static ToDoOpenHelper getInstance(Context context)
    {
        if(instance==null)
        {
            instance=new ToDoOpenHelper(context.getApplicationContext());
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        String todoSql="CREATE TABLE todo(id INTEGER PRIMARY KEY AUTOINCREMENT,title TEXT,content TEXT,time TEXT, date TEXT)";
        sqLiteDatabase.execSQL(todoSql);
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}