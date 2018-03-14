package metropolitan.com.student9;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.model.LatLng;
import com.karan.churi.PermissionManager.PermissionManager;

import android.Manifest;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
{
    EditText latEt;
    EditText lngEt;
    Button button;

    PermissionManager permission;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        latEt = findViewById(R.id.latEt);
        lngEt = findViewById(R.id.lngEt);
        button = findViewById(R.id.button);

        permission = new PermissionManager(){};
        ArrayList<String> granted = permission.getStatus().get(0).granted;
        if(!granted.contains("android.permission.ACCESS_COARSE_LOCATION") || !granted.contains("android.permission.ACCESS_FINE_LOCATION"))
        {
            permission.checkAndRequestPermissions(this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults)
    {
        permission.checkResult(requestCode,permissions, grantResults);

    }

    public void onClick(View v)
    {

        if(v.getId() == R.id.button)
        {
            LatLng latLng = new LatLng(Double.valueOf(latEt.getText().toString()), Double.valueOf(lngEt.getText().toString()));

            Bundle args = new Bundle();
            args.putParcelable("location", latLng);

            Intent i = new Intent(this, MapsActivity.class);
            i.putExtras(args);
            startActivity(i);
        }
    }

}
