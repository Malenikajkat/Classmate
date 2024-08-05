package com.malenikajkat.classmate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private Button buttonLogout;
    private AppBarConfiguration appBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_home);

        buttonLogout = findViewById(R.id.button_logout);

        // Adding logout functionality
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                Toast.makeText(MainActivity.this, "Successfully Logged out", Toast.LENGTH_SHORT).show();

                finish();
            }
        });

        // Adding navigation
        DrawerLayout drawerLayout = findViewById(R.id.drawerLayout);
        NavigationView navView = findViewById(R.id.navView);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);

        // Passing each menu ID as a set of Ids because each menu should be considered as top level destinations.
        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_attendance, R.id.nav_classes, R.id.nav_chat, R.id.nav_calendar,
        R.id.nav_diary, R.id.nav_groups, R.id.nav_lestures, R.id.nav_schedule)
        .setDrawerLayout(drawerLayout)
        .build();

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private BottomNavigationView navView;
    private ProgressBar mainProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainToolbar = findViewById(R.id.main_toolbar);
        navView = findViewById(R.id.nav_view);
        mainProgressBar = findViewById(R.id.main_progressBar);

        BadgeDrawable notificationsBadge =
        navView.getOrCreateBadge(R.id.navigation_notifications).setVisible(false);

        setSupportActionBar(mainToolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
        switch (destination.getId()) {
            case R.id.profileFragment:
            case R.id.chatFragment:
            case R.id.startFragment:
            case R.id.loginFragment:
            case R.id.createAccountFragment:
            navView.setVisibility(View.GONE);
            break;
            default:
            navView.setVisibility(View.VISIBLE);
            break;
        }
        showGlobalProgressBar(false);
        View currentFocus = getCurrentFocus();
        if (currentFocus != null) {
            currentFocus.getRootView().forceHideKeyboard();
        }
    });

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
            R.id.navigation_chats, R.id.navigation_notifications, R.id.navigation_users,
            R.id.navigation_settings, R.id.startFragment)
            .build();

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }

    @Override
    protected void onPause() {
        super.onPause();
        FirebaseDatabase.getInstance().goOffline();
    }

    @Override
    protected void onResume() {
        super.onResume();
        FirebaseDatabase.getInstance().goOnline();
        setupViewModelObservers();
    }

    private void setupViewModelObservers() {
        viewModel.getUserNotificationsList().observe(this, notifications -> {
        if (notifications.size() > 0) {
            notificationsBadge.setNumber(notifications.size());
            notificationsBadge.setVisible(true);
        } else {
            notificationsBadge.setVisible(false);
        }
    });
    }

    private void showGlobalProgressBar(boolean show) {
        mainProgressBar.setVisibility(show ? View.VISIBLE : View.GONE);
    }
}