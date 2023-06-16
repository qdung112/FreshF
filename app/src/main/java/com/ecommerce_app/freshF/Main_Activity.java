package com.ecommerce_app.freshF;


import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.ecommerce_app.freshF.Fragment.Cart_Fragment;
import com.ecommerce_app.freshF.Fragment.Home_Fragment;
import com.ecommerce_app.freshF.Fragment.Order_Fragment;
import com.ecommerce_app.freshF.Fragment.Profile_Fragment;
import com.google.android.material.navigation.NavigationView;

public class Main_Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private  Toolbar toolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        toolbar = findViewById(R.id.tool_bar);
        toolbar.setTitle("Home");
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new Home_Fragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);

        }

       /* View decorView = getWindow().getDecorView();
        decorView.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(Main_Activity.this, Login_Activity.class);
                startActivity(i);
                finish();
            }
        }, 3000);*/
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Home_Fragment()).commit();
                        toolbar.setTitle("Home");
                        drawerLayout.findViewById(R.id.nav_home);
                break;
            case R.id.nav_product:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Cart_Fragment()).commit();
                        toolbar.setTitle("Cart");
                break;
            case R.id.nav_order:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Order_Fragment()).commit();
                        toolbar.setTitle("Order");
                break;
            case R.id.nav_profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Profile_Fragment()).commit();
                        toolbar.setTitle("Profile");
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
