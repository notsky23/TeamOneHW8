package edu.neu.firebasechatapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import edu.neu.firebasechatapp.Fragments.AboutUsFragment;
import edu.neu.firebasechatapp.Fragments.ChatFragment;
import edu.neu.firebasechatapp.Fragments.UsersFragment;
import edu.neu.firebasechatapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new ChatFragment());

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

            switch (item.getItemId()) {
                case R.id.chat:
                    replaceFragment(new ChatFragment());
                    break;
                case R.id.users:
                    replaceFragment(new UsersFragment());
                    break;
                case R.id.aboutUs:
                    replaceFragment(new AboutUsFragment());
                    break;
            }

            return true;
        });
    }

    private void replaceFragment(Fragment fragment) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();

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
            // Set intent and start new activity
            Intent intent = new Intent(this, GalleryActivity.class);
            startActivity(intent);
        }
    }
}