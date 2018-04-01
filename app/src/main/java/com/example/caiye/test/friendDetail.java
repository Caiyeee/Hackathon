package com.example.caiye.test;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class friendDetail extends AppCompatActivity {
    private ListView listView;
    private TextView name;
    private TextView init_tags;
    private Button link2He;
    private Intent intentToShow;
    private Person friend;

    private List<Map<String, String>> getTags(String tags) {
        //todo 输入是逗号和分号分割的字符串
        List<String> tem = Arrays.asList(tags.split(";"));


        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_detail);
        listView = (ListView) findViewById(R.id.list_view_recommend);
        name = (TextView) findViewById(R.id.tv_name);
        init_tags = (TextView) findViewById(R.id.tv_tags_init);
        link2He = (Button) findViewById(R.id.btn_toHome);
        intentToShow = getIntent();
        if (intentToShow != null) {
            friend = (Person) intentToShow.getSerializableExtra("person");
            name.setText(friend.getName());
            init_tags.setText(friend.getTags_init());
            SimpleAdapter simpleAdapter = new SimpleAdapter(this, getTags(friend.getTags_add())
                    , R.layout.friend_item,
                    new String[]{"firstLetter", "name"}, new int[]{R.id.firstLetter, R.id.name});
            simpleAdapter.notifyDataSetChanged();
            listView.setAdapter(simpleAdapter);
            simpleAdapter.notifyDataSetChanged();
            listView.setAdapter(simpleAdapter);

        }


        link2He.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(friendDetail.this, MainActivity.class);
                intent.putExtra("friend", friend);
                startActivity(intent);
            }
        });
    }
}
