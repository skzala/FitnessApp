package com.skzala.fitnessapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class RegisterData extends AppCompatActivity {
    String user_password,user_name,user_email,date;
    SharedPreferences sharedPreferences;
    Button btnRegister;
    EditText edtheight,edtweight,edtchest,edtbiceps,edtwaist,edthips,edtthigh;
    TextView txtDate,txtName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_data);

        Intent intent = getIntent();
        user_name = intent.getStringExtra("username");
        user_email = intent.getStringExtra("email");
        user_password = intent.getStringExtra("password");

        txtName = findViewById(R.id.txtTrackName);
        txtDate = findViewById(R.id.txtTrackDate);

        edtheight = findViewById(R.id.edtTrackHeight);
        edtweight = findViewById(R.id.edtTrackWeight);
        edtchest = findViewById(R.id.edtTrackChest);
        edtbiceps = findViewById(R.id.edtTrackBiceps);
        edtwaist = findViewById(R.id.edtTrackWaist);
        edthips = findViewById(R.id.edtTrackHips);
        edtthigh = findViewById(R.id.edtTrackThigh);

        btnRegister = findViewById(R.id.btnUpdate);
       // Calendar calendar = Calendar.getInstance();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = dateFormat.format(calendar.getTime());
        date = currentDate.toString();

        txtName.setText(user_name);
        txtDate.setText(date);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(edtbiceps.getText().toString())||TextUtils.isEmpty(edtchest.getText().toString())||TextUtils.isEmpty(edtheight.getText().toString())||TextUtils.isEmpty(edtweight.getText().toString())||TextUtils.isEmpty(edtwaist.getText().toString())||TextUtils.isEmpty(edthips.getText().toString())||TextUtils.isEmpty(edtthigh.getText().toString())){
                    Toast.makeText(RegisterData.this, "Enter Valid Values", Toast.LENGTH_SHORT).show();
                }else{
                    Register();
                }
            }
        });
    }

    private void Register() {
        String URL = "https://spick-bells.000webhostapp.com/registerFitsify.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equalsIgnoreCase("Registration successful!")){
                    Toast.makeText(RegisterData.this, "You are Registered Now You can Login Again", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterData.this,loginscreen.class));
                }else {
                    Toast.makeText(RegisterData.this, response, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RegisterData.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", user_name);
                params.put("password", user_password);
                params.put("email", user_email);
                params.put("weight", edtweight.getText().toString());
                params.put("height", edtheight.getText().toString());
                params.put("chest", edtchest.getText().toString());
                params.put("biceps", edtbiceps.getText().toString());
                params.put("waist", edtwaist.getText().toString());
                params.put("thigh", edtthigh.getText().toString());
                params.put("hips", edthips.getText().toString());

                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(2000, 3, 1));
        Volley.newRequestQueue(RegisterData.this).add(stringRequest);
    }
}