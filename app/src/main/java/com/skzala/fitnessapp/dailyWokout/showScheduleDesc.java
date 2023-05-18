package com.skzala.fitnessapp.dailyWokout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
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
import com.skzala.fitnessapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class showScheduleDesc extends AppCompatActivity {
    private RecyclerView recyclerView;
    private dailyWorkoutAdepter dailyAdepter;
    private List<dailyWorkoutModel> dailyList;
    String wName,wTyp,day,name;
    TextView txtName,txtTyp,txtDay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_schedule_desc);
        //dailyWorkoutAdepter adapter = new dailyWorkoutAdepter(getActivity());


        Intent intent = getIntent();
        name = intent.getStringExtra("name");


        // Initialize RecyclerView and adapter
        recyclerView = findViewById(R.id.rwDailyWorkout);
        dailyList = new ArrayList<>();
        dailyWorkout myFragment = new dailyWorkout();
        dailyAdepter = new dailyWorkoutAdepter(dailyList);

        txtName = findViewById(R.id.txtName);
        txtTyp = findViewById(R.id.txtTyp);
        txtDay = findViewById(R.id.txtDay);

        // Set the adapter to the RecyclerView
        recyclerView.setAdapter(dailyAdepter);
        recyclerView.setLayoutManager(new LinearLayoutManager(showScheduleDesc.this));
        // Add some data to the list
        //dailyList.add(new dailyWorkoutModel(1, "Excercise 1 :-", "exercise1"));

        //dailyAdepter.notifyDataSetChanged();
        getWorkout();
    }
    private void getWorkout() {


        String URL = "https://spick-bells.000webhostapp.com/fetchWorkoutByName.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
               // Toast.makeText(showScheduleDesc.this, response, Toast.LENGTH_SHORT).show();
                try {

                    JSONArray array = new JSONArray(response);

                    JSONObject jsonObject = array.getJSONObject(0);

                    wName=jsonObject.getString("workout_name");
                    wTyp=jsonObject.getString("training_type");
                    day=jsonObject.getString("day");
                    for(int i = 1 ;i<=10;i++){

                        String ex = "exercise"+i;
                        if(!jsonObject.getString(ex).equalsIgnoreCase("null")||!jsonObject.getString(ex).equalsIgnoreCase("")){
                            dailyList.add(new dailyWorkoutModel(i, "Excercise "+i+" :-", jsonObject.getString(ex)));
                        }

                    }
                    dailyAdepter.notifyDataSetChanged();


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                txtName.setText(wName);
                txtTyp.setText(wTyp);
                txtDay.setText(day);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(showScheduleDesc.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
//                params.put("username", username);
                params.put("workoutName", name);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(2000, 3, 1));
        Volley.newRequestQueue(showScheduleDesc.this).add(stringRequest);
    }
}