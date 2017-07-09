package com.moodybugs.saim.navanatestapp.Activity;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_for_all_project);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map2);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        populateAllLocation(mMap);
        /*LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));*/
    }

    public void populateAllLocation(GoogleMap gMap){
        String responseAll = new SharedPrefDatabase(getApplicationContext()).RetriveMapProject() + "0";
        String responseDhaka = new SharedPrefDatabase(getApplicationContext()).RetriveMapProject() + "1";
        String responseChittagong = new SharedPrefDatabase(getApplicationContext()).RetriveMapProject() + "2";

        try {
            JSONObject jsonObject = new JSONObject(responseAll);
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
}
