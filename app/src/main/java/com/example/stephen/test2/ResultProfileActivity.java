package com.example.stephen.test2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class ResultProfileActivity extends AppCompatActivity {

    public static final String TAG = ResultProfileActivity.class.getSimpleName();
    String PhoneNo, Name, Bio;
    private TextView NameText, PhoneText, BioText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_profile);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        final Button Phone = (Button) findViewById(R.id.Phonebutton);
        Phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("" + PhoneNo));
                if (ActivityCompat.checkSelfPermission(ResultProfileActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                startActivity(callIntent);
            }
        });


        final Firebase dref = new Firebase("https://test1-polly.firebaseio.com/users");
        Query XXref = dref.child("" + MapResultsActivity.getID);
        XXref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Map<String, Object> shop = (HashMap<String, Object>) dataSnapshot.getValue();
                    PhoneNo = (String) shop.get("Phone");
                    Name = (String) shop.get("fullName");
                    Bio = (String) shop.get("Bio");
                    Log.d(TAG, "kebab");

                    NameText = (TextView) findViewById(R.id.NameID);
                    NameText.setText("" + Name);

                    PhoneText = (TextView) findViewById(R.id.PhoneID);
                    PhoneText.setText("" + PhoneNo);

                    BioText = (TextView) findViewById(R.id.BioID);
                    BioText.setText("" + Bio);


                }}

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


    }

}
