package com.malenikajkat.classmate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button buttonLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_home);

        buttonLogout = findViewById(R.id.button_logout);

        // Добавляем логин
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                Toast.makeText(MainActivity.this, "Successfully Logged out", Toast.LENGTH_SHORT).show();

                finish();
            }
        });

        // Добавляем навигацию
        val drawerLayout: DrawerLayout = findViewById(R.id.drawerLayout);
        val navView: NavigationView = findViewById(R.id.navView);
        val navController = findNavController(R.id.nav_host_fragment_content_main);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_attendance, R.id.nav_classes,  R.id.nav_chat,  R.id.nav_calendar,  R.id.nav_diary,  R.id.nav_groups,  R.id.nav_lestures,  R.id.nav_schedule
            ), drawerLayout
        );
        setupActionBarWithNavController(navController, appBarConfiguration);
        navView.setupWithNavController(navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = findNavController(R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}