package com.example.chapter1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button cancel_btn;
    private EditText edittext;
    private List<String> items = new ArrayList<>();
    private RecyclerView recyclerView;
    private SearchAdapter searchAdapter = new SearchAdapter();
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cancel_btn = (Button) findViewById(R.id.cancel);
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edittext.getText().clear();
            }
        });

        edittext = findViewById(R.id.edit_text);
        edittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d(TAG, "beforeTextChanged: " + charSequence);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d(TAG, "onTextChanged: " + charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.d(TAG, "afterTextChanged: " + editable);
                String s = editable.toString();
                items.clear();
                searchAdapter.notifyItems(items);
                for (int i = 0; i < 100; i++){
                    if (String.valueOf(i).contains(s)){
                        items.add(String.valueOf(i));
                    }
                }
                searchAdapter.notifyItems(items);
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(searchAdapter);

        if (edittext.getText().length() == 0){
            for (int i = 0; i < 100; i++){
                items.add(String.valueOf(i));
            }
            searchAdapter.notifyItems(items);
        }


    }
}