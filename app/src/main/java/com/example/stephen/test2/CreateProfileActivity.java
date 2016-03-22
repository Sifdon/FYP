package com.example.stephen.test2;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

public class CreateProfileActivity extends LoginActivity {

    private CheckBox checkBoxCarpenter;
    private TextView NameText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

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


                        NameText = (TextView) findViewById(R.id.NameID);
                        NameText.setText("" + FName);
                        //Image ProfilePic = Fobject.get
                        //final String ID = response.toString();

                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id, name, picture");//----------picture hasnt been added yet---------
        request.setParameters(parameters);
        request.executeAsync();

/*
        NameText = (TextView) findViewById(R.id.NameID);
        NameText.setText("" + FName);*/


        addListenerOnCarpenter();//see if listener for multiple checkboxes
        //error checking if done pressed and checkbox not clicked
        //pull all from facebook again

        //Display facebook info here in text view

        //checkboxes
        //When checked = string
        //checked = carpenter
        //pass to db as string

        //when done button is finished write to database all at once
        final Button done = (Button) findViewById(R.id.donebutton);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //when done button is finished write to database all at once

                //where id = id insert skills/user of app to user



                //if write to db is successful then go to search
                //startActivity(new Intent(CreateProfileActivity.this, SearchActivity.class));
            }
        });

    }
    public void addListenerOnCarpenter() {
        checkBoxCarpenter = (CheckBox) findViewById(R.id.checkBoxCarpenter);
        checkBoxCarpenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //is checkbox checked?
                if (((CheckBox) v).isChecked()) {


                    String Skill1 = "Carpenter";

                }
            }
        });
    }


}
