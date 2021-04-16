package com.example.homework_chapter_7;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.net.UrlQuerySanitizer;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.net.URL;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ImageFragment extends Fragment {

    private static final String TAG = "ImageFragment";
    private int index;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();
        index = args != null ? args.getInt("Index") : 0;
        Log.d(TAG, "onCreateView: " + index);
        View view = inflater.inflate(R.layout.fragment_image, container, false);
        ImageView imageView = view.findViewById(R.id.fragment_image_view);
        BitmapFactory.Options options = new BitmapFactory.Options();

        switch (index){
            case 0:
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.raw.rawimage, options);
                imageView.setImageBitmap(bitmap);
                break;
            case 1:
                Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), R.raw.icon_failure, options);
                imageView.setImageBitmap(bitmap1);
                break;
            case 2:
                Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(), R.raw.pikachu, options);
                imageView.setImageBitmap(bitmap2);

                break;
            default:
                break;
        }
        return view;
    }

}
