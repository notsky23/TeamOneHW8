package edu.neu.firebasechatapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class NewActivity extends AppCompatActivity {
    private ImageView fullImageView;
    private TextView fullTextView;
    private Button send;

    private FirebaseUser firebaseUser;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        // Set variables
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        intent = getIntent();
        final String userid = intent.getStringExtra("userid");

        fullImageView = findViewById(R.id.fullImageView);
        send = findViewById(R.id.sendButton);

        Glide.with(this).load(getIntent().getStringExtra("stickerID@#"))
                .into(fullImageView);

        fullTextView = findViewById(R.id.fullTextView);
        fullTextView.setText(getIntent().getStringExtra("name@#"));

        Button sendButton = findViewById(R.id.sendButton);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendSticker(firebaseUser.getUid(), userid,
                        getIntent().getStringExtra("stickerID@#"),
                        getIntent().getStringExtra("name@#"));
            }
        });
    }

    public void sendSticker(String sender, String receiver, String stickerId, String stickerName) {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender", sender);
        hashMap.put("receiver", receiver);
        hashMap.put("stickerId", stickerId);
        hashMap.put("stickerName", stickerName);

        reference.child("chatHistory").push().setValue(hashMap);
    }
}