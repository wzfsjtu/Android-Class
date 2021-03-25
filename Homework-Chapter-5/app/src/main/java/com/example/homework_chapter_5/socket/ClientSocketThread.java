package com.example.homework_chapter_5.socket;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.net.Socket;

import javax.xml.parsers.FactoryConfigurationError;

public class ClientSocketThread extends Thread {
    public ClientSocketThread(SocketActivity.SocketCallback callback) {
        this.callback = callback;
    }

    private SocketActivity.SocketCallback callback;

    //head请求内容
    private static String content = "HEAD /xxjj/index.html HTTP/1.1\r\nHost:www.sjtu.edu.cn\r\n\r\n";

    @Override
    public void run() {
        //TODO 6 用socket实现简单的HEAD请求（发送content）
        //  将返回结果用callback.onresponse(result)进行展示
        Log.d("ClientSocketThread", "run: start" );
        try {
            Socket socket = new Socket("www.sjtu.edu.cn", 80);
            BufferedOutputStream os = new BufferedOutputStream(socket.getOutputStream());
            BufferedInputStream is = new BufferedInputStream(socket.getInputStream());

            byte[] data = new byte[1024 * 5];//每次读取的字节数

            while(socket.isConnected()){
                os.write(content.getBytes());
                os.flush();

                int len = is.read(data);
                Log.d("ClientSocketThread", "run: " + len);
                callback.onResponse(new String(data, 0, len));
            }
        } catch (Exception e){
            e.printStackTrace();
        }


    }
}