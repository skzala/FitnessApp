package com.skzala.fitnessapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.skzala.fitnessapp.dailyWokout.dailyWorkout;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link exercisefrags#newInstance} factory method to
 * create an instance of this fragment.
 */
public class exercisefrags extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    CardView crdDailyWorkout, crdSchdules, crdCustomize;


    public exercisefrags() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment exercisefrags.
     */
    // TODO: Rename and change types and number of parameters
    public static exercisefrags newInstance(String param1, String param2) {
        exercisefrags fragment = new exercisefrags();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_exercisefrags, container, false);
        // Get the FragmentManager from the parent activity
        AppCompatActivity activity = (AppCompatActivity) requireActivity();
        FragmentManager fragmentManager = activity.getSupportFragmentManager();

        // Find your CardView objects by their IDs

        crdDailyWorkout = view.findViewById(R.id.crdDailyWorkout);
        crdSchdules = view.findViewById(R.id.crdSchedules);
        crdCustomize = view.findViewById(R.id.crdCustom);
        // Set an onClickListener for each CardView object
        crdDailyWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create a new instance of your exercise fragment
                dailyWorkout dailyWorkout = new dailyWorkout();

                // Begin a new fragment transaction and replace the current fragment with your exercise fragment
                FragmentTransaction fragmentTransaction3 = fragmentManager.beginTransaction();
                fragmentTransaction3.replace(R.id.content, dailyWorkout, "");
                fragmentTransaction3.commit();
            }
        });
        crdSchdules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create a new instance of your exercise fragment
                schedulesfrags schedulesFrags = new schedulesfrags();

                // Begin a new fragment transaction and replace the current fragment with your exercise fragment
                FragmentTransaction fragmentTransaction3 = fragmentManager.beginTransaction();
                fragmentTransaction3.replace(R.id.content, schedulesFrags, "");
                fragmentTransaction3.commit();
            }
        });

        crdCustomize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customworkoutFrag customworkoutfrag = new customworkoutFrag();

                // Begin a new fragment transaction and replace the current fragment with your exercise fragment
                FragmentTransaction fragmentTransaction3 = fragmentManager.beginTransaction();
                fragmentTransaction3.replace(R.id.content, customworkoutfrag, "");
                fragmentTransaction3.commit();
            }
        });
        // Inflate the layout for this fragment
        return view;
    }
}