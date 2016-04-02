package com.example.stephen.test2;

import android.Manifest;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.Resources;
import android.location.Location;
import android.media.Image;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TextView;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class LoginActivity extends AppCompatActivity implements LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, ResultCallback<LocationSettingsResult> {

    CallbackManager callbackManager;
    public static final String TAG = LoginActivity.class.getSimpleName();

    private LocationRequest mLocationRequest;
    //protected LocationSettingsRequest mLocationSettingsRequest;
    //protected boolean mRequestingLocationUpdates = false;
    private GoogleApiClient mGoogleApiClient;
    public static final int MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION = 0;
    public Location mLastLocation;
    protected static final int REQUEST_CHECK_SETTINGS = 0x1;
    String lat, lon;
    //String ID;
    //final String finalID = null;



    //private FragmentTabHost mTabHost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        loadPermissions(Manifest.permission.ACCESS_FINE_LOCATION, MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION);
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        setContentView(R.layout.activity_login);
        Firebase.setAndroidContext(this);

        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.example.stephen.test2",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }



        //----------------------------------------------implementing tabs------------------------------------------------------
        /*
        Resources res = getResources();

        mTabHost = (FragmentTabHost)findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

        mTabHost.addTab(mTabHost.newTabSpec("MapsPeople").setIndicator("", res.getDrawable(R.drawable.icon_people_config)),
                FragmentStackSupport.MapResultsActivity.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("Chats").setIndicator("", res.getDrawable(R.drawable.icon_messages_config)),
                LoaderCursorSupport.ChatActivity.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("Settings").setIndicator("", res.getDrawable(R.drawable.icon_settings_config)),
                LoaderCustomSupport.SettingsActivity.class, null);


        /*

        //implementeing tabhost for app
        // maps tab
        Intent intentmapspeople = new Intent().setClass(this, MapResultsActivity.class);
        TabHost.TabSpec tabSpecPeople = tabHost
                .newTabSpec("MapsPeople")
                .setIndicator("", res.getDrawable(R.drawable.icon_people_config))
                .setContent(intentmapspeople);

        // chat tab
        Intent intentchat = new Intent().setClass(this, ChatActivity.class);
        TabHost.TabSpec tabSpecChat = tabHost
                .newTabSpec("Apple")
                .setIndicator("", res.getDrawable(R.drawable.icon_messages_config))
                .setContent(intentchat);

        // settings tab
        Intent intentsettings = new Intent().setClass(this, SettingsActivity.class);
        TabHost.TabSpec tabSpecSettings = tabHost
                .newTabSpec("Windows")
                .setIndicator("", res.getDrawable(R.drawable.icon_settings_config))
                .setContent(intentsettings);


        // add all tabs
        tabHost.addTab(tabSpecPeople);
        tabHost.addTab(tabSpecChat);
        tabHost.addTab(tabSpecSettings);

        //set mapspeople tab as default (zero based)
        tabHost.setCurrentTab(0);
        */
        //-----------------------------------------------end implementing tabs--------------------------



        //adding location updates to this activity-----------------------------
        buildGoogleApiClient();
        /*
        createLocationRequest();
        periodicLocationUpdates();
        displayLocation();
        buildLocationSettingsRequest();
        checkLocationSettings();

        */
        //Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(LoginActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // request the permission.

        } else {
            ActivityCompat.requestPermissions(LoginActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION);
            {

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }



        //end of location updates----------------------------------------------------------

        callbackManager = CallbackManager.Factory.create();
        //MultiDex.install(this);
        final Firebase ref = new Firebase("https://test1-polly.firebaseio.com/");

        final Button btn = (Button) findViewById(R.id.mapbutton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, MapResultsActivity.class));
            }
        });

        final Button bttn = (Button) findViewById(R.id.searchbutton);
        bttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SearchActivity.class));
            }
        });

        final Button btttn = (Button) findViewById(R.id.Createbutton);

        btttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, CreateProfileActivity.class));
            }
        });


        onFacebookAccessTokenChange(AccessToken.getCurrentAccessToken());

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {


                        GraphRequest request = GraphRequest.newMeRequest(
                                AccessToken.getCurrentAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(final JSONObject Fobject, GraphResponse response) {

                                        String ID = null;
                                        try {
                                            ID = Fobject.getString("id");
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        Log.d(TAG, ID);

                                        String FName = null;
                                        try {
                                            FName = Fobject.getString("name");
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                        //Image ProfilePic = Fobject.get
                                        //final String ID = response.toString();


                                        final Firebase userRef = new Firebase("https://test1-polly.firebaseio.com/users");
                                        final String finalID = ID;
                                        //finalID = ID;
                                        final String finalFName = FName;
                                        //final String Skills = Skills[];
                                        userRef.child(ID).addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot snapshot) {
                                                if (snapshot.getValue() != null) {
                                                    Log.d(TAG, "USER EXISTS");

                                                    startActivity(new Intent(LoginActivity.this, SearchActivity.class));
                                                    //user exists move to search
                                                } else {
                                                    //user does not exist create user in db.
                                                    //then move to search settings
                                                    Log.d(TAG, "USER DOES NOT EXIST, CREATING ONE NOW");

                                                    //User needs to fill in what they are e.g skills/user
                                                    startActivity(new Intent(LoginActivity.this, CreateProfileActivity.class));

                                                    class User {
                                                        //profile Picture
                                                        private String Lat;
                                                        private String Long;
                                                        private String fullName;

                                                        public User() {
                                                        }

                                                        public User(String Lat, String Long, String fullName) {
                                                            this.Lat = Lat;
                                                            this.Long = Long;
                                                            this.fullName = fullName;
                                                        }

                                                        public String getLat() {
                                                            return Lat;
                                                        }

                                                        public String getLong() {
                                                            return Long;
                                                        }

                                                        public String getFullName() {
                                                            return fullName;
                                                        }
                                                    }

                                                    //final Firebase userRef = new Firebase("https://test1-polly.firebaseio.com/users");
                                                    Firebase Ref = ref.child("users").child("" + finalID);
                                                    User Fobject = new User("" + lat, "" + lon, "" + finalFName);
                                                    Ref.setValue(Fobject);
                                                }
                                            }
                                            @Override
                                            public void onCancelled(FirebaseError arg0) {

                                            }
                                        });
                                    }
                                });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id, name, picture");//----------picture hasnt been added yet---------
                        request.setParameters(parameters);
                        request.executeAsync();
                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                    }
                });

        //Firebase bash = new Firebase("https://test1-polly.firebaseio.com/users/" + finalID);

    }

    @Override
    protected void onStart() {
        super.onStart();
            mGoogleApiClient.connect();

    }

    @Override
    protected void onStop() {
        super.onStop();
            mGoogleApiClient.disconnect();

    }

    @Override
    protected void onPause() {
        super.onPause();
        //stopLocationUpdates();
        //maybe stop client
    }

    @Override
    public void onResume() {
        super.onResume();
        /*
        if(AccessToken.getCurrentAccessToken() != null) {
            //updating lat and lon in database on locationchanged

        }
        /*
        if (mGoogleApiClient.isConnected() && mRequestingLocationUpdates) {
            startLocationUpdates();
        }*/
        //start client
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //stopLocationUpdates();
        //stop client
    }

    /*
    private void periodicLocationUpdates() {
        if (!mRequestingLocationUpdates) {

            mRequestingLocationUpdates = true;
            // Starting the location updates
            startLocationUpdates();
            Log.d(TAG, "Periodic location updates started!");

        } else {
            mRequestingLocationUpdates = false;
            stopLocationUpdates();
            Log.d(TAG, "Periodic location updates stopped!");
        }
    }

    private void displayLocation() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if (mLastLocation != null) {
            double latitude = mLastLocation.getLatitude();
            double longitude = mLastLocation.getLongitude();

            Log.d(TAG, String.format("%s, %s", latitude, longitude));
            //bash.setText(String.format("%s, %s", latitude, longitude));
            //bash.setText( String.format( "Value of a: %.2f", latitude ) );


        } else {
            Log.d(TAG, "not working");

            //bash.setText("Couldn't get the location. Make sure location is enabled on the device");
        }
    }*/

    private void loadPermissions(String perm,int requestCode) {
        if (ContextCompat.checkSelfPermission(this, perm) != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this, perm)) {
                ActivityCompat.requestPermissions(this, new String[]{perm},requestCode);
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
                    Log.d(TAG, "Permission granted");


                } else {
                    Log.d(TAG, "Permission not granted");

                    // permission denied
                }
                return;
            }


        }
    }


    protected synchronized void buildGoogleApiClient() {
        Log.i(TAG, "Building GoogleApiClient");
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }



    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
    }



    /*
    protected void buildLocationSettingsRequest() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        mLocationSettingsRequest = builder.build();
    }

    protected void checkLocationSettings() {
        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(
                        mGoogleApiClient,
                        mLocationSettingsRequest
                );
        result.setResultCallback(this);
    }*/



    @Override
    public void onConnected(Bundle connectionHint) {

        //displayLocation();
        createLocationRequest();
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
            lat = String.valueOf(mLastLocation.getLatitude());
            lon = String.valueOf(mLastLocation.getLongitude());

        }

        //Log.d(TAG, lat +", " + lon);

        /*
        if (mRequestingLocationUpdates) {
            //startLocationUpdates();
        }*/


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        /*
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        //Log.i(TAG, "last location" + mLastLocation);
        if (mLastLocation != null) {
            // Determine whether a Geocoder is available.
            if (!Geocoder.isPresent()) {
                Toast.makeText(this, R.string.no_geocoder_available,
                        Toast.LENGTH_LONG).show();
                //return;
            }

        }*/

    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }




    protected void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }

    protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
    }

    @Override
    public void onLocationChanged(Location location) {
        //your code here
        //mLastLocation = location;
        //displayLocation();
        lat = String.valueOf(location.getLatitude());
        lon = String.valueOf(location.getLongitude());
        //updateUI();
        Log.d(TAG, lat + ", " + lon);
        final String[] ID = new String[1];

        GraphRequest request = GraphRequest.newMeRequest(
                AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(final JSONObject Fobject, GraphResponse response) {

                        ID[0] = null;
                        try {
                            ID[0] = Fobject.getString("id");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.d(TAG, ID[0]);

                        //updating lat and lon in database on locationchanged
                        final Firebase userRef = new Firebase("https://test1-polly.firebaseio.com/");
                        Firebase Ref = userRef.child("users").child("" + ID.toString());
                        //Query rRef = Ref.equalTo("", finalID);
                        Ref.child("lat").setValue("" + lat);
                        Ref.child("long").setValue("" + lon);



                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id");
        request.setParameters(parameters);
        request.executeAsync();
    }


    @Override
    public void onResult(LocationSettingsResult result) {

        final Status status = result.getStatus();
        switch (status.getStatusCode()) {
            case LocationSettingsStatusCodes.SUCCESS:
                Log.i(TAG, "All location settings are satisfied.");
                //Log.d(TAG,result.toString());
                //startLocationUpdates();
                break;
            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                Log.i(TAG, "Location settings are not satisfied. Show the user a dialog to" +
                        "upgrade location settings ");

                try {
                    // Show the dialog by calling startResolutionForResult(), and check the result
                    // in onActivityResult().
                    status.startResolutionForResult(LoginActivity.this, REQUEST_CHECK_SETTINGS);
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



    //----------------------------------------------------------------facebook
    private void onFacebookAccessTokenChange(final AccessToken token) {
        if (token != null) {
            final Firebase ref = new Firebase("https://test2-polly.firebaseio.com/");

            ref.authWithOAuthToken("facebook", token.getToken(), new Firebase.AuthResultHandler() {
                @Override
                public void onAuthenticated(AuthData authData) {
                    // The Facebook user is now authenticated with your Firebase app


                }

                @Override
                public void onAuthenticationError(FirebaseError Error) {
                    // there was an error
                    switch (Error.getCode()) {
                        case FirebaseError.USER_DOES_NOT_EXIST:
                            // handle a non existing user
                            break;
                        case FirebaseError.INVALID_PASSWORD:
                            // handle an invalid password
                            break;
                        default:
                            // handle other errors
                            break;
                    }
                }

            });/*else{
        // Logged out of Facebook so do a logout from the Firebase app
            ref.unauth();
            }*/
        }
    }


    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        buildGoogleApiClient();

    }




}
