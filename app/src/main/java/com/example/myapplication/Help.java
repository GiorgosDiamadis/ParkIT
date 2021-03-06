package com.example.myapplication;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Help extends Fragment {

    private FirebaseFirestore fstore;
    private EditText helpsend;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.help, container, false);
        Button add = v.findViewById(R.id.submit);
        helpsend = v.findViewById(R.id.help);

        fstore = FirebaseFirestore.getInstance();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                String usrid = firebaseAuth.getCurrentUser().getUid();
                String i = helpsend.getText().toString();
                DocumentReference ref = fstore.collection("help").document();
                Map<String, Object> map = new HashMap<>();
                map.put("help", i);
                map.put("userID", usrid);

                ref.set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @SuppressLint("ShowToast")
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getActivity(), "We got your message", Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });

        return v;
    }
}
