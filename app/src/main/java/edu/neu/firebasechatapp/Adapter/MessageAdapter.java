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

import java.util.ArrayList;

import edu.neu.firebasechatapp.Model.ChatModel;
import edu.neu.firebasechatapp.Model.StickerModel;
import edu.neu.firebasechatapp.NewActivity;
import edu.neu.firebasechatapp.R;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_RIGHT = 1;

    private FirebaseUser firebaseUser;
    private Context context;
    private ArrayList<ChatModel> chatModelArrayList;

    public MessageAdapter(Context context, ArrayList<ChatModel> chatModelArrayList) {
        this.context = context;
        this.chatModelArrayList = chatModelArrayList;
    }

    @NonNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == MSG_TYPE_RIGHT) {
            View view = LayoutInflater.from(context)
                    .inflate(R.layout.chat_item_right, parent, false);
            return new MessageAdapter.ViewHolder(view);
        } else {
            View view = LayoutInflater.from(context)
                    .inflate(R.layout.chat_item_left, parent, false);
            return new MessageAdapter.ViewHolder(view);
        }

//        View view = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.layout_sticker, parent, false);
//        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Glide.with(context)
                .load(chatModelArrayList.get(position).getStickerId())
                .into(holder.imageView);

        holder.textView.setText(chatModelArrayList.get(position).getStickerName());

    }

    @Override
    public int getItemCount() {
        return chatModelArrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (chatModelArrayList.get(position).getSender().equals(firebaseUser.getUid())) {
            return MSG_TYPE_RIGHT;
        } else {
            return MSG_TYPE_LEFT;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.fullImageViewLR);
            textView = itemView.findViewById(R.id.fullTextViewLR);
        }
    }
}
