package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Edit extends AppCompatActivity {


    private EditText phone, email, usern;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();


        usern = findViewById(R.id.username);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        Button change = findViewById(R.id.change);
        final FirebaseUser user = firebaseAuth.getCurrentUser();
//
//        usern.setText(user.getDisplayName());
//        email.setText(user.getEmail());
//        phone.setText(user.getPhoneNumber());

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                user.updateEmail(email.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        DocumentReference documentReference = firebaseFirestore.collection("users").document(user.getUid());
                        Map<String, Object> edited = new HashMap<>();
                        edited.put("email", email.getText().toString());
                        edited.put("username", usern.getText().toString());
                        edited.put("phone", phone.getText().toString());
                        documentReference.update(edited);
                        Toast.makeText(Edit.this, "Profile Edited!", Toast.LENGTH_LONG).show();

                    }
                });
            }
        });

    }
}