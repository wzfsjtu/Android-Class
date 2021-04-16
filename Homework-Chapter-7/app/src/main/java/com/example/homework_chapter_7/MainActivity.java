package com.example.homework_chapter_7;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button imageBtn = findViewById(R.id.imageBtn);
        Button mediaBtn = findViewById(R.id.mediaBtn);
        imageBtn.setOnClickListener(this);
        mediaBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imageBtn:
                Intent intent1 = new Intent(MainActivity.this, ImageActivity.class);
                startActivity(intent1);
                break;
            case R.id.mediaBtn:
                Intent intent2 = new Intent(MainActivity.this, MediaActivity.class);
                startActivity(intent2);
                break;

            default:
                break;
        }
    }
}