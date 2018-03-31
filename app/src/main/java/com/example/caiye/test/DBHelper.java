package com.example.caiye.test;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/** 这个文件用来数据库建表或者删除重建 **/

public class DBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME = "MySQLite.db";
    public DBHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建数据库
        String CREATE_TABLE = "CREATE TABLE FRIENDS ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "name TEXT,"
                + "tags_init TEXT,"
                +"tags_add TEXT)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //如果旧表存在，删除，数据会丢失
        db.execSQL("DROP TABLE IF EXISTS FRIENDS");
        //再次创建表
        onCreate(db);
    }
}
