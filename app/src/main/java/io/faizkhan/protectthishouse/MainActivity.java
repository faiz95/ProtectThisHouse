package io.faizkhan.protectthishouse;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public final static int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 255;

    private LocationManager locationManager;
    private LocationListener locationListener;

    private Location location;

    public TextView latitudeTextView;
    public TextView longitudeTextView;


    private LocationManager getLocationManager()
    {
        if (this.locationManager == null)
        {
            this.locationManager = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
        }
        return this.locationManager;
    }

    private LocationListener getLocationListener()
    {
        if (this.locationListener == null)
        {
            this.locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();

                    latitudeTextView.setText(Double.toString(latitude));
                    longitudeTextView.setText(Double.toString(longitude));
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            };
        }
        return locationListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        latitudeTextView = (TextView)findViewById(R.id.latitudeTextView);
        longitudeTextView = (TextView)findViewById(R.id.longitudeTextView);

        locationManager = getLocationManager();
        locationListener = getLocationListener();

        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000*5, 10, locationListener);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}
