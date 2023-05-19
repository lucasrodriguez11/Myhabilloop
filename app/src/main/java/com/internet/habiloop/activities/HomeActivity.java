package com.internet.habiloop.activities;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.internet.habiloop.R;


public class HomeActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Obtén una referencia al DrawerLayout y al NavigationView
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        Toolbar toolbar = findViewById(R.id.toolbar);

        /*

         navigationView.setNavigationItemSelectedListener(item -> {
            // Aquí puedes manejar la selección de elementos del menú
            switch (item.getItemId()) {
                case R.id.nav_home:
                    // Acción para la opción 1
                    return true;
                case R.id.nav_profile:
                    // Acción para la opción 2
                    return true;
                default:
                    return false;
            }
        });
        */

        // Maneja el clic en el ícono de hamburguesa
        toolbar.setNavigationOnClickListener(view -> {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START); // Cierra el panel lateral si está abierto
            } else {
                drawerLayout.openDrawer(GravityCompat.START); // Abre el panel lateral si está cerrado
            }
        });

        // Configura el ActionBarDrawerToggle para sincronizar el estado del DrawerLayout y el ícono de hamburguesa
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START); // Cierra el panel lateral si está abierto
            } else {
                drawerLayout.openDrawer(GravityCompat.START); // Abre el panel lateral si está cerrado
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}