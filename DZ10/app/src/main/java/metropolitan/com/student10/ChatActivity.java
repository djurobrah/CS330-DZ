package metropolitan.com.student10;

import android.support.v4.widget.ListViewAutoScrollHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ChatActivity extends AppCompatActivity
{
    TextView msgTv;
    EditText msgEt;
    Button msgBtn;

    DatabaseReference databaseReference;
    //String token;
    String randomKey;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        msgTv = findViewById(R.id.msgTv);
        msgEt = findViewById(R.id.msgEt);
        msgBtn = findViewById(R.id.msgBtn);

        //token = FirebaseInstanceId.getInstance().getToken();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Message");

        databaseReference.addChildEventListener(new ChildEventListener()
        {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s)
            {
                appendMessage(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s)
            {
                appendMessage(dataSnapshot);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot)
            {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s)
            {

            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {

            }
        });
    }

    String user, body;
    private void appendMessage(DataSnapshot dataSnapshot)
    {
        Iterator i = dataSnapshot.getChildren().iterator();
        while(i.hasNext())
        {
            body = (String)((DataSnapshot)i.next()).getValue();
            user = (String)((DataSnapshot)i.next()).getValue();
            msgTv.append(user + ": " + body + "\n");
        }
    }

    public void onClick(View v)
    {
        if(v.getId()==R.id.msgBtn)
        {
            //ubacuje msg sa random key
            Map<String, Object> map = new HashMap<>();
            randomKey = databaseReference.push().getKey();
            databaseReference.updateChildren(map);

            //ubacuje info u key
            DatabaseReference msgChild = databaseReference.child(randomKey);
            Map<String, Object> map1 = new HashMap<>();
            map1.put("user", getIntent().getStringExtra("username"));
            map1.put("body", msgEt.getText().toString());
            msgChild.updateChildren(map1);
        }
    }

}
