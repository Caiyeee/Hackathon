package com.example.caiye.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by caiye on 2018/3/31.
 */

public class Person {
    private int id;
    private String name;
    private String tags_init;
    private String tags_add;

    public Person(int id,String name, String tags_init){
        if(!name.isEmpty())
            this.name = name;
        else
            this.name = "";

        if(!tags_init.isEmpty())
            this.tags_init = tags_init;
        else
            this.tags_init = "";

        this.tags_add = "";
    }
    public Person(){
        this.id = -1;
        this.name = "";
        this.tags_init = "";
        this.tags_add = "";
    }

    //get函数
    public int getId() { return id; }
    public String getName(){
        return name;
    }
    public String getTags_init(){
        return tags_init;
    }
    public String getTags_add(){
        return tags_add;
    }


    //set函数
    public void setId(int id) { this.id = id; }
    public void setName(String name){
        this.name = name;
    }
    public void setTags_init(String a){
        if(!a.isEmpty()){
            this.tags_init = a;
        }
    }
    public void addTags_add(String a){
        if(!a.isEmpty()){
            this.tags_add += a;
        }
    }
}
