package com.example.stephen.test2;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;


import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.common.base.Joiner;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import static com.example.stephen.test2.MapResultsActivity.AddressResultReceiver.*;


public class MapResultsActivity extends FragmentActivity implements OnMapReadyCallback, ConnectionCallbacks, LocationListener, OnConnectionFailedListener, ResultCallback<LocationSettingsResult> {

    private GoogleMap mMap;
    public static final int MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION = 1;
    public static final String TAG = MapResultsActivity.class.getSimpleName();
    protected static final int REQUEST_CHECK_SETTINGS = 0x1;

    String id;
    String Lat, meLat;
    String Long, meLong;
    public int rad = SearchActivity.Rad;
    public String myID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_results);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);

        final Button search = (Button) findViewById(R.id.sbutton);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MapResultsActivity.this, SearchActivity.class));
            }
        });

        final Button btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MapResultsActivity.this, SettingsActivity.class));
            }
        });

        final Button btn2 = (Button) findViewById(R.id.button2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MapResultsActivity.this, FavouritesActivity.class));
            }
        });
        final Button btn3 = (Button) findViewById(R.id.button3);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MapResultsActivity.this, MapResultsActivity.class));
            }
        });


        GraphRequest request = GraphRequest.newMeRequest(
                AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(final JSONObject object, GraphResponse response) {
                        myID = null;
                        try {
                            myID = object.getString("id");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.d(TAG, "WORKING:" + myID);

                        final Firebase dref = new Firebase("https://test1-polly.firebaseio.com/users");
                        Query XXref = dref.child("" + myID);
                        XXref.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot child : dataSnapshot.getChildren()) {
                                    Map<String, Object> shop = (HashMap<String, Object>) dataSnapshot.getValue();
                                    meLat = (String) shop.get("lat");
                                    meLong = (String) shop.get("long");
                                    Log.d(TAG, "kebab");

                                    final Firebase ref = new Firebase("https://test1-polly.firebaseio.com/Skill");
                                    Query sref = ref.child("" + SearchActivity.Skill).orderByChild("id");

                                    sref.addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(final DataSnapshot Snapshot, String s) {
                                            for (DataSnapshot child : Snapshot.getChildren()) {//-------------MAYBE INCREMNET THIS TO DISPLAY

                                                final Map<String, Object> bop = (HashMap<String, Object>) Snapshot.getValue();

                                                id = (String) bop.get("id");
                                                //final String finalID = id;

                                                if (id != null) {


                                                        System.out.println("get id: " + id);
                                                        Log.d(TAG, "" + Snapshot.toString());

                                                        final Firebase Iref = new Firebase("https://test1-polly.firebaseio.com/users");
                                                        Query LLref = Iref.child("" + id);

                                                        LLref.addValueEventListener(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(DataSnapshot snapshot) {
                                                                for (DataSnapshot child : snapshot.getChildren()) {
                                                                    Map<String, Object> hop = (HashMap<String, Object>) snapshot.getValue();
                                                                    Lat = (String) hop.get("lat");
                                                                    Long = (String) hop.get("long");
                                                                    final String name = (String) hop.get("fullName");
                                                                    System.out.println("lat:" + Lat + "long:" + Long);
                                                                    //Log.d(TAG, "kebab");


                                                                    double Latme = Double.parseDouble(meLat);
                                                                    double Longme = Double.parseDouble(meLong);

                                                                    Log.d(TAG, "baaaassshhhh");

                                                                    double lat = Double.parseDouble(Lat);
                                                                    double lon = Double.parseDouble(Long);


                                                                    double R = 6371; // earth’s radius (mean radius = 6,371km)
                                                                    double dLat = Math.toRadians(Latme - lat);

                                                                    double dLon = Math.toRadians(Longme - lon);
                                                                    double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                                                                            Math.cos(Math.toRadians(Latme)) * Math.cos(Math.toRadians(lat)) *
                                                                                    Math.sin(dLon / 2) * Math.sin(dLon / 2);
                                                                    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
                                                                    double distance = R * c;

                                                                    Log.d(TAG, "" + distance);

                                                                    if (distance <= rad) {

                                                                        mMap.addMarker(new MarkerOptions()
                                                                                .position(new LatLng(lat, lon))
                                                                                .title(name));
                                                                        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {

                                                                            @Override
                                                                            public boolean onMarkerClick(Marker arg0) {
                                                                                if (arg0.getTitle().equals(name)) {
                                                                                    String getID = id;
                                                                                    startActivity(new Intent(MapResultsActivity.this, ResultProfileActivity.class));

                                                                                }
                                                                                return true;
                                                                            }

                                                                        });

                                                                    }
                                                                }
                                                            }


                                                            @Override
                                                            public void onCancelled(FirebaseError firebaseError) {
                                                            }
                                                        });




                                                }else {
                                                    Context context = getApplicationContext();
                                                    CharSequence text = "There are no Handymen in this area either";
                                                    int duration = Toast.LENGTH_SHORT;

                                                    Toast toast = Toast.makeText(context, text, duration);
                                                    toast.show();
                                                }
                                            }
                                        }

                                        @Override
                                        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                                        }

                                        @Override
                                        public void onChildRemoved(DataSnapshot dataSnapshot) {

                                        }

                                        @Override
                                        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                                        }

                                        @Override
                                        public void onCancelled(FirebaseError firebaseError) {

                                        }
                                    });








                                }
                            }

                            @Override
                            public void onCancelled(FirebaseError firebaseError) {

                            }
                        });






                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id");
        request.setParameters(parameters);
        request.executeAsync();

        Log.d(TAG, "" + myID);


        //select user where skill = skill
        //then check if there within the radius
        //if so display them


        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(MapResultsActivity.this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // request the permission.

        } else {
            ActivityCompat.requestPermissions(MapResultsActivity.this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION);
            {

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted

                } else {

                    // permission denied
                }
                return;
            }


        }
    }

    protected void onStart() {
        //mGoogleApiClient.connect();
        super.onStart();
    }



    @Override
    public void onConnected(Bundle connectionHint) {


    }


    @Override
    public void onConnectionFailed( ConnectionResult connectionResult) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }


    @Override
    protected void onPause() {
        super.onPause();
        //unregisterReceiver(mResultReceiver);
        //stopLocationUpdates();
    }

    @Override
    public void onResume() {
        super.onResume();
        /*
        if (mGoogleApiClient.isConnected() && !mRequestingLocationUpdates) {
            startLocationUpdates();
        }*/


    }




    protected void onStop() {
        //mGoogleApiClient.disconnect();
        super.onStop();
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

    }





    @Override
    public void onResult(LocationSettingsResult result) {

        final Status status = result.getStatus();
        switch (status.getStatusCode()) {
            case LocationSettingsStatusCodes.SUCCESS:
                Log.i(TAG, "All location settings are satisfied.");
                //startLocationUpdates();
                break;
            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                Log.i(TAG, "Location settings are not satisfied. Show the user a dialog to" +
                        "upgrade location settings ");

                try {
                    // Show the dialog by calling startResolutionForResult(), and check the result
                    // in onActivityResult().
                    status.startResolutionForResult(MapResultsActivity.this, REQUEST_CHECK_SETTINGS);
                } catch (IntentSender.SendIntentException e) {
                    Log.i(TAG, "PendingIntent unable to execute request.");
                }
                break;
            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                Log.i(TAG, "Location settings are inadequate, and cannot be fixed here. Dialog " +
                       "not created.");
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            // Check for the integer request code originally supplied to startResolutionForResult().
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        Log.i(TAG, "User agreed to make required location settings changes.");
                        //startLocationUpdates();
                        break;
                    case Activity.RESULT_CANCELED:
                        Log.i(TAG, "User chose not to make required location settings changes.");
                        break;
                }
                break;
        }
    }

    @Override
    public void onLocationChanged(final Location location) {
        //your code here
        //mCurrentLocation = location;
    }


}
