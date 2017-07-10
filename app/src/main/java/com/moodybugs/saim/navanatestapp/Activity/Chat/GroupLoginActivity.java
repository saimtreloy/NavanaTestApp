package com.moodybugs.saim.navanatestapp.Activity.Chat;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.moodybugs.saim.navanatestapp.R;
import com.moodybugs.saim.navanatestapp.Utility.MySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class GroupLoginActivity extends AppCompatActivity {

    public static Toolbar toolbar;

    ProgressBar progProjectGL;
    Button btnLoginGL;
    EditText inputGLPass, inputGLUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_login);

        init();
    }

    public void init() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("  Group Login");

        progProjectGL = (ProgressBar) findViewById(R.id.progProjectGL);
        btnLoginGL = (Button) findViewById(R.id.btnLoginGL);
        inputGLUser = (EditText) findViewById(R.id.inputGLUser);
        inputGLPass = (EditText) findViewById(R.id.inputGLPass);


        btnLoginGL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inputGLUser.getText().toString().isEmpty() || inputGLPass.getText().toString().isEmpty()) {

                } else {
                    progProjectGL.setVisibility(View.VISIBLE);
                    RetriveLoginInfo(inputGLUser.getText().toString(), inputGLPass.getText().toString());
                }
            }
        });
    }


    private void RetriveLoginInfo(final String uName, final String uPass) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://moodybugs.000webhostapp.com/NewspaperBox/login.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            progProjectGL.setVisibility(View.INVISIBLE);
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                            String code = jsonObject.getString("code");
                            String msg = jsonObject.getString("message");
                            if (code.equals("success")) {
                                Intent intent = new Intent(getApplicationContext(), GroupActivity.class);
                                intent.putExtra("USER_NAME", inputGLUser.getText().toString());
                                inputGLUser.setText("");
                                inputGLPass.setText("");
                                startActivity(intent);
                            } else {
                                showDialogInternetConnection(msg);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }


        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_name", uName);
                params.put("user_pass", uPass);
                return params;
            }
        };
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }


    public void showDialogInternetConnection(String message) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();

    }
}