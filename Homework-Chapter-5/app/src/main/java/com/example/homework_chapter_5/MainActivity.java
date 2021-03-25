package com.example.homework_chapter_5;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.homework_chapter_5.model.Message;
import com.example.homework_chapter_5.model.MessageListResponse;
import com.example.homework_chapter_5.socket.SocketActivity;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.producers.HttpUrlConnectionNetworkFetcher;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private FeedAdapter adapter = new FeedAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Fresco.initialize(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.rv_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        findViewById(R.id.btn_upload).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,UploadActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.btn_mine).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData(Constants.STUDENT_ID);
            }
        });

        findViewById(R.id.btn_all).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData(null);
            }
        });
        findViewById(R.id.btn_socket).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SocketActivity.class);
                startActivity(intent);
            }
        });



    }

    //TODO 2
    // 用HttpUrlConnection实现获取留言列表数据，用Gson解析数据，更新UI（调用adapter.setData()方法）
    // 注意网络请求和UI更新分别应该放在哪个线程中
    private void getData(String studentId){
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Message> messages = baseGetReposFromRemote(studentId);
                if (messages != null && !messages.isEmpty()){
                    new Handler(getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            showMessage(messages);
                        }
                    });
                }
            }
        }).start();
    }

    private List<Message> baseGetReposFromRemote(String studentId){
        String urlStr;
        if (studentId != null){
            urlStr = String.format(Constants.BASE_URL + "messages?student_id=%s", studentId);
        } else {
            urlStr = Constants.BASE_URL + "messages?";
        }
//        urlStr = String.format(Constants.BASE_URL + "messages?student_id=%s", studentId);
        Log.d(TAG, "baseGetReposFromRemote: " + urlStr);
        MessageListResponse messageListResponse = null;
        List<Message> result = null;
        try{
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(6000);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("token", Constants.token);
            if (conn.getResponseCode() == 200){
                InputStream in = conn.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));

                messageListResponse = new Gson().fromJson(reader, new TypeToken<MessageListResponse>() {
                }.getType());

                if (messageListResponse.success) {
                    result = messageListResponse.feeds;
                }

                reader.close();
                in.close();
            }
        } catch (Exception e){
            e.printStackTrace();
//            Toast.makeText(this, "网络异常", Toast.LENGTH_LONG).show();
        }
        return result;
    }

    private void showMessage(List<Message> messageslist){
        adapter.setData(messageslist);
    }

}