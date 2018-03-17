package metropolitan.com.student10;

import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.text.DateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;


public class Zad2Activity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zad2);

        DateTimeFragment dateTimeFragment = new DateTimeFragment();
        PictureFragment pictureFragment = new PictureFragment();

        FragmentManager fm = getFragmentManager();
        fm.beginTransaction()
                .add(R.id.frameLayout1, dateTimeFragment)
                .add(R.id.frameLayout2, pictureFragment)
                .commit();
    }
}
