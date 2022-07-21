package edu.neu.firebasechatapp.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;

import edu.neu.firebasechatapp.Adapter.UserAdapter;
import edu.neu.firebasechatapp.Model.ChatModel;
import edu.neu.firebasechatapp.Model.UserModel;
import edu.neu.firebasechatapp.Notifications.Token;
import edu.neu.firebasechatapp.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChatFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChatFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private ArrayList<UserModel> users;
    private FirebaseUser firebaseUser;
    private DatabaseReference reference;
    private ArrayList<String> userList;

    public ChatFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChatFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChatFragment newInstance(String param1, String param2) {
        ChatFragment fragment = new ChatFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_chat, container, false);

        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        recyclerView = view.findViewById(R.id.rvChat);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        userList = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("chatHistory");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userList.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ChatModel chatModel = snapshot.getValue(ChatModel.class);

                    if (chatModel.getSender().equals(firebaseUser.getUid())) {
                        userList.add(chatModel.getReceiver());
                    }

                    if (chatModel.getReceiver().equals(firebaseUser.getUid())) {
                        userList.add(chatModel.getSender());
                    }

                    readChats();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        updateToken(FirebaseMessaging.getInstance().getToken().toString());

        return view;
    }

    private void readChats() {
        users = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference("users");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                users.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    UserModel user = snapshot.getValue(UserModel.class);

                    // Display 1 user from chat
                    for (String id : userList) {
                        if (user.getUserid().equals(id)) {
                            if (users.size() != 0) {
                                for (UserModel user1 : users) {
                                    if (!user.getUserid().equals(user1.getUserid())) {
                                        users.add(user);
                                    }
                                }
                            } else {
                                users.add(user);
                            }
                        }
                    }
                }

                userAdapter = new UserAdapter(getContext(), users);
                recyclerView.setAdapter(userAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void updateToken(String token) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tokens");
        Token token1 = new Token(token);
        reference.child (firebaseUser.getUid()).setValue(token1);
    }

}