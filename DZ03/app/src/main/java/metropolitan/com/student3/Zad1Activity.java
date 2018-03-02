package metropolitan.com.student3;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Zad1Activity extends AppCompatActivity {

    private EditText et;
    private Button b1;
    private Button b2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zad1);
        et = (EditText)findViewById(R.id.editText);
        b1 = (Button)findViewById(R.id.button1);
        b2 = (Button)findViewById(R.id.button2);

        //omogucava vracanje na parenta
        ActionBar ab = this.getSupportActionBar();
        if(ab != null){
            ab.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            Toast.makeText(this, "Ekran je okrenut vodoravno", Toast.LENGTH_SHORT).show();
        }
        else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT)
        {
            Toast.makeText(this, "Ekran je okrenut uspravno", Toast.LENGTH_SHORT).show();
        }
    }

    public void onClickSetText(View v)
    {
        et.setText("Fakultet metropolitan");
        et.setKeyListener(null); //čini da EditText bude uneditable
    }

    public void onClickOpenAddressButton(View v) {
        String addressString = "Tadeuša Košćuška 63";

        Uri.Builder builder = new Uri.Builder();
        builder.scheme("geo")
                .appendPath("0,0")
                .appendQueryParameter("q", addressString);

        Uri addressUri = builder.build();

        showMap(addressUri);
    }

    private void showMap(Uri geoLocation) {

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(geoLocation);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home)
        {
            NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }
}
