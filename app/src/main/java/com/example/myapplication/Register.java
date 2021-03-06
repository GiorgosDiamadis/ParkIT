package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    EditText username, password, email, phone;
    Button login, register,already;
    FirebaseAuth fauth;
    FirebaseFirestore fstore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        login = findViewById(R.id.login);
        register = findViewById(R.id.register);
        already = findViewById(R.id.already);

        fauth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();

        if(fauth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String mail = email.getText().toString().trim();
                String pass = password.getText().toString().trim();
                final String ph = phone.getText().toString().trim();
                final String user = username.getText().toString().trim();


                if (TextUtils.isEmpty(mail)) {
                    email.setError("Email is required");
                    return;
                }

                if (TextUtils.isEmpty(pass)) {
                    password.setError("Password is required");
                    return;
                }

                if (pass.length() < 6) {
                    password.setError("Password must have at least 6 characters");
                    return;
                }

                fauth.createUserWithEmailAndPassword(mail, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            final String userID = fauth.getCurrentUser().getUid();
                            DocumentReference documentReference = fstore.collection("users").document(userID);
                            Map<String,Object> map = new HashMap<>();

                            map.put("username",user);
                            map.put("phone",ph);
                            map.put("email",mail);

                            documentReference.set(map);



                            Toast.makeText(Register.this, "User Created", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        } else {
                            Toast.makeText(Register.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });




            }
        });

        already.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Login.class));

            }
        });
    }
}