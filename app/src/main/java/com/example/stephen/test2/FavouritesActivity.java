package com.example.stephen.test2;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TabHost;

public class FavouritesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        TabHost tabHost = (TabHost)findViewById(android.R.id.tabhost);

        TabHost.TabSpec tab1 = tabHost.newTabSpec("First Tab");
        TabHost.TabSpec tab2 = tabHost.newTabSpec("Second Tab");
        TabHost.TabSpec tab3 = tabHost.newTabSpec("Third Tab");

        tab1.setIndicator("Tab1");
        tab1.setContent(new Intent(this,MapResultsActivity.class));

        tab2.setIndicator("Tab2");
        tab2.setContent(new Intent(this,FavouritesActivity.class));

        tab3.setIndicator("Tab3");
        tab3.setContent(new Intent(this,SettingsActivity.class));

        tabHost.addTab(tab1);
        tabHost.addTab(tab2);
        tabHost.addTab(tab3);
    }

}
