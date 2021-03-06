package com.example.myapplication;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PaymentFragment extends Fragment {

    private ListView listView;
    private Button add;
    private TextView c1, c2, c3;
    private EditText card;
    private FirebaseFirestore fstore;
    private FirebaseAuth fauth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.paymentfragment, container, false);
        fstore = FirebaseFirestore.getInstance();
        add = v.findViewById(R.id.button2);
        c1 = v.findViewById(R.id.cardnumber1);
        c2 = v.findViewById(R.id.cardnumber2);
        card = v.findViewById(R.id.textView19);
        c3 = v.findViewById(R.id.cardnumber3);

        final ArrayList<TextView> l = new ArrayList<>();
        l.add(c1);
        l.add(c2);
        l.add(c3);

        fstore.collection("cards").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    int i = 0;

                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if(i == 3){
                            break;
                        }
                        String number = document.get("number") + "";

                        if(l.get(i).getText().toString().equals("")){
                            l.get(i++).setText(number);
                        }else{
                            i++;
                        }

                    }
                }
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                fstore.collection("cards")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    int count = 0;
                                    for (DocumentSnapshot document : task.getResult()) {
                                        count++;
                                    }

                                    if(count < 3){
                                        DocumentReference ref = fstore.collection("cards").document();
                                        String i = card.getText().toString();

                                        Map<String, Object> map = new HashMap<>();
                                        map.put("number", i);

                                        ref.set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @SuppressLint("ShowToast")
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(getActivity(), "Card added.Reload page to see it!", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }else{
                                        Toast.makeText(getActivity(), "Cant add more than 3 cards", Toast.LENGTH_SHORT).show();

                                    }
                                } else {
                                }
                            }
                        });


            }
        });

        return v;
    }
}
