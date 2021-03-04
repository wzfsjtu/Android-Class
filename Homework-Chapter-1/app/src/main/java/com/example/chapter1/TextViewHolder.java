package com.example.chapter1;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TextViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private TextView mTextView;

    public TextViewHolder(@NonNull View itemView) {
        super(itemView);
        mTextView = itemView.findViewById(R.id.text);
//        ?
        itemView.setOnClickListener(this);
    }

    public void bind(String text) {
        String new_text = "这里是" + text;
        mTextView.setText(new_text);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(view.getContext(), MyActivity.class);
        intent.putExtra("extra", mTextView.getText().toString());
        view.getContext().startActivity(intent);
    }
}
