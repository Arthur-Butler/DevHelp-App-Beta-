package com.younglings.devhelp.ui.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.younglings.devhelp.R;

import com.younglings.devhelp.databinding.JobAdderBinding;
import com.younglings.devhelp.databinding.JobViewerBinding;

public class JobAdder extends AppCompatActivity {
    JobAdderBinding binding;
    TextView userName;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        binding = JobAdderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        userName = (TextView) findViewById(R.id.job_add_username);
        SharedPreferences prefs = getSharedPreferences("unique_name", MODE_PRIVATE);
        String username = prefs.getString("username","");
        userName.setText(username);
    }
    public void viewProfile(){
        Intent intent = new Intent(getApplicationContext(), LoggedInUserView.class);
        startActivity(intent);
    }
}
