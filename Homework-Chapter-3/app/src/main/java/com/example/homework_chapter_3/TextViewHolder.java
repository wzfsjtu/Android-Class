package com.example.homework_chapter_3;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TextViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private TextView mtextView;
    private String text;
    private String page_title;

    public TextViewHolder(@NonNull View itemView) {
        super(itemView);

        mtextView = itemView.findViewById(R.id.text_item);
        itemView.setOnClickListener(this);
    }

    public void bind(String bind_text, String title){
        text = bind_text;
        page_title = title;
        String new_text = "this is " + text;
        mtextView.setText(new_text);
    }

    @Override
    public void onClick(View view) {
        Toast.makeText(view.getContext(),
                "click " + text + " in " + page_title,
                Toast.LENGTH_SHORT).show();
    }
}
