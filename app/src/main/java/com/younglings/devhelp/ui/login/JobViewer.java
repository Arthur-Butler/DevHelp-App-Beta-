package com.younglings.devhelp.ui.login;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.younglings.devhelp.databinding.DevhelpExplorePageBinding;
import com.younglings.devhelp.databinding.JobViewerBinding;

public class JobViewer extends AppCompatActivity {

    JobViewerBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = JobViewerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = this.getIntent();

        if (intent != null){

            String name = intent.getStringExtra("name");
            String description = intent.getStringExtra("description");

            binding.textJob.setText(name);
        }

    }
}
