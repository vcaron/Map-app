package com.example.vincent.myapplication;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vincent.myapplication.Common.Common;
import com.example.vincent.myapplication.Model.OpenWeatherMap;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


class MapsActivity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener,
        GoogleMap.OnMarkerDragListener, GoogleMap.InfoWindowAdapter {
    LayoutInflater inflater=null;
    private GoogleMap mMap;
    OpenWeatherMap openWeatherMap = new OpenWeatherMap();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

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
        mMap.setOnMapClickListener(this);
        mMap.setOnMapLongClickListener(this);
        mMap.setOnMarkerDragListener(this);
        inflater =  getLayoutInflater();
        mMap.setInfoWindowAdapter(this);
        mMap.setOnInfoWindowClickListener(MyOnInfoWindowClickListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return super.onCreateOptionsMenu(menu);

        //Control
        txtCity = (TextView) findViewById(R.id.txtCity);
        txtLastUpdate = (TextView) findViewById(R.id.txtLastUpdate);
        txtDescription = (TextView) findViewById(R.id.txtDescription);
        txtHumidity = (TextView) findViewById(R.id.txtHumidity);
        txtTime = (TextView) findViewById(R.id.txtTime);
        txtCelsius = (TextView) findViewById(R.id.txtCelsius);
        imageView = (ImageView) findViewById(R.id.imageView);
    }

    // Appuie court sur map
    @Override
    public void onMapClick(LatLng latLng) {
        Toast.makeText(MapsActivity.this, "onMapClick:\n" + latLng.latitude + " : " + latLng.longitude, Toast.LENGTH_LONG).show();


        //Ajoute un marqueur
        MarkerOptions markerOptions = new MarkerOptions().position(latLng).title(latLng.toString());
        markerOptions.draggable(true);

        mMap.addMarker(markerOptions);
    }

    //Appuie long (pas de fonction pour l'instant)
    @Override
    public void onMapLongClick(LatLng latLng) {
        Toast.makeText(MapsActivity.this,
                "onMapLongClick:\n" + latLng.latitude + " : " + latLng.longitude,
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onMarkerDragStart(Marker marker) {
        marker.setTitle(marker.getPosition().toString());
        marker.showInfoWindow();
        marker.setAlpha(0.5f);
    }

    @Override
    public void onMarkerDrag(Marker marker) {
        marker.setTitle(marker.getPosition().toString());
        marker.showInfoWindow();
        marker.setAlpha(0.5f);
    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        marker.setTitle(marker.getPosition().toString());
        marker.showInfoWindow();
        marker.setAlpha(1.0f);
    }

    @Override
    public View getInfoWindow(Marker marker) {

        return null;

    }

    @Override
    public View getInfoContents(Marker marker) {

        View popup=inflater.inflate(R.layout.popup, null);
        TextView tv = (TextView) popup.findViewById(R.id.title);
        tv.setText("Météo :");
        TextView tv2 = (TextView) popup.findViewById(R.id.snippet);
        tv2.setText(marker.getTitle ());
        TextView tv3 = (TextView) popup.findViewById(R.id.Des);
        tv3.setText(String.format("%s", openWeatherMap.getName()));
        //ImageView im = (ImageView) popup.findViewById(R.id.icon);
        //im.getImage(openWeatherMap.getWeather().get(0).getIcon());

        return(popup);

        //return prepareInfoView(marker);
    }
    //Appuie sur la bulle info du marker
    GoogleMap.OnInfoWindowClickListener MyOnInfoWindowClickListener
            = new GoogleMap.OnInfoWindowClickListener() {
        @Override
        public void onInfoWindowClick(Marker marker) {
            Toast.makeText(MapsActivity.this,
                    "Vous avez cliquez sur :\n" +
                            marker.getPosition().latitude + "\n" +
                            marker.getPosition().longitude,
                    Toast.LENGTH_LONG).show();
        }
    };


}