package com.moodybugs.saim.navanatestapp.Activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
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
    GoogleApiClient mGoogleApiClient;
    LocationManager locationManager;
    LocationRequest mLocationRequest;

    TextView txtMapAll, txtMapDhaka, txtMapChittagong, txtMapCurrent;
    String responseAll, responseDhaka, responseChittagong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_for_all_project);

        init();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map2);
        mapFragment.getMapAsync(this);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
    }

    public void init() {
        txtMapAll = (TextView) findViewById(R.id.txtMapAll);
        txtMapDhaka = (TextView) findViewById(R.id.txtMapDhaka);
        txtMapChittagong = (TextView) findViewById(R.id.txtMapChittagong);
        txtMapCurrent = (TextView) findViewById(R.id.txtMapCurrent);

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mGoogleApiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
            @Override
            public void onConnected(@Nullable Bundle bundle) {
                mLocationRequest = new LocationRequest();
                mLocationRequest.setInterval(10);
                mLocationRequest.setFastestInterval(10);
                mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
            }
            @Override
            public void onConnectionSuspended(int i) {

            }
        }).addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
            @Override
            public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

            }
        }).addApi(LocationServices.API).build();
        mGoogleApiClient.connect();
        mMap.setMyLocationEnabled(true);

        responseAll = new SharedPrefDatabase(getApplicationContext()).RetriveMapProject() + "0";

        populateAllLocation(mMap, responseAll);
        txtMapAll.setTextColor(Color.parseColor("#FF0000"));
        txtMapAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.clear();
                populateAllLocation(mMap, responseAll);
                txtMapAll.setTextColor(Color.parseColor("#FF0000"));
                txtMapDhaka.setTextColor(Color.parseColor("#FFFFFF"));
                txtMapChittagong.setTextColor(Color.parseColor("#FFFFFF"));
                txtMapCurrent.setTextColor(Color.parseColor("#FFFFFF"));
            }
        });
        txtMapDhaka.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.clear();
                populateDhaka(mMap, responseAll);
                txtMapAll.setTextColor(Color.parseColor("#FFFFFF"));
                txtMapDhaka.setTextColor(Color.parseColor("#FF0000"));
                txtMapChittagong.setTextColor(Color.parseColor("#FFFFFF"));
                txtMapCurrent.setTextColor(Color.parseColor("#FFFFFF"));
            }
        });

        txtMapChittagong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.clear();
                populateChittagong(mMap, responseAll);
                txtMapAll.setTextColor(Color.parseColor("#FFFFFF"));
                txtMapDhaka.setTextColor(Color.parseColor("#FFFFFF"));
                txtMapChittagong.setTextColor(Color.parseColor("#FF0000"));
                txtMapCurrent.setTextColor(Color.parseColor("#FFFFFF"));
            }
        });

        txtMapCurrent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.clear();
                populateCurrentLocation();
                txtMapAll.setTextColor(Color.parseColor("#FFFFFF"));
                txtMapDhaka.setTextColor(Color.parseColor("#FFFFFF"));
                txtMapChittagong.setTextColor(Color.parseColor("#FFFFFF"));
                txtMapCurrent.setTextColor(Color.parseColor("#FF0000"));
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


    public void populateCurrentLocation(){

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, new android.location.LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    LatLng sydney = new LatLng(location.getLatitude(), location.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(sydney).title("My Current Location"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
                    mMap.animateCamera(CameraUpdateFactory.zoomTo(12));
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            });
        }else if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new android.location.LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    LatLng sydney = new LatLng(location.getLatitude(), location.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(sydney).title("My Current Location"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
                    mMap.animateCamera(CameraUpdateFactory.zoomTo(12));
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            });
        }
    }
}
