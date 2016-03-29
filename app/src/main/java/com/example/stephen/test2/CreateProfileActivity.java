package com.example.stephen.test2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
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

import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class CreateProfileActivity extends LoginActivity {

    private CheckBox checkBoxCarpenter, checkBoxLocksmith, checkBoxGlazer, checkBoxPlumber, checkBoxElectrician, checkBoxGardener, checkBoxMechanic;
    private TextView NameText;
    private ImageView Profiler;
    byte pro;
    String ID, bash;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        addListenerOnCheckboxes();



        GraphRequest request = GraphRequest.newMeRequest(
                AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(final JSONObject Fobject, GraphResponse response) {

                        ID = null;
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

                        //final String ID = response.toString();




                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id, name, picture");//----------picture hasnt been added yet---------
        request.setParameters(parameters);
        request.executeAsync();

        //addProfiler();


        /*
        public Bitmap getUserPic(String ID) {
            String imageURL;
            Bitmap bitmap = null;
            Log.d(TAG, "Loading Picture");
            imageURL = "http://graph.facebook.com/"+ID+"/picture?type=small";
            try {
                bitmap = BitmapFactory.decodeStream((InputStream) new URL(imageURL).getContent());
            } catch (Exception e) {
                Log.d("TAG", "Loading Picture FAILED");
                e.printStackTrace();
            }
            return bitmap;
        }*/





/*
        NameText = (TextView) findViewById(R.id.NameID);
        NameText.setText("" + FName);*/


        //addListenerOnCarpenter();//see if listener for multiple checkboxes
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
                //--------------------------------------------------come back-------------------------------------------

                //addListenerOnCheckboxes();
                getPhone();
                getBio();
                //bash = "bash";



                //if write to db is successful then go to search
                startActivity(new Intent(CreateProfileActivity.this, SearchActivity.class));
            }
        });

    }

    //public void addProfiler(){


    //}
    public void getPhone(){

        EditText PhoneNo = (EditText) findViewById(R.id.Phone);
        String PhoneNumber = PhoneNo.getText().toString();
        //if (PhoneNumber != null && bash != null)
        if (PhoneNumber != null)
        {
        final Firebase userRef = new Firebase("https://test1-polly.firebaseio.com/users");
        final Firebase ref = userRef.child("" + ID);
        Map<String, Object> Phone = new HashMap<String, Object>();
        Phone.put("Phone", "" + PhoneNumber);
        ref.updateChildren(Phone);
        }
    }

    public void getBio(){

        EditText Bio = (EditText) findViewById(R.id.Bio);
        String Bio2 = Bio.getText().toString();
        if (Bio2 != null) {
            final Firebase userRef = new Firebase("https://test1-polly.firebaseio.com/users");
            final Firebase ref = userRef.child("" + ID);
            Map<String, Object> Biog = new HashMap<String, Object>();
            Biog.put("Bio", "" + Bio2);
            ref.updateChildren(Biog);
        }
    }



    public void addListenerOnCheckboxes() {
        checkBoxCarpenter = (CheckBox) findViewById(R.id.checkBoxCarpenter);
        checkBoxCarpenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //is checkbox checked?
                if (((checkBoxCarpenter.isChecked()))) {

                    //updates user profile with selected skills
                    String Carpenter = "Carpenter";
                    final Firebase userRef = new Firebase("https://test1-polly.firebaseio.com/users");
                    final Firebase ref = userRef.child("" + ID);
                    Map<String, Object> Skill = new HashMap<String, Object>();
                    Skill.put("Skill", "" + Carpenter);
                    ref.updateChildren(Skill);
                }
            }
        });

        checkBoxLocksmith = (CheckBox) findViewById(R.id.checkBoxLocksmith);
        checkBoxLocksmith.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //is checkbox checked?
                if (((checkBoxLocksmith.isChecked()))) {

                    //updates user profile with selected skills
                    String Locksmith = "Locksmith";
                    final Firebase userRef = new Firebase("https://test1-polly.firebaseio.com/users");
                    final Firebase ref = userRef.child("" + ID);
                    Map<String, Object> Skill = new HashMap<String, Object>();
                    Skill.put("Skill", "" + Locksmith);
                    ref.updateChildren(Skill);
                }
            }
        });

        checkBoxGlazer = (CheckBox) findViewById(R.id.checkBoxGlazer);
        checkBoxGlazer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //is checkbox checked?
                if (((checkBoxGlazer.isChecked()))) {

                    //updates user profile with selected skills
                    String Glazer = "Glazer";
                    final Firebase userRef = new Firebase("https://test1-polly.firebaseio.com/users");
                    final Firebase ref = userRef.child("" + ID);
                    Map<String, Object> Skill = new HashMap<String, Object>();
                    Skill.put("Skill", "" + Glazer);
                    ref.updateChildren(Skill);
                }
            }
        });

        checkBoxPlumber = (CheckBox) findViewById(R.id.checkBoxPlumber);
        checkBoxPlumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //is checkbox checked?
                if (((checkBoxPlumber.isChecked()))) {

                    //updates user profile with selected skills
                    String Plumber = "Plumber";
                    final Firebase userRef = new Firebase("https://test1-polly.firebaseio.com/users");
                    final Firebase ref = userRef.child("" + ID);
                    Map<String, Object> Skill = new HashMap<String, Object>();
                    Skill.put("Skill", "" + Plumber);
                    ref.updateChildren(Skill);
                }
            }
        });

        checkBoxElectrician = (CheckBox) findViewById(R.id.checkBoxElectrician);
        checkBoxElectrician.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //is checkbox checked?
                if (((checkBoxElectrician.isChecked()))) {

                    //updates user profile with selected skills
                    String Electrician = "Electrician";
                    final Firebase userRef = new Firebase("https://test1-polly.firebaseio.com/users");
                    final Firebase ref = userRef.child("" + ID);
                    Map<String, Object> Skill = new HashMap<String, Object>();
                    Skill.put("Skill", "" + Electrician);
                    ref.updateChildren(Skill);
                }
            }
        });

        checkBoxGardener = (CheckBox) findViewById(R.id.checkBoxGardener);
        checkBoxGardener.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //is checkbox checked?
                if (((checkBoxGardener.isChecked()))) {

                    //updates user profile with selected skills
                    String Gardener = "Gardener";
                    final Firebase userRef = new Firebase("https://test1-polly.firebaseio.com/users");
                    final Firebase ref = userRef.child("" + ID);
                    Map<String, Object> Skill = new HashMap<String, Object>();
                    Skill.put("Skill", "" + Gardener);
                    ref.updateChildren(Skill);
                }
            }
        });

        checkBoxMechanic = (CheckBox) findViewById(R.id.checkBoxMechanic);
        checkBoxMechanic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //is checkbox checked?
                if (((checkBoxMechanic.isChecked()))) {

                    //updates user profile with selected skills
                    String Mechanic = "Mechanic";
                    final Firebase userRef = new Firebase("https://test1-polly.firebaseio.com/users");
                    final Firebase ref = userRef.child("" + ID);
                    Map<String, Object> Skill = new HashMap<String, Object>();
                    Skill.put("Skill", "" + Mechanic);
                    ref.updateChildren(Skill);
                }
            }
        });


    }




}
