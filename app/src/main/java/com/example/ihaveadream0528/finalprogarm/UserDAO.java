package com.example.ihaveadream0528.finalprogarm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by ihaveadream0528 on 2017/10/15.
 */

public class UserDAO {
    public static final String TABLE_NAME = "user";

    //欄位
    public static final String USER_ID = "user_id";
    public static final String USER_PASSWORD = "user_password";
    public static final String USER_NAME = "user_name";
    public static final String USER_INTRODUCTION = "user_introduction";
    public static final String USER_PERMISSION = "user_permission";
    public static final String LOCAL_ID = "local_id";

    public static final String[] COLUMNS = {USER_ID, USER_PASSWORD, USER_NAME, USER_INTRODUCTION, USER_PERMISSION, LOCAL_ID};

    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME +
            " ( " + USER_ID + " TEXT NOT NULL, " +
            USER_PASSWORD + " TEXT NOT NULL, " +
            USER_NAME + " TEXT NOT NULL, " +
            USER_INTRODUCTION + " TEXT NOT NULL, " +
            USER_PERMISSION + " INTEGER NOT NULL, " +
            LOCAL_ID + " INTEGER NOT NULL PRIMARY KEY);";

    private SQLiteDatabase database;
    public UserDAO(Context context){
        database = DBhelper.getDatabase(context);
    }
    //新增資料
    public long insert(User user){
        ContentValues values = new ContentValues();

        values.put(USER_ID, user.getId());
        values.put(USER_PASSWORD, user.getPassword());
        values.put(USER_NAME, user.getName());
        values.put(USER_INTRODUCTION, user.getIntroduction());
        values.put(USER_PERMISSION, user.getPermission());

        long id = database.insert(TABLE_NAME, null,values);
        return id;
    }
    //修改資料
    public void update(User user){
        ContentValues values = new ContentValues();
        values.put(USER_ID, user.getId());
        values.put(USER_PASSWORD, user.getPassword());
        values.put(USER_NAME, user.getName());
        values.put(USER_INTRODUCTION, user.getIntroduction());
        values.put(USER_PERMISSION, user.getPermission());

        String where = USER_ID + "=" + user.getId();
        database.update(TABLE_NAME, values, where, null);
    }
    //刪除資料
    public void delete(User user){
        database.delete(TABLE_NAME, null , null);
    }

    public User getUser(){
        Cursor result = database.query(TABLE_NAME, COLUMNS, null, null, null, null, null);
        if(result.moveToFirst()){
            User user = new User();
            user.setId(result.getString(result.getColumnIndex(USER_ID)));
            user.setPassword(result.getString(result.getColumnIndex(USER_PASSWORD)));
            user.setName(result.getString(result.getColumnIndex(USER_NAME)));
            user.setIntroduction(result.getString(result.getColumnIndex(USER_INTRODUCTION)));
            user.setPermission(result.getInt(result.getColumnIndex(USER_PERMISSION)));

            return user;
        }
        else{
            return null;
        }
    }
}
