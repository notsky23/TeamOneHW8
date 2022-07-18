package edu.neu.firebasechatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

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
            showMainActivity();
            return;
        }

        Button loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                showMainActivity();
                authenticateUser();
            }
        });
    }

    private void authenticateUser() {
        EditText etLoginUsername = findViewById(R.id.etUsernameL);
        EditText etLoginPassword = findViewById(R.id.etPasswordL);

        String username = etLoginUsername.getText().toString();
        String password = etLoginPassword.getText().toString();

        if (username.isEmpty()) {
            Toast.makeText(this, "Please fill in the username", Toast.LENGTH_LONG).show();
            return;
        }

        if (password.isEmpty()) {
            Toast.makeText(this, "Please fill in the password", Toast.LENGTH_LONG).show();
            return;
        }

        userAuth.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            showMainActivity();
                        }
                        else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginActivity.this, "Authentication failed",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void showMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
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