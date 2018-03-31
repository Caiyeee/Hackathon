package com.example.caiye.test;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;


public class DBOperation {
    private DBHelper dbHelper;
    public DBOperation(Context context){
        dbHelper = new DBHelper(context);
    }

    public int insert(Person person){
        //打开连接，写入数据
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("name",person.getName());
        values.put("tags_init",person.getTags_init());
        values.put("tags_add",person.getTags_add());
        long ID = db.insert("FRIENDS",null,values);
        db.close();
        return (int)ID;
    }

    public void delete(int ID){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("FRIENDS","id=?",new String[]{String.valueOf(ID)});
        db.close();
    }

    public void update(Person person){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

    //    values.put("id",person.getId());
        values.put("name",person.getName());
        values.put("tags_init",person.getTags_init());
        values.put("tags_add",person.getTags_add());

        db.update("FEIENDS",values,"id=?",new String[]{ String.valueOf(person.getId())});
        db.close();
    }

    public ArrayList<Person> query(String keyword){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT id, name, tags_init, tags_add FROM FRIENDS WHERE name LIKE '%" + keyword
                + "% or tags_init like %" + keyword + "% or tags_add like %" + keyword  + "%";
        ArrayList<Person>  arrayList = new ArrayList<Person>();
        Cursor cursor = db.rawQuery(query,null);
        if(cursor.moveToFirst()){
            do{
                 Person person = new Person();
                 person.setId(cursor.getInt(cursor.getColumnIndex("id")));
                 person.setName(cursor.getString(cursor.getColumnIndex("name")));
                 person.setTags_init(cursor.getString(cursor.getColumnIndex("tags_init")));
                 person.addTags_add(cursor.getString(cursor.getColumnIndex("tags_add")));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return arrayList;
    }

    public ArrayList<Person> getAllFriends(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT id, name, tags_init, tags_add FROM FRIENDS";
        ArrayList<Person>  arrayList = new ArrayList<Person>();
        Cursor cursor = db.rawQuery(query,null);
        if(cursor.moveToFirst()){
            do{
                Person person = new Person();
                person.setId(cursor.getInt(cursor.getColumnIndex("id")));
                person.setName(cursor.getString(cursor.getColumnIndex("name")));
                person.setTags_init(cursor.getString(cursor.getColumnIndex("tags_init")));
                person.addTags_add(cursor.getString(cursor.getColumnIndex("tags_add")));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return arrayList;
    }

    public Person getPersonById(int id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT id, name, tags_init, tags_add FROM FRIENDS WHERE id=" + id;
        int cnt = 0;
        Person person = new Person();
        Cursor cursor = db.rawQuery(query,new String[]{String.valueOf(id)});
        if(cursor.moveToFirst()){
            do{
                person.setId(cursor.getInt(cursor.getColumnIndex("id")));
                person.setName(cursor.getString(cursor.getColumnIndex("name")));
                person.setTags_init(cursor.getString(cursor.getColumnIndex("tags_init")));
                person.addTags_add(cursor.getString(cursor.getColumnIndex("tags_add")));
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return person;
    }
}
