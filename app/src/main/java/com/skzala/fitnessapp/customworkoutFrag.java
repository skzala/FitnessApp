package com.skzala.fitnessapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link customworkoutFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class customworkoutFrag extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    EditText edtname,edttyp,edtday,edtex1,edtex2,edtex3,edtex4,edtex5,edtex6,edtex7,edtex8,edtex9,edtex10;
    String user_id,name,typ,day,ex1,ex2,ex3,ex4,ex5,ex6,ex7,ex8,ex9,ex10;
    Button btnSave,btnex1,btnex2,btnex3,btnex4,btnex5,btnex6,btnex7,btnex8,btnex9,btnex10;
    private Context mContext;
    SharedPreferences sharedPreferences;

    public customworkoutFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment customworkoutFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static customworkoutFrag newInstance(String param1, String param2) {
        customworkoutFrag fragment = new customworkoutFrag();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        sharedPreferences = getActivity().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_customworkout, container, false);
        mContext = getContext();
        user_id = sharedPreferences.getString("user_id", "0");

        btnSave = view.findViewById(R.id.btnSave);


        edtname = view.findViewById(R.id.edtName);
        edttyp = view.findViewById(R.id.edtType);
        edtday = view.findViewById(R.id.edtDay);
        edtex1 = view.findViewById(R.id.edtex1);
        edtex2 = view.findViewById(R.id.edtex2);
        edtex3 = view.findViewById(R.id.edtex3);
        edtex4 = view.findViewById(R.id.edtex4);
        edtex5 = view.findViewById(R.id.edtex5);
        edtex6 = view.findViewById(R.id.edtex6);
        edtex7 = view.findViewById(R.id.edtex7);
        edtex8 = view.findViewById(R.id.edtex8);
        edtex9 = view.findViewById(R.id.edtex9);
        edtex10 = view.findViewById(R.id.edtex10);

        btnSave = view.findViewById(R.id.btnSave);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(edtname.getText().toString().isEmpty()){
                    Toast.makeText(mContext, "Enter Name for workout", Toast.LENGTH_SHORT).show();
                } else if (edttyp.getText().toString().isEmpty()) {
                    Toast.makeText(mContext, "Enter Type of workout", Toast.LENGTH_SHORT).show();
                } else if (edtday.getText().toString().isEmpty()) {
                    Toast.makeText(mContext, "Enter Day When you will perform workout", Toast.LENGTH_SHORT).show();
                } else if (edtex1.getText().toString().isEmpty()) {
                    Toast.makeText(mContext, "Enter Atleast One Exercise", Toast.LENGTH_SHORT).show();
                }else {
                    SaveData();
                }
            }
        });
        return view;
    }
    private void SaveData() {


        String URL = "https://spick-bells.000webhostapp.com/addNewPlan.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                    Toast.makeText(mContext, response, Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mContext, error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", user_id);
                params.put("workout_name", edtname.getText().toString());
                params.put("training_type", edttyp.getText().toString());
                params.put("day", edtday.getText().toString());
                params.put("exercise1", edtex1.getText().toString());
                if (!edtex2.getText().toString().isEmpty()) {
                    params.put("exercise2", edtex2.getText().toString());
                }
                if (!edtex3.getText().toString().isEmpty()) {
                    params.put("exercise3", edtex3.getText().toString());
                }
                if (!edtex4.getText().toString().isEmpty()) {
                    params.put("exercise4", edtex4.getText().toString());
                }
                if (!edtex5.getText().toString().isEmpty()) {
                    params.put("exercise5", edtex5.getText().toString());
                }
                if (!edtex6.getText().toString().isEmpty()) {
                    params.put("exercise6", edtex6.getText().toString());
                }
                if (!edtex7.getText().toString().isEmpty()) {
                    params.put("exercise7", edtex7.getText().toString());
                }
                if (!edtex8.getText().toString().isEmpty()) {
                    params.put("exercise8", edtex8.getText().toString());
                }
                if (!edtex9.getText().toString().isEmpty()) {
                    params.put("exercise9", edtex9.getText().toString());
                }
                if (!edtex10.getText().toString().isEmpty()) {
                    params.put("exercise10", edtex10.getText().toString());
                }


//                params.put("password", password);

                //
//    $workout_name = $_POST['workout_name'];
//    $training_type = $_POST['training_type'];
//    $day = $_POST['day'];
//    $exercise1 = $_POST['exercise1'];
//    $exercise2 = $_POST['exercise2'];
//    $exercise3 = $_POST['exercise3'];
//    $exercise4 = $_POST['exercise4'];
//    $exercise5 = $_POST['exercise5'];
//    $exercise6 = $_POST['exercise6'];
//    $exercise7 = $_POST['exercise7'];
//    $exercise8 = $_POST['exercise8'];
//    $exercise9 = $_POST['exercise9'];
//    $exercise10 = $_POST['exercise10'];


                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(2000, 3, 1));
        Volley.newRequestQueue(mContext).add(stringRequest);
    }
}