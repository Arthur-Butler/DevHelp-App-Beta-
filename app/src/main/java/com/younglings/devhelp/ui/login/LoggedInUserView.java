package com.younglings.devhelp.ui.login;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.younglings.devhelp.R;
/**
 * Class exposing authenticated user details to the UI.
 */
class LoggedInUserView extends AppCompatActivity {

    private String displayName;
    public void onCreate(Bundle savedInstanceBundle){
        super.onCreate(savedInstanceBundle);

    }

    //... other data fields that may be accessible to the UI
     public void pop_up_menu(){
         startActivity(new Intent(LoggedInUserView.this,Pop.class));
     }
}