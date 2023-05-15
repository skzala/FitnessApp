package com.skzala.fitnessapp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link counterFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class counterFrag extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Context mContext;
    Spinner spgender,spactivity;
    String gender,activity;
    float height=0,weight=0,age=0;
    EditText edtHight,edtWeight,edtAge;
    Button btnCal;
    TextView txtDesc,txtCal;
    float bmr,cal;

    public counterFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment counterFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static counterFrag newInstance(String param1, String param2) {
        counterFrag fragment = new counterFrag();
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
        mContext = getContext();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_counter, container, false);
        spgender = view.findViewById(R.id.spGender);
        spactivity = view.findViewById(R.id.spActivity);

        edtAge = view.findViewById(R.id.edtAge);
        edtHight = view.findViewById(R.id.edtHeight);
        edtWeight = view.findViewById(R.id.edtWeight);

        btnCal = view.findViewById(R.id.btnCalculate);
        txtDesc = view.findViewById(R.id.txtDesc);
        txtCal = view.findViewById(R.id.txtCal);

        btnCal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(TextUtils.isEmpty(edtHight.getText().toString())||TextUtils.isEmpty(edtWeight.getText().toString())||TextUtils.isEmpty(edtAge.getText().toString())){
                    Toast.makeText(mContext, "Enter Valid Values", Toast.LENGTH_SHORT).show();
                }else{
                    weight = Float.parseFloat(edtWeight.getText().toString());
                    height = Float.parseFloat(edtHight.getText().toString());
                    age = Float.parseFloat(edtAge.getText().toString());
                    // Replace `R.id.spinner` with your actual Spinner ID
                    // Toast.makeText(mContext, gender, Toast.LENGTH_SHORT).show();
                    if(gender.equalsIgnoreCase("Male")){
                        bmr = 66 + (13.75f * weight) + (5 * height) - (6.75f * age);
                    } else if (gender.equalsIgnoreCase("Female")) {
                        bmr = 655 + (9.56f * weight) + (1.85f * height) - (4.68f * age);
                    } else {
                        Toast.makeText(mContext, "select Gender", Toast.LENGTH_SHORT).show();
                    }
                    //  Toast.makeText(mContext, Float.toString(bmr), Toast.LENGTH_SHORT).show();
                    if(activity.equalsIgnoreCase("Sedentary (little to no exercise)")){
                        cal= (float) (bmr*1.2);
                    } else if (activity.equalsIgnoreCase("Lightly active (light exercise/sports 1-3 days/week)")) {
                        cal= (float) (bmr*1.375);
                    }else if (activity.equalsIgnoreCase("Moderately active (moderate exercise/sports 3-5 days/week)")) {
                        cal= (float) (bmr*1.55);
                    }else if (activity.equalsIgnoreCase("Very active (hard exercise/sports 6-7 days/week)")) {
                        cal= (float) (bmr*1.725);
                    }else if (activity.equalsIgnoreCase("Extra active (very hard exercise/sports and a physical job)")) {
                        cal= (float) (bmr*1.9);
                    }else{
                        Toast.makeText(mContext, "Select Activity Level", Toast.LENGTH_SHORT).show();
                    }
                    DecimalFormat decimalFormat = new DecimalFormat("#.##");
                    cal = Float.parseFloat(decimalFormat.format(cal));

                    float bmi = calculateBMI(weight, height);
                    //Toast.makeText(mContext, Float.toString(bmi), Toast.LENGTH_SHORT).show();
                    txtCal.setText(Float.toString(cal)+"Cals");
                    if(bmi <18.5){
                        txtDesc.setText("\tBased on your BMI calculation, it indicates that your BMI is very low, suggesting that you are underweight. This means that your current weight is below the healthy range for your height. It's important to address this and make appropriate changes to improve your overall health.\n" +
                                "\n" +
                                "\tConsidering your current calories Burned, which is "+Float.toString(cal)+", it is evident that you need to increase your calorie intake to support healthy weight gain. You should aim to consume more calories than you currently do in order to provide your body with the energy it needs to function properly and promote weight gain. It is recommended that you consult with a healthcare professional or a registered dietitian who can provide personalized guidance on the specific number of calories you should consume to achieve a healthy weight.\n" +
                                "\n" +
                                "\tRemember, achieving a healthy weight is not just about consuming more calories but also focusing on a well-balanced diet that includes nutrient-rich foods. Incorporating a variety of food groups, such as lean proteins, whole grains, fruits, vegetables, and healthy fats, can help you achieve your goal in a healthy and sustainable manner.");

                    } else if (bmi > 18.5 && bmi <24.9) {
                        txtDesc.setText("\tBased on your BMI calculation, it indicates that your BMI falls within the average or normal range. This suggests that you have a healthy weight for your height. Maintaining a healthy weight is important for overall well-being and reducing the risk of various health issues.\n" +
                                "\n" +
                                "\tConsidering your current calories Burned Daily, which is "+Float.toString(cal)+" calories per day, it is suitable for maintaining your weight within the normal range. This caloric intake provides your body with the energy it needs to function optimally and supports your overall health.\n" +
                                "\n" +
                                "\tIn terms of calories burned, it varies depending on your activity level and daily physical activities. Engaging in regular physical activity can help you burn additional calories and support weight management. Activities such as brisk walking, jogging, cycling, or participating in sports can contribute to burning calories.\n" +
                                "\n" +
                                "\tTo ensure that you are maintaining a healthy weight, it is recommended to monitor your calorie intake and balance it with your calorie expenditure through physical activity. If you have specific goals, such as losing weight or gaining muscle, it may be beneficial to consult with a healthcare professional or a registered dietitian who can provide personalized guidance and recommendations.\n" +
                                "\n" +
                                "\tRemember, achieving and maintaining a healthy weight is a combination of a balanced diet, regular physical activity, and adopting sustainable lifestyle habits. Listen to your body's needs and make gradual, sustainable changes to support your overall health and well-being.");
                    }else {
                        txtDesc.setText("\tBased on your BMI calculation, it indicates that your BMI falls within the overweight range. This suggests that you are carrying excess weight for your height. To improve your health and well-being, it is important to address this and make positive changes.\n" +
                                "\n" +
                                "\tHaving an overweight BMI can increase the risk of various health issues, including heart disease, high blood pressure, and diabetes. To achieve a healthier weight, it is recommended to focus on creating a calorie deficit by consuming fewer calories than your body needs.\n" +
                                "\n" +
                                "\tBased on your current calories Burned Daily, which is "+Float.toString(cal)+", you may need to reduce your caloric intake to support weight loss. It is advised to consult with a healthcare professional or a registered dietitian to determine an appropriate calorie deficit and develop a personalized meal plan.\n" +
                                "\n" +
                                "\tIn addition to managing your caloric intake, incorporating regular physical activity into your routine is essential for weight loss. Engaging in aerobic exercises, strength training, and other forms of physical activity can help burn calories, increase muscle mass, and improve overall fitness.\n" +
                                "\n" +
                                "\tRemember, achieving a healthier weight takes time and effort. It's important to approach weight loss in a sustainable and realistic manner. With the guidance of a healthcare professional or a registered dietitian, you can create a comprehensive plan that includes appropriate caloric intake and a balanced diet to support your weight loss goals.");
                    }
                }

            }
        });
        return view;

    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        spgender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gender = parent.getItemAtPosition(position).toString();
                // Use the selectedValue as needed
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle the case where no item is selected, if needed
            }
        });
        spactivity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                activity = parent.getItemAtPosition(position).toString();
                // Perform actions based on the selected item
                //Toast.makeText(mContext, activity, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(mContext, "select Something", Toast.LENGTH_SHORT).show();
                // Do something when nothing is selected
            }
        });




    }
    public static float calculateBMI(float weight, float height) {
        float heightInMeters = height / 100; // Convert height from centimeters to meters
        float bmi = weight / (heightInMeters * heightInMeters);
        return bmi;
    }
}