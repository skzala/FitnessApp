package com.skzala.fitnessapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.JsonReader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;

import java.io.InputStream;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link homefrags#newInstance} factory method to
 * create an instance of this fragment.
 */
public class homefrags extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    // Declare your CardView objects
    CardView crdWorkout, crdPtracking, crdCounter;

    public homefrags() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment homefrags.
     */
    // TODO: Rename and change types and number of parameters
    public static homefrags newInstance(String param1, String param2) {
        homefrags fragment = new homefrags();
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

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_homefrags, container, false);

        // Get the FragmentManager from the parent activity
        AppCompatActivity activity = (AppCompatActivity) requireActivity();
        FragmentManager fragmentManager = activity.getSupportFragmentManager();

        // Find your CardView objects by their IDs
        crdWorkout = view.findViewById(R.id.crdWorkouts);
        crdPtracking = view.findViewById(R.id.crdTracking);
        crdCounter = view.findViewById(R.id.crdCounter);

        // Set an onClickListener for each CardView object
        crdWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create a new instance of your exercise fragment
                exercisefrags exerciseFragment = new exercisefrags();

                // Begin a new fragment transaction and replace the current fragment with your exercise fragment
                FragmentTransaction fragmentTransaction3 = fragmentManager.beginTransaction();
                fragmentTransaction3.replace(R.id.content, exerciseFragment, "");
                fragmentTransaction3.commit();
            }
        });

        crdPtracking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create a new instance of your exercise fragment
                TrackingFrag trackingFrag = new TrackingFrag();

                // Begin a new fragment transaction and replace the current fragment with your exercise fragment
                FragmentTransaction fragmentTransaction3 = fragmentManager.beginTransaction();
                fragmentTransaction3.replace(R.id.content, trackingFrag, "");
                fragmentTransaction3.commit();
            }
        });
        crdCounter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create a new instance of your exercise fragment
                counterFrag counterfrag = new counterFrag();

                // Begin a new fragment transaction and replace the current fragment with your exercise fragment
                FragmentTransaction fragmentTransaction3 = fragmentManager.beginTransaction();
                fragmentTransaction3.replace(R.id.content, counterfrag, "");
                fragmentTransaction3.commit();
            }
        });
        return view;
    }

}
