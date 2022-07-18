package edu.neu.firebasechatapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class MessageActivity extends AppCompatActivity {

    private FirebaseUser firebaseUser;
    private DatabaseReference reference;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
    }

    /**.
     * Open new activity/page - Gallery
     *
     * @param view  the view object
     */
    public void galleryActivity(View view) {
        // Set variables
        int thisId = view.getId();

        // If id for button is the same then go to next page/activity
        if (thisId == R.id.galleryButton) {
            // Set variables
            firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
            intent = getIntent();
            final String userid = intent.getStringExtra("userid");

            // Set intent and start new activity
            Intent intent = new Intent(this, GalleryActivity.class);
            intent.putExtra("userid", userid);
            startActivity(intent);
        }
    }
}