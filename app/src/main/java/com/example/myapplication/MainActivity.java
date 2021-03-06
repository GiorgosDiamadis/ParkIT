package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    FirebaseAuth fauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fauth = FirebaseAuth.getInstance();
        if (fauth.getCurrentUser() == null) {
            startActivity(new Intent(getApplicationContext(),Login.class));
            finish();
        }
        setContentView(R.layout.activity_main);
        drawerLayout = findViewById(R.id.nav_view);

        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
        getSupportFragmentManager().beginTransaction().replace(R.id.Fragment_container,new HomeFragment());
        Toolbar toolbar = findViewById(R.id.toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_draw_open, R.string.navigation_draw_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        if(savedInstanceState==null)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.Fragment_container,new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.home);
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()) {
            case R.id.home:
                getSupportFragmentManager().beginTransaction().replace(R.id.Fragment_container, new HomeFragment()).commit();
                break;
            case R.id.my_acc:
                getSupportFragmentManager().beginTransaction().replace(R.id.Fragment_container, new UserFragment()).commit();
                break;
            case R.id.payments:
                getSupportFragmentManager().beginTransaction().replace(R.id.Fragment_container, new PaymentFragment()).commit();
                break;

            case R.id.parking_activity:
                getSupportFragmentManager().beginTransaction().replace(R.id.Fragment_container,new ParkingActivity()).commit();
                break;


            case R.id.report_issue:
                getSupportFragmentManager().beginTransaction().replace(R.id.Fragment_container, new ReportIssue()).commit();
                break;

            case R.id.help:
                getSupportFragmentManager().beginTransaction().replace(R.id.Fragment_container, new Help()).commit();
                break;

            case R.id.about:
                getSupportFragmentManager().beginTransaction().replace(R.id.Fragment_container, new About()).commit();
                break;

            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), Login.class));
                finish();
        }

        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}