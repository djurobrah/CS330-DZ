package metropolitan.com.student5;

import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class NextActivity extends AppCompatActivity {

    ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);

        ActionBar ab = this.getSupportActionBar();
        if(ab != null){
            ab.setDisplayHomeAsUpEnabled(true);
        }

        iv = (ImageView)findViewById(R.id.imageView);
        //iv.setOnCreateContextMenuListener(this);

        registerForContextMenu(iv);

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Izaberi predmet:");
        menu.add(0, v.getId(), 0, "CS101");
        menu.add(0, v.getId(), 0, "CS102");
        menu.add(0, v.getId(), 0, "CS103");
        menu.add(0, v.getId(), 0, "IT355");
        menu.add(0, v.getId(), 0, "CS330");
    }


    @Override
    public boolean onContextItemSelected(MenuItem item)
    {
        if(item.getTitle()=="CS330")
        {
            Toast.makeText(this, "To je moj omiljeni predmet!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this, "To mi nije omiljeni predmet!", Toast.LENGTH_SHORT).show();
        }
        return true;
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
