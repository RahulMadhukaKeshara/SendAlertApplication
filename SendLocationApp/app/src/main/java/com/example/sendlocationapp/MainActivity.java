package com.example.sendlocationapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity {

    private LocationListener locationListener;
    private LocationManager locationManager;
    private final long MIN_TIME=1000; //minimum time interval between location updates, in milliseconds
    private final long MIN_DISTANCE=5; //minimum distance between location updates, in meters
    private LatLng latLng;
    private double lng;
    private double lat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, PackageManager.PERMISSION_GRANTED);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                PackageManager.PERMISSION_GRANTED);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                PackageManager.PERMISSION_GRANTED);
    }

    public void onMapReady(GoogleMap googleMap) {

        locationListener=new LocationListener() {

            @Override
            public void onLocationChanged(Location location) {

                try {

                    lat = location.getLatitude();
                    lng = location.getLongitude();
                    //sendAlert();
                }catch (SecurityException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) { }
            @Override
            public void onProviderEnabled(String provider) { }
            @Override
            public void onProviderDisabled(String provider) { }
        };
        try {
            locationManager=(LocationManager)getSystemService(LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,MIN_TIME,MIN_DISTANCE,locationListener);
        }catch (SecurityException e){e.printStackTrace();}

    }

    public void sendAlert(View view) {
        String mobileNo = "0768652634";
        String msg  = "https://www.google.lk/maps/?q"+ lat +"," + lng ;

        try {

                SmsManager smgr = SmsManager.getDefault();
                smgr.sendTextMessage(mobileNo, null, msg, null, null);

                Toast.makeText(getApplicationContext(), " I’m Rahul Madhuka IM/2017/003. Please Help Me. \n" +
                        "I’m in " + mobileNo + "SMS : " + msg, Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "SMS Sending Failed" + e, Toast.LENGTH_LONG).show();
        }
    }

}


