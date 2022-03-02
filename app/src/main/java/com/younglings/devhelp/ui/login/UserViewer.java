package com.younglings.devhelp.ui.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.ValueEventListener;
import com.google.android.gms.tasks.OnCompleteListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.younglings.devhelp.R;
import com.younglings.devhelp.databinding.UserProfileBinding;

import java.util.ArrayList;
import java.util.Arrays;

public class UserViewer extends AppCompatActivity {
    UserProfileBinding binding;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;
    public DatabaseReference mDatabase;
    private static final String TAG = "Profile Page";
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        binding = UserProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        SharedPreferences prefs = getSharedPreferences("unique_name", MODE_PRIVATE);
        String username = prefs.getString("username","");
        myRef = database.getReference("Users/"+username);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        TextView displayName = findViewById(R.id.editTextTextPersonName2);
        displayName.setText(username);
        StringBuffer str= new StringBuffer(username);
        //str.append(' ');
        showMessage(str.toString());
        Log.e(TAG, "onComplete: " + str.toString());
        //String username2= '"'+username+'"';
        if (str.toString().equals(null) || str.toString().equals("") || str.toString().equals(" ")){
            Intent intent = new Intent(UserViewer.this, Explore.class);
            startActivity(intent);
        }
        else{
            mDatabase.child("Users").child(str.toString()).child("Email").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (!task.isSuccessful()) {
                        Log.e("firebase", "Error getting data", task.getException());
                        setContentView(R.layout.activity_login);
                    }
                    else {
                        if(task.getResult().getValue() == null){
                            Log.e("firebase", "Error getting data", task.getException());
                            showMessage("User account could not load Email");
                            Intent intent = new Intent(UserViewer.this, Explore.class);
                            startActivity(intent);
                        }else {
                            Log.d("firebase", task.getResult().getValue().toString());
                            String fire_email = task.getResult().getValue().toString();
                            TextView displayEmail = findViewById(R.id.editTextTextPersonName3);
                            displayEmail.setText(fire_email);
                            Log.e(TAG, "onComplete: " + fire_email);
                        }
                    }
                }
            });
            mDatabase.child("Users").child(str.toString()).child("Skills").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (!task.isSuccessful()) {
                        Log.e("firebase", "Error getting data Skills", task.getException());
                        setContentView(R.layout.activity_login);
                    }
                    else {
                        if(task.getResult().getValue() == null){
                            Log.e("firebase", "Error getting data", task.getException());
                            showMessage("User account could not load");
                            Intent intent = new Intent(UserViewer.this, Explore.class);
                            startActivity(intent);
                        }else {
                            Log.d("firebase", task.getResult().getValue().toString());
                            String fire_skills = task.getResult().getValue().toString();
                            String[] skillsArray = fire_skills.split("\\s*,\\s*");
                            ArrayList<String> skillsList = new ArrayList<String>(Arrays.asList(skillsArray));
                            TableLayout table = (TableLayout)findViewById(R.id.tableLayout);
                            for(int i=0;i<skillsList.size();i++)
                            {
                                TableRow row=new TableRow(UserViewer.this);
                                String phone = skillsList.get(i);
                                TextView tv1=new TextView(UserViewer.this);
                                tv1.setText(phone);
                                row.addView(tv1);
                                table.addView(row);
                            }
                        }
                    }
                }
            });
            mDatabase.child("Users").child(str.toString()).child("Specialization").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (!task.isSuccessful()) {
                        Log.e("firebase", "Error getting data", task.getException());
                        setContentView(R.layout.activity_login);
                    }
                    else {
                        if(task.getResult().getValue() == null){
                            Log.e("firebase", "Error getting data", task.getException());
                            showMessage("User account could not load Specialization");
                            Intent intent = new Intent(UserViewer.this, Explore.class);
                            startActivity(intent);
                        }else {
                            Log.d("firebase", task.getResult().getValue().toString());
                            String fire_position = task.getResult().getValue().toString();
                            TextView displayPosition = findViewById(R.id.editTextTextPersonName4);
                            displayPosition.setText(fire_position);
                            Log.e(TAG, "onComplete: " + fire_position);
                        }
                    }
                }
            });
        }
        binding.gridLayout.setClickable(true);
        binding.gridLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop_up_menu();
            }
        });
        binding.github.setClickable(true);
        binding.github.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://github.com/Arthur-Butler");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        binding.linkedin.setClickable(true);
        binding.linkedin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://www.linkedin.com/in/arthur-butler-7721591a4");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        binding.personal.setClickable(true);
        binding.personal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("http://www.arthurnyanisobutler.com/");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
    }
    private void showMessage(String text) {
        Toast.makeText(getApplicationContext(),text, Toast.LENGTH_LONG).show();
    }
    public void pop_up_menu(){
        startActivity(new Intent(UserViewer.this,Pop.class));
    }
}
