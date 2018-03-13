package metropolitan.com.student8;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.provider.Telephony;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{
    EditText editText;
    ImageButton bt1;
    ImageButton bt2;
    TextView tv;
    IntentFilter intentFilter;
    SMSReceiver smsReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.editText);
        bt1 = (ImageButton) findViewById(R.id.imageButton1);
        bt2 = (ImageButton) findViewById(R.id.imageButton2);

        smsReceiver = new SMSReceiver();
        registerReceiver(smsReceiver, new IntentFilter(Telephony.Sms.Intents.SMS_RECEIVED_ACTION));

        smsReceiver.setListener(new SMSReceiver.Listener()
        {
            @Override
            public void onTextReceived(String text)
            {
                editText.setText(text);
            }
        });
    }

    @Override
    protected void onPause()
    {
        unregisterReceiver(smsReceiver);
        super.onPause();
    }

    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.imageButton1:
            {
                sendEmail();
            }
            case R.id.imageButton2:
            {
                sendSms();
                //sendSmsViaIntent(); //iz nekog razloga radi samo na telefonu, ali ne na emulatoru
            }
            editText.getText().clear();
        }
    }

    private void sendSmsViaIntent()
    {
        String phoneNumber = "5554";
        String message = editText.getText().toString();
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
        intent.setType("vnd.android-dir/mms-sms");
        intent.putExtra("address",phoneNumber);
        intent.putExtra("sms_body",message);

        if (intent.resolveActivity(getPackageManager()) != null)
        {
            startActivity(intent);
        }
    }

    private void sendEmail()
    {
        String[] addresses = {"djurica.djuricic.2727@metropolitan.ac.rs"};
        String subject = "Naslov";
        String body = editText.getText().toString();

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, body);
        if (intent.resolveActivity(getPackageManager()) != null)
        {
            startActivity(intent);
        }
    }

    private void sendSms()
    {
        String phoneNumber = "5554";
        String message = editText.getText().toString();

        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phoneNumber, null, message, null, null);
    }
}
