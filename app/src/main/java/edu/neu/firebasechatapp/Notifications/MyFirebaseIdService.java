package edu.neu.firebasechatapp.Notifications;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Objects;

import edu.neu.firebasechatapp.MessageActivity;

public class MyFirebaseIdService extends FirebaseMessagingService {

    @Override
    public void onNewToken(String token) {
        updateToken(token);
    }
    private void updateToken(String refreshToken) {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        Token token = new Token(refreshToken);
        FirebaseDatabase.getInstance().getReference("Tokens")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(token);
    }

}
