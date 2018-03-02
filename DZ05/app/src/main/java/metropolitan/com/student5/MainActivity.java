package metropolitan.com.student5;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
            case R.id.nextButton:
            {
                Intent i = new Intent(MainActivity.this, NextActivity.class);
                startActivity(i);
                break;
            }
            case R.id.uniBtn:
            {
                Intent i = new Intent(MainActivity.this, WebViewActivity.class);
                i.putExtra("url", "http://www.metropolitan.ac.rs/");
                startActivity(i);
                break;
            }
            case R.id.fitBtn:
            {
                Intent i = new Intent(MainActivity.this, WebViewActivity.class);
                i.putExtra("url", "http://www.metropolitan.ac.rs/osnovne-studije/fakultet-informacionih-tehnologija/");
                startActivity(i);
                break;
            }
            case R.id.famBtn:
            {
                Intent i = new Intent(MainActivity.this, WebViewActivity.class);
                i.putExtra("url", "http://www.metropolitan.ac.rs/osnovne-studije/fakultet-za-menadzment/");
                startActivity(i);
                break;
            }
            case R.id.fduBtn:
            {
                Intent i = new Intent(MainActivity.this, WebViewActivity.class);
                i.putExtra("url", "http://www.metropolitan.ac.rs/fakultet-digitalnih-umetnosti-2/");
                startActivity(i);
                break;
            }
        }
        return true;
    }
}
