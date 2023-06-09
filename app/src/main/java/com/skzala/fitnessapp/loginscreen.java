package com.skzala.fitnessapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.skzala.fitnessapp.dailyWokout.dailyWorkoutModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class loginscreen extends AppCompatActivity {

    String username,password;
    EditText edtusername,edtpassword;
    Button btnLogin,btnRegister;
    TextView txtHint;
    ProgressDialog progressDialog;
    SharedPreferences sharedPreferences;
    CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginscreen);

        progressDialog = new ProgressDialog(loginscreen.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);

        sharedPreferences = getSharedPreferences("MyPref", Context.MODE_PRIVATE);

        checkBox = findViewById(R.id.checkBox);

        String check = sharedPreferences.getString("loggedin", "default value");

        if(check.equals("1")){
            startActivity(new Intent(loginscreen.this,MainActivity.class));
            finish();
        }



        try {
            VideoView videoView = findViewById(R.id.videoView);
            String path = "android.resource://" + getPackageName() + "/" + R.raw.loginbg;
            videoView.setVideoURI(Uri.parse(path));
            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.setLooping(true);
                    mp.setVolume(0, 0);
                    mp.start();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        edtusername = findViewById(R.id.editTextUsername);
        edtpassword = findViewById(R.id.editTextPassword);
        btnLogin = findViewById(R.id.buttonLogin);
        btnRegister = findViewById(R.id.btnRegister);
        txtHint = findViewById(R.id.txtHint);

        txtHint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String url = "https://www.skzala.com";

// Create an intent with the ACTION_VIEW action and the URL as data
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);


            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(edtusername.getText().toString().trim().length() == 0){
                    Toast.makeText(loginscreen.this, "Enter Username", Toast.LENGTH_SHORT).show();
                } else if (edtpassword.getText().toString().trim().length() == 0) {
                    Toast.makeText(loginscreen.this, "Enter Password", Toast.LENGTH_SHORT).show();
                }
                else {
                    logincheck();
                }
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(loginscreen.this,RegisterActivity.class));

            }
        });


    }

    private void logincheck() {
        progressDialog.show();
        username=edtusername.getText().toString();
        password=edtpassword.getText().toString();
        String URL = "https://spick-bells.000webhostapp.com/login_fitness.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    JSONObject jsonObject = new JSONObject(response);
                        // Toast.makeText(mContext, jsonObject.toString(), Toast.LENGTH_SHORT).show();
                       String flag =  jsonObject.getString("success");
                       String id =  jsonObject.getString("id");

                    if(flag.equals("true")){
                       // Toast.makeText(loginscreen.this, id, Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("user_id", id);
                        editor.putString("user_name", username);
                        // Check if the checkbox is checked
                        if (checkBox.isChecked()) {
                            // checkBox.setText("New Text");
                            editor.putString("loggedin", "1");
                        }
                        editor.apply();
                        startActivity(new Intent(loginscreen.this,MainActivity.class));
                        finish();
                    }

                    else{
                        progressDialog.dismiss();
                        Toast.makeText(loginscreen.this, "The user Data Not found", Toast.LENGTH_SHORT).show();
                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(loginscreen.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("password", password);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(2000, 3, 1));
        Volley.newRequestQueue(getBaseContext()).add(stringRequest);
    }
}