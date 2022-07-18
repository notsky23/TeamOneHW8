package edu.neu.firebasechatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import edu.neu.firebasechatapp.Adapter.StickerAdapter;
import edu.neu.firebasechatapp.Model.ImageModel;

public class GalleryActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<ImageModel> imageModelArrayList;
    private StickerAdapter stickerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(GalleryActivity.this, 1));
        recyclerView.setHasFixedSize(true);

        imageModelArrayList = new ArrayList<>();

        clearAll();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("images");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                clearAll();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ImageModel imageModel = new ImageModel();
                    imageModel.setName(snapshot.child("name").getValue().toString());
                    imageModel.setImageurl(snapshot.child("stickerID").getValue().toString());

                    imageModelArrayList.add(imageModel);
                }

                stickerAdapter = new StickerAdapter(getApplicationContext(), imageModelArrayList);
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

        if (imageModelArrayList != null) {
            imageModelArrayList.clear();

            if(stickerAdapter != null) {
                stickerAdapter.notifyDataSetChanged();
            }
        }

        imageModelArrayList = new ArrayList<>();
    }
}