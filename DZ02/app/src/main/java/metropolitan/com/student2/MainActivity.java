package metropolitan.com.student2;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity implements SingleChoiceDialog.NoticeDialogListener{

    private Button btnWebsite;
    private Button btnMap;
    private Button btnCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnWebsite = (Button) findViewById(R.id.open_website_button);
        btnMap = (Button) findViewById(R.id.open_map_button);
        btnCall = (Button) findViewById(R.id.call_button);
    }

    public void showDialog(View v) {
        // Create an instance of the dialog fragment and show it
        DialogFragment dialog = new SingleChoiceDialog();
        dialog.show(getFragmentManager(), "SingleChoiceDialog");
    }

    // The dialog fragment receives a reference to this Activity through the
    // Fragment.onAttach() callback, which it uses to call the following methods
    // defined by the NoticeDialogFragment.NoticeDialogListener interface
    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        dialog = (SingleChoiceDialog) dialog;
        switch (((SingleChoiceDialog) dialog).number)
        {
            case 0:
            {
                String url = "http://www.metropolitan.ac.rs/osnovne-studije/fakultet-informacionih-tehnologija/";
                onClickOpenWebpageButton(url);
                break;
            }
            case 1:
            {
                String url = "http://www.metropolitan.ac.rs/osnovne-studije/fakultet-za-menadzment/";
                onClickOpenWebpageButton(url);
                break;
            }
            case 2:
            {
                String url = "http://www.metropolitan.ac.rs/fakultet-digitalnih-umetnosti-2/";
                onClickOpenWebpageButton(url);
                break;
            }
        }
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {}

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

    public void onClickCall(View v) {

        String number = "+381638712025"; //moj broj xoxo
        dialPhoneNumber(number);
    }

    public void dialPhoneNumber(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public void onClickOpenWebpageButton(String url) {

        openWebpage(url);
    }

    public void openWebpage(String url)
    {
        Uri uri = Uri.parse(url);
        Intent i = new Intent(Intent.ACTION_VIEW, uri);
        if (i.resolveActivity(getPackageManager()) != null) {
            startActivity(i);
        }
    }

}
