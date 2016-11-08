package erica.locationscrapbook;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private Location mLastLocation;

    private double mLatitudeText;
    private double mLongitudeText;
    private Geocoder geoCoder;
    private DBService service;

    // Debugging tag
    private String TAG = "MapsActivity.java";

    // Butterknifing
    @BindView(R.id.addCurrent) Button addCurrent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        ButterKnife.bind(MapsActivity.this);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        service = new DBService(this);
        geoCoder = new Geocoder(this);

        // Create an instance of GoogleAPIClient.
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
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

        if (mMap != null) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                Toast.makeText(MapsActivity.this, "We need permission to access your location!", Toast.LENGTH_SHORT).show();

                int permissionCheck = 0;

                ActivityCompat.requestPermissions(MapsActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION}, permissionCheck);
            } else {
                mMap.setMyLocationEnabled(true);
            }
        }
    }

    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    @Override
    public void onConnected(Bundle connectionHint) {

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);


        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "permission not granted");
            return;
        }

        if (service.getAll() != null) {
            ArrayList<erica.locationscrapbook.Location> allLoc = service.getAll();
            for (int i = 0; i < allLoc.size(); i++) {
                erica.locationscrapbook.Location markerLocation = allLoc.get(i);

                mMap.addMarker(new MarkerOptions().position(markerLocation.getLatLng())
                        .title(markerLocation.getName())
                        .snippet(markerLocation.getDescription()));
            }
        }

        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            mLatitudeText = mLastLocation.getLatitude();
            mLongitudeText = mLastLocation.getLongitude();
        }
        
        final LatLng current_location = new LatLng(mLatitudeText, mLongitudeText);


        addCurrent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // make an alert dialog

                AlertDialog.Builder input = new AlertDialog.Builder(MapsActivity.this);
                input.setTitle("Input");
                input.setCancelable(false);

                LinearLayout layout = new LinearLayout(MapsActivity.this);
                layout.setOrientation(LinearLayout.VERTICAL);

                final EditText location = new EditText(MapsActivity.this);
                location.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);

                location.setHint("Location");
                layout.addView(location);

                final CheckBox useCurrent = new CheckBox(MapsActivity.this);
                useCurrent.setText(R.string.currentLocation);
                layout.addView(useCurrent);

                input.setView(layout);


                input.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (useCurrent.isChecked()) {
                            MarkerOptions newMarkerOpts = new MarkerOptions()
                                    .position(current_location)
                                    .title("Current Location")
                                    .snippet("Description");
                            Marker currentLoc = mMap.addMarker(newMarkerOpts);
                            service.addLoc(currentLoc);
                        } else {
                            try {
                                List<Address> geoResults = geoCoder.getFromLocationName(location.getText().toString(), 1);
                                while (geoResults.size()==0) {
                                    geoResults = geoCoder.getFromLocationName(location.getText().toString(), 1);
                                }
                                if (geoResults.size()>0) {
                                    Address addr = geoResults.get(0);
                                    LatLng past_location = new LatLng(addr.getLatitude(), addr.getLongitude());

                                    Marker pastLoc = mMap.addMarker(new MarkerOptions().position(past_location).title("Location Name").snippet("Description"));
                                    service.addLoc(pastLoc);
                                }
                            } catch (Exception e) {
                                Log.d(TAG, e.getMessage());
                            }
                        }
                    }
                });

                input.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                AlertDialog userInput = input.create();
                userInput.show();

            }
        });



        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(final Marker marker) {
                AlertDialog.Builder input = new AlertDialog.Builder(MapsActivity.this);
                input.setTitle("Input");
                input.setCancelable(false);

                LinearLayout layout = new LinearLayout(MapsActivity.this);
                layout.setOrientation(LinearLayout.VERTICAL);

                final EditText title = new EditText(MapsActivity.this);
                title.setHint("Title");
                title.setText(marker.getTitle());
                layout.addView(title);

                final EditText description = new EditText(MapsActivity.this);
                description.setHint("Description");
                description.setText(marker.getSnippet());
                layout.addView(description);

                input.setView(layout);


                input.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        marker.setTitle(title.getText().toString());
                        marker.setSnippet(description.getText().toString());
                        marker.hideInfoWindow();

                        service.updateLoc(marker);
                    }
                });

                input.setNeutralButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        marker.remove();

                        service.deleteLoc(marker);
                    }
                });

                input.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                AlertDialog userInput = input.create();
                userInput.show();
            }
        });

        mMap.moveCamera(CameraUpdateFactory.newLatLng(current_location));
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
