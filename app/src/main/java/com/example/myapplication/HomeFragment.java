package com.example.myapplication;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment implements View.OnClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.homefragment, container, false);

        Button b = (Button) v.findViewById(R.id.add_parking_spot);
        Button s = (Button) v.findViewById(R.id.search_parking);
        b.setOnClickListener(this);
        s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),SearchParkingSpot.class));

            }
        });
        return v;
    }

    @Override
    public void onClick(View v) {
        startActivity(new Intent(getActivity(), AddParkingSpot.class));
    }



}
