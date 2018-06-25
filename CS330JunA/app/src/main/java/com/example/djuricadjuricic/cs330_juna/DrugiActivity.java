package com.example.djuricadjuricic.cs330_juna;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DrugiActivity extends AppCompatActivity implements View.OnClickListener{

    Intent service;
    Button playBtn;
    Button stopBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drugi);

        playBtn = findViewById(R.id.playBtn);
        playBtn.setOnClickListener(this);

        stopBtn = findViewById(R.id.stopBtn);
        stopBtn.setOnClickListener(this);

        service=new Intent(this, BackgroundSoundService.class);
    }


    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.playBtn)
        {
            startService(service);
        }
        if(view.getId() == R.id.stopBtn)
        {
            stopService(service);
        }
    }
}
