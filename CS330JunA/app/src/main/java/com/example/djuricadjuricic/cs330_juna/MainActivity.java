package com.example.djuricadjuricic.cs330_juna;

import android.app.FragmentManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    WelcomeFragment welcomeFragment;
    DrugiFragment drugiFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        welcomeFragment = new WelcomeFragment();
        drugiFragment = new DrugiFragment();

        FragmentManager fm = getFragmentManager();
        fm.beginTransaction()
                .add(R.id.frameLayout1, welcomeFragment)
                .add(R.id.frameLayout2, drugiFragment)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.btnF1:
            {
                welcomeFragment.changeBackground();
                break;
            }
            case R.id.btnF2:
            {
                drugiFragment.changeBackground();
                break;
            }

        }
        return true;
    }
}
