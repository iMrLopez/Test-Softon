package com.marnysofton.weatherapp;

import android.app.AlertDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.marnysofton.weatherapp.Fragment.AboutAppFragment;
import com.marnysofton.weatherapp.Fragment.CitiesListFragment;
import com.marnysofton.weatherapp.Fragment.CityDetailFragment;
import com.marnysofton.weatherapp.Model.City;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, CitiesListFragment.OnListFragmentInteractionListener {

    private ArrayList<City> cities;
    private City selectedCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        this.cities = ((ArrayList<City>) getIntent().getSerializableExtra("Cities"));
        displaySelectedScreen(R.id.nav_cities);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            AlertDialog.Builder builder;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
            } else {
                builder = new AlertDialog.Builder(this);
            }
            builder.setTitle(getString(R.string.Settings))
                    .setMessage(getString(R.string.alert_settings_content))
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        displaySelectedScreen(item.getItemId());
        return true;
    }

    @Override
    public void onListFragmentInteraction(City item) {
        this.selectedCity = item;
        displaySelectedItem();
    }

    private void displaySelectedScreen(int itemId) {

        Fragment selectedFragment = null;

        switch(itemId){
            case R.id.nav_cities:
                selectedFragment = CitiesListFragment.newInstance(1,cities);
                break;
            case R.id.nav_about:
                selectedFragment = AboutAppFragment.newInstance();
                break;
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.citiesListFragment, selectedFragment);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.commit(); //Commit fragment transaction to update user UI

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    private void displaySelectedItem(){
        Fragment selectedFragment =  CityDetailFragment.newInstance(selectedCity);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if ( getResources().getBoolean(R.bool.isTablet)) { //Get to know if device is a tablet
            findViewById(R.id.cityDetailFragment).setVisibility(View.VISIBLE);
            transaction.replace(R.id.cityDetailFragment, selectedFragment);
        } else {
            transaction.replace(R.id.citiesListFragment, selectedFragment);
        }

        //transaction.replace(R.id.citiesListFragment, selectedFragment);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.addToBackStack(selectedFragment.getClass().getSimpleName());
        transaction.commit(); //Commit fragment transaction to update user UI
    }


}
