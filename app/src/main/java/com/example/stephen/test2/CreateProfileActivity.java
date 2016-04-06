package com.example.stephen.test2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.firebase.client.Firebase;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

///******************************************************************************
//All activities utilise standard api code from:
//
///Google Play Services: http://developer.android.com/google/index.html
///Facebook Graph API: https://developers.facebook.com/docs/graph-api
///Firebase API: https://www.firebase.com/docs/android/api/

///******************************************************************************
public class CreateProfileActivity extends AppCompatActivity {

    private TextView NameText;
    private ImageView Profiler;
    Bitmap fbitmap;
    byte pro;
    static String ID;
    public static List<String> list = new ArrayList<String>();
    public static final String TAG = CreateProfileActivity.class.getSimpleName();
    //String id;
    String Skill = null;
    private RadioGroup radio;
    //private RadioButton Carpenter, Locksmith, Glazer, Plumber, Gardener, Mechanic, Electrician;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        //granting permissions for image retrieval
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        addlistenertoradio();

        //facebook graph request
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




        //see if listener for multiple checkboxes
        //error checking if done pressed and checkbox not clicked
        //pull all from facebook again

        //Display facebook info here in text view


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

    public void getUserPic() {///////-----------------this isnt working---------------------
        String imageURL;
        fbitmap = null;
        Log.d(TAG, "Loading Picture");
        imageURL = "https://graph.facebook.com/"+ID+"/picture?type=small";
        try {
            fbitmap = BitmapFactory.decodeStream((InputStream) new URL(imageURL).getContent());
        } catch (Exception e) {
            Log.d(TAG, "Loading Picture FAILED");
            e.printStackTrace();
        }
        //return fbitmap;
        //bitmap = Profiler;
        if(fbitmap != null){
            Profiler = (ImageView)findViewById(R.id.imageView);
            Profiler.setImageBitmap(fbitmap);
        }
    }




    //}
    public void getPhone(){//writing phone number and bios to users profile in database

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

//listener for radio check buttons
    //sets skill
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
                }else if (checkedId == R.id.UserButton){
                    Skill ="User";
                }
                Log.d(TAG, "BAAAAAAAAAAAAAAAAAAASSSSSSSSSSSSSSSSSSSHHH");
            }
        });
    }


    //writes skill to databse storing with id using push method
    public void writeskillstodatabase(){
        //String Skilljoined = Joiner.on(",").join(list);

        class Skill {
            private  String Id;
            public Skill(String id){
                this.Id = id;
            }
            public String getId(){
                return Id;
            }
        }
        Log.d(TAG, "EEEEERRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR");

        final Firebase rRef = new Firebase("https://test1-polly.firebaseio.com/");//took out users
        final Firebase ref = rRef.child("Skill").child("" + Skill);
        Skill Skillage = new Skill("" + ID);
        ref.push().setValue(Skillage);

    }
}
