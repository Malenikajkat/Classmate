package com.malenikajkat.classmate.ui.notifications
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.malenikajkat.classmate.databinding.ListItemNotificationBinding
import com.malenikajkat.classmate.data.models.UserInfo

class NotificationAdapter: ListAdapter<UserInfo, NotificationAdapter.ViewHolder>(UserInfoDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListItemNotificationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(private val binding: ListItemNotificationBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: UserInfo) {
            binding.userInfo = item
            binding.executePendingBindings()
        }
    }
}

class UserInfoDiffCallback : DiffUtil.ItemCallback<UserInfo>() {
    override fun areItemsTheSame(oldItem: UserInfo, newItem: UserInfo): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: UserInfo, newItem: UserInfo): Boolean {
        return oldItem == newItem
    }
}
