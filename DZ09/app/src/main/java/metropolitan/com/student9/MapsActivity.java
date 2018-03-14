package metropolitan.com.student9;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback
{
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            finish();
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);

        Intent intent = getIntent();
        LatLng latLng = intent.getParcelableExtra("location");

        Geocoder gk = new Geocoder(getBaseContext(), Locale.getDefault());

        mMap.addMarker(new MarkerOptions().position(latLng).title("Set marker"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

        //postavlja novi marker i toastuje lokaciju
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener()
        {
            @Override
            public void onMapClick(LatLng position)
            {
                mMap.addMarker(new MarkerOptions().position(position));
                Geocoder gk = new Geocoder(getBaseContext(), Locale.getDefault());
                try {
                    List<Address> adr;
                    adr = gk.getFromLocation(position.latitude, position.longitude,
                            1);
                    String ad = "";
                    if (adr.size() > 0) {
                        for (int i = 0; i < adr.get(0).getMaxAddressLineIndex();
                             i++)
                            ad += adr.get(0).getAddressLine(i) + "\n";
                    }
                    Toast.makeText(getBaseContext(),ad,Toast.LENGTH_SHORT).show();
                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }
        });

        //toastuje lokaciju prvog markera
        try
        {
            List<Address> adr;
            adr = gk.getFromLocation(latLng.latitude, latLng.longitude, 1);
            String ad = "";
            if (adr.size() > 0)
            {
                for (int i = 0; i < adr.get(0).getMaxAddressLineIndex(); i++)
                {
                    ad += adr.get(0).getAddressLine(i) + "\n";
                }

            }
            Toast.makeText(getBaseContext(),ad,Toast.LENGTH_SHORT).show();
        }
        catch (IOException e){
            e.printStackTrace();
        }

        mMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener()
        {
            @Override
            public boolean onMyLocationButtonClick()
            {
                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                return true; //false ako ocemo da se pomeri na to nasu lokaciju
            }
        });

    }


}
