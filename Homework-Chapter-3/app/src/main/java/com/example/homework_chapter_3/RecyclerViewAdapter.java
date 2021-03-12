package com.example.homework_chapter_3;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<TextViewHolder> {

    private List<String> mItems = new ArrayList<>();
    private String page_title;

    public RecyclerViewAdapter(String title) {
        super();
        page_title = title;
    }

    @NonNull
    @Override
    public TextViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TextViewHolder(LayoutInflater.from(parent.getContext())
        .inflate(R.layout.item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TextViewHolder holder, int position) {
        holder.bind(mItems.get(position), page_title);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }


    public void notifyitems(@NonNull List<String> items){
        mItems.clear();
        mItems.addAll(items);
        notifyDataSetChanged();
    }
}
