package com.example.caiye.test;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;


import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionButton;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionHelper;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionLayout;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RFACLabelItem;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RapidFloatingActionContentLabelList;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView listview;
    private RapidFloatingActionLayout rfabLayout;
    private RapidFloatingActionButton rfabButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listview = (ListView) findViewById(R.id.listview);
        rfabLayout = (RapidFloatingActionLayout) findViewById(R.id.rfablayout);
        rfabButton = (RapidFloatingActionButton) findViewById(R.id.btn_rfab);

        //悬浮按钮
//        RapidFloatingActionContentLabelList rfabContent = new RapidFloatingActionContentLabelList(this);
//        List<RFACLabelItem> items = new ArrayList<>();
//        items.add(new RFACLabelItem<Integer>()
//                    .setLabel("新增朋友")
//                    .setResId(R.mipmap.add)
//                    .setIconNormalColor(Color.WHITE)
//                    .setIconPressedColor(0xffbf360c)
//                    .setLabelColor(R.color.black)
//                    .setWrapper(0));
//        items.add(new RFACLabelItem<Integer>()
//                .setLabel("好友列表")
//                .setResId(R.mipmap.add)
//                .setIconNormalColor(Color.WHITE)
//                .setIconPressedColor(0xffbf360c)
//                .setLabelColor(R.color.black)
//                .setWrapper(0));
//        items.add(new RFACLabelItem<Integer>()
//                .setLabel("设置")
//                .setResId(R.mipmap.add)
//                .setIconNormalColor(Color.WHITE)
//                .setIconPressedColor(0xffbf360c)
//                .setLabelColor(R.color.black)
//                .setWrapper(0));
//
//        rfabContent.setItems(items)
//                    .setIconShadowRadius(ABTextUtil.dip2px(this,5))
//                    .setIconShadowColor(0xff888888)
//                    .setIconShadowDy(ABTextUtil.dip2px(this,5));
//
//        RapidFloatingActionHelper rfabHelper = new RapidFloatingActionHelper(
//                this, rfabLayout, rfabButton, rfabContent
//        ).build();
//
//        //按钮的点击事件（跳转）
//        rfabContent.setOnRapidFloatingActionContentLabelListListener(new RapidFloatingActionContentLabelList.OnRapidFloatingActionContentLabelListListener() {
//            @Override
//            public void onRFACItemLabelClick(int i, RFACLabelItem rfacLabelItem) {
//
//            }
//
//            @Override
//            public void onRFACItemIconClick(int i, RFACLabelItem rfacLabelItem) {
//
//            }
//        });


    }
}
