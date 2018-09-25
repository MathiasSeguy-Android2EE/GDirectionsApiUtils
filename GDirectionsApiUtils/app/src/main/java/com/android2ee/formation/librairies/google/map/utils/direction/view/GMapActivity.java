package com.android2ee.formation.librairies.google.map.utils.direction.view;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.android2ee.formation.librairies.google.map.utils.direction.DCACallBack;
import com.android2ee.formation.librairies.google.map.utils.direction.GDirectionsApiUtils;
import com.android2ee.formation.librairies.google.map.utils.direction.model.GDColor;
import com.android2ee.formation.librairies.google.map.utils.direction.model.GDLegs;
import com.android2ee.formation.librairies.google.map.utils.direction.model.GDPath;
import com.android2ee.formation.librairies.google.map.utils.direction.model.GDPoint;
import com.android2ee.formation.librairies.google.map.utils.direction.model.GDirection;
import com.android2ee.formation.librairies.google.map.utils.direction.util.Avoid;
import com.android2ee.formation.librairies.google.map.utils.direction.util.GDirectionData;
import com.android2ee.formation.librairies.google.map.utils.direction.util.GDirectionMapsOptions;
import com.android2ee.formation.librairies.google.map.utils.direction.util.Mode;
import com.android2ee.formation.librairies.google.map.utils.direction.util.UnitSystem;
import com.android2ee.formations.librairies.google.map.utils.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Dot;
import com.google.android.gms.maps.model.Gap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PatternItem;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Harini on 22-06-2017.
 */
public class GMapActivity extends AppCompatActivity implements DCACallBack {
    /***********************************************************
     *  Attributes
     **********************************************************/
    private static final String TAG = "GMapActivity";
    /**
     * The google Api key to use, this one come from the manifest
     * It's used for test
     * You need to uncomment the activity and meta data in the manifest to enable this activity
     * You also need to define (in your gradle) it's an application, not a lib
     * If you want to test
     */
    private static final String GoogleApiKey = "AIzaSyCkmF88VZEneajCLRXP50NB7Fla3pFG3oE";
    /**
     * Represents the Google Map
     */
    private GoogleMap mMap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);
        initializeGMap();
    }

    /**
     * Get the Google Direction between mDevice location and the touched location using the Walk
     *
     * @param startPoint
     * @param endPoint
     */
    private void getDirections(LatLng startPoint, LatLng endPoint) {
        GDirectionData.Builder builder = new GDirectionData.Builder(startPoint, endPoint,GoogleApiKey)
                .setMode(Mode.MODE_DRIVING)
                .setAvoid(Avoid.AVOID_HIGHWAYS)
                .setLanguage("fr")
                .setAlternative(true)
                .setUs(UnitSystem.US_METRIC);
        GDirectionData data = new GDirectionData.DepartureCreator(builder)
                .setDeparture_time("now")
                .build();
        GDirectionsApiUtils.getDirection(this, data);
    }

    @Override
    public void onDirectionLoaded(List<GDirection> directions) {
        ArrayList<GDColor> colors = new ArrayList<GDColor>();
        colors.add(new GDColor(Color.MAGENTA, BitmapDescriptorFactory.HUE_MAGENTA));
        colors.add(new GDColor(Color.BLUE, BitmapDescriptorFactory.HUE_BLUE));
        colors.add(new GDColor(Color.RED, BitmapDescriptorFactory.HUE_ORANGE));
        colors.add(new GDColor(Color.GREEN, BitmapDescriptorFactory.HUE_CYAN));

        PolylineOptions polylineOptions;

        for (int i = 0; i < directions.size(); i++) {

            ArrayList<GDColor> color = new ArrayList<GDColor>();
            color.add(colors.get(i));

            if( i != 0 ) {
                List<PatternItem> pattern = Arrays.asList(new Dot(), new Gap(20));
                polylineOptions = new PolylineOptions().width(10).pattern(pattern);
            } else {
                polylineOptions = new PolylineOptions().width(10).zIndex(5);
            }

            GDirectionMapsOptions.Builder builder = new GDirectionMapsOptions.Builder()
                    .setColors(color)
                    .setPolylineOptions(polylineOptions);

            GDirectionMapsOptions mapOptions = builder.build();
            GDirectionsApiUtils.drawGDirectionWithoutPathMarker(directions.get(i), mMap, mapOptions);

            if( i == 0 ) {
                int pathListSize = directions.get(i).getLegsList().get(0).getPathsList().size();
                int pathSize = directions.get(i).getLegsList().get(0).getPathsList().get(pathListSize - 1).getPath().size();

                GDPoint path = directions.get(i).getLegsList().get(0).getPathsList().get(pathListSize - 1).getPath().get(pathSize - 1);

                Log.d(TAG, "Start Latitude : " + directions.get(i).getLegsList().get(0).getPathsList().get(0).getPath().get(0).getLatLng().latitude + " " +
                        " Start Longitude : " + directions.get(i).getLegsList().get(0).getPathsList().get(0).getPath().get(0).getLatLng().longitude );

                Log.d(TAG, "End Latitude : " + path.getLatLng().latitude + " End Longitude : " + path.getLatLng().longitude );

                mMap.addMarker(new MarkerOptions().position(directions.get(i).getLegsList().get(0).getPathsList().get(0).getPath().get(0).getLatLng())
                        .icon(((colors != null && colors.size() > 0) ?
                                BitmapDescriptorFactory.defaultMarker(colors.get(0 % colors.size()).colorPin) :
                                BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))));

                mMap.addMarker(new MarkerOptions().position(path.getLatLng())
                        .icon(((colors != null && colors.size() > 0) ?
                                BitmapDescriptorFactory.defaultMarker(colors.get(0 % colors.size()).colorPin) :
                                BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))));

                Log.d(TAG, "Distance : " + directions.get(i).getLegsList().get(0).getmDistance());
                Log.d(TAG, "Duration : " + directions.get(i).getLegsList().get(0).getmDuration());
            }
        }
    }

    @Override
    public void onDirectionLoadedFailure() {

    }

    /***********************************************************
     *  Managing Google Map
     **********************************************************/
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    private void initializeGMap() {
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.vlca_fgt_map);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                onFinishInitializationGMap(googleMap);
            }
        });
    }

    /**
     * Called when the map is ready to add all markers and objects to the map.
     */
    private void onFinishInitializationGMap(GoogleMap googleMap) {
        mMap = googleMap;
        UiSettings uiSettings = mMap.getUiSettings();
        uiSettings.setMapToolbarEnabled(false);
        uiSettings.setCompassEnabled(false);
        uiSettings.setRotateGesturesEnabled(false);
        uiSettings.setMyLocationButtonEnabled(false);

        getDirections(new LatLng(48.830582, 2.235642), new LatLng(52.37141, 17.83080));
    }
}
