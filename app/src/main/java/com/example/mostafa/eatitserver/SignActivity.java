package com.example.mostafa.eatitserver;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mostafa.eatitserver.Common.Common;
import com.example.mostafa.eatitserver.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignActivity extends AppCompatActivity {
    EditText editPhone, editPassword;
    Button btnSignIn;
    DatabaseReference users;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        btnSignIn = (Button) findViewById(R.id.btnSign);
        editPhone = (EditText) findViewById(R.id.editPhone);
        editPassword = (EditText) findViewById(R.id.editPassword);
        //init FireBase
        database = FirebaseDatabase.getInstance();
        users = database.getReference("user");//name of json in firebase
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Common.isConnected(getBaseContext()))
                    signInUser(editPhone.getText().toString(), editPassword.getText().toString());
                else {
                    Toast.makeText(SignActivity.this, "Please Check Your Internet", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
    }

    private void signInUser(String phone, String password) {
        final ProgressDialog dialog = new ProgressDialog(SignActivity.this);
        dialog.setMessage("Loading...");
        dialog.show();
        final String localPhone = phone;
        final String localPassword = password;
        users.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(localPhone).exists()) {
                    dialog.dismiss();
                    User user = dataSnapshot.child(localPhone).getValue(User.class);
                    user.setPhone(localPhone);
                    if (Boolean.parseBoolean(user.getIsStaff())) {
                        if (user.getPassword().equals(localPassword)) {
                            //login ok
                            Intent intent = new Intent(SignActivity.this, Home.class);
                            Common.currentUser = user;
                            startActivity(intent);
                            finish();

                        } else
                            Toast.makeText(SignActivity.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(SignActivity.this, "Wrong Admin Account", Toast.LENGTH_SHORT).show();

                } else {
                    dialog.dismiss();
                    Toast.makeText(SignActivity.this, "user Not exists", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
