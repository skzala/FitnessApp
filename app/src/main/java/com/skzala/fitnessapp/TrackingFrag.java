package com.skzala.fitnessapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TrackingFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TrackingFrag extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Context mContext;
    String user_id,user_name;
    Button btnUpdate;
    EditText edtheight,edtweight,edtchest,edtbiceps,edtwaist,edthips,edtthigh;
    TextView txtDate,txtName;
    SharedPreferences sharedPreferences;

    public TrackingFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TrackingFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static TrackingFrag newInstance(String param1, String param2) {
        TrackingFrag fragment = new TrackingFrag();
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
        sharedPreferences = requireActivity().getSharedPreferences("MyPref", Context.MODE_PRIVATE);

       user_id = sharedPreferences.getString("user_id", "default value");
       user_name = sharedPreferences.getString("user_name", "default value");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tracking, container, false);
        mContext = getContext();

        txtName = view.findViewById(R.id.txtTrackName);
        txtDate = view.findViewById(R.id.txtTrackDate);

        edtheight = view.findViewById(R.id.edtTrackHeight);
        edtweight = view.findViewById(R.id.edtTrackWeight);
        edtchest = view.findViewById(R.id.edtTrackChest);
        edtbiceps = view.findViewById(R.id.edtTrackBiceps);
        edtwaist = view.findViewById(R.id.edtTrackWaist);
        edthips = view.findViewById(R.id.edtTrackHips);
        edtthigh = view.findViewById(R.id.edtTrackThigh);

        btnUpdate = view.findViewById(R.id.btnUpdate);

        getTracking();

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              setTracking();
            }
        });

        return view;
    }

    private void getTracking() {

        String URL = "https://spick-bells.000webhostapp.com/fetchTracking.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                  //  Toast.makeText(mContext, response, Toast.LENGTH_SHORT).show();
                    JSONArray array = new JSONArray(response);

                    JSONObject jsonObject = array.getJSONObject(0);

                   // Toast.makeText(mContext, "sdf"+date, Toast.LENGTH_SHORT).show();
                    txtName.setText(user_name);
                    txtDate.setText(jsonObject.getString("date"));
                    edtheight.setText(jsonObject.getString("height"));
                    edtweight.setText(jsonObject.getString("weight"));
                    edtchest.setText(jsonObject.getString("chest"));
                    edtbiceps.setText(jsonObject.getString("biceps"));
                    edtwaist.setText(jsonObject.getString("waist"));
                    edthips.setText(jsonObject.getString("thigh"));
                    edtthigh.setText(jsonObject.getString("hips"));
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
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(2000, 3, 1));
        Volley.newRequestQueue(mContext).add(stringRequest);
    }
    private void setTracking() {

        String URL = "https://spick-bells.000webhostapp.com/updateTracking.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Toast.makeText(mContext, response, Toast.LENGTH_SHORT).show();
                //getTracking();
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
        Volley.newRequestQueue(mContext).add(stringRequest);
    }

}