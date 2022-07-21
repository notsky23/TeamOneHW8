package edu.neu.firebasechatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import edu.neu.firebasechatapp.Fragments.APIService;
import edu.neu.firebasechatapp.Model.UserModel;
import edu.neu.firebasechatapp.Notifications.Client;
import edu.neu.firebasechatapp.Notifications.Data;
import edu.neu.firebasechatapp.Notifications.MyResponse;
import edu.neu.firebasechatapp.Notifications.Sender;
import edu.neu.firebasechatapp.Notifications.Token;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewActivity extends AppCompatActivity {
    private Context context;
    private ImageView fullImageView;
    private TextView fullTextView;
    private Button send;

    private FirebaseUser firebaseUser;
    private Intent intent;

    private String userid;
    private APIService apiService;
    boolean notify = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        // Set variables
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        intent = getIntent();
        userid = intent.getStringExtra("userid");
        apiService = Client.getClient("https://fcm.googleapis.com/").create(APIService.class);

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
                        getIntent().getStringExtra("name@#"), view);
            }
        });
    }

    public void sendSticker(String sender, String receiver, String stickerId, String stickerName, View view) {
        // notify
        notify = true;

        // Add values to database
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender", sender);
        hashMap.put("receiver", receiver);
        hashMap.put("stickerId", stickerId);
        hashMap.put("stickerName", stickerName);

        reference.child("chatHistory").push().setValue(hashMap);

        // Set variables
        int thisId = view.getId();

        // If id for button is the same then go to next page/activity
        if (thisId == R.id.sendButton) {
            // Set intent and start new activity
//            intent = new Intent(this, MessageActivity.class);
//            startActivity(intent);

            String user = hashMap.get("receiver").toString();
            Intent intent = new Intent(this, MessageActivity.class);
            intent.putExtra("userid", user);
            startActivity(intent);
        }

//        final String msg = stickerName;

        reference = FirebaseDatabase.getInstance().getReference("users").child(firebaseUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserModel user = dataSnapshot.getValue(UserModel.class);
                if (notify) {
                    sendNotification(receiver, user.getUsername(), stickerName);
                }
                notify = false;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void sendNotification (String receiver, String username, String message) {
        DatabaseReference tokens = FirebaseDatabase.getInstance().getReference("Tokens");
        Query query = tokens.orderByKey().equalTo(receiver);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Token token = snapshot.getValue(Token.class);
                    Data data = new Data(firebaseUser.getUid(), R.mipmap.ic_launcher,
                            username + ": " + message, "New Message", userid);
                    Sender sender = new Sender(data, token.getToken());
                    apiService.sendNotification(sender)
                            .enqueue(new Callback<MyResponse>() {
                                @Override
                                public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                                    if (response.code() == 200) {
                                        if (response.body().success != 1) {
                                            Toast.makeText(NewActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<MyResponse> call, Throwable t) {

                                }
                            });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}