package edu.neu.firebasechatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import edu.neu.firebasechatapp.Adapter.MessageAdapter;
import edu.neu.firebasechatapp.Adapter.StickerAdapter;
import edu.neu.firebasechatapp.Model.ChatModel;
import edu.neu.firebasechatapp.Model.StickerModel;

public class MessageActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<ChatModel> chatModelArrayList;
    private MessageAdapter messageAdapter;
    private FirebaseUser firebaseUser;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        recyclerView = findViewById(R.id.recyclerViewC);
        recyclerView.setLayoutManager(new GridLayoutManager(MessageActivity.this, 1));
        recyclerView.setHasFixedSize(true);

        // Set variables
//        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
//        intent = getIntent();
//        final String userid = intent.getStringExtra("userid");

        chatModelArrayList = new ArrayList<>();

        clearAll();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("chatHistory");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                clearAll();

                // Set variables
//                firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
//                intent = getIntent();
//                final String chatHistory = intent.getStringExtra("chatHistory");

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ChatModel chatModel = new ChatModel();
                    chatModel.setSender(snapshot.child("sender").getValue().toString());
                    chatModel.setReceiver(snapshot.child("receiver").getValue().toString());
                    chatModel.setStickerName(snapshot.child("stickerName").getValue().toString());
                    chatModel.setStickerId(snapshot.child("stickerId").getValue().toString());

                    chatModelArrayList.add(chatModel);

//                    if (snapshot.child("sender").getValue().toString().equals(firebaseUser.getUid())
//                            && snapshot.child("receiver").getValue().toString().equals(userid)) {
//                        chatModelArrayList.add(chatModel);
//                    }
                }

                messageAdapter = new MessageAdapter(getApplicationContext(), chatModelArrayList);
                recyclerView.setAdapter(messageAdapter);
                messageAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MessageActivity.this, "Error: " + error.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
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

    private void clearAll() {

        if (chatModelArrayList != null) {
            chatModelArrayList.clear();

            if(messageAdapter != null) {
                messageAdapter.notifyDataSetChanged();
            }
        }

        chatModelArrayList = new ArrayList<>();
    }
}