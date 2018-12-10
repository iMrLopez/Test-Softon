package com.marnysofton.weatherapp;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.marnysofton.weatherapp.Model.City;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SplashScreenActivity extends AppCompatActivity {

    private final int CITIES_TO_SHOW_COUNT = 15;
    private final int LOCATION_PERMISSION_REQUEST_CODE = 1252;

    private Location userLocation;
    private ArrayList<City> cities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getActualUserLocation();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getActualUserLocation();
            } else { // if permission is not granted
                //TODO decide what you want to do if you don't get permissions
            }
        }
    }

    private void getActualUserLocation() {

            ((TextView)findViewById(R.id.text_loadStatus)).setText(R.string.loading_gettingLocation);
            FusedLocationProviderClient mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
            if (ContextCompat.checkSelfPermission(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                if (Build.VERSION.SDK_INT >= 23) { // Marshmallow
                    ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
                }
            } else {

                mFusedLocationClient.getLastLocation()
                        .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                // Got last known location. In some rare situations this can be null.
                                if (location != null) {
                                    Log.d("MLL","Got the user location properly");
                                    ((TextView)findViewById(R.id.text_loadStatus)).setText(R.string.loading_gotLocation);
                                    userLocation = location;
                                    performCallToApi();
                                    Log.d("MLL","Got the data from the API");
                                    ((TextView)findViewById(R.id.text_loadStatus)).setText(R.string.loading_gotCities);
                                }else{
                                    Log.d("MLL","Something weird happened, the user location was not retrieved");
                                    //TODO notify the user of this error in the screen layout
                                    ((TextView)findViewById(R.id.text_loadStatus)).setText(R.string.loading_errCannotGetLocation);
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("MLL","Unable to get the user location");
                        ((TextView)findViewById(R.id.text_loadStatus)).setText(R.string.loading_errCannotGetLocation);
                    }
                });
            }
    }

    private void performCallToApi() {

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = null;

        url = getUrlForRequest(userLocation);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("MLL","Got a response from the API");
                        getListOfCitiesFromJson(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("MLL","No response from API");
                ((TextView)findViewById(R.id.text_loadStatus)).setText(R.string.loading_errCannotGetCities);
            }
        });
        queue.add(stringRequest);
    }

    private String getUrlForRequest(Location uLocation) {
        StringBuilder sb = new StringBuilder(getResources().getString(R.string.API_cities_list));
        sb.append("lat="+uLocation.getLatitude()+"&");
        sb.append("lon="+uLocation.getLongitude()+"&");
        sb.append("cnt="+ CITIES_TO_SHOW_COUNT +"&");
        sb.append("appid="+getResources().getString(R.string.API_key));

        Log.d("MLL","Formed api request properly");

        return sb.toString();
    }

    private void getListOfCitiesFromJson(String json) {
        try {
            JSONObject jsonObj = new JSONObject(json);

            cities  = new Gson().fromJson(jsonObj.getString("list"), new TypeToken<ArrayList<City>>(){}.getType());
            Log.d("MLL","Cities loaded properly in ArrayList");
            this.moveToNextActivity();
        } catch (JSONException e) {
            Log.d("MLL","Cities NOT LOADED PROPERLY");
            e.printStackTrace();
            ((TextView)findViewById(R.id.text_loadStatus)).setText(R.string.loading_errCannotParseCities);
        }
    }

    private void moveToNextActivity(){
        Intent i = new Intent(this, MainActivity.class);
        i.putExtra("Cities", this.cities);
        this.startActivity(i);
        finish();
    }


}
