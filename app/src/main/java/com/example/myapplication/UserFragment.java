package com.example.myapplication;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class UserFragment extends Fragment {

    private TextView username, heyuser, phone, email;
    private FirebaseAuth firebaseAuth;
    private Button edit;
    private FirebaseFirestore firebaseFirestore;
    private String usrid;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.myaccount, container, false);
        username = v.findViewById(R.id.textView223);
        heyuser = v.findViewById(R.id.heyuser);
        phone = v.findViewById(R.id.textView22322);
        email = v.findViewById(R.id.textView2232);
        edit = v.findViewById(R.id.edit);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        usrid = firebaseAuth.getCurrentUser().getUid();

        final DocumentReference documentReference = firebaseFirestore.collection("users").document(usrid);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                phone.setText(value.getString("phone"));
                email.setText(value.getString("email"));
                username.setText(value.getString("username"));
                heyuser.setText("Hey, " + value.getString("username"));
            }
        });


        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),Edit.class));
            }
        });


        return v;
    }
}
