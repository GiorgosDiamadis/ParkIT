package com.example.myapplication;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class AddParkingSpot extends AppCompatActivity {
    private EditText city, street, number, area, cost, available;
    private Button camera;
    private Button choose;
    private static final int CAMERA_REQUEST = 1888;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    private static final int PICK_IMAGE_REQUEST = 234;
    private FirebaseFirestore fstore;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    protected String _path;
    private Uri imgURI;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_add_parking_spot);
        Button add = findViewById(R.id.add_to_database);
        camera = findViewById(R.id.photo);
        choose = findViewById(R.id.choose);
        city = findViewById(R.id.city);
        street = findViewById(R.id.street);
        number = findViewById(R.id.number);
        area = findViewById(R.id.area);
        cost = findViewById(R.id.cost_hour);
        available = findViewById(R.id.available_spots);
        fstore = FirebaseFirestore.getInstance();

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String c = city.getText().toString().trim();
                String s = street.getText().toString().trim();
                String n = number.getText().toString().trim();
                String a = area.getText().toString().trim();
                String co = cost.getText().toString().trim();
                String av = available.getText().toString().trim();

                DocumentReference ref = fstore.collection("parking").document();
                Map<String, Object> parkings = new HashMap<>();
                parkings.put("city", c);
                parkings.put("street", s);
                parkings.put("number", n);
                parkings.put("area", a);
                parkings.put("cost", co);
                parkings.put("available spots", av);

                StorageReference reference = storageReference.child("images/img.jpg");
                reference.putFile(imgURI).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(getApplicationContext(), "Image uploaded", Toast.LENGTH_LONG).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();

                    }
                });

                ref.set(parkings).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(AddParkingSpot.this, "Parking Added", Toast.LENGTH_LONG).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddParkingSpot.this, "Failure!!", Toast.LENGTH_LONG).show();

                    }
                });
            }
        });

        camera.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                } else {
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                }
            }
        });

        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select an image"), PICK_IMAGE_REQUEST);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            } else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {

            Bitmap photo = (Bitmap) data.getExtras().get("data");
            MediaStore.Images.Media.insertImage(getContentResolver(), photo, "img", "yourDescription");

        } else if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK) {
            imgURI = data.getData();
            Toast.makeText(this, "Image selected", Toast.LENGTH_LONG).show();

        }
    }


}