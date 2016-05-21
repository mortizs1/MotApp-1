package mma.motapp;

import android.util.Log;
import android.Manifest;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;
import android.view.MenuItem;
import android.content.Intent;
import android.content.Context;
import android.widget.ListView;
import android.app.ProgressDialog;
import android.app.FragmentManager;
import android.content.pm.PackageManager;

import android.support.v7.widget.Toolbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.design.widget.NavigationView;

import java.util.List;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.android.volley.VolleyError;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.LatLng;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;

import mma.motapp.model.Motel;
import mma.motapp.controller.AppController;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        OnMarkerClickListener,
        OnMapReadyCallback{

    SupportMapFragment sMapFragment;
    // Log tag
    private static final String TAG = MainActivity.class.getSimpleName();
    // Movies json url
    private static final String url = "http://motapp.herokuapp.com/ubicacions.json";
    private ProgressDialog pDialog;
    private List<Motel> motelList = new ArrayList<Motel>();
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        android.support.v4.app.FragmentManager sFm = getSupportFragmentManager();

        sMapFragment = SupportMapFragment.newInstance();

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        sMapFragment.getMapAsync(this);
        sFm.beginTransaction().add(R.id.map, sMapFragment).commit();

        pDialog = new ProgressDialog(this);
        // Showing progress dialog before making http request
        pDialog.setMessage("Cargando conexión...");
        pDialog.show();

        // Creating volley request obj
        JsonArrayRequest motelReq = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        hidePDialog();
                        // Parsing json
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject obj = response.getJSONObject(i);
                                Motel motel = new Motel();
                                motel.setId(obj.getInt("id"));
                                motel.setTitle(obj.getString("motel"));
                                motel.setLatitud(((Number) obj.get("latitud"))
                                        .doubleValue());
                                motel.setLongitud(((Number) obj.get("longitud"))
                                        .doubleValue());
                                // adding motel to motels array
                                motelList.add(motel);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                hidePDialog();
            }
        });
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(motelReq);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (item.getItemId()) {
            case R.id.action_buscar:
                Log.i("ActionBar", "Buscar!");
                ;
                return true;
            case R.id.action_promocion:
                Log.i("ActionBar", "Promocion!");
                ;
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        FragmentManager fm = getFragmentManager();
        android.support.v4.app.FragmentManager sFm = getSupportFragmentManager();

        int id = item.getItemId();

        if (sMapFragment.isAdded())
            sFm.beginTransaction().hide(sMapFragment).commit();

        if (id == R.id.nav_maps) {
            getSupportActionBar().setTitle("Mapa");
            if (!sMapFragment.isAdded())
                sFm.beginTransaction().add(R.id.map, sMapFragment).commit();
            else
                sFm.beginTransaction().show(sMapFragment).commit();

        } else if (id == R.id.nav_favoritos) {
            getSupportActionBar().setTitle("Favoritos");
            fm.beginTransaction().replace(R.id.content_frame, new ImportFragment()).commit();
        } else if (id == R.id.nav_gallery) {
            getSupportActionBar().setTitle("Galería");
            fm.beginTransaction().replace(R.id.content_frame, new ImportFragment()).commit();
        } else if (id == R.id.nav_slideshow) {
            getSupportActionBar().setTitle("Slider");
            fm.beginTransaction().replace(R.id.content_frame, new ImportFragment()).commit();
        } else if (id == R.id.nav_config) {
            getSupportActionBar().setTitle("Configuración");
            fm.beginTransaction().replace(R.id.content_frame, new ImportFragment()).commit();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onMapReady(GoogleMap mMap) {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMap().setPadding(0, 1400, 0, 0);

        for (int i = 0; i < motelList.size(); i++) {
            System.out.println("Creando marcadores "+
                    "titulo "+motelList.get(i).getTitle()+
                    " lat "+motelList.get(i).getLatitud()+
                    " long "+motelList.get(i).getLongitud());

            int id = motelList.get(i).getId();
            String name = motelList.get(i).getTitle();
            double lat = motelList.get(i).getLatitud();
            double lon = motelList.get(i).getLongitud();

            mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(motelList.get(i).getLatitud(), motelList.get(i).getLongitud()))
                    .title(motelList.get(i).getTitle())
                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher)));
        }

        mMap.setOnMarkerClickListener((OnMarkerClickListener) this);
        mMap.setMyLocationEnabled(true);
        mMap.animateCamera(CameraUpdateFactory
                .newLatLngZoom(new LatLng(6.244203, -75.58121189999997), 12));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        final Context con = this;
        Intent intent = new Intent(con, RoomsActitvity.class);
        /*switch (marker.getTitle()){
            case "Motel Penthouse":
                Toast.makeText(getApplicationContext(), "Motel Penthouse",
                        Toast.LENGTH_SHORT).show();
                startActivity(intent);
                break;
        }*/
        startActivity(intent);
        return false;
    }


    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }

}
