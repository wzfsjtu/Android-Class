package com.example.homework_chapter_3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button ex1_btn, ex2_btn, ex3_btn;
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ex1_btn = (Button) findViewById(R.id.ex1_btn);
        ex2_btn = (Button) findViewById(R.id.ex2_btn);
        ex3_btn = (Button) findViewById(R.id.ex3_btn);
        ex1_btn.setOnClickListener(this);
        ex2_btn.setOnClickListener(this);
        ex3_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ex1_btn:
                Log.d(TAG, "onClick: ex1" );
                Intent intent1 = new Intent(MainActivity.this, Ex1Activity.class);
                startActivity(intent1);
                break;
            case R.id.ex2_btn:
                Log.d(TAG, "onClick: ex2");
                Intent intent2 = new Intent(MainActivity.this, Ex2Activity.class);
                startActivity(intent2);
                break;
            case R.id.ex3_btn:
                Log.d(TAG, "onClick: ex3");
                Intent intent3 = new Intent(MainActivity.this, Ex3Activity.class);
                startActivity(intent3);
                break;
            default:
                break;
        }
    }
}