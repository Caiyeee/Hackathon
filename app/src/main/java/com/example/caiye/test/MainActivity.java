package com.example.caiye.test;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
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


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    private ListView listview;
    private Button btnRecorder;
    private String fullText = " ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SpeechUtility.createUtility(this, SpeechConstant.APPID + "=5abf120b");
        setContentView(R.layout.activity_main);
        btnRecorder = (Button) findViewById(R.id.btn_record);
        listview = (ListView) findViewById(R.id.listview);
        btnRecorder.setOnClickListener(this);
        String text =
                "早上好\n" +
                        "早上好，吃了吗\n" +
                        "吃了面条\n" +
                        "我也吃了面条\n" +
                        "不错，我很喜欢吃面条\n" +
                        "牛肉面是我最爱\n" +
                        "中午了，一起吃个饭吧\n" +
                        "好啊好啊\n";
        List<String> sentenceList = HanLP.extractSummary(text, 1);
        System.out.println(sentenceList);

    }

    @Override
    public void onClick(View view) {
        btnVoice();
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



