package com.example.bg50xx.assignment;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, View.OnClickListener {
    //Initialize variables
    private GoogleMap mMap;
    ImageButton home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        //set up the imageButton for navigation
        home = (ImageButton) findViewById(R.id.imgHome);
        home.setOnClickListener(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.moveCamera(CameraUpdateFactory.zoomTo(16));

        // Add a marker for Natural History museum and move the camera
        LatLng nhm = new LatLng(51.495915, -0.176366);
        LatLng southkentube = new LatLng(51.494144, -0.173929);
        LatLng gloucestertube = new LatLng(51.494463, -0.182911);
        LatLng thaisquare = new LatLng(51.495280, -0.173654);
        LatLng fernandezwells = new LatLng (51.494984, -0.173264);
        LatLng honestburgers = new LatLng (51.494417, -0.173550);
        Marker m0 = mMap.addMarker(new MarkerOptions().position(nhm).title("click to go to official Natural History Museum website").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        Marker m1 =mMap.addMarker(new MarkerOptions().position(southkentube).title("find underground times for South Kensington station").icon(BitmapDescriptorFactory.fromResource(R.drawable.tube)));
        Marker m2 =mMap.addMarker(new MarkerOptions().position(gloucestertube).title("find underground times for Gloucester Road station").icon(BitmapDescriptorFactory.fromResource(R.drawable.tube)));
        Marker m3 =mMap.addMarker(new MarkerOptions().position(thaisquare).title("Book a table @ Thai Square Restaurant").icon(BitmapDescriptorFactory.fromResource(R.drawable.restaurant)));
        Marker m4 =mMap.addMarker(new MarkerOptions().position(fernandezwells).title("Book a table @ Fernandez & Wells restaurant").icon(BitmapDescriptorFactory.fromResource(R.drawable.restaurant)));
        Marker m5 =mMap.addMarker(new MarkerOptions().position(honestburgers).title("Book a table @ Honest burgers").icon(BitmapDescriptorFactory.fromResource(R.drawable.restaurant)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(nhm));// Set a listener for marker click.
        mMap.setOnMarkerClickListener(this);
        m0.setTag(0);
        m1.setTag(0);
        m2.setTag(0);
        m3.setTag(0);
        m4.setTag(0);
        m5.setTag(0);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        // Retrieve the data from the marker.
        Integer clickCount = (Integer) marker.getTag();
        String clicked = marker.getId();
        Log.d("marker", clicked);


        // Check if a click count was set, then display the click count.
        if (clickCount != null) {
            clickCount = clickCount + 1;
            marker.setTag(clickCount);
            Log.d("marker", clickCount.toString());
        }
        //if clickcount is greater than one, open relevant browser window
        if(clickCount > 1){
            if (clicked.equals("m0")){
                Log.d("marker", "Working");
                Uri link = Uri.parse("http://www.nhm.ac.uk");
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, link);
                startActivity(browserIntent);
            }
            if(clicked.equals("m1") || clicked.equals("m2")){
                Log.d("marker", "Working");
                Uri link = Uri.parse("https://tfl.gov.uk/modes/tube/");
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, link);
                startActivity(browserIntent);
            }
            if(clicked.equals("m3") || clicked.equals("m4") || clicked.equals("m5")){
                Log.d("marker", "Working");
                Uri link = Uri.parse("https://www.bookatable.co.uk/london-kensington-restaurants");
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, link);
                startActivity(browserIntent);
            }
        }

        // Return false to indicate that we have not consumed the event and that we wish
        // for the default behavior to occur (which is for the camera to move such that the
        // marker is centered and for the marker's info window to open, if it has one).
        return false;
    }

    @Override
    public void onClick(View view) {
        Intent myIntent = new Intent(this.getApplication().getApplicationContext(), MainActivity.class);
        startActivity(myIntent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}
