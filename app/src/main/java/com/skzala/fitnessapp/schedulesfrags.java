package com.skzala.fitnessapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.skzala.fitnessapp.dailyWokout.dailyWorkout;
import com.skzala.fitnessapp.dailyWokout.dailyWorkoutAdepter;
import com.skzala.fitnessapp.dailyWokout.dailyWorkoutModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link schedulesfrags#newInstance} factory method to
 * create an instance of this fragment.
 */
public class schedulesfrags extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Context mContext;
    private RecyclerView recyclerView;
    private dailyWorkoutAdepter dailyAdepter;
    private List<dailyWorkoutModel> dailyList;
    String user_id;
    SharedPreferences sharedPreferences;
    public schedulesfrags() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment schedulesfrags.
     */
    // TODO: Rename and change types and number of parameters
    public static schedulesfrags newInstance(String param1, String param2) {
        schedulesfrags fragment = new schedulesfrags();
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
        View view = inflater.inflate(R.layout.fragment_schedulesfrags, container, false);

        mContext = getContext();


        user_id = sharedPreferences.getString("user_id", "0");
// Initialize RecyclerView and adapter
        recyclerView = view.findViewById(R.id.rwSchedules);
        dailyList = new ArrayList<>();
        dailyAdepter = new dailyWorkoutAdepter(dailyList);

// Set the adapter to the RecyclerView
        recyclerView.setAdapter(dailyAdepter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        getSchedule();

        //dailyAdepter.notifyDataSetChanged();

        return view;

    }
    private void getSchedule() {


        String URL = "https://spick-bells.000webhostapp.com/fetchSchedule.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
             //   Toast.makeText(mContext, "hi", Toast.LENGTH_SHORT).show();
                try {

                    JSONArray array = new JSONArray(response);

                    for (int i = 0; i < array.length(); i++) {
                      //  Toast.makeText(mContext, i, Toast.LENGTH_SHORT).show();
                        JSONObject jsonObject = array.getJSONObject(i);
                   // Toast.makeText(mContext, jsonObject.toString(), Toast.LENGTH_SHORT).show();
                        dailyList.add(new dailyWorkoutModel(0, Integer.toString(i+1), jsonObject.getString("workout_name")));

                    }
                    dailyAdepter.notifyDataSetChanged();


                } catch (JSONException e) {
                    e.printStackTrace();
                }

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
//                params.put("password", password);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(2000, 3, 1));
        Volley.newRequestQueue(mContext).add(stringRequest);
    }
}