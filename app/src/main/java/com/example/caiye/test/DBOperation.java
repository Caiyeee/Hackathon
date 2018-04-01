package com.example.caiye.test;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;


public class DBOperation {
    private DBHelper dbHelper;
    public DBOperation(Context context){
        dbHelper = new DBHelper(context);
    }

    public int insert(String name,String tags_init){
        //打开连接，写入数据
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("name",name);
        values.put("tags_init",tags_init);
        long ID = db.insert("FRIENDS",null,values);
        db.close();
        Log.e("ID",":"+ID);
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

        db.update("FRIENDS",values,"id=?",new String[]{ String.valueOf(person.getId())});
        db.close();
    }

    public ArrayList<Person> queryName(String name){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("FRIENDS",null,"name=?",new String[]{name},null,null,null);
        ArrayList<Person>  arrayList = new ArrayList<Person>();
        if(cursor.moveToFirst()){
            do{
                Person person = new Person();
                person.setId(cursor.getInt(cursor.getColumnIndex("id")));
                person.setName(cursor.getString(cursor.getColumnIndex("name")));
                person.setTags_init(cursor.getString(cursor.getColumnIndex("tags_init")));
                person.addTags_add(cursor.getString(cursor.getColumnIndex("tags_add")));
                arrayList.add(person);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return arrayList;
    }

    public ArrayList<Person> query(String keyword){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT id, name, tags_init, tags_add FROM FRIENDS WHERE name LIKE \'%" + keyword
                + "%\' or tags_init like \'%" + keyword + "%\' or tags_add like \'%" + keyword  + "%\'";
        Log.e("query",query);
        ArrayList<Person>  arrayList = new ArrayList<Person>();
        Cursor cursor = db.rawQuery(query,null);
        if(cursor.moveToFirst()){
            Log.e("cursor","has");
            do{
                 Person person = new Person();
                 person.setId(cursor.getInt(cursor.getColumnIndex("id")));
                 person.setName(cursor.getString(cursor.getColumnIndex("name")));
                 person.setTags_init(cursor.getString(cursor.getColumnIndex("tags_init")));
                 person.addTags_add(cursor.getString(cursor.getColumnIndex("tags_add")));
                 arrayList.add(person);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        Log.e("list","size"+arrayList.size());
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
                arrayList.add(person);
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
