package com.example.caiye.test;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class friendDetail extends AppCompatActivity {
    private ListView listView;
    private TextView name;
    private TextView init_tags;
    private Button link2He;
    private Intent intentToShow;
    private Person friend;

    private List<String> getTags(String tags){
        Log.e("tags_add",tags);
        List<String> tem = Arrays.asList(tags.split(";"));
        List<String> list = new ArrayList<String>();
        for(int i=0; i<tem.size(); i++){
            String s = tem.get(i);
            for(int j=0; j<s.length(); j++)
                if(s.charAt(j)!=' '){
                    list.add(tem.get(i));
                    break;
                }
        }
        return list;
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
            if(friend.getName()!=null)
                name.setText(friend.getName());
            init_tags.setText(friend.getTags_init());
            listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1,getTags(friend.getTags_add())));
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
