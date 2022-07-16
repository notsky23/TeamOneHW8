package edu.neu.firebasechatapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class NewActivity extends AppCompatActivity {
    private ImageView fullImageView;
    private TextView fullTextView;
    private Button download, send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        fullImageView = findViewById(R.id.fullImageView);
        send = findViewById(R.id.send);

        Glide.with(this).load(getIntent().getStringExtra("stickerID@#"))
                .into(fullImageView);

        fullTextView = findViewById(R.id.fullTextView);
        fullTextView.setText(getIntent().getStringExtra("name@#"));
    }
}