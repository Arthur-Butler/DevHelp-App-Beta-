package com.younglings.devhelp.ui.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.younglings.devhelp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText userMail,userPassword;
    private Button btnLogin;
    private ProgressBar loginProgress;
    private DatabaseReference mDatabase;
    private Intent HomeActivity;
    private TextView logToRegBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        logToRegBtn = findViewById(R.id.signUp);
        logToRegBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,EmailAndPasswordRegisterActivity.class);
                startActivity(intent);
            }
        });


        userMail = findViewById(R.id.username);
        userPassword = findViewById(R.id.password);
        btnLogin = findViewById(R.id.login);
        loginProgress = findViewById(R.id.loading);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        HomeActivity = new Intent(this, MainActivity.class);



        loginProgress.setVisibility(View.INVISIBLE);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginProgress.setVisibility(View.VISIBLE);
                btnLogin.setVisibility(View.INVISIBLE);

                final String mail = userMail.getText().toString();
                final String password = userPassword.getText().toString();

                if (mail.isEmpty() || password.isEmpty()) {
                    showMessage("Please Verify All Field");
                    btnLogin.setVisibility(View.VISIBLE);
                    loginProgress.setVisibility(View.INVISIBLE);
                }
                else
                {
                    signIn(mail,password);
                }




            }
        });
    }

    private void signIn(String mail, String password) {

        mDatabase.child("Users").child(mail).child(password).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                    showMessage("Password or Username incorrect");
                }
                else {
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
                    Intent intent = new Intent(getApplicationContext(), Explore.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void updateUI() {
        startActivity(HomeActivity);
        finish();

    }

    private void showMessage(String text) {
        Toast.makeText(getApplicationContext(),text, Toast.LENGTH_LONG).show();
    }


}