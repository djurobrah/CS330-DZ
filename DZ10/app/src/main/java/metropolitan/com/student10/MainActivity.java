package metropolitan.com.student10;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import metropolitan.com.student10.fcm.FirebaseIDService;
import com.google.firebase.iid.FirebaseInstanceId;

public class MainActivity extends AppCompatActivity
{
    Button btn1;
    Button btn2;
    Button btn3;
    EditText usernameEt;
    ListView resultsLv;

    ArrayList<HashMap<String, String>> itemList;
    private String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        usernameEt = findViewById(R.id.usernameEt);
        resultsLv = findViewById(R.id.resultsLV);

        itemList = new ArrayList<HashMap<String,String>>();
    }

    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btn1:
            {
                new GetItems().execute();
                break;
            }
            case R.id.btn2:
            {
                Intent i = new Intent(this, Zad2Activity.class);
                startActivity(i);
                break;
            }
            case R.id.btn3:
            {
                Intent i = new Intent(this, ChatActivity.class);
                i.putExtra("username", usernameEt.getText().toString());
                startActivity(i);
                break;
            }
        }
    }

    private class GetItems extends AsyncTask<Void, Void, Void>
    {

        @Override
        protected Void doInBackground(Void... voids)
        {
            if(!itemList.isEmpty())
            {
                itemList.clear();
            }
            HttpHandler httpHandler = new HttpHandler();
            String url = "https://my.api.mockaroo.com/cs330-dz10.json?key=4975b240";
            String jsonString = httpHandler.makeServiceCall(url);

            if(jsonString != null)
            {
                try
                {
                    JSONArray items = new JSONArray(jsonString);
                    for (int i = 0; i < items.length(); i++)
                    {
                        JSONObject item = items.getJSONObject(i);
                        String id = item.getString("id");
                        String name = item.getString("name");

                        HashMap<String, String> itemAsHashMap = new HashMap<>();
                        itemAsHashMap.put(id, name);

                        itemList.add(itemAsHashMap);
                    }
                }
                catch (final JSONException e)
                {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                }
            }
            else
            {
                Log.e(TAG, "Couldn't get JSON from server. ");
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void results)
        {
            super.onPostExecute(results);
            //ListAdapter mi daje prazne iteme
            //ListAdapter adapter = new SimpleAdapter(MainActivity.this, itemList, R.layout.list_item, new String[]{"id", "name"}, new int[]{R.id.list_item_id, R.id.list_item_ime});
            ArrayAdapter<HashMap<String, String>> adapter = new ArrayAdapter<HashMap<String, String>>(MainActivity.this, android.R.layout.simple_expandable_list_item_1, itemList);
            resultsLv.setAdapter(adapter);
        }

    }
}
