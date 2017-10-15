package com.example.ihaveadream0528.finalprogarm;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBhelper extends SQLiteOpenHelper{

    //這裡有點問題 一支手機無法裝兩個此應用程式 會導致使用相同資料庫
    public static final String DATABASE_NAME = "database.db";
    public static final int VERSION = 1;
    private static SQLiteDatabase database;

    public DBhelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(UserDAO.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXIST " + UserDAO.TABLE_NAME);
    }

    @Override
    public SQLiteDatabase getWritableDatabase(){
        return super.getWritableDatabase();
    }

    public static SQLiteDatabase getDatabase(Context context){
        if (database == null || !database.isOpen()){
            database = new DBhelper(context, DATABASE_NAME, null, VERSION).getWritableDatabase();
        }
        return database;
    }
}
