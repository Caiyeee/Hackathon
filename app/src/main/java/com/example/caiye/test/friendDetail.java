package com.example.caiye.test;

import android.Manifest;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class friendDetail extends AppCompatActivity {
    private ListView listView;
    private TextView name;
    private Button link2He;
    private Intent intentToShow;

    private List<String> getData() {

        List<String> data = new ArrayList<String>();
        data.add("测试数据1");
        data.add("测试数据2");
        data.add("测试数据3");
        data.add("测试数据4");

        return data;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_detail);
        listView = (ListView) findViewById(R.id.list_view_recommend);
        name = (TextView) findViewById(R.id.name);
        link2He = (Button) findViewById(R.id.btn_toHome);
        intentToShow = getIntent();
        if(intentToShow!=null){
            //todo
        }

        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1,
                getData()));

        link2He.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(friendDetail.this, MainActivity.class);
                intent.putExtra("name", "recommend");//todo
                intent.putExtra("tags_init", "tags");//todo
                startActivity(intent);
            }
        });
    }
}
