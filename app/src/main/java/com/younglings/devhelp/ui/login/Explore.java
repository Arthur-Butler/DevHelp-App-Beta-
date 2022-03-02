package com.younglings.devhelp.ui.login;

import android.content.SharedPreferences;
import android.os.Bundle;
import java.util.Hashtable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import java.util.ArrayList;

import android.view.View;
import android.content.Intent;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.ValueEventListener;
import android.widget.AdapterView;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import com.younglings.devhelp.databinding.DevhelpExplorePageBinding;
import com.younglings.devhelp.R;


public class Explore extends AppCompatActivity {
    private TextView databaseView;
    private static final String TAG = "Explore";
    public String valueHolder;
    public String[] name = {};
    public String[] positions = {};
    public int get_count;
    DevhelpExplorePageBinding binding;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Jobs");
    ArrayList<String> nameList = new ArrayList<>();
    ArrayList<String> posList= new ArrayList<>();
    ListView listView;
    TextView userName;
    public DatabaseReference mDatabase;
    private View mControlsView;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        //mControlsView = binding.jobList.getEmptyView();
        binding = DevhelpExplorePageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        userName = (TextView) findViewById(R.id.profilename);
        SharedPreferences prefs = getSharedPreferences("unique_name", MODE_PRIVATE);
        String username = prefs.getString("username","");
        userName.setText(username);
        //databaseView = findViewById(R.id.content);
        listView = (ListView) findViewById(R.id.jobList);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                long long_count = dataSnapshot.getChildrenCount();
                Log.d("firebase", String.valueOf(long_count));
                get_count = (int) (long) long_count;
                for (int i = 1; i < get_count; i++) {
                    String urlComp = "Jobs/" + i;
                    DatabaseReference myReference = database.getReference(urlComp);
                    basicRead(myReference);
                    /*String urlPos = "Jobs/" + i + "/Position";
                    myReference = database.getReference(urlPos);
                    basicRead(myReference);*/
                }
                Log.d("firebase", String.valueOf(get_count));
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
        binding.button.setClickable(true);
        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addJob();
            }
        });
    }
    public void basicRead(DatabaseReference myRefer){
        myRefer.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName){
                    /*Hashtable<Integer, String> my_dict = new Hashtable<Integer, String>();
                    for (int i = 1; i < 3; i++){
                        my_dict.put(i, s);
                    }*/
                    String text = snapshot.getValue(String.class);
                    String[] animalsArray = text.split("\\s*,\\s*");
                    nameList = new ArrayList<String>(Arrays.asList(name));
                    nameList.add(animalsArray[0]);
                    name = nameList.toArray(name);
                    posList = new ArrayList<String>(Arrays.asList(positions));
                    posList.add(animalsArray[1]);
                    positions = posList.toArray(positions);;
                    ArrayList<Job> jobArrayList = new ArrayList<>();

                    for(int i = 0;i< name.length;i++){

                        Job job = new Job("Company Name: "+name[i], "Dev Needed: "+positions[i]+" | "+animalsArray[2]);
                        jobArrayList.add(job);

                    }
                    ListAdapter listAdapter = new ListAdapter(Explore.this,jobArrayList);

                    binding.jobList.setAdapter(listAdapter);
                    binding.jobList.setClickable(true);
                    binding.jobList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            Intent i = new Intent(Explore.this,JobViewer.class);
                            i.putExtra("name",name[position]);
                            i.putExtra("positions",positions[position]);
                            startActivity(i);

                        }
                    });
                    /*String plate = snapshot.getValue(String.class);
                    String email = snapshot.child("Position").getValue(String.class);*/

                    /*ListViewExplore customBaseAdapter = new ListViewExplore(getApplicationContext(), name, positions);
                    listView.setAdapter(customBaseAdapter);*/
                    Log.d("firebase", animalsArray[0] + " " + animalsArray[1]);
                    //etc
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for (DataSnapshot ds : snapshot.getChildren()) {
                    String plate = ds.child("Company").getValue(String.class).toString();
                    String email = ds.child("Position").getValue(String.class).toString();
                    nameList = new ArrayList<String>(Arrays.asList(name));
                    nameList.add(plate);
                    name = nameList.toArray(name);
                    posList = new ArrayList<String>(Arrays.asList(positions));
                    posList.add(email);
                    positions = posList.toArray(positions);
                    ListViewExplore customBaseAdapter = new ListViewExplore(getApplicationContext(), name, positions);
                    listView.setAdapter(customBaseAdapter);
                    Log.d("firebase", plate + " " + email);
                    //etc
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    String plate = ds.child("Company").getValue(String.class).toString();
                    String email = ds.child("Position").getValue(String.class).toString();
                    nameList = new ArrayList<String>(Arrays.asList(name));
                    nameList.add(plate);
                    name = nameList.toArray(name);
                    posList = new ArrayList<String>(Arrays.asList(positions));
                    posList.add(email);
                    positions = posList.toArray(positions);
                    ListViewExplore customBaseAdapter = new ListViewExplore(getApplicationContext(), name, positions);
                    listView.setAdapter(customBaseAdapter);
                    Log.d("firebase", plate + " " + email);
                    //etc
                }
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    String plate = ds.child("Company").getValue(String.class);
                    String email = ds.child("Position").getValue(String.class);
                    nameList = new ArrayList<String>(Arrays.asList(name));
                    nameList.add(plate);
                    name = nameList.toArray(name);
                    posList = new ArrayList<String>(Arrays.asList(positions));
                    posList.add(email);
                    positions = posList.toArray(positions);
                    ListViewExplore customBaseAdapter = new ListViewExplore(getApplicationContext(), name, positions);
                    listView.setAdapter(customBaseAdapter);
                    Log.d("firebase", plate + " " + email);
                    //etc
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    public void viewProfile(View view){
        //ImageView profileImg = findViewById(R.id.profileimage2);
        Intent intent = new Intent(getApplicationContext(), UserViewer.class);
        startActivity(intent);
    }

    public void addJob(){
        // Code here executes on main thread after user presses button
        Intent intent = new Intent(Explore.this, JobAdder.class);
        startActivity(intent);
    };

    public void writeNewJob() {
        TextView companyName = findViewById(R.id.CompanyName);
        TextView description = findViewById(R.id.Description);
        Spinner spinnerDev = findViewById(R.id.spinnerDevAdd);
        Spinner spinnerLang = findViewById(R.id.spinnerMainLang);
        String compVar= companyName.getText().toString();
        String descriptVar= description.getText().toString();
        String devVar= spinnerDev.getSelectedItem().toString();
        String langVar= spinnerLang.getSelectedItem().toString();
        mDatabase.child("Jobs").child(String.valueOf(get_count+1)).child("Company").setValue(compVar);
        mDatabase.child("Jobs").child(String.valueOf(get_count+1)).child("Description").setValue(descriptVar);
        mDatabase.child("Jobs").child(String.valueOf(get_count+1)).child("SpecializationNeeded").setValue(devVar);
        mDatabase.child("Jobs").child(String.valueOf(get_count+1)).child("NeededSkills").setValue(langVar);
    }
    public void getJobCount(){
    }

}
