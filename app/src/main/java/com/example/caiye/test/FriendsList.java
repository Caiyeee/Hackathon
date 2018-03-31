package com.example.caiye.test;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by caiye on 2018/3/31.
 */

public class FriendsList extends AppCompatActivity {
    private ArrayList<Person> friends;
    private SearchView searchView;
    private ListView listView;
    private FloatingActionButton jumpToHome;
    private FloatingActionButton addBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friendslist);

        searchView = (SearchView) findViewById(R.id.search);
        listView = (ListView) findViewById(R.id.listview);
        jumpToHome = (FloatingActionButton)findViewById(R.id.jumpToHome);
        addBtn = (FloatingActionButton) findViewById(R.id.add);

        for(int i=0; i<8; i++){
            Person person = new Person("haha",null);
            friends.add(person);
        }

        //填充数据
        SimpleAdapter simpleAdapter = new SimpleAdapter(this,getFriends(),R.layout.friend_item,
                new String[]{"firstLetter","name"},new int[]{R.id.firstLetter,R.id.name});
        simpleAdapter.notifyDataSetChanged();
        listView.setAdapter(simpleAdapter);

        //点击更多/主页按钮
        jumpToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(addBtn.getVisibility()==View.GONE){//此时是更多按钮
                    addBtn.setVisibility(View.VISIBLE);
                    jumpToHome.setImageResource(R.mipmap.home);
                } else {//此时是返回主页按钮
                    jumpToHome.setImageResource(R.mipmap.more);
                    addBtn.setVisibility(View.GONE);
                    //跳转回主页
                    finish();
                }
            }
        });
        //点击添加按钮
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //查找

    }

    private List<Map<String,String>> getFriends(){
        List<Map<String,String>> list = new ArrayList<Map<String,String>>();

        if(!friends.isEmpty()){
            for(int i=0; i<friends.size(); i++){
                Map<String,String> map = new HashMap<String,String>();
                map.put("firstLetter",friends.get(i).getName().substring(0,1).toUpperCase());
                map.put("name",friends.get(i).getName());
                list.add(map);
            }
        }
        return list;
    }
}
