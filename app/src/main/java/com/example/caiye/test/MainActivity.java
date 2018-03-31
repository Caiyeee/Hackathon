package com.example.caiye.test;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;


import com.google.gson.Gson;
import com.hankcs.hanlp.HanLP;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;

import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private ListView listview;
    private Button btnRecorder;
    private Button btnRecorderEnd;
    private String fullText = " ";
    private TextView name;
    private TextView tags;
    private FloatingActionButton jumpToFriends;
    private ImageView youandme;
    private Intent intentToShow;
    private Person person;
    private List<Map<String,String>> recommend;
    private SimpleAdapter simpleAdapter;
    private String getName;
    private String getTag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SpeechUtility.createUtility(this, SpeechConstant.APPID + "=5abf120b");
        setContentView(R.layout.activity_main);

        recommend = new ArrayList<Map<String, String>>();
        name = (TextView) findViewById(R.id.name_now);
        tags = (TextView)findViewById(R.id.tags);
        youandme = (ImageView) findViewById(R.id.youandme);
        btnRecorder = (Button) findViewById(R.id.btn_record);
        btnRecorderEnd = (Button) findViewById(R.id.btn_end);
        listview = (ListView) findViewById(R.id.listview);
        jumpToFriends = (FloatingActionButton) findViewById(R.id.jumpToFriends);


        intentToShow = getIntent();
        if(intentToShow!=null){
            getName = intentToShow.getStringExtra("name");
            getTag = intentToShow.getStringExtra("tags_init");
        }

        if(getName==null){
            name.setText("None");
            youandme.setAlpha(255);
        } else {
            name.setText(getName);
            youandme.setAlpha(100);
            //获取推荐，放到recommend中
            /////////////////////////////////////////////////////////////////////
        }

        if(getTag==null)
            tags.setText("您沒有對這位朋友設置標籤喔！");
        else
            tags.setText(getTag);

        SimpleAdapter simpleAdapter = new SimpleAdapter(this,recommend,R.layout.friend_item,
                new String[]{"title",""},new int[]{R.id.firstLetter,R.id.name});
        simpleAdapter.notifyDataSetChanged();
        listview.setAdapter(simpleAdapter);

        //点击悬浮按钮跳转到好友列表页面
        jumpToFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,FriendsList.class);
                startActivity(intent);
            }
        });

        //开始录音
        btnRecorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnVoice();
            }
        });
        btnRecorderEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String retString = "";
                List<String> sentenceList = HanLP.extractKeyword(fullText, 5);
                for (String item : sentenceList) {
                    retString = retString + item + ",";
                }
                retString = retString.substring(0, retString.length() - 1) + ";";
                //todo to show the keyword
                Log.d(TAG, "onClick: ");
            }
        });
    }

    // 开始说话：
    private void btnVoice() {
        RecognizerDialog dialog = new RecognizerDialog(this, null);
        dialog.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        dialog.setParameter(SpeechConstant.ACCENT, "mandarin");
        dialog.setParameter(SpeechConstant.VAD_BOS, "4000");
        dialog.setParameter(SpeechConstant.VAD_EOS, "8000");
        dialog.setListener(new RecognizerDialogListener() {
            @Override
            public void onResult(RecognizerResult recognizerResult, boolean isLast) {
                Log.d(TAG, "onResult: " + 222);
                if (!isLast) {
                    //解析语音
                    String result = parseVoice(recognizerResult.getResultString());
                    fullText = fullText + result;
                }
            }

            @Override
            public void onError(SpeechError speechError) {
            }
        });
        dialog.show();
        Toast.makeText(this, "请开始说话", Toast.LENGTH_SHORT).show();
    }

    public String parseVoice(String resultString) {
        Gson gson = new Gson();
        Voice voiceBean = gson.fromJson(resultString, Voice.class);

        StringBuilder sb = new StringBuilder();
        ArrayList<Voice.WSBean> ws = voiceBean.ws;
        for (Voice.WSBean wsBean : ws) {
            String word = wsBean.cw.get(0).w;
            sb.append(word);
        }
        return sb.toString();
    }

    public class Voice {
        ArrayList<WSBean> ws;

        class WSBean {
            ArrayList<CWBean> cw;
        }

        class CWBean {
            String w;
        }
    }

}



