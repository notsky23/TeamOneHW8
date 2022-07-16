package edu.neu.firebasechatapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**.
     * Open new activity/page - About Me
     *
     * @param view  the view object
     */
    public void aboutMeActivity(View view) {
        // Set variables
        int thisId = view.getId();

        // If id for button is the same then go to next page/activity
        if (thisId == R.id.aboutMeButton) {
            // Set intent and start new activity
            Intent intent = new Intent(this, AboutMeActivity.class);
            startActivity(intent);
        }
    }
}