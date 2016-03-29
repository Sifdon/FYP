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
import android.widget.SeekBar;
import android.widget.TextView;

import com.firebase.client.Firebase;

import java.util.HashMap;
import java.util.Map;

public class SearchActivity extends LoginActivity implements SeekBar.OnSeekBarChangeListener {

    public static final String TAG = SearchActivity.class.getSimpleName();
    private SeekBar RadiusBar;
    private TextView RadiusText;
    private CheckBox checkBoxCarpenter, checkBoxLocksmith, checkBoxGlazer, checkBoxPlumber, checkBoxElectrician, checkBoxGardener, checkBoxMechanic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        //addListenerOnCarpenter();

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);




        RadiusBar = (SeekBar)findViewById(R.id.SeekbarID); // make seekbar object
        RadiusBar.setOnSeekBarChangeListener(this);
        RadiusBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int Radius, boolean fromUser) {
                RadiusText = (TextView) findViewById(R.id.RadiusTextID);
                RadiusText.setText("Radius:: KM's " + Radius);
                Log.d(TAG, "" + Radius);

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

                addListenerOnCheckboxes();;
                //startActivity(new Intent(SearchActivity.this, MapResultsActivity.class));
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

    public void addListenerOnCheckboxes() {
        checkBoxCarpenter = (CheckBox) findViewById(R.id.checkBoxCarpenter);
        checkBoxCarpenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //is checkbox checked?
                if (((checkBoxCarpenter.isChecked()))) {

                    //updates user profile with selected skills
                    String Carpenter = "Carpenter";

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
                   
                }
            }
        });


    }





}
