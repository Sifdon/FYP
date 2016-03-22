package com.example.stephen.test2;

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

import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.TextView;

public class SearchActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {

    public static final String TAG = SearchActivity.class.getSimpleName();
    private SeekBar RadiusBar;
    private TextView RadiusText;
    private CheckBox checkBoxCarpenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        addListenerOnCarpenter();

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);


        //Done button like create profile
        //once pressed sends to maps where queries start

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

    public void addListenerOnCarpenter() {
        checkBoxCarpenter = (CheckBox) findViewById(R.id.checkBoxCarpenter);
        checkBoxCarpenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //is checkbox checked?
                if (((CheckBox) v).isChecked()) {

                }
            }
        });
    }





}
