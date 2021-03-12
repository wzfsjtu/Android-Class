package com.example.homework_chapter_3;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SearchEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.airbnb.lottie.LottieAnimationView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MyFragment extends Fragment {

    private static final String TAG = "MyFragment";
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private List<String> items = new ArrayList<>();
    private LottieAnimationView lottieAnimationView;
    private int fragment_id;
    private String page_title;

    public MyFragment(int id, String title) {
        super();
        fragment_id = id;
        page_title = title;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Log.d(TAG, "onCreateView: " + fragment_id);

        View view = inflater.inflate(R.layout.fragment_my, container, false);
        recyclerViewAdapter = new RecyclerViewAdapter(page_title);
        recyclerView = view.findViewById(R.id.recycler_item);
        recyclerView.setLayoutManager(new LinearLayoutManager(inflater.getContext()));
        recyclerView.setAdapter(recyclerViewAdapter);
        items.clear();
        for (int i = 0; i < 100; i++){
            items.add(String.valueOf(i));
        }
        recyclerViewAdapter.notifyitems(items);

        lottieAnimationView = view.findViewById(R.id.load_item);

        ObjectAnimator loadAnimator1 = ObjectAnimator.ofFloat(lottieAnimationView,
                "alpha", 1, 1);
        loadAnimator1.setDuration(5000);
        ObjectAnimator loadAnimator2 = ObjectAnimator.ofFloat(lottieAnimationView,
                "alpha", 1, 0);
        loadAnimator2.setDuration(1000);

        ObjectAnimator recyclerAnimator = ObjectAnimator.ofFloat(recyclerView,
                "alpha", 0, 1);
        recyclerAnimator.setDuration(1000);


        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(loadAnimator1).before(loadAnimator2);
        animatorSet.play(loadAnimator2).with(recyclerAnimator);
        animatorSet.start();

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach: " + fragment_id);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: " + fragment_id);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated: " + fragment_id);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: " + fragment_id);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: " + fragment_id);
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: " + fragment_id);
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: " + fragment_id);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView: " + fragment_id);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: " + fragment_id);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach: " + fragment_id);
    }
}
