package com.example.caiye.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
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

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private ListView listView;
    private Button btnRecorder;
    private Button btnRecorderEnd;
    private String fullText = " ";

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
        SpeechUtility.createUtility(this, SpeechConstant.APPID + "=5abf120b");
        setContentView(R.layout.activity_main);



        btnRecorder = (Button) findViewById(R.id.btn_record);
        btnRecorderEnd = (Button) findViewById(R.id.btn_end);
        listView = (ListView) findViewById(R.id.listview);
        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1,
                getData()));
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



