package com.example.djuricadjuricic.cs330_juna;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.net.MalformedURLException;
import java.net.URL;

public class WelcomeFragment extends Fragment {

    LinearLayout ll;
    public WelcomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_welcome, container, false);
        ll = (LinearLayout) rootView.findViewById(R.id.ll1);
        ImageView imageView = rootView.findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.met);

        return rootView;
    }

    public void changeBackground()
    {
        ll.setBackgroundColor(Color.RED);
    }

}
