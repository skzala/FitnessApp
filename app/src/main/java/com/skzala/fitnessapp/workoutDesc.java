package com.skzala.fitnessapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class workoutDesc extends AppCompatActivity {
    String name;
    TextView exName,exHits,exWeights,exSets,exReps,exDesc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_desc);

        Intent intent = getIntent();
        name = intent.getStringExtra("name");

        exName = findViewById(R.id.exName);
        exHits = findViewById(R.id.exHits);
        exWeights = findViewById(R.id.exWeight);
        exSets = findViewById(R.id.exSets);
        exReps = findViewById(R.id.exReps);
        exDesc = findViewById(R.id.exDesc);

        getWorkoutdesc();

        if(name.equals("null")){
            exName.setText("Just Rest");
            exDesc.setText("Go Grab a bed Lie down on it and just Chill Out");
        }
      //  Toast.makeText(this, name, Toast.LENGTH_SHORT).show();
    }

    private void getWorkoutdesc() {


        String URL = "https://spick-bells.000webhostapp.com/getWorkoutDetails.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONArray array = new JSONArray(response);

                    JSONObject jsonObject = array.getJSONObject(0);

//                    wName=jsonObject.getString("workout_name");
//                    wTyp=jsonObject.getString("training_type");
//                    day=jsonObject.getString("day");

                    exName.setText(jsonObject.getString("exercise_name"));
                    exHits.setText(jsonObject.getString("bodypart"));
                    exWeights.setText(jsonObject.getString("weight"));
                    exSets.setText(jsonObject.getString("sets"));
                    exReps.setText(jsonObject.getString("reps"));
                    exDesc.setText(jsonObject.getString("description"));



                } catch (JSONException e) {
                    e.printStackTrace();
                }

//                txtName.setText(wName);
//                txtTyp.setText(wTyp);
//                txtDay.setText(day);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(workoutDesc.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("exercise_name", name);

                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(2000, 3, 1));
        Volley.newRequestQueue(workoutDesc.this).add(stringRequest);
    }

}