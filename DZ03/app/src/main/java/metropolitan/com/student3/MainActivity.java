package metropolitan.com.student3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.menuItem1:
                {
                    Intent i = new Intent(MainActivity.this, Zad1Activity.class);
                    startActivity(i);
                    break;
                }
            case R.id.menuItem3:
            {
                Intent i = new Intent(MainActivity.this, Zad3Activity.class);
                startActivity(i);
                break;
            }
        }
        return true;
    }
}
