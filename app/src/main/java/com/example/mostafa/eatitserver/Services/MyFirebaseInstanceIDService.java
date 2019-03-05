package com.example.mostafa.eatitserver.Services;

import com.example.mostafa.eatitserver.Common.Common;
import com.example.mostafa.eatitserver.Model.Token;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by mostafa on 1/30/2018.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

        updateTokenToServer(refreshedToken);
    }
    private void updateTokenToServer(String refreshedToken) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("Tokens");
        Token token= new Token(refreshedToken,true);//true because this from server to client
        reference.child(Common.currentUser.getPhone()).setValue(token);

    }
}
