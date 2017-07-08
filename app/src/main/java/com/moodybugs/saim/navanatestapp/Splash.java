package com.moodybugs.saim.navanatestapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.moodybugs.saim.navanatestapp.Utility.ApiUrl;
import com.moodybugs.saim.navanatestapp.Utility.MySingleton;

public class Splash extends AppCompatActivity {

    TextView txtLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        txtLoading = (TextView) findViewById(R.id.txtLoading);

        if (isInternetConnected()){
            SaveFutureProject();
        }else {
            showDialogInternetConnection();
        }
    }


    public void SaveFutureProject() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, ApiUrl.futureProject,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            txtLoading.setText("Future project loading complete");
                            SaveOtherProject();
                        }catch (Exception e){

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }


    public void SaveOtherProject() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, ApiUrl.otherProject,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            txtLoading.setText("Other project loading complete");
                            SaveMapProjectList();
                        }catch (Exception e){

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }

    public void SaveMapProjectList() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, ApiUrl.mapProject+0,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            txtLoading.setText("Map project list loading complete");
                            finish();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }catch (Exception e){

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }


    //Internet connection checking dialog
    public void showDialogInternetConnection(){

        LayoutInflater factory = LayoutInflater.from(this);
        final View deleteDialogView = factory.inflate(R.layout.dialog_internet, null);
        final AlertDialog deleteDialog = new AlertDialog.Builder(this).create();
        deleteDialog.setView(deleteDialogView);
        deleteDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        deleteDialogView.findViewById(R.id.btnDialogInternetRetry).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isInternetConnected()){
                    deleteDialog.dismiss();
                    SaveFutureProject();
                }else {
                    showDialogInternetConnection();
                }
            }
        });
        deleteDialogView.findViewById(R.id.btnDialogInternetClose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteDialog.dismiss();
                finish();
            }
        });

        deleteDialog.show();

    }


    //Checking Internet Connection
    public boolean isInternetConnected(){
        ConnectivityManager connectivityManager;
        connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected()) {
            return true;
        }
        return false;
    }
}
