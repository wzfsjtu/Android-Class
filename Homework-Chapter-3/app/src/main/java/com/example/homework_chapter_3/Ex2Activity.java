package com.example.homework_chapter_3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.pes.androidmaterialcolorpickerdialog.ColorPicker;
import com.pes.androidmaterialcolorpickerdialog.ColorPickerCallback;

import java.io.InputStream;

public class Ex2Activity extends AppCompatActivity {

    private View target;
    private View startColorPicker;
    private View endColorPicker;
    private Button durationSelector;
    private AnimatorSet animatorSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ex2);

        target = findViewById(R.id.target);
        startColorPicker = findViewById(R.id.start_color_picker);
        endColorPicker = findViewById(R.id.end_color_picker);
        durationSelector = findViewById(R.id.duration_selector);

        startColorPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ColorPicker colorPicker = new ColorPicker(Ex2Activity.this);
                colorPicker.setColor(getBackgroundColor(startColorPicker));
                colorPicker.enableAutoClose();
                colorPicker.setCallback(new ColorPickerCallback() {
                    @Override
                    public void onColorChosen(int color) {
                        onStartColorChanged(color);
                    }
                });
                colorPicker.show();
            }
        });

        endColorPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ColorPicker colorPicker = new ColorPicker(Ex2Activity.this);
                colorPicker.setColor(getBackgroundColor(endColorPicker));
                colorPicker.enableAutoClose();
                colorPicker.setCallback(new ColorPickerCallback() {
                    @Override
                    public void onColorChosen(int color) {
                        onEndColorChanged(color);
                    }
                });
                colorPicker.show();
            }
        });

        durationSelector.setText(String.valueOf(1000));
        durationSelector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialDialog.Builder(Ex2Activity.this)
                        .inputType(InputType.TYPE_CLASS_NUMBER)
                        .input(getString(R.string.duration_hint), durationSelector.getText(), new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                                onDurationChanged(input.toString());
                            }
                        })
                        .show();
            }
        });
        resetTargetAnimation();
    }

    private int getBackgroundColor(View view) {
        Drawable bg = view.getBackground();
        if (bg instanceof ColorDrawable){
            return ((ColorDrawable) bg).getColor();
        }
        return Color.WHITE;
    }

    private void onStartColorChanged(int color){
        startColorPicker.setBackgroundColor(color);
        resetTargetAnimation();
    }

    private void onEndColorChanged(int color){
        endColorPicker.setBackgroundColor(color);
        resetTargetAnimation();
    }

    private void onDurationChanged(String input){
        boolean isValid = true;
        try {
            int duration = Integer.parseInt(input);
            if (duration < 100 || duration > 10000) {
                isValid = false;
            }
        } catch (Throwable e) {
            isValid = false;
        }
        if (isValid) {
            durationSelector.setText(input);
            resetTargetAnimation();
        } else {
            Toast.makeText(Ex2Activity.this, R.string.invalid_duration, Toast.LENGTH_LONG).show();
        }
    }

    private void resetTargetAnimation(){
        if (animatorSet != null){
            animatorSet.cancel();
        }

        // 在这里实现了一个 ObjectAnimator，对 target 控件的背景色进行修改
        // 可以思考下，这里为什么要使用 ofArgb，而不是 ofInt 呢？
        ObjectAnimator animator1 = ObjectAnimator.ofArgb(target,
                "backgroundColor",
                getBackgroundColor(startColorPicker),
                getBackgroundColor(endColorPicker));
        animator1.setDuration(Integer.parseInt(durationSelector.getText().toString()));
        animator1.setRepeatCount(ObjectAnimator.INFINITE);
        animator1.setRepeatMode(ObjectAnimator.REVERSE);

        // TODO ex2-1：在这里实现另一个 ObjectAnimator，对 target 控件的大小进行缩放，从 1 到 2 循环

        ObjectAnimator animator2x = ObjectAnimator.ofFloat(target,
                "scaleX",
                1.0f, 2.0f
                );
        animator2x.setDuration(Integer.parseInt(durationSelector.getText().toString()));
        animator2x.setInterpolator(new LinearInterpolator());
        animator2x.setRepeatCount(ObjectAnimator.INFINITE);
        animator2x.setRepeatMode(ObjectAnimator.REVERSE);

        ObjectAnimator animator2y = ObjectAnimator.ofFloat(target,
                "scaleY",
                1.0f, 2.0f
        );
        animator2y.setDuration(Integer.parseInt(durationSelector.getText().toString()));
        animator2y.setInterpolator(new LinearInterpolator());
        animator2y.setRepeatCount(ObjectAnimator.INFINITE);
        animator2y.setRepeatMode(ObjectAnimator.REVERSE);
        // TODO ex2-2：在这里实现另一个 ObjectAnimator，对 target 控件的透明度进行修改，从 1 到 0.5f 循环

        ObjectAnimator animator3 = ObjectAnimator.ofFloat(target,
                "alpha",
                1f, 0.5f
        );
        animator3.setDuration(Integer.parseInt(durationSelector.getText().toString()));
        animator3.setInterpolator(new LinearInterpolator());
        animator3.setRepeatCount(ObjectAnimator.INFINITE);
        animator3.setRepeatMode(ObjectAnimator.REVERSE);
        // TODO ex2-3: 将上面创建的其他 ObjectAnimator 都添加到 AnimatorSet 中

        animatorSet = new AnimatorSet();
        animatorSet.playTogether(animator1, animator2x, animator2y, animator3);
        animatorSet.start();
    }
}