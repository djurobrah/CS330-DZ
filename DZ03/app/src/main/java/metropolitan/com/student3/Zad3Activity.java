package metropolitan.com.student3;

import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;

public class Zad3Activity extends AppCompatActivity {

    private RadioGroup rg;
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zad3);

        rg = (RadioGroup)findViewById(R.id.radioGroup);
        lv = (ListView)findViewById(R.id.listView);

        final ArrayList<String> predmeti = new ArrayList<>();
        final ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.single_list_item, predmeti);
        lv.setAdapter(adapter);

        //omogucava vracanje na parenta
        ActionBar ab = this.getSupportActionBar();
        if(ab != null){
            ab.setDisplayHomeAsUpEnabled(true);
        }

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId)
                {
                    case R.id.radioButton1:
                    {
                        predmeti.clear();
                        for(String predmet: getResources().getStringArray(R.array.fit_predmeti))
                        {
                            predmeti.add(predmet);
                        }
                        adapter.notifyDataSetChanged();
                        break;
                    }
                    case R.id.radioButton2:
                    {
                        predmeti.clear();
                        for(String predmet: getResources().getStringArray(R.array.fzm_predmeti))
                        {
                            predmeti.add(predmet);
                        }
                        adapter.notifyDataSetChanged();
                        break;
                    }
                    case R.id.radioButton3:
                    {
                        predmeti.clear();
                        for(String predmet: getResources().getStringArray(R.array.fdu_predmeti))
                        {
                            predmeti.add(predmet);
                        }
                        adapter.notifyDataSetChanged();
                        break;
                    }
                }
            }
        });

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
