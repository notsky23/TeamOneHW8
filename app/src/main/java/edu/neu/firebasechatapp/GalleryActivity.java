package edu.neu.firebasechatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import edu.neu.firebasechatapp.Adapter.StickerAdapter;
import edu.neu.firebasechatapp.Model.StickerModel;

public class GalleryActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<StickerModel> stickerModelArrayList;
    private StickerAdapter stickerAdapter;
    private FirebaseUser firebaseUser;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        recyclerView = findViewById(R.id.recyclerViewG);
        recyclerView.setLayoutManager(new GridLayoutManager(GalleryActivity.this, 1));
        recyclerView.setHasFixedSize(true);

        stickerModelArrayList = new ArrayList<>();

        clearAll();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("images");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                clearAll();

                // Set variables
                firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                intent = getIntent();
                final String userid = intent.getStringExtra("userid");

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    StickerModel stickerModel = new StickerModel();
                    stickerModel.setName(snapshot.child("name").getValue().toString());
                    stickerModel.setImageurl(snapshot.child("stickerID").getValue().toString());

                    stickerModelArrayList.add(stickerModel);
                }

                stickerAdapter = new StickerAdapter(getApplicationContext(), stickerModelArrayList, userid);
                recyclerView.setAdapter(stickerAdapter);
                stickerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(GalleryActivity.this, "Error: " + error.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void clearAll() {

        if (stickerModelArrayList != null) {
            stickerModelArrayList.clear();

            if(stickerAdapter != null) {
                stickerAdapter.notifyDataSetChanged();
            }
        }

        stickerModelArrayList = new ArrayList<>();
    }
}