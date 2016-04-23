package mma.motapp;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
import android.Manifest;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;


public class MapsActivity extends ActionBarActivity implements
        OnMapReadyCallback,
        OnMyLocationButtonClickListener,
        ActivityCompat.OnRequestPermissionsResultCallback{

    private GoogleMap mMap;
    private boolean mPermissionDenied = false;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mapFragment.getMap().setPadding(0, 1400, 0, 100);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng medellin   = new LatLng(6.244203, -75.58121189999997);
        LatLng penthouse  = new LatLng(6.2582513, -75.57997879999999);
        LatLng thematic   = new LatLng(6.2475828, -75.56012269999997);
        LatLng pegasus    = new LatLng(6.2767208, -75.6189794);
        LatLng collins    = new LatLng(6.2665204, -75.602798);
        LatLng dlusso     = new LatLng(6.293998500000001, -75.57108920000002);
        LatLng momentos   = new LatLng(6.2469813, -75.56813360000001);
        LatLng eros       = new LatLng(6.2546754, -75.5703656);
        LatLng metropolis = new LatLng(6.2673888, -75.562344);
        LatLng ejecutivo  = new LatLng(6.2387662, -75.59996990000002);
        LatLng puntozero  = new LatLng(6.2582513, -75.57997879999999);

        mMap.addMarker(new MarkerOptions().position(penthouse).title("Motel Penthouse")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher)));
        mMap.addMarker(new MarkerOptions().position(thematic).title("Thematic Suites")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher)));
        mMap.addMarker(new MarkerOptions().position(pegasus).title("Motel Pegasus")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher)));
        mMap.addMarker(new MarkerOptions().position(collins).title("Motel Collins")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher)));
        mMap.addMarker(new MarkerOptions().position(dlusso).title("D'Lusso")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher)));
        mMap.addMarker(new MarkerOptions().position(momentos).title("Momentos Suites")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher)));
        mMap.addMarker(new MarkerOptions().position(eros).title("Motel Eros")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher)));
        mMap.addMarker(new MarkerOptions().position(metropolis).title("Motel Metrópolis")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher)));
        mMap.addMarker(new MarkerOptions().position(ejecutivo).title("Motel Ejecutivo La 33")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher)));
        mMap.addMarker(new MarkerOptions().position(puntozero).title("Punto Zero")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher)));

        mMap.moveCamera(CameraUpdateFactory.newLatLng(medellin));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(medellin, 12));
        //mMap.setOnMyLocationButtonClickListener(this);
        enableMyLocation();
    }


    /**
     * Enables the My Location layer if the fine location permission has been granted.
     */
    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            //PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
            // Manifest.permission.ACCESS_FINE_LOCATION, true);
        } else if (mMap != null) {
            // Access to the location has been granted to the app.
            mMap.setMyLocationEnabled(true);
        }

    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this, "Tu localización", Toast.LENGTH_SHORT).show();
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false;
    }

}
