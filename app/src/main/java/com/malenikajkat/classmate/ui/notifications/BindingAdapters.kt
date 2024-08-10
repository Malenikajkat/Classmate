package com.malenikajkat.classmate.ui.notifications
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.malenikajkat.classmate.ui.notifications.UserInfo
import com.malenikajkat.classmate.ui.notifications.UsersAdapter

@BindingAdapter("notificationsList")
fun bindNotificationsList(recyclerView: RecyclerView, usersInfoList: List<UserInfo>?) {
    usersInfoList?.let {
        val adapter = recyclerView.adapter as UsersAdapter
        adapter.submitList(usersInfoList)
    }
}