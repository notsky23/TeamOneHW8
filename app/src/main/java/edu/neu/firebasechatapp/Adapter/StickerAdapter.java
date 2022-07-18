package edu.neu.firebasechatapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

import edu.neu.firebasechatapp.Model.StickerModel;
import edu.neu.firebasechatapp.NewActivity;
import edu.neu.firebasechatapp.R;

public class StickerAdapter extends RecyclerView.Adapter<StickerAdapter.ViewHolder> {
    private Context context;
    private ArrayList<StickerModel> stickerModelArrayList;
    private String userid;

    public StickerAdapter(Context context, ArrayList<StickerModel> stickerModelArrayList, String userid) {
        this.context = context;
        this.stickerModelArrayList = stickerModelArrayList;
        this.userid = userid;
    }

    @NonNull
    @Override
    public StickerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_sticker, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Glide.with(context)
                .load(stickerModelArrayList.get(position).getImageurl())
                .into(holder.imageView);

        holder.textView.setText(stickerModelArrayList.get(position).getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, NewActivity.class);
                intent.putExtra("stickerID@#", stickerModelArrayList.get(position).getImageurl());
                intent.putExtra("name@#", stickerModelArrayList.get(position).getName());
                intent.putExtra("userid", userid);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return stickerModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageviewLS);
            textView = itemView.findViewById(R.id.textviewLS);
        }
    }
}
