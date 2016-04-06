package com.example.stephen.test2;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.firebase.client.Firebase;

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




public class SearchActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {

    public static final String TAG = SearchActivity.class.getSimpleName();
    private SeekBar RadiusBar;
    private TextView RadiusText;
    private RadioGroup radio;
    static String Skill = null;
    static int Rad = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);

        addlistenertoradio();

            //defininf a seek bar for radius
        RadiusBar = (SeekBar)findViewById(R.id.SeekbarID); // make seekbar object
        RadiusBar.setOnSeekBarChangeListener(this);
        RadiusBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int Radius, boolean fromUser) {
                RadiusText = (TextView) findViewById(R.id.RadiusTextID);
                RadiusText.setText("Radius:: KM's " + Radius);
                Log.d(TAG, "" + Radius);
                Rad = Radius;//radius can be accessed outside this activity

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        //Done button like create profile
        //once pressed sends to maps where queries start
        final Button done = (Button) findViewById(R.id.donebutton);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // if radius and checkbox not null start intent

                //addListenerOnCheckboxes();
                startActivity(new Intent(SearchActivity.this, MapResultsActivity.class));
            }
        });


    }
    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int Radius,
                                  boolean fromUser) {
        // TODO Auto-generated method stub

        RadiusText.setText("Radius:: KM's " + Radius);


    }

    //skill variable is set and accessed on maps results
    public void addlistenertoradio(){
        radio = (RadioGroup)findViewById(R.id.radioGroup);
        radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.CarpenterButton) {
                    Skill = "Carpenter";
                } else if (checkedId == R.id.LocksmithButton) {
                    Skill = "Locksmith";
                } else if (checkedId == R.id.GlazerButton) {
                    Skill = "Glazer";
                } else if (checkedId == R.id.PlumberButton) {
                    Skill = "Plumber";
                } else if (checkedId == R.id.MechanicButton) {
                    Skill = "Mechanic";
                } else if (checkedId == R.id.ElectricianButton) {
                    Skill = "Electrician";
                } else if (checkedId == R.id.GardenerButton) {
                    Skill = "Gardener";
                }
                Log.d(TAG, "BAAAAAAAAAAAAAAAAAAASSSSSSSSSSSSSSSSSSSHHH");
            }
        });
    }

    //saves state of activity if destroyed
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putInt("rad", Rad);
        savedInstanceState.putString("MyString", "" + Skill);
    }
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        Rad = savedInstanceState.getInt("MyInt");
        Skill = savedInstanceState.getString("MyString");
    }






}
