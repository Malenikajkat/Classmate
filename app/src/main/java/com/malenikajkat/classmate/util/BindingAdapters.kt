package com.malenikajkat.classmate.util
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.malenikajkat.classmate.ui.notifications.NotificationAdapter
import com.malenikajkat.classmate.data.models.UserInfo

@BindingAdapter("notificationsList")
fun setNotificationsList(recyclerView: RecyclerView, usersInfoList: List<UserInfo>?) {
    usersInfoList?.let {
        // Assuming we have an adapter for the recycler view
        (recyclerView.adapter as? NotificationAdapter)?.submitList(usersInfoList)
    }
}