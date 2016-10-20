package erica.locationscrapbook;

import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity {
//
//    private GoogleMap mMap;
//;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
//
//
//    }
//
//
//    LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
//            .addLocationRequest(mLocationRequest);
//
////    // Assume thisActivity is the current activity
////    int permissionCheck = ContextCompat.checkSelfPermission(MainActivity.this,
////            Manifest.permission.ACCESS_FINE_LOCATION);
//
//    PendingResult<LocationSettingsResult> result =
//            LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient,
//                    builder.build());
//
////
////    result.setResultCallback(new ResultCallback<LocationSettingsResult>()) {
////        @Override
////        public void onResult(LocationSettingsResult result) {
////            final Status status = result.getStatus();
////            final LocationSettingsStates= result.getLocationSettingsStates();
////            switch (status.getStatusCode()) {
////                case LocationSettingsStatusCodes.SUCCESS:
////                    // All location settings are satisfied. The client can
////                    // initialize location requests here.
////                    ...
////                    break;
////                case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
////                    // Location settings are not satisfied, but this can be fixed
////                    // by showing the user a dialog.
////                    try {
////                        // Show the dialog by calling startResolutionForResult(),
////                        // and check the result in onActivityResult().
////                        status.startResolutionForResult(
////                                OuterClass.this,
////                                REQUEST_CHECK_SETTINGS);
////                    } catch (IntentSender.SendIntentException e) {
////                        // Ignore the error.
////                    }
////                    break;
////                case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
////                    // Location settings are not satisfied. However, we have no way
////                    // to fix the settings so we won't show the dialog.
////                    ...
////                    break;
////            }
////        }
////    });
//
////    @Override
//
//
//    @Override
//    public void onConnected(@Nullable Bundle bundle) {
//
//    }
//
//    @Override
//    public void onConnectionSuspended(int i) {
//        Toast.makeText(this.getApplicationContext(), "suspended", Toast.LENGTH_LONG).show();
//    }
//
//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//
//        mMap = googleMap;
//        setUpMap();
//
//    }
//
//    public void setUpMap() {
//
//        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
//
//        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            Log.d(TAG, "You need to accept all permissions");
//            return;
//        }
//        mMap.setMyLocationEnabled(true);
//    }
//
//    @Override
//    public void onLocationChanged(Location location) {
////
////        double dLatitude = mLastLocation.getLatitude();
////        double dLongitude = mLastLocation.getLongitude();
////        marker = mMap.addMarker(new MarkerOptions().position(new LatLng(dLatitude, dLongitude))
////                .title("My Location").icon(BitmapDescriptorFactory
////                        .defaultMarker(BitmapDescriptorFactory.HUE_RED)));
////        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(dLatitude, dLongitude), 8));
////
////    }
//
//    @Override
//    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
//
//    }
}
