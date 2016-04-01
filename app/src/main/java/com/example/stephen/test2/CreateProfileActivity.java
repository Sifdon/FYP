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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.common.base.Joiner;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateProfileActivity extends AppCompatActivity {

    //private CheckBox checkBoxCarpenter, checkBoxLocksmith, checkBoxGlazer, checkBoxPlumber, checkBoxElectrician, checkBoxGardener, checkBoxMechanic;
    private TextView NameText;
    private ImageView Profiler;
    Bitmap fbitmap;
    byte pro;
    String ID;
    public static List<String> list = new ArrayList<String>();
    public static final String TAG = CreateProfileActivity.class.getSimpleName();
    String id;
    String Skill = null;
    private RadioGroup radio;
    private RadioButton Carpenter, Locksmith, Glazer, Plumber, Gardener, Mechanic, Electrician;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        //addListenerOnCheckboxes();
        addlistenertoradio();





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
        parameters.putString("fields", "id, name");
        request.setParameters(parameters);
        request.executeAsync();

        getUserPic();
        Profiler = (ImageView) findViewById(R.id.imageView);
        Profiler.setImageBitmap(fbitmap);

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
                //addListenerOnCheckboxes();
                getPhone();
                getBio();
                writeskillstodatabase();
                //if write to db is successful then go to search
                startActivity(new Intent(CreateProfileActivity.this, SearchActivity.class));
            }
        });

    }

    public Bitmap getUserPic() {///////-----------------this isnt working---------------------
        String imageURL;
        fbitmap = null;
        Log.d(TAG, "Loading Picture");
        imageURL = "http://graph.facebook.com/"+ID+"/picture?type=small";
        try {
            fbitmap = BitmapFactory.decodeStream((InputStream) new URL(imageURL).getContent());
        } catch (Exception e) {
            Log.d(TAG, "Loading Picture FAILED");
            e.printStackTrace();
        }
        return fbitmap;
        //bitmap = Profiler;

    }




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

/*

    public void addListenerOnCheckboxes() {
        checkBoxCarpenter = (CheckBox) findViewById(R.id.checkBoxCarpenter);
        checkBoxCarpenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //is checkbox checked?
                if (((checkBoxCarpenter.isChecked()))) {

                    String Carpenter = "Carpenter";
                    list.add("" + Carpenter);
                }else {
                    list.remove("Carpenter");
                    Log.d(TAG, "REMOVED");
                }
            }
        });

        checkBoxLocksmith = (CheckBox) findViewById(R.id.checkBoxLocksmith);
        checkBoxLocksmith.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //is checkbox checked?
                if (((checkBoxLocksmith.isChecked()))) {

                    String Locksmith = "Locksmith";
                    list.add("" + Locksmith);
                }else {
                    list.remove("Locksmith");
                    Log.d(TAG, "REMOVED");
                }
            }
        });

        checkBoxGlazer = (CheckBox) findViewById(R.id.checkBoxGlazer);
        checkBoxGlazer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //is checkbox checked?
                if (((checkBoxGlazer.isChecked()))) {

                    String Glazer = "Glazer";
                    list.add("" + Glazer);
                }else {
                    list.remove("Glazer");
                    Log.d(TAG, "REMOVED");
                }
            }
        });

        checkBoxPlumber = (CheckBox) findViewById(R.id.checkBoxPlumber);
        checkBoxPlumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //is checkbox checked?
                if (((checkBoxPlumber.isChecked()))) {

                    String Plumber = "Plumber";
                    list.add("" + Plumber);
                } else {
                    list.remove("Plumber");
                    Log.d(TAG, "REMOVED");
                }
            }
        });

        checkBoxElectrician = (CheckBox) findViewById(R.id.checkBoxElectrician);
        checkBoxElectrician.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //is checkbox checked?
                if (((checkBoxElectrician.isChecked()))) {

                    String Electrician = "Electrician";
                    list.add("" + Electrician);
                }else {
                    list.remove("Electrician");
                    Log.d(TAG, "REMOVED");
                }
            }
        });

        checkBoxGardener = (CheckBox) findViewById(R.id.checkBoxGardener);
        checkBoxGardener.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //is checkbox checked?
                if (((checkBoxGardener.isChecked()))) {

                    String Gardener = "Gardener";
                    list.add("" + Gardener);
                }else {
                    list.remove("Gardener");
                    Log.d(TAG, "REMOVED");
                }
            }
        });

        checkBoxMechanic = (CheckBox) findViewById(R.id.checkBoxMechanic);
        checkBoxMechanic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //is checkbox checked?
                if (((checkBoxMechanic.isChecked()))) {

                    String Mechanic = "Mechanic";
                    list.add("" + Mechanic);
                } else {
                    list.remove("Mechanic");
                    Log.d(TAG, "REMOVED");
                }
            }
        });


    }*/

    public void addlistenertoradio(){
        radio = (RadioGroup)findViewById(R.id.radioGroup);
        radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.CarpenterButton){
                    Skill ="Carpenter";
                }else if(checkedId == R.id.LocksmithButton){
                    Skill ="Locksmith";
                }else if(checkedId == R.id.GlazerButton){
                    Skill ="Glazer";
                }else if(checkedId == R.id.PlumberButton){
                    Skill ="Plumber";
                }else if(checkedId == R.id.MechanicButton){
                    Skill ="Mechanic";
                }else if(checkedId == R.id.ElectricianButton){
                    Skill ="Electrician";
                }else if(checkedId == R.id.GardenerButton){
                    Skill ="Gardener";
                }
                Log.d(TAG, "BAAAAAAAAAAAAAAAAAAASSSSSSSSSSSSSSSSSSSHHH");
            }
        });
    }

    public void writeskillstodatabase(){
        //String Skilljoined = Joiner.on(",").join(list);

        class Skills extends CreateProfileActivity {
            public Skills() {
            }
            public Skills(String id, String Skill) {
                this.id = id;
                this.Skill = Skill;
            }
            public String getid() {
                return id;
            }
            public String getSkill() {
                return Skill;
            }
        }
        Log.d(TAG, "EEEEERRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR");


        final Firebase userRef = new Firebase("https://test1-polly.firebaseio.com/");//took out users
        final Firebase ref = userRef.child("Skills");

        Skills Skillage = new Skills("" + ID, "" + Skill);
        ref.setValue(Skillage);

        //ref.setValue(Fobject);
            //Map<String, Object> Skills = new HashMap<String, Object>();
            //Skills.put("Skills", "" + ID  "" + Skill0);
            //Skills.put("Skills", "" + Skilljoined);


    }


}
