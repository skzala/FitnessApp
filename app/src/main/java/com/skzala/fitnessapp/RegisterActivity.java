package com.skzala.fitnessapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

public class RegisterActivity extends AppCompatActivity {
    String username,password,email;
    EditText edtusername,edtpassword,edtemail;
    Button btnLogin,btnRegister;
    ProgressDialog progressDialog;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        progressDialog = new ProgressDialog(RegisterActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);

        sharedPreferences = getSharedPreferences("MyPref", Context.MODE_PRIVATE);

        try {
            VideoView videoView = findViewById(R.id.videoView);
            String path = "android.resource://" + getPackageName() + "/" + R.raw.loginbg;
            videoView.setVideoURI(Uri.parse(path));
            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.setLooping(true);
                    mp.setVolume(0, 0);
                    mp.start();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        edtusername = findViewById(R.id.editTextUsername);
        edtpassword = findViewById(R.id.editTextPassword);
        edtemail = findViewById(R.id.edtEmail);
        btnLogin = findViewById(R.id.buttonLogin);
        btnRegister = findViewById(R.id.btnRegister);

//        InputFilter emailFilter = new InputFilter() {
//            @Override
//            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
//                String input = dest.subSequence(0, dstart) + source.toString() + dest.subSequence(dend, dest.length());
//                if (end > start) {
//                    // User is still typing, allow any input
//                    return null;
//                } else {
//                    // User has finished typing, validate as email address
//                    if (!Patterns.EMAIL_ADDRESS.matcher(input).matches()) {
//                        return "";
//                    }
//                }
//                return null;
//            };
//        };
//        //edtemail.setFilters(new InputFilter[] { emailFilter });



        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(RegisterActivity.this,loginscreen.class));

            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = edtemail.getText().toString().trim();
                if(TextUtils.isEmpty(edtpassword.getText().toString())||TextUtils.isEmpty(edtemail.getText().toString())||TextUtils.isEmpty(edtusername.getText().toString())){
                    Toast.makeText(RegisterActivity.this, "enter Details", Toast.LENGTH_SHORT).show();
                } else if (!isValidEmail(email)) {
                    Toast.makeText(RegisterActivity.this, "Enter Valid Email", Toast.LENGTH_SHORT).show();
                } else{

                    username = edtusername.getText().toString().trim();
                    email = edtemail.getText().toString().trim();
                    password = edtpassword.getText().toString().trim();

                    Intent intent = new Intent(RegisterActivity.this, RegisterData.class);

                    // Put extra values
                    intent.putExtra("username", username);
                    intent.putExtra("email", email);
                    intent.putExtra("password", password);
                    // Start the new activity
                    startActivity(intent);
                }

            }
        });

    }
    private boolean isValidEmail(CharSequence email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

}