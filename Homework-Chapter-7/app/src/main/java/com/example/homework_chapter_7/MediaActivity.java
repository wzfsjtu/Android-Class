package com.example.homework_chapter_7;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;

public class MediaActivity extends AppCompatActivity {

    private static final String TAG = "MediaActivity";

    MediaPlayer player;
    SurfaceView surfaceView;
    SurfaceHolder holder;

    SeekBar seekBar;
    TextView currentTime;
    TextView totalTime;
    final int SEEKBAR_CHANGED = 1;

    boolean isSeek = false;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case SEEKBAR_CHANGED:
                    currentTime.setText(calculateTime(player.getCurrentPosition() / 1000));
                    totalTime.setText(calculateTime(player.getDuration() / 1000));
                    seekBar.setProgress(player.getCurrentPosition());
                    break;
                default:
                    break;
            }
        }
    };

    Thread seekThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media);
        player = new MediaPlayer();
        surfaceView = findViewById(R.id.surfaceView);

        seekBar = findViewById(R.id.seekBar);
        currentTime = findViewById(R.id.currentTime);
        totalTime = findViewById(R.id.totalTime);

        try {
            player.setDataSource(getResources().openRawResourceFd(R.raw.big_buck_bunny));
            holder = surfaceView.getHolder();
            holder.setFormat(PixelFormat.TRANSPARENT);
            holder.addCallback(new PlayerCallBack());
            player.prepare();
            player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    // 自动播放
                    seekBar.setMax(player.getDuration());
                    seekBar.setProgress(0);
                    currentTime.setText(calculateTime(0));
                    totalTime.setText(calculateTime(player.getDuration() / 1000));
                    player.start();
                    player.setLooping(true);
                }
            });
            player.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
                @Override
                public void onBufferingUpdate(MediaPlayer mp, int percent) {
                    System.out.println(percent);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }




        findViewById(R.id.buttonPlay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.start();
            }
        });

        findViewById(R.id.buttonPause).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.pause();
            }
        });

        findViewById(R.id.buttonRePlay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.seekTo(0);
                player.start();
            }
        });



        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                isSeek = true;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Log.d(TAG, "onStopTrackingTouch: " + seekBar.getProgress());
                Log.d(TAG, "onStopTrackingTouch: " + player.getDuration());
                Log.d(TAG, "onStopTrackingTouch: " + player.getCurrentPosition());
                player.seekTo(seekBar.getProgress());
                player.start();
                isSeek = false;
            }
        });

        seekThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    try{
                        Thread.sleep(50);
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                    if (!isSeek){
//                       Log.d(TAG, "run: " +  player.getCurrentPosition());
                        Message message = new Message();
                        message.what = SEEKBAR_CHANGED;
                        handler.sendMessage(message);
                    }
                }
            }
        });
        seekThread.start();
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (player != null) {
            player.stop();
            player.release();
        }
    }

    private class PlayerCallBack implements SurfaceHolder.Callback {
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            player.setDisplay(holder);
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {

        }
    }

    public String calculateTime(int time) {
        int minute;
        int second;
        if (time >= 60) {
            minute = time / 60;
            second = time % 60;
            //分钟再0~9
            if (minute >= 0 && minute < 10) {
                //判断秒
                if (second >= 0 && second < 10) {
                    return "0" + minute + ":" + "0" + second;
                } else {
                    return "0" + minute + ":" + second;
                }
            } else {
                //分钟大于10再判断秒
                if (second >= 0 && second < 10) {
                    return minute + ":" + "0" + second;
                } else {
                    return minute + ":" + second;
                }
            }
        } else if (time < 60) {
            second = time;
            if (second >= 0 && second < 10) {
                return "00:" + "0" + second;
            } else {
                return "00:" + second;
            }
        }
        return null;
    }
}