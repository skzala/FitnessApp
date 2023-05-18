package com.skzala.fitnessapp.dailyWokout;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link dailyWorkout#newInstance} factory method to
 * create an instance of this fragment.
 */
public class dailyWorkout extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView recyclerView;
    private dailyWorkoutAdepter dailyAdepter;
    private List<dailyWorkoutModel> dailyList;
    private Context mContext;
    String wName,wTyp,day,user_id;
    TextView txtName,txtTyp,txtDay;
    SharedPreferences sharedPreferences;
    public dailyWorkout() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment dailyWorkout.
     */
    // TODO: Rename and change types and number of parameters
    public static dailyWorkout newInstance(String param1, String param2) {
        dailyWorkout fragment = new dailyWorkout();
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
        View view = inflater.inflate(R.layout.fragment_daily_workout, container, false);
        mContext = getContext();
        user_id = sharedPreferences.getString("user_id", "0");
        //dailyWorkoutAdepter adapter = new dailyWorkoutAdepter(getActivity());

        // Initialize RecyclerView and adapter
        recyclerView = view.findViewById(R.id.rwDailyWorkout);
        dailyList = new ArrayList<>();
        dailyWorkout myFragment = new dailyWorkout();
        dailyAdepter = new dailyWorkoutAdepter(dailyList);

        txtName = view.findViewById(R.id.txtName);
        txtTyp = view.findViewById(R.id.txtTyp);
        txtDay = view.findViewById(R.id.txtDay);

        // Set the adapter to the RecyclerView
        recyclerView.setAdapter(dailyAdepter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        // Add some data to the list

        getWorkout();
        // Inflate the layout for this fragment
        return view;

    }

    private void getWorkout() {


        String URL = "https://spick-bells.000webhostapp.com/dailyWorkouts.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONArray array = new JSONArray(response);

                    JSONObject jsonObject = array.getJSONObject(0);

                    wName=jsonObject.getString("workout_name");
                    wTyp=jsonObject.getString("training_type");
                    day=jsonObject.getString("day");

                    for(int i = 1 ;i<=10;i++){

                        String ex = "exercise"+i;
                        if(!jsonObject.getString(ex).equalsIgnoreCase("null")||!jsonObject.getString(ex).equalsIgnoreCase("un")){
                            dailyList.add(new dailyWorkoutModel(i, "Excercise "+i+" :-", jsonObject.getString(ex)));
                        }

                    }

//                    dailyList.add(new dailyWorkoutModel(1, "Excercise 1 :-", jsonObject.getString("exercise1")));
//                    dailyList.add(new dailyWorkoutModel(2, "Excercise 2 :-", jsonObject.getString("exercise2")));
//                    dailyList.add(new dailyWorkoutModel(3, "Excercise 3 :-", jsonObject.getString("exercise3")));
//                    dailyList.add(new dailyWorkoutModel(4, "Excercise 4 :-", jsonObject.getString("exercise4")));
//                    dailyList.add(new dailyWorkoutModel(5, "Excercise 5 :-", jsonObject.getString("exercise5")));
//                    dailyList.add(new dailyWorkoutModel(6, "Excercise 6 :-", jsonObject.getString("exercise6")));
//                    dailyList.add(new dailyWorkoutModel(6, "Excercise 7 :-", jsonObject.getString("exercise7")));
//                    dailyList.add(new dailyWorkoutModel(6, "Excercise 8 :-", jsonObject.getString("exercise8")));
//                    dailyList.add(new dailyWorkoutModel(6, "Excercise 9 :-", jsonObject.getString("exercise9")));
//                    dailyList.add(new dailyWorkoutModel(6, "Excercise 10 :-", jsonObject.getString("exercise10")));
                    // Notify the adapter that the data has changed
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