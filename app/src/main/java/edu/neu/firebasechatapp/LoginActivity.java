package edu.neu.firebasechatapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth userAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize Firebase Auth
        userAuth = FirebaseAuth.getInstance();
        if (userAuth.getCurrentUser() != null) {
            finish();
            return;
        }
    }

    /**.
     * Open new activity/page - Main
     *
     * @param view  the view object
     */
    public void mainActivity(View view) {
        // Set variables
        int thisId = view.getId();

        // If id for button is the same then go to next page/activity
        if (thisId == R.id.loginButton) {
            // Set intent and start new activity
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    /**.
     * Open new activity/page - Create Account
     *
     * @param view  the view object
     */
    public void createAccountActivity(View view) {
        // Set variables
        int thisId = view.getId();

        // If id for button is the same then go to next page/activity
        if (thisId == R.id.tvCreateAccountSwitch) {
            // Set intent and start new activity
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        }
    }
}