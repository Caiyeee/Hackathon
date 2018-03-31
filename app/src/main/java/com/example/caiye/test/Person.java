package com.example.caiye.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by caiye on 2018/3/31.
 */

public class Person {
    private String name;
    private ArrayList<String> tags_init;
    private ArrayList<HashMap<String,List<String>>> tags_add;

    public Person(String name, ArrayList<String> tags_init){
        this.name = name;
        if(!tags_init.isEmpty()){
            for(int i=0; i<tags_init.size(); i++)
                this.tags_init.add(tags_init.get(i));
        }
    }
    //get函数
    public String getName(){
        return name;
    }
    public ArrayList<String> getTags_init(){
        return tags_init;
    }
    public ArrayList<HashMap<String,List<String>>> getTags_add(){
        return tags_add;
    }

    //set函数
    public void setName(String name){
        this.name = name;
    }
    public void addTags_init(ArrayList<String> a){
        if(!a.isEmpty()){
            for(int i=0; i<a.size(); i++)
                tags_init.add(a.get(i));
        }
    }
    public void addTags_add(ArrayList<HashMap<String,List<String>>> a){
        if(!a.isEmpty()){
            for(int i=0; i<a.size(); i++)
                tags_add.add(a.get(i));
        }
    }
}
