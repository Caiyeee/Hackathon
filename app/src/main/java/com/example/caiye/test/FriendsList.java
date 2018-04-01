package com.example.caiye.test;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.TextView;
import android.widget.Toast;

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
    private RelativeLayout relativeLayout;
    private ListView searchList;
    private DBOperation dbOperation;
    private SimpleAdapter simpleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friendslist);

        searchView = (SearchView) findViewById(R.id.search);
        listView = (ListView) findViewById(R.id.friends);
        jumpToHome = (FloatingActionButton) findViewById(R.id.jumpToHome);
        addBtn = (FloatingActionButton) findViewById(R.id.add);
        relativeLayout = (RelativeLayout) findViewById(R.id.friendsAndFloat);
        searchList = (ListView) findViewById(R.id.searchList);

        //获取数组库的所有数据
        dbOperation = new DBOperation(this);
        friends = dbOperation.getAllFriends();
        //填充listview
        simpleAdapter = new SimpleAdapter(this,getFriends(friends),R.layout.friend_item,
                new String[]{"firstLetter","name"},new int[]{R.id.firstLetter,R.id.name});
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, getFriends(friends), R.layout.friend_item,
                new String[]{"firstLetter", "name"}, new int[]{R.id.firstLetter, R.id.name});
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
                LayoutInflater factory = LayoutInflater.from(FriendsList.this);
                View newView = factory.inflate(R.layout.dialog,null);
                AlertDialog.Builder dialog = new AlertDialog.Builder(FriendsList.this);
                final EditText nameInput = (EditText) newView.findViewById(R.id.nameInput);
                final EditText tagsInput = (EditText) newView.findViewById(R.id.tagsInput);
                dialog.setView(newView);
                dialog.setTitle("新增朋友");
                dialog.setCancelable(false);
                dialog.setPositiveButton("保存", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(nameInput.length()==0)
                            Toast.makeText(getApplicationContext(),"姓名不能為空",Toast.LENGTH_SHORT);
                        else {
                            ArrayList<Person> persons = dbOperation.queryName(nameInput.getText().toString());
                            if(persons!=null && persons.size()!=0)
                                Toast.makeText(getApplicationContext(),"該名字已被添加過",Toast.LENGTH_SHORT);
                            else {
                                String name = nameInput.getText().toString();
                                String tags = tagsInput.getText().toString();
                                int id = dbOperation.insert(name,tags);
                                Person person = new Person(id,name,tags);
                                friends.add(person);
                                simpleAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                });
                dialog.setNegativeButton("取消", null);
                dialog.create().show();
            }
        });

        //查找
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            //当点击搜索按钮时触发该方法
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            // 当搜索内容改变时触发该方法
            @Override
            public boolean onQueryTextChange(String newText) {
                if(!TextUtils.isEmpty(newText)){
                    relativeLayout.setVisibility(View.GONE);
                    searchList.setVisibility(View.VISIBLE);
                    ArrayList<Person> queryData = dbOperation.query(newText);

                    SimpleAdapter queryAdapter = getQueryAdapter(newText);
                    updateLayout(queryAdapter);
                } else {//搜索内容为空时切换回原列表
                    searchList.setVisibility(View.GONE);
                    relativeLayout.setVisibility(View.VISIBLE);
                }
                return false;
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedItem = (String) adapterView.getItemAtPosition(i);
                //todo link to friend_detail
            }
        });


    }

    //人物查询
    public SimpleAdapter getQueryAdapter(String keyword) {
        ArrayList<Person> arrayList = new ArrayList<Person>();
        arrayList = dbOperation.query(keyword);
        SimpleAdapter queryAdapter = new SimpleAdapter(this,getFriends(arrayList),R.layout.friend_item,
                new String[]{"firstLetter","name"},new int[]{R.id.firstLetter,R.id.name});
        return queryAdapter;
    }

    //查询界面更新
    public void updateLayout(SimpleAdapter sAdapter)
    {
        searchList.setAdapter(sAdapter);
    }

    //构造adapter需要
    private List<Map<String,String>> getFriends(ArrayList<Person> friend){
        List<Map<String,String>> list = new ArrayList<Map<String,String>>();

        if(!friend.isEmpty()){
            for(int i=0; i<friend.size(); i++){
                Map<String,String> map = new HashMap<String,String>();
                map.put("firstLetter",friend.get(i).getName().substring(0,1).toUpperCase());
                map.put("name",friend.get(i).getName());
                list.add(map);
            }
        }
        return list;
    }

}
