package com.moodybugs.saim.navanatestapp.Activity;

import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.moodybugs.saim.navanatestapp.R;
import com.moodybugs.saim.navanatestapp.Utility.SharedPrefDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MapsActivityForAllProject extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    TextView txtMapAll,txtMapDhaka, txtMapChittagong;
    String responseAll, responseDhaka, responseChittagong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_for_all_project);

        init();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map2);
        mapFragment.getMapAsync(this);
    }

    public void init(){
        txtMapAll = (TextView) findViewById(R.id.txtMapAll);
        txtMapDhaka = (TextView) findViewById(R.id.txtMapDhaka);
        txtMapChittagong = (TextView) findViewById(R.id.txtMapChittagong);


    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        responseAll = new SharedPrefDatabase(getApplicationContext()).RetriveMapProject() + "0";


        txtMapAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.clear();
                populateAllLocation(mMap, responseAll);
            }
        });
        txtMapDhaka.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.clear();
                populateDhaka(mMap, responseAll);
            }
        });

        txtMapChittagong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.clear();
                populateChittagong(mMap, responseAll);
            }
        });
    }

    public void populateAllLocation(GoogleMap gMap, String response){

        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("project");
            for (int i=0; i<jsonArray.length(); i++){
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                String name = jsonObject1.getString("project_name");
                double lat = Double.parseDouble(jsonObject1.getString("latitude"));
                double lon = Double.parseDouble(jsonObject1.getString("longitude"));

                LatLng sydney = new LatLng(lat, lon);
                gMap.addMarker(new MarkerOptions().position(sydney).title(name));
                gMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
                gMap.animateCamera(CameraUpdateFactory.zoomTo(12));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void populateDhaka(GoogleMap gMap, String response){

        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("project");
            for (int i=0; i<jsonArray.length(); i++){
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                String city = jsonObject1.getString("city");
                if (city.equals("1")){
                    String name = jsonObject1.getString("project_name");
                    double lat = Double.parseDouble(jsonObject1.getString("latitude"));
                    double lon = Double.parseDouble(jsonObject1.getString("longitude"));

                    LatLng sydney = new LatLng(lat, lon);
                    gMap.addMarker(new MarkerOptions().position(sydney).title(name));
                    gMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
                    gMap.animateCamera(CameraUpdateFactory.zoomTo(12));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void populateChittagong(GoogleMap gMap, String response){

        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("project");
            for (int i=0; i<jsonArray.length(); i++){
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                String city = jsonObject1.getString("city");
                if (city.equals("2")) {
                    String name = jsonObject1.getString("project_name");
                    double lat = Double.parseDouble(jsonObject1.getString("latitude"));
                    double lon = Double.parseDouble(jsonObject1.getString("longitude"));

                    LatLng sydney = new LatLng(lat, lon);
                    gMap.addMarker(new MarkerOptions().position(sydney).title(name));
                    gMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
                    gMap.animateCamera(CameraUpdateFactory.zoomTo(12));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
