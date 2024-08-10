package com.malenikajkat.classmate.ui.notifications

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.malenikajkat.classmate.ui.notifications.NotificationsViewModel;

public class NotificationsActivity extends AppCompatActivity {

    private NotificationsViewModel viewModel;
    private RecyclerView usersRecyclerView;
    private UsersAdapter usersAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        viewModel = new ViewModelProvider(this).get(NotificationsViewModel.class);

        usersRecyclerView = findViewById(R.id.usersRecyclerView);
        usersRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        viewModel.getUsersInfoList().observe(this, usersInfoList -> {
        if (usersAdapter == null) {
            usersAdapter = new UsersAdapter(usersInfoList);
            usersRecyclerView.setAdapter(usersAdapter);
        } else {
            usersAdapter.notifyDataSetChanged();
        }
    });
    }
}